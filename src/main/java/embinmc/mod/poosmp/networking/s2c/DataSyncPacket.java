package embinmc.mod.poosmp.networking.s2c;

import embinmc.mod.poosmp.PooSMPMod;
import embinmc.mod.poosmp.misc.PooSavedData;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jspecify.annotations.NonNull;

public record DataSyncPacket(CompoundTag nbt) implements CustomPacketPayload {
    public static final StreamCodec<FriendlyByteBuf, DataSyncPacket> CODEC = CustomPacketPayload.codec(DataSyncPacket::write, DataSyncPacket::new);
    public static final Type<DataSyncPacket> TYPE = new Type<>(PooSMPMod.id("data_sync"));

    public DataSyncPacket(FriendlyByteBuf buf) {
        this(buf.readNbt());
    }

    @Override
    public @NonNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    private void write(FriendlyByteBuf buf) {
        buf.writeNbt(nbt);
    }

    public static void receiver(DataSyncPacket packet, ClientPlayNetworking.Context context) {
        PooSavedData.Client.sync(packet.nbt().get(PooSavedData.KEY));
    }
}
