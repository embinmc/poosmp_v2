package embinmc.mod.poosmp.item.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;

import java.util.Objects;
import java.util.function.Consumer;

public record RequestedDiscComponent(String requester, boolean stereo) implements TooltipProvider {
    public static final Codec<RequestedDiscComponent> CODEC = RecordCodecBuilder.create(ci -> ci.group(
            Codec.string(1, 40).fieldOf("requester").forGetter(RequestedDiscComponent::requester),
            Codec.BOOL.optionalFieldOf("is_stereo", false).forGetter(RequestedDiscComponent::stereo)
    ).apply(ci, RequestedDiscComponent::new));

    @Override
    public void addToTooltip(Item.TooltipContext tooltipContext, Consumer<Component> consumer, TooltipFlag tooltipFlag, DataComponentGetter dataComponentGetter) {
        if (this.stereo()) {
            consumer.accept(Component.literal("This song is stereo, so the sound created by").withStyle(ChatFormatting.LIGHT_PURPLE, ChatFormatting.ITALIC));
            consumer.accept(Component.literal("the jukebox will not be played locationally.").withStyle(ChatFormatting.LIGHT_PURPLE, ChatFormatting.ITALIC));
        }
        if (!Objects.equals(this.requester(), "Embin")) {
            consumer.accept(Component.literal("Requested by ").append(this.requester()).withStyle(ChatFormatting.GRAY));
        }
    }
}
