package embinmc.mod.poosmp.item.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import embinmc.mod.poosmp.PooSMPMod;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.server.ServerScoreboard;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;
import net.minecraft.world.level.Level;
import net.minecraft.world.scores.PlayerTeam;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public record MobSummonerComponent(EntityType<?> entity, List<String> possibleNames) implements TooltipProvider {
    public static final Codec<MobSummonerComponent> CODEC = RecordCodecBuilder.create(msc -> msc.group(
            BuiltInRegistries.ENTITY_TYPE.byNameCodec().fieldOf("entity").forGetter(MobSummonerComponent::entity),
            Codec.string(1, 40).listOf(0, 50).fieldOf("possible_names").forGetter(MobSummonerComponent::possibleNames)
    ).apply(msc, MobSummonerComponent::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, MobSummonerComponent> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.registry(Registries.ENTITY_TYPE), MobSummonerComponent::entity,
            ByteBufCodecs.fromCodec(Codec.string(1, 40).listOf(0, 50)), MobSummonerComponent::possibleNames,
            MobSummonerComponent::new
    );
    public static final List<String> NAMES_DEFAULT = List.of("Goon", "Henchmen", "Minion");
    public static final List<String> NAMES_VILLAGER = List.of("Villager", "Worker");
    public static final List<String> NAMES_COW = List.of("Cow", "Ol' Betsey");

    @Override
    public void addToTooltip(Item.TooltipContext tooltipContext, Consumer<Component> consumer, TooltipFlag tooltipFlag, DataComponentGetter dataComponentGetter) {
        String entityId = this.entity().builtInRegistryHolder().getRegisteredName();
        Component valueComponent = Component.literal(entityId).withStyle(ChatFormatting.YELLOW);
        consumer.accept(Component.translatable("poosmp.mob_stick.entity", valueComponent).withStyle(ChatFormatting.GRAY));
    }

    public static InteractionResult onUse(Level level, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (level instanceof ServerLevel serverLevel && player instanceof ServerPlayer serverPlayer) {
            BlockPos pos = serverPlayer.blockPosition();
            MobSummonerComponent mobSummoner = Objects.requireNonNull(itemStack.get(PooComponents.MOB_SUMMONER));
            Entity entity = mobSummoner.entity().spawn(serverLevel, pos, EntitySpawnReason.SPAWN_ITEM_USE);
            if (entity == null) {
                PooSMPMod.LOGGER.error("Failed to create entity from MobSummoner item");
                return InteractionResult.FAIL;
            }
            if (entity instanceof Mob mob) {
                ItemStack offhandItem = new ItemStack(Items.TOTEM_OF_UNDYING);
                mob.setItemInHand(InteractionHand.OFF_HAND, offhandItem);
            }

            String customName;
            if (!mobSummoner.possibleNames().isEmpty()) {
                int random = player.getRandom().nextInt(0, mobSummoner.possibleNames().size());
                customName = mobSummoner.possibleNames().get(random);
            } else {
                customName = "Mob";
            }
            Component customNameText = serverPlayer.getDisplayName().copy().append("'s ").append(customName);
            entity.setCustomName(customNameText);
            entity.setCustomNameVisible(true);

            ServerScoreboard scoreboard = serverLevel.getServer().getScoreboard();
            PlayerTeam team = scoreboard.getPlayerTeam(serverPlayer.getStringUUID());
            if (team != null) {
                scoreboard.addPlayerToTeam(entity.getScoreboardName(), team);
            } else {
                PlayerTeam newTeam = scoreboard.addPlayerTeam(serverPlayer.getStringUUID());
                newTeam.setDisplayName(newTeam.getDisplayName());
                scoreboard.addPlayerToTeam(player.getScoreboardName(), newTeam);
                scoreboard.addPlayerToTeam(entity.getScoreboardName(), newTeam);
            }
        }
        player.awardStat(Stats.ITEM_USED.get(itemStack.getItem()));
        player.getCooldowns().addCooldown(itemStack, 20);
        return InteractionResult.SUCCESS;
    }
}
