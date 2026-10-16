package embinmc.mod.poosmp.datagen.worldgen;

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

    private static RuleSource makeStateRule(final Block block) {
        return SurfaceRules.state(block.defaultBlockState());
    }

    // kill me
    static RuleSource modifiedOverworld(boolean includeExtraSurfaceRules) {
        ConditionSource aboveOverworldSeaLevel = SurfaceRules.yBlockCheck(VerticalAnchor.absolute(63), 0);
        ConditionSource notUnderwater = SurfaceRules.waterBlockCheck(-1, 0);
        ConditionSource aboveWater = SurfaceRules.waterBlockCheck(0, 0);
        ConditionSource notUnderDeepWater = SurfaceRules.waterStartCheck(-4, -1);
        RuleSource surfaceGravel = SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.ON_CEILING, STONE), GRAVEL);
        RuleSource surfaceSand = SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.ON_CEILING, SANDSTONE), SAND);
        RuleSource surfaceUnderwater = SurfaceRules.sequence(
                SurfaceRules.ifTrue(SurfaceRules.noiseCondition(Noises.CALCITE, -0.02, 0.02), CLAY),
                surfaceSand
        );
        RuleSource surfaceShore = SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.noiseCondition(Noises.CALCITE, -0.02, 0.02), MUD), DIRT);
        RuleSource surfaceLand = SurfaceRules.sequence(SurfaceRules.ifTrue(aboveWater, GRASS_BLOCK), DIRT);

        RuleSource underSurfaceRule = SurfaceRules.sequence(
                SurfaceRules.ifTrue(
                        SurfaceRules.isBiome(PooBiomes.DIMWORLD_GRAVELY_FIELDS),
                        SurfaceRules.sequence(
                                SurfaceRules.ifTrue(surfaceNoiseAbove(2.0), ROOTED_DIRT),
                                SurfaceRules.ifTrue(surfaceNoiseAbove(1.6), surfaceGravel),
                                surfaceLand
                        )
                ),
                DIRT
        );

        RuleSource biomeSurfaceRule = SurfaceRules.sequence(
                SurfaceRules.ifTrue(
                        SurfaceRules.isBiome(PooBiomes.DIMWORLD_GRAVELY_FIELDS),
                        SurfaceRules.sequence(
                                SurfaceRules.ifTrue(surfaceNoiseAbove(2.0), ROOTED_DIRT),
                                SurfaceRules.ifTrue(surfaceNoiseAbove(1.6), surfaceGravel),
                                surfaceLand
                        )
                ),
                surfaceLand
        );

        RuleSource surfaceFinal = SurfaceRules.sequence(
                SurfaceRules.ifTrue(
                        SurfaceRules.ON_FLOOR,
                        SurfaceRules.ifTrue(notUnderwater, biomeSurfaceRule)
                ),
                SurfaceRules.ifTrue(
                        notUnderDeepWater,
                        SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, underSurfaceRule)
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.ON_FLOOR,
                        SurfaceRules.sequence(
                                SurfaceRules.ifTrue(notUnderDeepWater, surfaceShore),
                                surfaceUnderwater
                        )
                )
        );

        return SurfaceRules.sequence(
                SurfaceRules.ifTrue(SurfaceRules.abovePreliminarySurface(), surfaceFinal), // final surface
                SurfaceRules.ifTrue(
                        SurfaceRules.verticalGradient("bedrock_floor", VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(5)),
                        BEDROCK
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.verticalGradient("deepslate", VerticalAnchor.absolute(-25), VerticalAnchor.absolute(-20)),
                        DEEPSLATE
                )
        );
    }

    private static ConditionSource surfaceNoiseAbove(final double threshold) {
        return SurfaceRules.noiseCondition(Noises.SURFACE, threshold / 8.25, Double.MAX_VALUE);
    }
}
