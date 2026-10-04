package embinmc.mod.poosmp.event;

import embinmc.mod.poosmp.PooSMPMod;
import embinmc.mod.poosmp.item.component.PooComponents;
import net.fabricmc.fabric.api.client.rendering.v1.TooltipComponentCallback;
import net.fabricmc.fabric.api.event.player.ItemEvents;
import net.fabricmc.fabric.api.item.v1.ComponentTooltipAppenderRegistry;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.SectionPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.EitherHolder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;

import java.util.Optional;
import java.util.Set;

public class PooSMPEvents {
    public static void register() {
        PooItemEvents.INVENTORY_TICK_PRE_ITEM.register(PooSMPMod.id("creative_snitch"),
            (itemStack, serverLevel, entity, slot) -> {
                if (!itemStack.has(PooComponents.FROM_CREATIVE))
                    return true;
                if (itemStack.getOrDefault(PooComponents.FROM_CREATIVE, false)) {
                    MinecraftServer server = serverLevel.getServer();
                    Component playerName = entity.getName();
                    Component text = Component.translatable("poosmp.creative_snitch", playerName, itemStack.getStyledHoverName());
                    server.getPlayerList().broadcastSystemMessage(text, false);
                    itemStack.set(PooComponents.FROM_CREATIVE, false);
                }
                return true;
            }
        );

        ItemEvents.USE.register(PooSMPMod.id("warp_stick"), (level, player, interactionHand) -> {
            ItemStack itemStack = player.getItemInHand(interactionHand);
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
        });

        LootTableEvents.MODIFY_DROPS.register(PooSMPMod.id("dimworld_stick"), (holder, lootContext, list) -> {
            if (holder.is(BuiltInLootTables.SIMPLE_DUNGEON) || holder.is(BuiltInLootTables.ANCIENT_CITY)) {
                if (lootContext.getRandom().nextBoolean()) {

                }
            }
        });

        ComponentTooltipAppenderRegistry.addAfter(DataComponents.JUKEBOX_PLAYABLE, PooComponents.REQUESTED_DISC);
    }
}
