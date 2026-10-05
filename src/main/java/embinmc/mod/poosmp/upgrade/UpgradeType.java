package embinmc.mod.poosmp.upgrade;

import com.mojang.serialization.MapCodec;
import embinmc.mod.poosmp.PooSMPMod;
import net.minecraft.core.Registry;

public record UpgradeType<T extends Upgrade>(String name, MapCodec<T> codec) {
    public static void register(Registry<UpgradeType<?>> r) {
        Registry.register(r, PooSMPMod.id("attributes"), AttributeUpgrade.TYPE);
        Registry.register(r, PooSMPMod.id("status_effect"), StatusEffectUpgrade.TYPE);
        Registry.register(r, PooSMPMod.id("explode_entity_when_hit"), ExplodeEntityWhenHitUpgrade.TYPE);
    }
}
