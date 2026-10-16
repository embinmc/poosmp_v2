package embinmc.mod.poosmp.misc;

import embinmc.mod.poosmp.PooSMPMod;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.*;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.util.ARGB;
import net.minecraft.util.TriState;
import net.minecraft.world.attribute.AmbientParticle;
import net.minecraft.world.attribute.BackgroundMusic;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public interface PooBiomes {
    ResourceKey<Biome> DDE_PLAINS = create("ddededodediamante_plains"),
    SKY_ISLANDS = create("sky_islands"),
    OUTER_HYRULE = create("outer_hyrule"),
    GRASSLANDS = create("grasslands"),
    HYRULE = create("hyrule"),
    HYRULE_CASTLE = create("hyrule_castle"),
    MISSINGNO = create("missingno"),
    SLENDERMAN_LAND = create("slenderman_land"),
    DEPTHS = create("depths"),
    HYRULE_OCEAN = create("hyrule_ocean"),
    DIMWORLD_SURFACE = create("dimworld_surface"),
    DIMWORLD_DEPTHS = create("dimworld_depths"),
    DIMWORLD_SKY = create("dimworld_sky"),
    DIMWORLD_GLOOMY_MEADOWS = create("gloomy_meadows"),
    DIMWORLD_GLOOMY_OCEAN = create("gloomy_ocean"),
    DIMWORLD_DEEP_GLOOMY_OCEAN = create("deep_gloomy_ocean"),
    DIMWORLD_GRAVELY_FIELDS = create("gravely_fields"),
    DIMWORLD_WET_SKY = create("wet_sky"),
    DIMWORLD_BARRENS = create("dim_barrens");

    private static ResourceKey<Biome> create(String id) {
        return ResourceKey.create(Registries.BIOME, PooSMPMod.id(id));
    }

    interface Tags {
        TagKey<Biome> IS_DIMWORLD = tag("is_dimworld");
        TagKey<Biome> IS_DIMWORLD_LAND = tag("is_dimworld_land");
        TagKey<Biome> IS_DIMWORLD_OCEAN = tag("is_dimworld_ocean");
        TagKey<Biome> IS_DIMWORLD_SKY = tag("is_dimworld_sky");

        private static TagKey<Biome> tag(String id) {
            return TagKey.create(Registries.BIOME, PooSMPMod.id(id));
        }
    }

    static void bootstrap(BootstrapContext<Biome> context) {
        QuickBootstrapLoader<Biome> bootstrap = new QuickBootstrapLoader<>(context) {
            @Override
            public void bootstrap() {
                this.register(PooBiomes.DDE_PLAINS, new Biome.BiomeBuilder()
                        .downfall(0.8f)
                        .temperature(0.85f)
                        .hasPrecipitation(true)
                        .mobSpawnSettings(MobSpawnSettings.EMPTY)
                        .specialEffects(new BiomeSpecialEffects(
                                14237668, // water color
                                Optional.of(16098068), // foliage color
                                Optional.empty(), // dry foliage color
                                Optional.empty(), // grass color override
                                BiomeSpecialEffects.GrassColorModifier.NONE
                        ))
                        .setAttribute(EnvironmentAttributes.SKY_COLOR, 0xf07aff)
                        .setAttribute(EnvironmentAttributes.FOG_COLOR, 0xffc2fd)
                        .setAttribute(EnvironmentAttributes.WATER_FOG_COLOR, 0x330532)
                        .setAttribute(EnvironmentAttributes.AMBIENT_PARTICLES, AmbientParticle.of(ParticleTypes.CHERRY_LEAVES, 0.01f))
                        .setAttribute(EnvironmentAttributes.BACKGROUND_MUSIC, this.quickMusic(SoundEvents.MUSIC_CREDITS))
                        .generationSettings(this.genSettings(builder -> {
                            builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.FLOWER_CHERRY);
                            builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, TreePlacements.JUNGLE_BUSH);
                        }))
                );
                this.register(PooBiomes.DIMWORLD_DEEP_GLOOMY_OCEAN, new Biome.BiomeBuilder()
                        .temperature(0.6f)
                        .downfall(0.5f)
                        .hasPrecipitation(false)
                        .mobSpawnSettings(MobSpawnSettings.EMPTY)
                        .specialEffects(new BiomeSpecialEffects(
                                0x484874, // water color
                                Optional.of(0x48485a), // foliage color
                                Optional.empty(), // dry foliage color
                                Optional.of(0x486454), // grass color override
                                BiomeSpecialEffects.GrassColorModifier.NONE
                        ))
                        .setAttribute(EnvironmentAttributes.SKY_COLOR, 0x555555)
                        .setAttribute(EnvironmentAttributes.AMBIENT_PARTICLES, AmbientParticle.of(ParticleTypes.COPPER_FIRE_FLAME, 0.0006f))
                        .generationSettings(this.genSettings(builder -> {
                            builder.addFeature(GenerationStep.Decoration.UNDERGROUND_STRUCTURES, CavePlacements.MONSTER_ROOM_DEEP);
                            dimworldOres(builder);
                            builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AquaticPlacements.SEA_PICKLE);
                        }))
                );
                this.register(PooBiomes.DEPTHS, new Biome.BiomeBuilder()
                        .downfall(0.5f)
                        .temperature(0.8f)
                        .hasPrecipitation(false)
                        .mobSpawnSettings(MobSpawnSettings.EMPTY)
                        .specialEffects(new BiomeSpecialEffects(
                                6062520, // water color
                                Optional.of(5658198), // foliage color
                                Optional.empty(), // dry foliage color
                                Optional.of(7039851), // grass color override
                                BiomeSpecialEffects.GrassColorModifier.NONE
                        ))
                        .setAttribute(EnvironmentAttributes.AMBIENT_PARTICLES, AmbientParticle.of(ParticleTypes.CRIMSON_SPORE, 0.01f))
                        .setAttribute(EnvironmentAttributes.SKY_COLOR, 0x349249)
                        .setAttribute(EnvironmentAttributes.FOG_COLOR, 0x1b6b3c)
                        .setAttribute(EnvironmentAttributes.FOG_START_DISTANCE, 8f)
                        .setAttribute(EnvironmentAttributes.FOG_END_DISTANCE, 240f)
                        .setAttribute(EnvironmentAttributes.SKY_LIGHT_FACTOR, 0.8f)
                        .setAttribute(EnvironmentAttributes.SKY_LIGHT_COLOR, 0xb3d8bf)
                        .setAttribute(EnvironmentAttributes.EYEBLOSSOM_OPEN, TriState.TRUE)
                        .setAttribute(EnvironmentAttributes.PIGLINS_ZOMBIFY, false)
                        .setAttribute(EnvironmentAttributes.WATER_FOG_COLOR, 0xadadad)
                        .setAttribute(EnvironmentAttributes.CLOUD_COLOR, 0x00000000)
                        .generationSettings(BiomeGenerationSettings.EMPTY)
                );
                this.register(PooBiomes.DIMWORLD_BARRENS, new Biome.BiomeBuilder()
                        .temperature(0.8f)
                        .downfall(0.5f)
                        .hasPrecipitation(false)
                        .mobSpawnSettings(MobSpawnSettings.EMPTY)
                        .specialEffects(new BiomeSpecialEffects(
                                0x444455, // water color
                                Optional.of(0x444455), // foliage color
                                Optional.empty(), // dry foliage color
                                Optional.of(0x444455), // grass color override
                                BiomeSpecialEffects.GrassColorModifier.NONE
                        ))
                        .setAttribute(EnvironmentAttributes.SKY_COLOR, 0x444444)
                        .setAttribute(EnvironmentAttributes.AMBIENT_PARTICLES, AmbientParticle.of(ParticleTypes.COPPER_FIRE_FLAME, 0.0006f))
                        .generationSettings(this.genSettings(builder -> {
                            builder.addFeature(GenerationStep.Decoration.UNDERGROUND_STRUCTURES, CavePlacements.MONSTER_ROOM);
                            builder.addFeature(GenerationStep.Decoration.UNDERGROUND_STRUCTURES, CavePlacements.MONSTER_ROOM_DEEP);
                            dimworldOres(builder);
                            builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_FIREFLY_BUSH_NEAR_WATER);
                        }))
                );
                this.register(PooBiomes.DIMWORLD_DEPTHS, new Biome.BiomeBuilder()
                        .temperature(0.8f)
                        .downfall(0.5f)
                        .hasPrecipitation(false)
                        .mobSpawnSettings(MobSpawnSettings.EMPTY)
                        .specialEffects(new BiomeSpecialEffects(
                                0x553344, // water color
                                Optional.of(0x553344), // foliage color
                                Optional.empty(), // dry foliage color
                                Optional.of(0x553344), // grass color override
                                BiomeSpecialEffects.GrassColorModifier.NONE
                        ))
                        .setAttribute(EnvironmentAttributes.SKY_COLOR, 0x444444)
                        .setAttribute(EnvironmentAttributes.AMBIENT_PARTICLES, List.of(
                                new AmbientParticle(ParticleTypes.COPPER_FIRE_FLAME, 0.0004f),
                                new AmbientParticle(ParticleTypes.FIREFLY, 0.006f)
                        ))
                        .setAttribute(EnvironmentAttributes.FOG_COLOR, 0x2f0f16)
                        .setAttribute(EnvironmentAttributes.FOG_START_DISTANCE, 8f)
                        .setAttribute(EnvironmentAttributes.FOG_END_DISTANCE, 240f)
                        .setAttribute(EnvironmentAttributes.WATER_FOG_COLOR, 0xad8d9d)
                        .generationSettings(this.genSettings(builder -> {
                            builder.addFeature(GenerationStep.Decoration.UNDERGROUND_STRUCTURES, CavePlacements.MONSTER_ROOM);
                            builder.addFeature(GenerationStep.Decoration.UNDERGROUND_STRUCTURES, CavePlacements.MONSTER_ROOM_DEEP);
                            dimworldOres(builder);
                            builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_FIREFLY_BUSH_NEAR_WATER);
                        }))
                );
                this.register(PooBiomes.DIMWORLD_SKY, new Biome.BiomeBuilder()
                        .temperature(0.8f)
                        .downfall(0.5f)
                        .hasPrecipitation(false)
                        .mobSpawnSettings(MobSpawnSettings.EMPTY)
                        .specialEffects(new BiomeSpecialEffects(
                                0x666688, // water color
                                Optional.of(0x666688), // foliage color
                                Optional.empty(), // dry foliage color
                                Optional.of(0x666688), // grass color override
                                BiomeSpecialEffects.GrassColorModifier.NONE
                        ))
                        .setAttribute(EnvironmentAttributes.SKY_COLOR, 0x555555)
                        .setAttribute(EnvironmentAttributes.AMBIENT_PARTICLES, List.of(
                                new AmbientParticle(ParticleTypes.COPPER_FIRE_FLAME, 0.0002f),
                                new AmbientParticle(ParticleTypes.FIREFLY, 0.006f)
                        ))
                        .setAttribute(EnvironmentAttributes.FOG_COLOR, 0x222244)
                        .generationSettings(BiomeGenerationSettings.EMPTY)
                );
                this.register(PooBiomes.DIMWORLD_SURFACE, new Biome.BiomeBuilder()
                        .temperature(0.8f)
                        .downfall(0.5f)
                        .hasPrecipitation(false)
                        .mobSpawnSettings(MobSpawnSettings.EMPTY)
                        .specialEffects(new BiomeSpecialEffects(
                                0x444455, // water color
                                Optional.of(0x444455), // foliage color
                                Optional.empty(), // dry foliage color
                                Optional.of(0x444455), // grass color override
                                BiomeSpecialEffects.GrassColorModifier.NONE
                        ))
                        .setAttribute(EnvironmentAttributes.SKY_COLOR, 0x444444)
                        .setAttribute(EnvironmentAttributes.AMBIENT_PARTICLES, AmbientParticle.of(ParticleTypes.COPPER_FIRE_FLAME, 0.0004f))
                        .generationSettings(this.genSettings(builder -> {
                            builder.addFeature(GenerationStep.Decoration.UNDERGROUND_STRUCTURES, CavePlacements.MONSTER_ROOM);
                            builder.addFeature(GenerationStep.Decoration.UNDERGROUND_STRUCTURES, CavePlacements.MONSTER_ROOM_DEEP);
                            dimworldOres(builder);
                            builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_DEAD_BUSH_BADLANDS);
                            builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_FIREFLY_BUSH_NEAR_WATER);
                        }))
                );
                this.register(PooBiomes.DIMWORLD_GLOOMY_MEADOWS, new Biome.BiomeBuilder()
                        .temperature(0.8f)
                        .downfall(0.5f)
                        .hasPrecipitation(false)
                        .mobSpawnSettings(MobSpawnSettings.EMPTY)
                        .specialEffects(new BiomeSpecialEffects(
                                0x555566, // water color
                                Optional.of(0x555566), // foliage color
                                Optional.empty(), // dry foliage color
                                Optional.of(0x555566), // grass color override
                                BiomeSpecialEffects.GrassColorModifier.NONE
                        ))
                        .setAttribute(EnvironmentAttributes.AMBIENT_PARTICLES, List.of(
                                new AmbientParticle(ParticleTypes.COPPER_FIRE_FLAME, 0.0006f),
                                new AmbientParticle(ParticleTypes.FIREFLY, 0.001f)
                        ))
                        .setAttribute(EnvironmentAttributes.SKY_COLOR, 0x153826)
                        .setAttribute(EnvironmentAttributes.FOG_COLOR, 0x1f1f26)
                        .setAttribute(EnvironmentAttributes.FOG_START_DISTANCE, 8f)
                        .generationSettings(this.genSettings(builder -> {
                            builder.addFeature(GenerationStep.Decoration.UNDERGROUND_STRUCTURES, CavePlacements.MONSTER_ROOM);
                            builder.addFeature(GenerationStep.Decoration.UNDERGROUND_STRUCTURES, CavePlacements.MONSTER_ROOM_DEEP);
                            dimworldOres(builder);
                            builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_GRASS_BADLANDS);
                            builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_DRY_GRASS_BADLANDS);
                            builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_SUGAR_CANE_BADLANDS);
                            builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_PUMPKIN);
                            builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_FIREFLY_BUSH_NEAR_WATER);
                            builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.FLOWER_MEADOW);
                            builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.TREES_MEADOW);
                        }))
                );
                this.register(PooBiomes.DIMWORLD_GLOOMY_OCEAN, new Biome.BiomeBuilder()
                        .temperature(0.6f)
                        .downfall(0.5f)
                        .hasPrecipitation(false)
                        .mobSpawnSettings(MobSpawnSettings.EMPTY)
                        .specialEffects(new BiomeSpecialEffects(
                                0x444469, // water color
                                Optional.of(0x445450), // foliage color
                                Optional.empty(), // dry foliage color
                                Optional.of(0x444455), // grass color override
                                BiomeSpecialEffects.GrassColorModifier.NONE
                        ))
                        .setAttribute(EnvironmentAttributes.SKY_COLOR, 0x444444)
                        .setAttribute(EnvironmentAttributes.AMBIENT_PARTICLES, AmbientParticle.of(ParticleTypes.COPPER_FIRE_FLAME, 0.0006f))
                        .generationSettings(this.genSettings(builder -> {
                            builder.addFeature(GenerationStep.Decoration.UNDERGROUND_STRUCTURES, CavePlacements.MONSTER_ROOM);
                            builder.addFeature(GenerationStep.Decoration.UNDERGROUND_STRUCTURES, CavePlacements.MONSTER_ROOM_DEEP);
                            dimworldOres(builder);
                            builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.FLOWER_DEFAULT);
                            builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_DEAD_BUSH_BADLANDS);
                            builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_FIREFLY_BUSH_NEAR_WATER);
                        }))
                );
                this.register(PooBiomes.GRASSLANDS, new Biome.BiomeBuilder()
                        .temperature(1.2f)
                        .downfall(1f)
                        .hasPrecipitation(false)
                        .mobSpawnSettings(MobSpawnSettings.EMPTY)
                        .specialEffects(new BiomeSpecialEffects(
                                4445678, // water color
                                Optional.of(1355008), // foliage color
                                Optional.empty(), // dry foliage color
                                Optional.of(2802176), // grass color override
                                BiomeSpecialEffects.GrassColorModifier.NONE
                        ))
                        .setAttribute(EnvironmentAttributes.WATER_FOG_COLOR, 0x270131)
                        .generationSettings(BiomeGenerationSettings.EMPTY)
                );
                this.register(PooBiomes.DIMWORLD_GRAVELY_FIELDS, new Biome.BiomeBuilder()
                        .temperature(0.8f)
                        .downfall(0.5f)
                        .hasPrecipitation(false)
                        .mobSpawnSettings(MobSpawnSettings.EMPTY)
                        .specialEffects(new BiomeSpecialEffects(
                                0x444455, // water color
                                Optional.of(0x444455), // foliage color
                                Optional.empty(), // dry foliage color
                                Optional.of(0x444455), // grass color override
                                BiomeSpecialEffects.GrassColorModifier.NONE
                        ))
                        .setAttribute(EnvironmentAttributes.SKY_COLOR, 0x444444)
                        .setAttribute(EnvironmentAttributes.AMBIENT_PARTICLES, AmbientParticle.of(ParticleTypes.COPPER_FIRE_FLAME, 0.0008f))
                        .generationSettings(this.genSettings(builder -> {
                            builder.addFeature(GenerationStep.Decoration.UNDERGROUND_STRUCTURES, CavePlacements.MONSTER_ROOM);
                            builder.addFeature(GenerationStep.Decoration.UNDERGROUND_STRUCTURES, CavePlacements.MONSTER_ROOM_DEEP);
                            dimworldOres(builder);
                            builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_FIREFLY_BUSH_NEAR_WATER);
                        }))
                );
                this.register(PooBiomes.HYRULE, new Biome.BiomeBuilder()
                        .temperature(1.2f)
                        .downfall(1f)
                        .hasPrecipitation(false)
                        .mobSpawnSettings(MobSpawnSettings.EMPTY)
                        .specialEffects(new BiomeSpecialEffects(
                                ARGB.colorFromFloat(1f, 0.11f, 0.36f, 0.87f), // water color
                                Optional.of(1355008), // foliage color
                                Optional.empty(), // dry foliage color
                                Optional.of(2802176), // grass color override
                                BiomeSpecialEffects.GrassColorModifier.NONE
                        ))
                        .setAttribute(EnvironmentAttributes.WATER_FOG_COLOR, 0x050533)
                        .generationSettings(BiomeGenerationSettings.EMPTY)
                );
                this.register(PooBiomes.HYRULE_CASTLE, new Biome.BiomeBuilder()
                        .temperature(1.4f)
                        .downfall(0.2f)
                        .hasPrecipitation(false)
                        .mobSpawnSettings(MobSpawnSettings.EMPTY)
                        .specialEffects(new BiomeSpecialEffects(
                                6062520, // water color
                                Optional.of(5658198), // foliage color
                                Optional.empty(), // dry foliage color
                                Optional.of(7039851), // grass color override
                                BiomeSpecialEffects.GrassColorModifier.NONE
                        ))
                        .setAttribute(EnvironmentAttributes.CLOUD_HEIGHT, 450f)
                        .setAttribute(EnvironmentAttributes.SKY_COLOR, 0x9b3326)
                        .setAttribute(EnvironmentAttributes.FOG_COLOR, 0x6b241b)
                        .setAttribute(EnvironmentAttributes.FOG_START_DISTANCE, 12f)
                        .setAttribute(EnvironmentAttributes.FOG_END_DISTANCE, 256f)
                        .setAttribute(EnvironmentAttributes.SKY_LIGHT_FACTOR, 0.8f)
                        .setAttribute(EnvironmentAttributes.SKY_LIGHT_COLOR, 0xffaaaa)
                        .setAttribute(EnvironmentAttributes.EYEBLOSSOM_OPEN, TriState.TRUE)
                        .setAttribute(EnvironmentAttributes.PIGLINS_ZOMBIFY, false)
                        .setAttribute(EnvironmentAttributes.WATER_FOG_COLOR, 0xadadad)
                        .setAttribute(EnvironmentAttributes.CLOUD_COLOR, 0xaa8d2525)
                        .generationSettings(BiomeGenerationSettings.EMPTY)
                );
                this.register(PooBiomes.HYRULE_OCEAN, new Biome.BiomeBuilder()
                        .temperature(1.2f)
                        .downfall(1f)
                        .hasPrecipitation(false)
                        .mobSpawnSettings(MobSpawnSettings.EMPTY)
                        .specialEffects(new BiomeSpecialEffects(
                                ARGB.colorFromFloat(1f, 0.18f, 0.3f, 0.9f), // water color
                                Optional.of(1355008), // foliage color
                                Optional.empty(), // dry foliage color
                                Optional.of(2802176), // grass color override
                                BiomeSpecialEffects.GrassColorModifier.NONE
                        ))
                        .setAttribute(EnvironmentAttributes.WATER_FOG_COLOR, 0x050533)
                        .generationSettings(this.genSettings(builder -> {
                            builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AquaticPlacements.WARM_OCEAN_VEGETATION);
                            builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AquaticPlacements.SEAGRASS_WARM);
                            builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AquaticPlacements.SEA_PICKLE);
                        }))
                );
                this.register(PooBiomes.MISSINGNO, new Biome.BiomeBuilder()
                        .temperature(1.2f)
                        .downfall(1f)
                        .hasPrecipitation(false)
                        .mobSpawnSettings(MobSpawnSettings.EMPTY)
                        .specialEffects(new BiomeSpecialEffects(
                                14237668, // water color
                                Optional.of(16711901), // foliage color
                                Optional.empty(), // dry foliage color
                                Optional.empty(), // grass color override
                                BiomeSpecialEffects.GrassColorModifier.NONE
                        ))
                        .setAttribute(EnvironmentAttributes.SKY_COLOR, 0xff00dd)
                        .setAttribute(EnvironmentAttributes.FOG_COLOR, 0xff44ee)
                        .setAttribute(EnvironmentAttributes.WATER_FOG_COLOR, 0x330532)
                        .setAttribute(EnvironmentAttributes.BACKGROUND_MUSIC, this.quickMusic(SoundEvents.MUSIC_CREATIVE))
                        .generationSettings(BiomeGenerationSettings.EMPTY)
                );
                this.register(PooBiomes.OUTER_HYRULE, new Biome.BiomeBuilder()
                        .temperature(1.2f)
                        .downfall(1f)
                        .hasPrecipitation(false)
                        .mobSpawnSettings(MobSpawnSettings.EMPTY)
                        .specialEffects(new BiomeSpecialEffects(
                                1793246, // water color
                                Optional.of(1355008), // foliage color
                                Optional.empty(), // dry foliage color
                                Optional.of(2802176), // grass color override
                                BiomeSpecialEffects.GrassColorModifier.NONE
                        ))
                        .setAttribute(EnvironmentAttributes.WATER_FOG_COLOR, 0x050533)
                        .generationSettings(BiomeGenerationSettings.EMPTY)
                );
                this.register(PooBiomes.SKY_ISLANDS, new Biome.BiomeBuilder()
                        .temperature(0.6f)
                        .downfall(0.5f)
                        .hasPrecipitation(false)
                        .mobSpawnSettings(MobSpawnSettings.EMPTY)
                        .specialEffects(new BiomeSpecialEffects(
                                1662177, // water color
                                Optional.of(16303894), // foliage color
                                Optional.empty(), // dry foliage color
                                Optional.of(16762880), // grass color override
                                BiomeSpecialEffects.GrassColorModifier.NONE
                        ))
                        .setAttribute(EnvironmentAttributes.BACKGROUND_MUSIC, this.quickMusic(SoundEvents.MUSIC_CREDITS))
                        .setAttribute(EnvironmentAttributes.SKY_COLOR, 0xc9ddfd)
                        .setAttribute(EnvironmentAttributes.FOG_COLOR, 0xc3d4ee)
                        .setAttribute(EnvironmentAttributes.WATER_FOG_COLOR, 0x050533)
                        .generationSettings(BiomeGenerationSettings.EMPTY)
                );
                this.register(PooBiomes.SLENDERMAN_LAND, new Biome.BiomeBuilder()
                        .temperature(1f)
                        .downfall(0.8f)
                        .hasPrecipitation(false)
                        .mobSpawnSettings(MobSpawnSettings.EMPTY)
                        .specialEffects(new BiomeSpecialEffects(
                                0x777777, // water color
                                Optional.of(0x777777), // foliage color
                                Optional.empty(), // dry foliage color
                                Optional.of(0x777777), // grass color override
                                BiomeSpecialEffects.GrassColorModifier.NONE
                        ))
                        .setAttribute(EnvironmentAttributes.AMBIENT_PARTICLES, AmbientParticle.of(ParticleTypes.ASH, 0.01f))
                        .setAttribute(EnvironmentAttributes.SKY_COLOR, 0x333333)
                        .setAttribute(EnvironmentAttributes.FOG_COLOR, 0x555555)
                        .setAttribute(EnvironmentAttributes.WATER_FOG_COLOR, 0x444444)
                        .generationSettings(BiomeGenerationSettings.EMPTY)
                );
                this.register(PooBiomes.DIMWORLD_WET_SKY, new Biome.BiomeBuilder()
                        .temperature(0.7f)
                        .downfall(0.8f)
                        .hasPrecipitation(false)
                        .mobSpawnSettings(MobSpawnSettings.EMPTY)
                        .specialEffects(new BiomeSpecialEffects(
                                0x666688, // water color
                                Optional.of(0x666688), // foliage color
                                Optional.empty(), // dry foliage color
                                Optional.of(0x666688), // grass color override
                                BiomeSpecialEffects.GrassColorModifier.NONE
                        ))
                        .setAttribute(EnvironmentAttributes.AMBIENT_PARTICLES, List.of(
                                new AmbientParticle(ParticleTypes.COPPER_FIRE_FLAME, 0.0002f),
                                new AmbientParticle(ParticleTypes.FIREFLY, 0.006f)
                        ))
                        .setAttribute(EnvironmentAttributes.SKY_COLOR, 0x555555)
                        .setAttribute(EnvironmentAttributes.FOG_COLOR, 0x202040)
                        .setAttribute(EnvironmentAttributes.FOG_START_DISTANCE, 8f)
                        .setAttribute(EnvironmentAttributes.FOG_END_DISTANCE, 240f)
                        .generationSettings(BiomeGenerationSettings.EMPTY)
                );
            }

            private void register(ResourceKey<Biome> key, Biome.BiomeBuilder builder) {
                this.context.register(key, builder.build());
            }

            private BackgroundMusic quickMusic(Holder<SoundEvent> sound) {
                return new BackgroundMusic(new Music(sound, 20, 60, false));
            }

            private BiomeGenerationSettings genSettings(Consumer<BiomeGenerationSettings.Builder> consumer) {
                HolderGetter<PlacedFeature> placedFeatures = this.holderGetter(Registries.PLACED_FEATURE);
                HolderGetter<ConfiguredWorldCarver<?>> worldCarvers = this.holderGetter(Registries.CONFIGURED_CARVER);
                BiomeGenerationSettings.Builder builder = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);
                consumer.accept(builder);
                return builder.build();
            }

            private static void dimworldOres(BiomeGenerationSettings.Builder builder) {
                builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_COAL_UPPER);
                builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_COAL_LOWER);
                builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_GOLD);
                builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_GOLD_LOWER);
                builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_REDSTONE);
                builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_REDSTONE_LOWER);
                builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_DIAMOND);
                builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_DIAMOND_MEDIUM);
                builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_DIAMOND_LARGE);
                builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_DIAMOND_BURIED);
                builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_LAPIS);
                builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_LAPIS_BURIED);
                builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_COPPER_LARGE);
                builder.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, MiscOverworldPlacements.FREEZE_TOP_LAYER);
            }
        };
        bootstrap.bootstrap();
    }
}
