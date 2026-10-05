package embinmc.mod.poosmp.upgrade;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import embinmc.mod.poosmp.misc.PooUtil;

public record UpgradePrice(double base, double addMultiplierPerAmountPurchased) {
    public static final Codec<UpgradePrice> CODEC = RecordCodecBuilder.create(up -> up.group(
            Codec.doubleRange(0.01D, 500_000_000D).fieldOf("base").forGetter(UpgradePrice::base),
            Codec.doubleRange(0.01D, 50D).fieldOf("addMultiplierPerAmountPurchased").forGetter(UpgradePrice::addMultiplierPerAmountPurchased)
    ).apply(up, UpgradePrice::new));

    public double getFinalPrice(int amountPurchased) {
        double price = this.base();
        for (int i = 0; i < amountPurchased; i++) {
            price += this.base() * this.addMultiplierPerAmountPurchased();
        }
        return PooUtil.roundTwo(price);
    }
}
