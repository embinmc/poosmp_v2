package embinmc.mod.poosmp.networking.c2s;

import embinmc.mod.poosmp.PooSMPMod;
import embinmc.mod.poosmp.misc.PooRegistries;
import embinmc.mod.poosmp.misc.PooSavedData;
import embinmc.mod.poosmp.upgrade.Upgrade;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import org.jspecify.annotations.NonNull;

import java.util.Optional;

public record BuyUpgradePacket(int upgrade) implements CustomPacketPayload {
    public static final StreamCodec<FriendlyByteBuf, BuyUpgradePacket> CODEC = CustomPacketPayload.codec(BuyUpgradePacket::write, BuyUpgradePacket::new);
    public static final Type<BuyUpgradePacket> TYPE = new Type<>(PooSMPMod.id("buy_upgrade"));

    public BuyUpgradePacket(FriendlyByteBuf buf) {
        this(buf.readInt());
    }

    private void write(FriendlyByteBuf buf) {
        buf.writeInt(this.upgrade);
    }

    @Override
    public @NonNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void receiver(BuyUpgradePacket packet, ServerPlayNetworking.Context context) {
        ServerPlayer player = context.player();
        MinecraftServer server = context.server();
        server.execute(() -> {
            ServerLevel level = player.level();
            PooSavedData savedData = PooSavedData.get(player);
            Registry<Upgrade> upgrades = level.registryAccess().lookupOrThrow(PooRegistries.Keys.UPGRADE);
            Optional<Holder.Reference<Upgrade>> optional = upgrades.get(packet.upgrade());
            if (optional.isEmpty()) {
                String message = player.getPlainTextName() + " requested to buy invalid upgrade: " + packet.upgrade();
                server.sendSystemMessage(Component.literal(message).withStyle(ChatFormatting.RED));
                return;
            }
            Holder<Upgrade> holder = optional.orElseThrow();
            ResourceKey<Upgrade> key = holder.unwrapKey().orElseThrow();
            if (savedData.buyUpgrade(player, key)) {
                String message = "Player " + player.getPlainTextName() + " successfully purchased upgrade: " + key.identifier();
                server.sendSystemMessage(Component.literal(message).withStyle(ChatFormatting.YELLOW));
            }
        });
    }
}
