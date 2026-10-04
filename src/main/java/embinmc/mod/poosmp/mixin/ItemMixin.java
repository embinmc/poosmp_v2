package embinmc.mod.poosmp.mixin;

import embinmc.mod.poosmp.block.BlockWithTooltip;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(Item.class)
public abstract class ItemMixin {
    @Inject(method = "appendHoverText", at = @At("TAIL"))
    public void tooltip(ItemStack itemStack, Item.TooltipContext tooltipContext, TooltipDisplay tooltipDisplay, Consumer<Component> consumer, TooltipFlag tooltipFlag, CallbackInfo ci) {
        Item item = itemStack.getItem();
        if (item instanceof BlockItem blockItem && blockItem.getBlock() instanceof BlockWithTooltip blockWithTooltip) {
            blockWithTooltip.appendTooltip(itemStack, tooltipContext, tooltipDisplay, consumer, tooltipFlag);
        }
    }
}
