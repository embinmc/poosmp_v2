package embinmc.mod.poosmp.upgrade;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import embinmc.mod.poosmp.misc.PooRegistries;
import embinmc.mod.poosmp.misc.PooSavedData;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.resources.RegistryFixedCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;

import java.util.Optional;
import java.util.function.BiConsumer;

public interface Upgrade {
    Codec<Upgrade> DIRECT_CODEC = PooRegistries.UPGRADE_TYPE.byNameCodec().dispatch(Upgrade::getType, UpgradeType::codec);


    /// @param amountPurchased Is the amount the player has bought AFTER buying.
    void onBuy(int amountPurchased, ServerPlayer player, ServerLevel level, MinecraftServer server);

    /// @param amountPurchased Is the amount the player had bought BEFORE selling.
    void onSell(int amountPurchased, ServerPlayer player, ServerLevel level, MinecraftServer server);

    void onRespawn(int amountPurchased, ServerPlayer player, ServerLevel level, MinecraftServer server);
    void onTick(int amountPurchased, ServerPlayer player, ServerLevel level, MinecraftServer server);
    default void onJoin(int amountPurchased, ServerPlayer player, ServerLevel level, MinecraftServer server) {}
    default void onDeath(int amountPurchased, ServerPlayer player, ServerLevel level, MinecraftServer server) {}
    default void onHitEntity(int amountPurchased, ServerPlayer player, LivingEntity hitEntity, ServerLevel level) {}

    Properties properties();
    UpgradeType<?> getType();

    default void onRespawn(int amountPurchased, ServerPlayer player) {
        this.onRespawn(amountPurchased, player, player.level(), player.level().getServer());
    }

    default void onBuy(int amountPurchased, ServerPlayer player) {
        this.onBuy(amountPurchased, player, player.level(), player.level().getServer());
    }

    default void onSell(int amountPurchased, ServerPlayer player) {
        this.onSell(amountPurchased, player, player.level(), player.level().getServer());
    }

    default void onJoin(int amountPurchased, ServerPlayer player) {
        this.onJoin(amountPurchased, player, player.level(), player.level().getServer());
    }

    default void onTick(int amountPurchased, ServerPlayer player) {
        this.onTick(amountPurchased, player, player.level(), player.level().getServer());
    }

    default void onDeath(int amountPurchased, ServerPlayer player) {
        this.onDeath(amountPurchased, player, player.level(), player.level().getServer());
    }

    default void onHitEntity(int amountPurchased, ServerPlayer player, LivingEntity hitEntity) {
        this.onHitEntity(amountPurchased, player, hitEntity, player.level());
    }

    record Properties(Holder<Item> icon, Component name, UpgradePrice price, boolean canBeSold, Optional<Integer> maxPurchases) {
        public static final MapCodec<Properties> MAP_CODEC = RecordCodecBuilder.mapCodec(p -> p.group(
                RegistryFixedCodec.create(Registries.ITEM).fieldOf("icon").forGetter(Properties::icon),
                ComponentSerialization.CODEC.fieldOf("name").forGetter(Properties::name),
                UpgradePrice.CODEC.fieldOf("price").forGetter(Properties::price),
                Codec.BOOL.optionalFieldOf("can_be_sold", true).forGetter(Properties::canBeSold),
                ExtraCodecs.NON_NEGATIVE_INT.optionalFieldOf("max_purchases").forGetter(Properties::maxPurchases)
        ).apply(p, Properties::new));
    }

    static void withPurchasedUpgrades(ServerPlayer serverPlayer, BiConsumer<Holder<Upgrade>, Integer> consumer) {
        RegistryAccess registries = serverPlayer.registryAccess();
        Registry<Upgrade> registry = registries.lookupOrThrow(PooRegistries.Keys.UPGRADE);
        PooSavedData data = PooSavedData.get(serverPlayer);
        data.getPurchasedUpgradesIdMap(serverPlayer).forEach((identifier, integer) -> {
            if (!registry.containsKey(identifier) || integer < 1)
                return;
            Holder<Upgrade> upgradeHolder = registry.get(identifier).orElseThrow();
            ResourceKey<Upgrade> upgradeKey = upgradeHolder.unwrapKey().orElseThrow();
            consumer.accept(upgradeHolder, data.upgradePurchaseAmount(serverPlayer, upgradeKey));
        });
    }
}