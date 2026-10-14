package embinmc.mod.poosmp.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import embinmc.mod.poosmp.event.PooItemEvents;
import embinmc.mod.poosmp.item.PooItems;
import embinmc.mod.poosmp.item.component.DecodeExceptionComponent;
import embinmc.mod.poosmp.item.component.PooComponents;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Unit;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
    @WrapOperation(method = "inventoryTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/Item;inventoryTick(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/entity/EquipmentSlot;)V"))
    private void poosmp$wrapInventoryTick(Item instance, ItemStack itemStack, ServerLevel serverLevel, Entity entity, EquipmentSlot equipmentSlot, Operation<Void> original) {
        if (PooItemEvents.INVENTORY_TICK_PRE_ITEM.invoker().onTick(itemStack, serverLevel, entity, equipmentSlot)) {
            original.call(instance, itemStack, serverLevel, entity, equipmentSlot);
            PooItemEvents.INVENTORY_TICK_POST_ITEM.invoker().onTick(itemStack, serverLevel, entity, equipmentSlot);
        }
    }

    @WrapOperation(method = "interactLivingEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/Item;interactLivingEntity(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/InteractionResult;"))
    private InteractionResult poosmp$interactLivingEntityEvent(Item instance, ItemStack itemStack, Player player, LivingEntity livingEntity, InteractionHand interactionHand, Operation<InteractionResult> original) {
        InteractionResult result = PooItemEvents.INTERACT_LIVING_ENTITY.invoker().interact(itemStack, player, livingEntity, interactionHand);
        if (result != null)
            return result;
        return original.call(instance, itemStack, player, livingEntity, interactionHand);
    }

    @WrapMethod(method = "createOptionalStreamCodec")
    private static StreamCodec<RegistryFriendlyByteBuf, ItemStack> poosmp$allowDecode(StreamCodec<RegistryFriendlyByteBuf, DataComponentPatch> streamCodec, Operation<StreamCodec<RegistryFriendlyByteBuf, ItemStack>> original) {
        return new StreamCodec<>() {
            public ItemStack decode(RegistryFriendlyByteBuf registryFriendlyByteBuf) {
                int i = registryFriendlyByteBuf.readVarInt();
                if (i <= 0) {
                    return ItemStack.EMPTY;
                }

                Holder<Item> holder;
                try {
                    holder = Item.STREAM_CODEC.decode(registryFriendlyByteBuf);
                } catch (Exception e) {
                    holder = PooItems.DECODE_EXCEPTION_DUMMY.builtInRegistryHolder();
                }
                DataComponentPatch dataComponentPatch;
                try {
                    dataComponentPatch = streamCodec.decode(registryFriendlyByteBuf);
                } catch (Exception e) {
                    dataComponentPatch = DataComponentPatch.builder().set(PooComponents.DECODE_EXCEPTION, DecodeExceptionComponent.INSTANCE).build();
                }
                return new ItemStack(holder, i, dataComponentPatch);
            }

            public void encode(RegistryFriendlyByteBuf registryFriendlyByteBuf, ItemStack itemStack) {
                if (itemStack.isEmpty()) {
                    registryFriendlyByteBuf.writeVarInt(0);
                } else {
                    registryFriendlyByteBuf.writeVarInt(itemStack.getCount());
                    Item.STREAM_CODEC.encode(registryFriendlyByteBuf, itemStack.getItemHolder());
                    streamCodec.encode(registryFriendlyByteBuf, itemStack.components.asPatch());
                }
            }
        };
    }
}
