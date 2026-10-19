package embinmc.mod.poosmp.datagen.worldgen;

import embinmc.mod.poosmp.block.PooBlocks;
import embinmc.mod.poosmp.misc.PooBiomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.SurfaceRules.RuleSource;
import net.minecraft.world.level.levelgen.SurfaceRules.ConditionSource;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.CaveSurface;

public interface PooSurfaceRules {
    RuleSource AIR = makeStateRule(Blocks.AIR);
    RuleSource BEDROCK = makeStateRule(Blocks.BEDROCK);
    RuleSource STONE = makeStateRule(Blocks.STONE);
    RuleSource DEEPSLATE = makeStateRule(Blocks.DEEPSLATE);
    RuleSource DIRT = makeStateRule(Blocks.DIRT);
    RuleSource GRASS_BLOCK = makeStateRule(Blocks.GRASS_BLOCK);
    RuleSource GRAVEL = makeStateRule(Blocks.GRAVEL);
    RuleSource SAND = makeStateRule(Blocks.SAND);
    RuleSource SANDSTONE = makeStateRule(Blocks.SANDSTONE);
    RuleSource WATER = makeStateRule(Blocks.WATER);
    RuleSource LAVA = makeStateRule(Blocks.LAVA);
    RuleSource ROOTED_DIRT = makeStateRule(Blocks.ROOTED_DIRT);
    RuleSource CLAY = makeStateRule(Blocks.CLAY);
    RuleSource MUD = makeStateRule(Blocks.MUD);
    RuleSource BLACKSTONE = makeStateRule(Blocks.BLACKSTONE);
    RuleSource COARSE_DIRT = makeStateRule(Blocks.COARSE_DIRT);
    RuleSource DIM_MOSS = makeStateRule(PooBlocks.DIM_MOSS_BLOCK);

    private static RuleSource makeStateRule(final Block block) {
        return SurfaceRules.state(block.defaultBlockState());
    }

    // kill me
    static RuleSource modifiedOverworld(boolean includeExtraSurfaceRules) {
        ConditionSource notUnderwater = SurfaceRules.waterBlockCheck(-1, 0);
        ConditionSource aboveWater = SurfaceRules.waterBlockCheck(0, 0);
        ConditionSource notUnderDeepWater = SurfaceRules.waterStartCheck(-3, -1);
        RuleSource surfaceGravel = SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.ON_CEILING, STONE), GRAVEL);
        RuleSource surfaceSand = SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.ON_CEILING, SANDSTONE), SAND);
        RuleSource surfaceUnderwater = SurfaceRules.sequence(
            SurfaceRules.ifTrue(surfaceNoiseAbove(1.3), CLAY),
            surfaceSand
        );
        RuleSource surfaceShore = SurfaceRules.sequence(
            SurfaceRules.ifTrue(surfaceNoiseAbove(1.4), MUD),
            DIRT
        );
        RuleSource surfaceLand = SurfaceRules.sequence(SurfaceRules.ifTrue(aboveWater, GRASS_BLOCK), DIRT);

        RuleSource biomeSurfaceRule = SurfaceRules.sequence(
            SurfaceRules.ifTrue(
                SurfaceRules.isBiome(PooBiomes.DIMWORLD_GRAVELY_FIELDS),
                SurfaceRules.sequence(
                    SurfaceRules.ifTrue(surfaceNoiseAbove(0.3), surfaceGravel),
                    SurfaceRules.ifTrue(surfaceNoiseAbove(-0.4), DIM_MOSS)
                )
            ),
            SurfaceRules.ifTrue(
                SurfaceRules.isBiome(PooBiomes.DIMWORLD_BARRENS),
                SurfaceRules.sequence(
                    SurfaceRules.ifTrue(surfaceNoiseAbove(0.6), ROOTED_DIRT),
                    SurfaceRules.ifTrue(surfaceNoiseAbove(0.2), BLACKSTONE),
                    SurfaceRules.ifTrue(surfaceNoiseAbove(-0.7), COARSE_DIRT)
                )
            ),
            surfaceLand
        );

        RuleSource underSurfaceRule = SurfaceRules.sequence(
            SurfaceRules.ifTrue(
                SurfaceRules.isBiome(PooBiomes.DIMWORLD_GRAVELY_FIELDS),
                SurfaceRules.sequence(
                    SurfaceRules.ifTrue(surfaceNoiseAbove(0.3), surfaceGravel),
                    SurfaceRules.ifTrue(surfaceNoiseAbove(-0.4), DIM_MOSS)
                )
            ),
            SurfaceRules.ifTrue(
                SurfaceRules.isBiome(PooBiomes.DIMWORLD_BARRENS),
                SurfaceRules.sequence(
                    SurfaceRules.ifTrue(surfaceNoiseAbove(0.6), ROOTED_DIRT),
                    SurfaceRules.ifTrue(surfaceNoiseAbove(0.2), BLACKSTONE),
                    SurfaceRules.ifTrue(surfaceNoiseAbove(-0.7), COARSE_DIRT)
                )
            ),
            DIRT
        );

        RuleSource surfaceFinal = SurfaceRules.sequence(
                SurfaceRules.ifTrue(
                        SurfaceRules.ON_FLOOR,
                        SurfaceRules.sequence(
                                SurfaceRules.ifTrue(notUnderwater, biomeSurfaceRule),
                                SurfaceRules.ifTrue(notUnderDeepWater, surfaceShore),
                                surfaceUnderwater
                        )
                ),
                SurfaceRules.ifTrue(
                        notUnderDeepWater,
                        SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, underSurfaceRule)
                )
        );

        return SurfaceRules.sequence(
                SurfaceRules.ifTrue(SurfaceRules.abovePreliminarySurface(), surfaceFinal), // final surface
                bedrockFloor(),
                SurfaceRules.ifTrue(
                        SurfaceRules.verticalGradient("deepslate", VerticalAnchor.absolute(-25), VerticalAnchor.absolute(-20)),
                        DEEPSLATE
                )
        );
    }

    static RuleSource bedrockFloor() {
        return SurfaceRules.ifTrue(
                SurfaceRules.verticalGradient("bedrock_floor", VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(5)),
                BEDROCK
        );
    }

    private static ConditionSource surfaceNoiseAbove(final double threshold) {
        return SurfaceRules.noiseCondition(Noises.SURFACE, threshold / 8.25, Double.MAX_VALUE);
    }
}
