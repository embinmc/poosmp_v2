package embinmc.mod.poosmp.event;

import embinmc.mod.poosmp.PooSMPMod;
import embinmc.mod.poosmp.item.component.ItemUseComponents;
import embinmc.mod.poosmp.item.component.PooComponents;
import embinmc.mod.poosmp.item.component.WeddingRing;
import embinmc.mod.poosmp.upgrade.Upgrade;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientEntityEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.item.v1.ComponentTooltipAppenderRegistry;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.fabric.api.networking.v1.EntityTrackingEvents;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;

import java.util.Objects;

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

        /// [Upgrade#onRespawn(int, ServerPlayer)]
        ServerPlayerEvents.AFTER_RESPAWN.register((oldPlayer, newPlayer, alive) -> {
            Upgrade.withPurchasedUpgrades(newPlayer, (upgradeHolder, bought) -> {
                Upgrade upgrade = upgradeHolder.value();
                upgrade.onRespawn(bought, newPlayer);
            });
        });

        /// [Upgrade#onTick(int, ServerPlayer)]
        embin.strangeitems.event.ServerPlayerEvents.ON_TICK.register(serverPlayer -> {
            Upgrade.withPurchasedUpgrades(serverPlayer, (upgradeHolder, bought) -> {
                Upgrade upgrade = upgradeHolder.value();
                upgrade.onTick(bought, serverPlayer);
            });
            return InteractionResult.PASS;
        });

        /// [Upgrade#onHitEntity(int, ServerPlayer, LivingEntity)]
        ServerLivingEntityEvents.ALLOW_DAMAGE.register((entity, source, amount) -> {
            if (source.getDirectEntity() instanceof ServerPlayer player) {
                Upgrade.withPurchasedUpgrades(player, (upgradeHolder, bought) -> {
                    Upgrade upgrade = upgradeHolder.value();
                    upgrade.onHitEntity(bought, player, entity);
                });
            }
            return true;
        });

        /// [Upgrade#onDeath(int, ServerPlayer)]
        ServerLivingEntityEvents.ALLOW_DEATH.register((entity, source, amount) -> {
            if (entity instanceof ServerPlayer player) {
                Upgrade.withPurchasedUpgrades(player, (upgradeHolder, bought) -> {
                    Upgrade upgrade = upgradeHolder.value();
                    upgrade.onDeath(bought, player);
                });
            }
            return true;
        });

        /// [Upgrade#onJoin(int, ServerPlayer)]
        ServerPlayerEvents.JOIN.register(player -> {
            Upgrade.withPurchasedUpgrades(player, (upgradeHolder, bought) -> {
                Upgrade upgrade = upgradeHolder.value();
                upgrade.onJoin(bought, player);
            });
        });
    }
}
