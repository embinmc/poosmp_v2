package embinmc.mod.poosmp.event;

import embinmc.mod.poosmp.PooSMPMod;
import embinmc.mod.poosmp.item.component.ItemUseComponents;
import embinmc.mod.poosmp.item.component.PooComponents;
import embinmc.mod.poosmp.item.component.WeddingRing;
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
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;

import java.util.Objects;
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

        PooItemEvents.INVENTORY_TICK_PRE_ITEM.register(PooSMPMod.id("wedding_ring"),
                (itemStack, serverLevel, entity, slot) -> {
                    if (!itemStack.has(PooComponents.WEDDING_RING))
                        return true;
                    WeddingRing weddingRing = Objects.requireNonNull(itemStack.get(PooComponents.WEDDING_RING));
                    boolean isMarried = entity instanceof ServerPlayer player && weddingRing.isUserMarried(itemStack, player);
                    ItemAttributeModifiers attributes = isMarried ? weddingRing.marriedAttributes() : weddingRing.notMarriedAttributes();
                    if (!itemStack.getOrDefault(DataComponents.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.EMPTY).equals(attributes))
                        itemStack.set(DataComponents.ATTRIBUTE_MODIFIERS, attributes);
                    return true;
                }
        );

        LootTableEvents.MODIFY_DROPS.register(PooSMPMod.id("dimworld_stick"), (holder, lootContext, list) -> {
            if (holder.is(BuiltInLootTables.SIMPLE_DUNGEON) || holder.is(BuiltInLootTables.ANCIENT_CITY)) {
                if (lootContext.getRandom().nextBoolean()) {

                }
            }
        });

        ComponentTooltipAppenderRegistry.addAfter(DataComponents.JUKEBOX_PLAYABLE, PooComponents.REQUESTED_DISC);
        ComponentTooltipAppenderRegistry.addAfter(DataComponents.JUKEBOX_PLAYABLE, PooComponents.BIOME_TRANSFORMER);
        ComponentTooltipAppenderRegistry.addAfter(DataComponents.JUKEBOX_PLAYABLE, PooComponents.MOB_SUMMONER);
        ItemUseComponents.register();
    }
}
