package embinmc.mod.poosmp.misc;

import embinmc.mod.poosmp.PooSMPMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public interface PooFeatures {
    ResourceKey<ConfiguredFeature<?, ?>> DIM_MOSS_PATCH_BONEMEAL = createKey("dim_moss_patch_bonemeal");

    static ResourceKey<ConfiguredFeature<?, ?>> createKey(String id) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, PooSMPMod.id(id));
    }
}
