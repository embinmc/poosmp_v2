package embinmc.mod.poosmp.networking;

import embinmc.mod.poosmp.networking.c2s.BuyUpgradePacket;
import embinmc.mod.poosmp.networking.c2s.PurchaseItemPacket;
import embinmc.mod.poosmp.networking.c2s.SellUpgradePacket;
import embinmc.mod.poosmp.networking.s2c.DataSyncPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public class PooMessages {
    public static void register() {
        // server to client
        PayloadTypeRegistry.playS2C().register(DataSyncPacket.TYPE, DataSyncPacket.CODEC);

        // client to server
        PayloadTypeRegistry.playC2S().register(BuyUpgradePacket.TYPE, BuyUpgradePacket.CODEC);
        PayloadTypeRegistry.playC2S().register(SellUpgradePacket.TYPE, SellUpgradePacket.CODEC);
        PayloadTypeRegistry.playC2S().register(PurchaseItemPacket.TYPE, PurchaseItemPacket.CODEC);
    }

    public static void registerS2C() {
        ClientPlayNetworking.registerGlobalReceiver(DataSyncPacket.TYPE, DataSyncPacket::receiver);
    }

    public static void registerC2S() {
        ServerPlayNetworking.registerGlobalReceiver(BuyUpgradePacket.TYPE, BuyUpgradePacket::receiver);
        ServerPlayNetworking.registerGlobalReceiver(SellUpgradePacket.TYPE, SellUpgradePacket::receiver);
        ServerPlayNetworking.registerGlobalReceiver(PurchaseItemPacket.TYPE, PurchaseItemPacket::receiver);
    }
}
