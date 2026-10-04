package embinmc.mod.poosmp.item.component;

import embinmc.mod.poosmp.PooSMPMod;
import net.fabricmc.fabric.api.event.player.ItemEvents;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
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
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.hurtingprojectile.windcharge.WindCharge;
import net.minecraft.world.item.EitherHolder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.levelgen.Heightmap;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Optional;
import java.util.Set;

public class ItemUseComponents {
    public static final Identifier WARP_STICK = PooSMPMod.id("warp_stick");
    public static final Identifier POOP_STICK = PooSMPMod.id("poop_stick");
    public static final Identifier WIND_STICK = PooSMPMod.id("wind_stick");
    public static final Identifier BIOME_STICK = PooSMPMod.id("biome_stick");
    public static final Identifier MOB_STICK = PooSMPMod.id("mob_stick");
    public static final Identifier ZAP_STICK = PooSMPMod.id("zap_stick");
    public static final Identifier MAGIC_DEVICE = PooSMPMod.id("magic_device");
    public static final Identifier SERVER_SAYS_WHAT_STICK = PooSMPMod.id("server_says_what_stick");

    private ItemUseComponents() {
        throw new IllegalStateException("PooSMP: Cannot create instance of ItemUseComponents!");
    }

    public static void register() {
        ItemEvents.USE.register(WARP_STICK, require(PooComponents.WARP_DIMENSION, ItemUseComponents::warpStick));
        ItemEvents.USE.register(WIND_STICK, require(PooComponents.WIND_SHOOTER, ItemUseComponents::windStick));
        ItemEvents.USE.register(SERVER_SAYS_WHAT_STICK, require(PooComponents.SERVER_SAYS_WHAT, ItemUseComponents::serverSaysWhatStick));
    }

    private record ConditionalCallback(DataComponentType<?> component, ItemEvents.UseCallback callback) implements ItemEvents.UseCallback {
        @Override
        public @Nullable InteractionResult use(@NonNull Level level, Player player, @NonNull InteractionHand interactionHand) {
            ItemStack itemStack = player.getItemInHand(interactionHand);
            if (itemStack.has(component))
                return callback.use(level, player, interactionHand);
            return InteractionResult.PASS;
        }
    }

    private static <T> ItemEvents.UseCallback require(DataComponentType<T> component, ItemEvents.UseCallback callback) {
        return new ConditionalCallback(component, callback);
    }

    private static InteractionResult warpStick(Level level, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        player.getCooldowns().addCooldown(itemStack, 160);
        if (!(level instanceof ServerLevel currentLevel) || !(player instanceof ServerPlayer serverPlayer))
            return InteractionResult.SUCCESS;
        double posX = player.getX();
        double posZ = player.getZ();
        EitherHolder<Level> fallback = new EitherHolder<>(PooSMPMod.HYRULE);
        Optional<Holder<Level>> optionalDim = itemStack.getOrDefault(PooComponents.WARP_DIMENSION, fallback).unwrap(level.registryAccess());
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
}
