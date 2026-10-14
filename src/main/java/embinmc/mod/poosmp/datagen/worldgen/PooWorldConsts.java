package embinmc.mod.poosmp.datagen.worldgen;

import embinmc.mod.poosmp.PooSMPMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.NoiseSettings;

public interface PooWorldConsts {
    NoiseSettings NOISE_SETTINGS = new NoiseSettings(-128, 640, 1, 2);
    ResourceKey<NoiseGeneratorSettings> MODIFIED_OVERWORLD = ResourceKey.create(Registries.NOISE_SETTINGS, PooSMPMod.id("modified_overworld"));
    ResourceKey<NoiseGeneratorSettings> MISSINGNO = ResourceKey.create(Registries.NOISE_SETTINGS, PooSMPMod.id("missingno"));


}
