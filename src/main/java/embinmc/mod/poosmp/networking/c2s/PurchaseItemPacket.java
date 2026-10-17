package embinmc.mod.poosmp.networking.c2s;

import embinmc.mod.poosmp.PooSMPMod;
import embinmc.mod.poosmp.economy.ItemValue;
import embinmc.mod.poosmp.economy.ShopCategory;
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
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.NonNull;

public record PurchaseItemPacket(int category, int item, byte amount) implements CustomPacketPayload {
    public static final StreamCodec<FriendlyByteBuf, PurchaseItemPacket> CODEC = CustomPacketPayload.codec(PurchaseItemPacket::write, PurchaseItemPacket::new);
    public static final Type<PurchaseItemPacket> TYPE = new Type<>(PooSMPMod.id("purchase_item"));

    public PurchaseItemPacket(FriendlyByteBuf buf) {
        this(buf.readInt(), buf.readInt(), buf.readByte());
    }

    @Override
    public @NonNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    private void write(FriendlyByteBuf buf) {
        buf.writeInt(this.category);
        buf.writeInt(this.item);
        buf.writeByte(this.amount);
    }

    public static void receiver(PurchaseItemPacket packet, ServerPlayNetworking.Context context) {
        Registry<ShopCategory> shopCategories = context.server().registryAccess().lookupOrThrow(PooRegistries.Keys.SHOP_CATEGORY);
        Registry<Item> items = context.server().registryAccess().lookupOrThrow(Registries.ITEM);
        if (shopCategories.get(packet.category()).isEmpty() || items.get(packet.item()).isEmpty()) {
            PooSMPMod.LOGGER.warn("Player {} tried to buy from unknown shop category or bought an unknown item", context.player().getPlainTextName());
            return;
        }
        Holder<ShopCategory> shopCategoryHolder = shopCategories.get(packet.category()).orElseThrow();
        Holder<Item> itemHolder = items.get(packet.item()).orElseThrow();
        PooSavedData savedData = PooSavedData.get(context.server());
        ItemValue itemValue = shopCategoryHolder.value().getItemValue(itemHolder);
        if (itemValue == null) {
            PooSMPMod.LOGGER.warn("Player {} cannot buy {} from {}", context.player().getPlainTextName(), itemHolder.getRegisteredName(), shopCategoryHolder.getRegisteredName());
            return;
        }
        double finalPrice = itemValue.price() * packet.amount();
        if (savedData.getBalance(context.player()) < finalPrice) {
            PooSMPMod.LOGGER.warn("Player {} tried to buy {} from {}, but they cannot afford it", context.player().getPlainTextName(), itemHolder.getRegisteredName(), shopCategoryHolder.getRegisteredName());
            return;
        }
        savedData.addBalance(context.player(), -finalPrice);
        ServerPlayer player = context.player();
        ItemEntity itemEntity = new ItemEntity(player.level(), player.getX(), player.getY(), player.getZ(), new ItemStack(itemHolder, Math.clamp(packet.amount(), 1, 99)));
        player.level().addFreshEntity(itemEntity);
        PooSMPMod.LOGGER.info("Player {} purchased {} {}", context.player().getPlainTextName(), packet.amount(), itemHolder.getRegisteredName());
    }
}
