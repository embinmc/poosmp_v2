package embinmc.mod.poosmp.item.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import io.netty.buffer.ByteBuf;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;

import java.util.function.Consumer;

public enum DecodeExceptionComponent implements TooltipProvider {
    INSTANCE;

    public static final Codec<DecodeExceptionComponent> CODEC = MapCodec.unitCodec(INSTANCE);
    public static final StreamCodec<ByteBuf, DecodeExceptionComponent> STREAM_CODEC = StreamCodec.unit(INSTANCE);

    @Override
    public void addToTooltip(Item.TooltipContext tooltipContext, Consumer<Component> consumer, TooltipFlag tooltipFlag, DataComponentGetter dataComponentGetter) {
        consumer.accept(Component.literal("Failed to decode components on this item!").withStyle(ChatFormatting.RED));
        consumer.accept(Component.literal("If you're seeing this, that's not good!").withStyle(ChatFormatting.RED));
    }
}
