package embinmc.mod.poosmp.networking.c2s;

import embinmc.mod.poosmp.PooSMPMod;
import embinmc.mod.poosmp.economy.ItemValue;
import embinmc.mod.poosmp.economy.ShopCategory;
import embinmc.mod.poosmp.item.component.BiomeStickComponent;
import embinmc.mod.poosmp.item.component.PooComponents;
import embinmc.mod.poosmp.misc.PooRegistries;
import embinmc.mod.poosmp.misc.PooSavedData;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.EitherHolder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.biome.Biome;
import org.jspecify.annotations.NonNull;

import java.util.Objects;
import java.util.Optional;

public record ModifyBiomeStickPacket(int modType, int value) implements CustomPacketPayload {
    public static final int TYPE_HORIZONTAL_DIAMETER = 0;
    public static final int TYPE_VERTICAL_DIAMETER = 1;
    public static final int TYPE_BIOME = 2;
    public static final StreamCodec<FriendlyByteBuf, ModifyBiomeStickPacket> CODEC = CustomPacketPayload.codec(ModifyBiomeStickPacket::write, ModifyBiomeStickPacket::new);
    public static final Type<ModifyBiomeStickPacket> TYPE = new Type<>(PooSMPMod.id("modify_biome_stick"));

    public ModifyBiomeStickPacket(FriendlyByteBuf buf) {
        this(buf.readInt(), buf.readInt());
    }

    @Override
    public @NonNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    private void write(FriendlyByteBuf buf) {
        buf.writeInt(this.modType);
        buf.writeInt(this.value);
    }

    public static void receiver(ModifyBiomeStickPacket packet, ServerPlayNetworking.Context context) {
        Registry<Biome> biomes = context.server().registryAccess().lookupOrThrow(Registries.BIOME);
        ServerPlayer player = context.player();
        ItemStack heldStick = player.getMainHandItem();
        if (!heldStick.has(PooComponents.BIOME_TRANSFORMER)) {
            PooSMPMod.LOGGER.error("Player {} tried to modify their biome stick whilst not holding one", player.getPlainTextName());
            return;
        }
        BiomeStickComponent biomeStick = Objects.requireNonNull(heldStick.get(PooComponents.BIOME_TRANSFORMER));
        switch (packet.modType()) {
            case TYPE_HORIZONTAL_DIAMETER -> {
                byte diameter = (byte) Math.clamp(packet.value(), 1, 127);
                BiomeStickComponent newDiameter = biomeStick.withHorizontalDiameter(diameter);
                heldStick.set(PooComponents.BIOME_TRANSFORMER, newDiameter);
            }
            case TYPE_VERTICAL_DIAMETER -> {
                byte diameter = (byte) Math.clamp(packet.value(), 1, 127);
                BiomeStickComponent newDiameter = biomeStick.withVerticalDiameter(diameter);
                heldStick.set(PooComponents.BIOME_TRANSFORMER, newDiameter);
            }
            case TYPE_BIOME -> {
                Optional<Holder.Reference<Biome>> optional = biomes.get(packet.value());
                if (optional.isEmpty()) {
                    PooSMPMod.LOGGER.error("Player {} tried to modify their biome stick with unknown biome: {}", player.getPlainTextName(), packet.value());
                    return;
                }
                BiomeStickComponent newBiome = biomeStick.withBiome(new EitherHolder<>(optional.orElseThrow()));
                heldStick.set(PooComponents.BIOME_TRANSFORMER, newBiome);
            }
            default -> {
                PooSMPMod.LOGGER.error("Player {} tried to modify their biome stick with invalid mod type: {}", player.getPlainTextName(), packet.modType());
                return;
            }
        }
        player.playSound(SoundEvents.ENCHANTMENT_TABLE_USE);
    }
}
