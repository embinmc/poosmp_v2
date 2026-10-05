package embinmc.mod.poosmp.item.component;

import embinmc.mod.poosmp.PooSMPMod;
import embinmc.mod.poosmp.event.PooItemEvents;
import embinmc.mod.poosmp.misc.PooSMPGameRules;
import embinmc.mod.poosmp.misc.PooUtil;
import net.fabricmc.fabric.api.event.player.ItemEvents;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.SectionPos;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.entity.projectile.hurtingprojectile.windcharge.WindCharge;
import net.minecraft.world.item.EitherHolder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

import java.util.Optional;
import java.util.Set;

public class ItemUseComponents {
    public static final float MAGIC_DEVICE_EXPLOSION_POWER = 3.25f;
    public static final Identifier WARP_STICK = PooSMPMod.id("warp_stick");
    public static final Identifier POOP_STICK = PooSMPMod.id("poop_stick");
    public static final Identifier WIND_STICK = PooSMPMod.id("wind_stick");
    public static final Identifier BIOME_STICK = PooSMPMod.id("biome_stick");
    public static final Identifier MOB_STICK = PooSMPMod.id("mob_stick");
    public static final Identifier ZAP_STICK = PooSMPMod.id("zap_stick");
    public static final Identifier MAGIC_DEVICE = PooSMPMod.id("magic_device");
    public static final Identifier SERVER_SAYS_WHAT_STICK = PooSMPMod.id("server_says_what_stick");
    public static final Identifier BOOM_STICK = PooSMPMod.id("magic_device");

    private ItemUseComponents() {
        throw new IllegalStateException("PooSMP: Cannot create instance of ItemUseComponents!");
    }

    public static void register() {
        ItemEvents.USE.register(SERVER_SAYS_WHAT_STICK, require(PooComponents.SERVER_SAYS_WHAT, ItemUseComponents::serverSaysWhatStick));
        ItemEvents.USE.register(WARP_STICK,   require(PooComponents.DIMENSION_WARPER,   ItemUseComponents::warpStick));
        ItemEvents.USE.register(WIND_STICK,   require(PooComponents.WIND_SHOOTER,       ItemUseComponents::windStick));
        ItemEvents.USE.register(POOP_STICK,   require(PooComponents.POOP_STICK,         ItemUseComponents::poopStick));
        ItemEvents.USE.register(BIOME_STICK,  require(PooComponents.BIOME_TRANSFORMER,  BiomeStickComponent::onUse));
        ItemEvents.USE.register(BOOM_STICK,   require(PooComponents.BOOM_STICK,         ItemUseComponents::boomStick));
        ItemEvents.USE.register(MAGIC_DEVICE, require(PooComponents.EXPLOSION_SPAWNER,  ItemUseComponents::magicDevice));
        PooItemEvents.INTERACT_LIVING_ENTITY.register(MAGIC_DEVICE, require(PooComponents.EXPLOSION_SPAWNER, ItemUseComponents::magicDeviceInteract));
        ItemEvents.USE.register(ZAP_STICK,    require(PooComponents.LIGHTNING_SUMMONER, ItemUseComponents::zapStick));
        ItemEvents.USE.register(MOB_STICK,    require(PooComponents.MOB_SUMMONER,       MobSummonerComponent::onUse));
    }

    private record ConditionalCallback(DataComponentType<?> component, ItemEvents.UseCallback callback) implements ItemEvents.UseCallback {
        @Override
        public @Nullable InteractionResult use(Level level, Player player, InteractionHand interactionHand) {
            ItemStack itemStack = player.getItemInHand(interactionHand);
            if (itemStack.has(component))
                return callback.use(level, player, interactionHand);
            return null;
        }
    }

    private record ConditionalInteractionCallback(DataComponentType<?> component, PooItemEvents.LivingEntityInteraction callback) implements PooItemEvents.LivingEntityInteraction {
        @Override
        public @Nullable InteractionResult interact(ItemStack stack, Player player, LivingEntity entity, InteractionHand hand) {
            if (stack.has(this.component))
                return this.callback().interact(stack, player, entity, hand);
            return null;
        }
    }

    private static <T> ItemEvents.UseCallback require(DataComponentType<T> component, ItemEvents.UseCallback callback) {
        return new ConditionalCallback(component, callback);
    }

    private static <T> PooItemEvents.LivingEntityInteraction require(DataComponentType<T> component, PooItemEvents.LivingEntityInteraction callback) {
        return new ConditionalInteractionCallback(component, callback);
    }

