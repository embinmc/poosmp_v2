package embinmc.mod.poosmp.misc;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.RegistryFixedCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Util;
import net.minecraft.world.entity.decoration.painting.PaintingVariant;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.EitherHolder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gamerules.GameRule;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

@SuppressWarnings("NullableProblems")
public class PooUtil {
    public static final Comparator<Holder<PaintingVariant>> PAINTING_COMPARATOR = Comparator.comparing(
            Holder::value, Comparator.comparingInt(PaintingVariant::area).thenComparing(PaintingVariant::width)
    );
    public static final Component OUTLAWED_EXPLOSIVE_TEXT = Component.literal("Explosive gadgets are currently outlawed").withStyle(ChatFormatting.RED);
    public static final Component SUPPRESSED_EXPLOSIVE = Component.literal("Explosion suppressed due to explosive gadgets currently being outlawed").withStyle(ChatFormatting.RED);

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException("PooSMP: Cannot round to less than 0 places");
        BigDecimal bigDecimal = BigDecimal.valueOf(value);
        bigDecimal = bigDecimal.setScale(places, RoundingMode.HALF_UP);
        return bigDecimal.doubleValue();
    }

    public static double roundTwo(double value) {
        return round(value, 2);
    }

    public static void getPaintingStacks(TagKey<PaintingVariant> paintingTag, HolderLookup.Provider provider, Consumer<ItemStack> consumer) {
        HolderLookup.RegistryLookup<PaintingVariant> registryLookup = provider.lookupOrThrow(Registries.PAINTING_VARIANT);
        registryLookup.listElements().filter(painting -> painting.is(paintingTag)).sorted(PooUtil.PAINTING_COMPARATOR).forEach(painting -> {
            ItemStack itemStack = new ItemStack(Items.PAINTING);
            itemStack.set(DataComponents.PAINTING_VARIANT, painting);
            consumer.accept(itemStack);
        });
    }

    public static <T> void forEachEntry(HolderLookup.Provider provider, ResourceKey<Registry<T>> registryKey, Consumer<Holder<T>> consumer) {
        provider.lookupOrThrow(registryKey).listElements().forEach(consumer);
    }

    public static <T> void forEachEntryInTag(HolderLookup.Provider provider, ResourceKey<Registry<T>> registryKey, TagKey<T> tagKey, Consumer<Holder<T>> consumer) {
        provider.lookupOrThrow(registryKey).get(tagKey).ifPresent(holders -> holders.forEach(consumer));
    }

    public static <T> T getGameRuleValue(Level level, GameRule<T> gameRule) {
        if (level instanceof ServerLevel serverLevel) {
            return serverLevel.getGameRules().get(gameRule);
        } else return gameRule.defaultValue();
    }

    public static Component formattedIntText(int integer, ChatFormatting... formatting) {
        return Component.literal("%,d".formatted(integer)).withStyle(formatting);
    }

    public static <T> Codec<EitherHolder<T>> quickEitherHolderCodec(ResourceKey<Registry<T>> registry) {
        return EitherHolder.codec(registry, RegistryFixedCodec.create(registry));
    }

    public static <T> StreamCodec<? super RegistryFriendlyByteBuf, EitherHolder<T>> quickEitherHolderStreamCodec(ResourceKey<Registry<T>> registry) {
        return EitherHolder.streamCodec(registry, ByteBufCodecs.holderRegistry(registry));
    }

    public static <T> List<T> listFromEnumeration(final Iterator<T> enumeration) {
        return Util.make(new ArrayList<>(), list -> {
            while (enumeration.hasNext()) {
                final T element = enumeration.next();
                list.add(element);
            }
        });
    }

    public static Codec<Byte> byteRange(final byte minInclusive, final byte maxInclusive) {
        final Function<Byte, DataResult<Byte>> checker = Codec.checkRange(minInclusive, maxInclusive);
        return Codec.BYTE.flatXmap(checker, checker);
    }

    public static @Nullable ServerPlayer getPlayerByName(String name, ServerLevel world) {
        for (ServerPlayer player : world.players()) {
            if (Objects.equals(name, player.getScoreboardName())) {
                return player;
            }
        }
        return null;
    }
}
