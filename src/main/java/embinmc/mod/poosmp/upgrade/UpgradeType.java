package embinmc.mod.poosmp.upgrade;

import com.mojang.serialization.MapCodec;

public record UpgradeType<T extends Upgrade>(String name, MapCodec<T> codec) {
}