    private static InteractionResult warpStick(Level level, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        player.getCooldowns().addCooldown(itemStack, 160);
        if (!(level instanceof ServerLevel currentLevel) || !(player instanceof ServerPlayer serverPlayer))
            return InteractionResult.SUCCESS;
        double posX = player.getX();
        double posZ = player.getZ();
        EitherHolder<Level> fallback = new EitherHolder<>(PooSMPMod.HYRULE);
        Optional<Holder<Level>> optionalDim = itemStack.getOrDefault(PooComponents.DIMENSION_WARPER, fallback).unwrap(level.registryAccess());
        if (optionalDim.isEmpty()) {
            serverPlayer.sendSystemMessage(Component.literal("Dimension not found").withStyle(ChatFormatting.RED));
            return InteractionResult.FAIL;
        }
        Holder<Level> dim = optionalDim.orElseThrow();
        ResourceKey<Level> dimKey = dim.unwrapKey().orElseThrow();
        ServerLevel targetLevel = currentLevel.getServer().getLevel(dimKey);
        if (targetLevel == null)
            throw new IllegalStateException("ServerLevel for dimension is null but holder for it is present");
        ServerLevel destination = currentLevel.dimension().equals(dimKey) ? currentLevel.getServer().overworld() : targetLevel;
        LevelChunk chunk = destination.getChunk(SectionPos.blockToSectionCoord(posX), SectionPos.blockToSectionCoord(posZ));
        destination.startTickingChunk(chunk);
        int h = destination.getHeight(Heightmap.Types.WORLD_SURFACE, serverPlayer.getBlockX(), serverPlayer.getBlockZ());
        serverPlayer.teleportTo(destination, posX, h, posZ, Set.of(), serverPlayer.getYRot(), serverPlayer.getXRot(), true);
        serverPlayer.addEffect(new MobEffectInstance(MobEffects.RESISTANCE, 8, 5, false, true));
        serverPlayer.awardStat(Stats.ITEM_USED.get(itemStack.getItem()));
        return InteractionResult.SUCCESS;
    }

    private static InteractionResult serverSaysWhatStick(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        String message = "What?";
        Component customItemName = stack.get(DataComponents.CUSTOM_NAME);
        if (customItemName != null) {
            message = customItemName.getString();
        }
        if (message.contains("testicle") || message.contains("rubbing") || message.contains("Rubbing")) {
            message = "cubey smells";
        }
        if (!level.isClientSide()) {
            MinecraftServer server = player.level().getServer();
            Commands commandManager = server.getCommands();
            CommandSourceStack commandSource = server.createCommandSourceStack();
            commandManager.performPrefixedCommand(commandSource, "say " + message);
        }
        return InteractionResult.SUCCESS;
    }

    private static InteractionResult windStick(Level level, Player player, InteractionHand hand) {
        ItemStack heldItem = player.getItemInHand(hand);
        if (level instanceof ServerLevel serverLevel && player instanceof ServerPlayer serverPlayer) {
            Projectile.spawnProjectileFromRotation((lvl, livingEntity, itemStack) -> {
                double x = serverPlayer.position().x();
                double y = serverPlayer.getEyePosition().y();
                double z = serverPlayer.position().z();
                return new WindCharge(serverPlayer, lvl, x, y, z);
            }, serverLevel, serverPlayer.getItemInHand(hand), serverPlayer, 0f, 2f, 0f);
            serverPlayer.awardStat(Stats.ITEM_USED.get(heldItem.getItem()));
        }
        return InteractionResult.SUCCESS;
    }

    private static InteractionResult poopStick(Level world, Player user, InteractionHand hand) {
        double player_x = user.getX();
        double player_y = user.getY();
        double player_z = user.getZ();
        if (Math.random() > 0.5) {
            world.playSound(null, player_x, player_y + 5, player_z, SoundEvents.HORSE_DEATH, SoundSource.PLAYERS);
            if (world instanceof ServerLevel serverLevel) {
                if (serverLevel.getGameRules().get(PooSMPGameRules.OUTLAW_EXPLOSIVE_GADGETS)) {
                    user.displayClientMessage(PooUtil.OUTLAWED_EXPLOSIVE_TEXT, true);
                } else {
                    serverLevel.explode(null, player_x, player_y, player_z, 5.0F, Level.ExplosionInteraction.NONE);
                }
            }
        } else {
            world.playSound(null, player_x, player_y, player_z, SoundEvents.GOAT_HORN_SOUND_VARIANTS.get(3).value(), SoundSource.PLAYERS);
        }
        user.awardStat(Stats.ITEM_USED.get(user.getItemInHand(hand).getItem()));
        return InteractionResult.SUCCESS;
    }

