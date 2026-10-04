package embinmc.mod.poosmp.item.component;

import embinmc.mod.poosmp.misc.PooUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.EitherHolder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;

import java.util.Optional;
import java.util.function.Consumer;

public record BiomeStickComponent(int radius, EitherHolder<Biome> selectedBiome) implements TooltipProvider {
    @Override
    public void addToTooltip(Item.TooltipContext tooltipContext, Consumer<Component> consumer, TooltipFlag tooltipFlag, DataComponentGetter dataComponentGetter) {
        if (tooltipContext.registries() == null)
            return;
        Optional<Holder<Biome>> optionalBiome = this.selectedBiome().unwrap(tooltipContext.registries());
        if (optionalBiome.isEmpty()) {
            consumer.accept(Component.literal("Invalid biome selected").withStyle(ChatFormatting.RED));
            return;
        }
        Holder<Biome> biome = optionalBiome.orElseThrow();
        if (biome.unwrapKey().isEmpty()) {
            consumer.accept(Component.literal("Invalid biome selected").withStyle(ChatFormatting.RED));
            return;
        }
        ResourceKey<Biome> biomeKey = biome.unwrapKey().orElseThrow();
        Component intText = PooUtil.formattedIntText(this.radius, ChatFormatting.YELLOW);
        Component biomeText = Component.literal(biomeKey.identifier().toString()).withStyle(ChatFormatting.YELLOW);
        consumer.accept(Component.translatable("poosmp.biome_stick.radius", intText).withStyle(ChatFormatting.GRAY));
        consumer.accept(Component.translatable("poosmp.biome_stick.current_biome", biomeText).withStyle(ChatFormatting.GRAY));
    }

    public static InteractionResult onUse(Level level, Player player, InteractionHand hand) {
        ItemStack stackInHand = player.getItemInHand(hand);
        BiomeStickComponent biomeStick = stackInHand.get(PooComponents.BIOME_TRANSFORMER);
        Optional<Holder<Biome>> optionalBiome = level.registryAccess()
    }
}
