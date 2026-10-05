package embinmc.mod.poosmp.upgrade;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record UpgradePrice(double base, double multiplierPerAmountPurchased) {
    public static final Codec<UpgradePrice> CODEC = RecordCodecBuilder.create(up -> up.group(
            Codec.doubleRange(0.01D, 500_000_000D).fieldOf("base").forGetter(UpgradePrice::base),
            Codec.doubleRange(0.01D, 50D).fieldOf("multiplierPerAmountPurchased").forGetter(UpgradePrice::multiplierPerAmountPurchased)
    ).apply(up, UpgradePrice::new));
}
