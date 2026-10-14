package embinmc.mod.poosmp.datagen.worldgen;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
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

    private static RuleSource makeStateRule(final Block block) {
        return SurfaceRules.state(block.defaultBlockState());
    }

    // kill me
    static RuleSource modifiedOverworld(boolean includeExtraSurfaceRules) {
        ConditionSource aboveOverworldSeaLevel = SurfaceRules.yBlockCheck(VerticalAnchor.absolute(63), 0);
        ConditionSource notUnderwater = SurfaceRules.stoneDepthCheck(-1, false, 0, CaveSurface.FLOOR);
        ConditionSource aboveWater = SurfaceRules.stoneDepthCheck(0, false, 0, CaveSurface.FLOOR);
        ConditionSource notUnderDeepWater = SurfaceRules.waterStartCheck(-3, -1);
        ConditionSource hole = SurfaceRules.hole();
        ConditionSource steep = SurfaceRules.steep();
        RuleSource surfaceUnderwater = SAND;
        RuleSource surfaceShore = DIRT;
        RuleSource surfaceLand = GRASS_BLOCK;

        RuleSource surfaceFinal = SurfaceRules.sequence(
                SurfaceRules.ifTrue(
                        notUnderwater,
                        surfaceShore
                ),
                SurfaceRules.ifTrue(
                        notUnderDeepWater,
                        SurfaceRules.sequence(
                                SurfaceRules.ifTrue(aboveWater, surfaceLand),
                                surfaceShore
                        )
                ),
                SurfaceRules.ifTrue(
                        aboveWater,
                        surfaceUnderwater
                )
        );

        return SurfaceRules.sequence(
                SurfaceRules.ifTrue(SurfaceRules.abovePreliminarySurface(), surfaceFinal), // final surface
                SurfaceRules.ifTrue(
                        SurfaceRules.verticalGradient("bedrock_floor", VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(5)),
                        BEDROCK
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.verticalGradient("deepslate", VerticalAnchor.absolute(-5), VerticalAnchor.absolute(0)),
                        DEEPSLATE
                )
        );
    }
}
