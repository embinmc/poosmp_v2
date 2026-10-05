package embinmc.mod.poosmp.economy;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import embinmc.mod.poosmp.misc.PooUtil;
import net.minecraft.util.ExtraCodecs;

import java.util.OptionalDouble;

public record ItemValue(double price, OptionalDouble optionalSellValue) {
    private static final Codec<ItemValue> SHORT_CODEC = Codec.DOUBLE.xmap(
            d -> new ItemValue(d, OptionalDouble.empty()),
            ItemValue::price
    );
    private static final Codec<ItemValue> LONG_CODEC = RecordCodecBuilder.create(iv -> iv.group(
            Codec.DOUBLE.fieldOf("price").forGetter(ItemValue::price),
            PooUtil.optionalDouble(Codec.DOUBLE.optionalFieldOf("sell_value")).forGetter(ItemValue::optionalSellValue)
    ).apply(iv, ItemValue::new));
    public static final Codec<ItemValue> CODEC = Codec.either(SHORT_CODEC, LONG_CODEC).xmap(
            Either::unwrap, itemValue -> itemValue.optionalSellValue().isEmpty() ? Either.left(itemValue) : Either.right(itemValue)
    );

    public double sellValue() {
        return this.optionalSellValue().orElse(PooUtil.roundTwo(this.price() / 2D));
    }

    @Override
    public double price() {
        return PooUtil.roundTwo(this.price);
    }
}
