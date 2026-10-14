package embinmc.mod.poosmp.datagen.worldgen;

import com.mojang.serialization.Codec;
import embinmc.mod.poosmp.PooSMPMod;
import embinmc.mod.poosmp.datagen.PooCodecDataProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.OverworldBiomeBuilder;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

public class PooNoiseGenSettingsProvider extends PooCodecDataProvider<NoiseGeneratorSettings> {
    public static final ResourceKey<DensityFunction> SHIFT_X = createKey("shift_x");
    public static final ResourceKey<DensityFunction> SHIFT_Z = createKey("shift_z");

    public PooNoiseGenSettingsProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(dataOutput, registriesFuture, Registries.NOISE_SETTINGS, NoiseGeneratorSettings.DIRECT_CODEC);
    }

    @Override
    public void generate(HolderLookup.@NonNull Provider provider) {
        this.provider.accept(NoiseGeneratorSettings.OVERWORLD.identifier(), modifiedOverworld(provider));
    }

    private static NoiseGeneratorSettings modifiedOverworld(HolderLookup.@NonNull Provider provider) {
        HolderLookup.RegistryLookup<DensityFunction> functions = provider.lookupOrThrow(Registries.DENSITY_FUNCTION);
        DensityFunction offset = getFunction(functions, NoiseRouterData.OFFSET);
        DensityFunction factor = getFunction(functions, NoiseRouterData.FACTOR);
        return new NoiseGeneratorSettings(
                PooWorldConsts.NOISE_SETTINGS,
                Blocks.STONE.defaultBlockState(),
                Blocks.WATER.defaultBlockState(),
                noNewCaves(functions, provider.lookupOrThrow(Registries.NOISE), preliminarySurfaceLevel(offset, factor)),
                PooSurfaceRules.modifiedOverworld(true),
                new OverworldBiomeBuilder().spawnTarget(),
                63, false, false, false, false
        );
    }

    private static NoiseRouter noNewCaves(final HolderGetter<DensityFunction> functions, final HolderGetter<NormalNoise.NoiseParameters> noises, final DensityFunction slide) {
        DensityFunction shiftX = getFunction(functions, SHIFT_X);
        DensityFunction shiftZ = getFunction(functions, SHIFT_Z);
        DensityFunction temperature = DensityFunctions.shiftedNoise2d(shiftX, shiftZ, 0.25, noises.getOrThrow(Noises.TEMPERATURE));
        DensityFunction vegetation = DensityFunctions.shiftedNoise2d(shiftX, shiftZ, 0.25, noises.getOrThrow(Noises.VEGETATION));
        DensityFunction fullNoise = postProcess(slide);
        return new NoiseRouter(
                DensityFunctions.zero(),
                DensityFunctions.zero(),
                DensityFunctions.zero(),
                DensityFunctions.zero(),
                temperature,
                vegetation,
                DensityFunctions.zero(),
                DensityFunctions.zero(),
                DensityFunctions.zero(),
                DensityFunctions.zero(),
                DensityFunctions.zero(),
                fullNoise,
                DensityFunctions.zero(),
                DensityFunctions.zero(),
                DensityFunctions.zero()
        );
    }

    private static ResourceKey<DensityFunction> createKey(final String name) {
        return ResourceKey.create(Registries.DENSITY_FUNCTION, Identifier.withDefaultNamespace(name));
    }

    private static DensityFunction postProcess(final DensityFunction slide) {
        DensityFunction blended = DensityFunctions.blendDensity(slide);
        return DensityFunctions.mul(DensityFunctions.interpolated(blended), DensityFunctions.constant(0.64)).squeeze();
    }

    private static DensityFunction getFunction(final HolderGetter<DensityFunction> functions, final ResourceKey<DensityFunction> name) {
        return new DensityFunctions.HolderHolder(functions.getOrThrow(name));
    }

    private static DensityFunction preliminarySurfaceLevel(final DensityFunction offset, final DensityFunction factor) {
        DensityFunction cachedFactor = DensityFunctions.cache2d(factor);
        DensityFunction cachedOffset = DensityFunctions.cache2d(offset);
        DensityFunction upperBound = remap(
                DensityFunctions.add(
                        DensityFunctions.mul(DensityFunctions.constant(0.2734375), cachedFactor.invert()),
                        DensityFunctions.mul(DensityFunctions.constant(-1.0), cachedOffset)
                ),
                1.5,
                -1.5,
                -64.0,
                320.0
        );
        upperBound = upperBound.clamp(-40.0, 320.0);
        DensityFunction density = DensityFunctions.add(
                slideOverworld(
                        false,
                        DensityFunctions.add(noiseGradientDensity(cachedFactor, offsetToDepth(cachedOffset)), DensityFunctions.constant(-0.703125)).clamp(-64.0, 64.0)
                ),
                DensityFunctions.constant(-0.390625)
        );
        return DensityFunctions.findTopSurface(density, upperBound, -64, PooWorldConsts.NOISE_SETTINGS.getCellHeight());
    }

    private static DensityFunction remap(DensityFunction input, double fromMin, double fromMax, double toMin, double toMax) {
        double factor = (toMax - toMin) / (fromMax - fromMin);
        double offset = toMin - fromMin * factor;
        return DensityFunctions.add(DensityFunctions.mul(input, DensityFunctions.constant(factor)), DensityFunctions.constant(offset));
    }

    private static DensityFunction slideOverworld(final boolean isAmplified, final DensityFunction caves) {
        return slide(caves, -64, 384, isAmplified ? 16 : 80, isAmplified ? 0 : 64, -0.078125, 0, 24, isAmplified ? 0.4 : 0.1171875);
    }

    private static DensityFunction slide(
            DensityFunction caves,
            int minY,
            int height,
            int topStartY,
            int topEndY,
            double topTarget,
            int bottomStartY,
            int bottomEndY,
            double bottomTarget
    ) {
        DensityFunction noiseValue = caves;
        DensityFunction topFactor = DensityFunctions.yClampedGradient(minY + height - topStartY, minY + height - topEndY, 1.0, 0.0);
        noiseValue = DensityFunctions.lerp(topFactor, topTarget, noiseValue);
        DensityFunction bottomFactor = DensityFunctions.yClampedGradient(minY + bottomStartY, minY + bottomEndY, 0.0, 1.0);
        return DensityFunctions.lerp(bottomFactor, bottomTarget, noiseValue);
    }

    private static DensityFunction noiseGradientDensity(final DensityFunction factor, final DensityFunction depthWithJaggedness) {
        DensityFunction gradientUnscaled = DensityFunctions.mul(depthWithJaggedness, factor);
        return DensityFunctions.mul(DensityFunctions.constant(4.0), gradientUnscaled.quarterNegative());
    }

    private static DensityFunction offsetToDepth(final DensityFunction offset) {
        return DensityFunctions.add(DensityFunctions.yClampedGradient(-64, 320, 1.5, -1.5), offset);
    }
}
