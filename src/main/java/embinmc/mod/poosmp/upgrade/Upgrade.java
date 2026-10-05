package embinmc.mod.poosmp.upgrade;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import embinmc.mod.poosmp.misc.PooRegistries;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.RegistryFixedCodec;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.Item;

import java.util.Optional;

public interface Upgrade {
    Codec<Upgrade> DIRECT_CODEC = PooRegistries.UPGRADE_TYPE.byNameCodec().dispatch(Upgrade::getType, UpgradeType::codec);

    void onRespawn(ServerPlayer player, ServerLevel level, MinecraftServer server);
    void onBuy(ServerPlayer player, ServerLevel level, MinecraftServer server);
    void onSell(ServerPlayer player, ServerLevel level, MinecraftServer server);
    void onJoin(ServerPlayer player, ServerLevel level, MinecraftServer server);
    void onTick(ServerPlayer player, ServerLevel level, MinecraftServer server);
    void onDeath(ServerPlayer player, ServerLevel level, MinecraftServer server);

    UpgradeType<?> getType();

    default void onRespawn(ServerPlayer player) {
        this.onRespawn(player, player.level(), player.level().getServer());
    }

    default void onBuy(ServerPlayer player) {
        this.onBuy(player, player.level(), player.level().getServer());
    }

    default void onSell(ServerPlayer player) {
        this.onSell(player, player.level(), player.level().getServer());
    }

    default void onJoin(ServerPlayer player) {
        this.onJoin(player, player.level(), player.level().getServer());
    }

    default void onTick(ServerPlayer player) {
        this.onTick(player, player.level(), player.level().getServer());
    }

    default void onDeath(ServerPlayer player) {
        this.onDeath(player, player.level(), player.level().getServer());
    }

    record Properties(Holder<Item> icon, UpgradePrice price, boolean canBeSold, Optional<Integer> maxPurchases) {
        public static final MapCodec<Properties> MAP_CODEC = RecordCodecBuilder.mapCodec(p -> p.group(
                RegistryFixedCodec.create(Registries.ITEM).fieldOf("icon").forGetter(Properties::icon),
                UpgradePrice.CODEC.fieldOf("price").forGetter(Properties::price),
                Codec.BOOL.optionalFieldOf("can_be_sold", true).forGetter(Properties::canBeSold),
                ExtraCodecs.NON_NEGATIVE_INT.optionalFieldOf("max_purchases").forGetter(Properties::maxPurchases)
        ).apply(p, Properties::new));
    }
}