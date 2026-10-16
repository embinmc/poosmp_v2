package embinmc.mod.poosmp.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import embinmc.mod.poosmp.economy.ItemValue;
import embinmc.mod.poosmp.economy.ShopCategory;
import embinmc.mod.poosmp.event.PooItemEvents;
import embinmc.mod.poosmp.item.PooItemTags;
import embinmc.mod.poosmp.item.PooItems;
import embinmc.mod.poosmp.item.component.DecodeExceptionComponent;
import embinmc.mod.poosmp.item.component.PooComponents;
import embinmc.mod.poosmp.misc.PooRegistries;
import embinmc.mod.poosmp.misc.PooUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
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
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

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

    @WrapOperation(method = "addDetailsToTooltip", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/Item;appendHoverText(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/Item$TooltipContext;Lnet/minecraft/world/item/component/TooltipDisplay;Ljava/util/function/Consumer;Lnet/minecraft/world/item/TooltipFlag;)V"))
    private void poosmp$addValueToTooltip(Item instance, ItemStack itemStack, Item.TooltipContext tooltipContext, TooltipDisplay tooltipDisplay, Consumer<Component> consumer, TooltipFlag tooltipFlag, Operation<Void> original) {
        original.call(instance, itemStack, tooltipContext, tooltipDisplay, consumer, tooltipFlag);
        if (tooltipContext.registries() == null || itemStack.is(PooItemTags.DOESNT_SHOW_SELL_VALUE_TOOLTIP))
            return;
        Optional<ItemValue> optionalValue = PooUtil.getValueOfStack(itemStack, tooltipContext.registries());
        if (optionalValue.isEmpty())
            return;
        ItemValue value = optionalValue.orElseThrow();
        String formatted = "Sells for $%,.2f".formatted(value.sellValue());
        Component sellText = Component.literal(formatted).withStyle(ChatFormatting.GRAY);
        consumer.accept(sellText);
    }
}