    private static InteractionResult boomStick(Level world, Player user, InteractionHand hand) {
        ItemStack itemStack = user.getItemInHand(hand);
        user.awardStat(Stats.ITEM_USED.get(itemStack.getItem()));
        user.getCooldowns().addCooldown(itemStack, 20);
        if (world instanceof ServerLevel serverLevel) {
            if (serverLevel.getGameRules().get(PooSMPGameRules.OUTLAW_EXPLOSIVE_GADGETS)) {
                user.displayClientMessage(PooUtil.OUTLAWED_EXPLOSIVE_TEXT, true);
                return InteractionResult.FAIL;
            }
            double player_x = user.getX();
            double player_z = user.getZ();
            double player_y;
            BlockPos pos = BlockPos.containing(player_x, user.getY() - 1, player_z);
            if (world.getBlockState(pos).isRedstoneConductor(world, pos)) {
                player_y = user.getY();
            } else {
                player_y = user.getY() - 1;
            }
            user.setInvulnerable(true);
            world.explode(null, player_x, player_y, player_z, 3.5F, Level.ExplosionInteraction.NONE);
            user.setInvulnerable(false);
        }
        return InteractionResult.SUCCESS;
    }

    private static InteractionResult magicDevice(Level world, Player user, InteractionHand hand) {
        ItemStack itemStack = user.getItemInHand(hand);
        user.awardStat(Stats.ITEM_USED.get(itemStack.getItem()));
        if (world instanceof ServerLevel serverLevel) {
            float explosionPower = MAGIC_DEVICE_EXPLOSION_POWER;
            if (serverLevel.getGameRules().get(PooSMPGameRules.OUTLAW_EXPLOSIVE_GADGETS)) {
                user.displayClientMessage(PooUtil.OUTLAWED_EXPLOSIVE_TEXT, true);
                return InteractionResult.FAIL;
            }
            HitResult hitResult = user.pick(20.0D, 0.0F, false);
            DamageSources damageSources = new DamageSources(world.registryAccess());
            ExplosionDamageCalculator eb = new ExplosionDamageCalculator();
            Vec3 size = new Vec3(1, 1, 1);
            Vec3 size2 = new Vec3(20, 20, 20);
            EntityHitResult entityHitResult = ProjectileUtil.getEntityHitResult(user, size, size2, user.getBoundingBox(), e -> !e.isSpectator() && e.isPickable(), 20);
            if (itemStack.has(DataComponents.CUSTOM_NAME)) {
                String victim_name = itemStack.get(DataComponents.CUSTOM_NAME).getString();
                ServerPlayer victim = PooUtil.getPlayerByName(victim_name, serverLevel);
                if (victim != null) {
                    world.explode(null, damageSources.explosion(null, null), eb, victim.getEyePosition(), explosionPower, false, Level.ExplosionInteraction.TRIGGER);
                } else {
                    user.displayClientMessage(Component.literal("No player with such name exists!").withStyle(ChatFormatting.YELLOW), true);
                }
            } else if (entityHitResult != null && entityHitResult.getEntity() != null) {
                Vec3 pos = entityHitResult.getEntity().getEyePosition();
                world.explode(null, damageSources.explosion(null, null), eb, pos, explosionPower, false, Level.ExplosionInteraction.TRIGGER);
            } else if (hitResult.getType() == HitResult.Type.BLOCK) {
                BlockPos blockPos = ((BlockHitResult) hitResult).getBlockPos();
                double x = blockPos.getX();
                double y = blockPos.getY() + 1.0D;
                double z = blockPos.getZ();
                world.explode(null, x, y, z, explosionPower, Level.ExplosionInteraction.TRIGGER);
            } else {
                Vec3 pos = hitResult.getLocation();
                world.explode(null, damageSources.explosion(null, null), eb, pos, explosionPower, false, Level.ExplosionInteraction.TRIGGER);
            }
            itemStack.hurtAndBreak(1, user, hand);
        }
        return InteractionResult.SUCCESS;
    }

    private static InteractionResult magicDeviceInteract(ItemStack stack, Player user, LivingEntity entity, InteractionHand hand) {
        Level world = user.level();
        if (!world.isClientSide()) {
            Vec3 pos = entity.getEyePosition();
            DamageSources damageSources = new DamageSources(world.registryAccess());
            ExplosionDamageCalculator eb = new ExplosionDamageCalculator();
            world.explode(null, damageSources.explosion(null, null), eb, pos, MAGIC_DEVICE_EXPLOSION_POWER, false, Level.ExplosionInteraction.TRIGGER);
            stack.hurtAndBreak(1, user, hand);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    private static InteractionResult zapStick(Level world, Player user, InteractionHand hand) {
        if (!world.isClientSide()) {
            Commands commandManager = world.getServer().getCommands();
            CommandSourceStack commandSource = world.getServer().createCommandSourceStack().withSuppressedOutput();
            String player_uuid = user.getStringUUID();
            commandManager.performPrefixedCommand(commandSource, "execute at " + player_uuid + " run summon minecraft:lightning_bolt");
        }
        user.awardStat(Stats.ITEM_USED.get(user.getItemInHand(hand).getItem()));
        user.getCooldowns().addCooldown(user.getItemInHand(hand), 10);
        return InteractionResult.SUCCESS;
    }
}
