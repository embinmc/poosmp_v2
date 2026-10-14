package embinmc.mod.poosmp.block;

import embinmc.mod.poosmp.PooSMPMod;
import embinmc.mod.poosmp.annoyance.Annoyance;
import embinmc.mod.poosmp.annoyance.Annoyances;
import embinmc.mod.poosmp.misc.PooFeatures;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import java.util.function.Function;

public interface PooBlocks {
    static void init() {
        PooSMPMod.LOGGER.info("Making PooSMP blocks!!!");
    }

    private static Block register(ResourceKey<Block> id, Function<BlockBehaviour.Properties, Block> block, BlockBehaviour.Properties properties) {
        return Registry.register(BuiltInRegistries.BLOCK, id, block.apply(properties.setId(id)));
    }

    private static Block register(ResourceKey<Block> id, BlockBehaviour.Properties properties) {
        return register(id, Block::new, properties);
    }

    private static Block registerStair(ResourceKey<Block> id, Block block) {
        return register(id, properties -> new StairBlock(block.defaultBlockState(), properties), BlockBehaviour.Properties.ofLegacyCopy(block));
    }

    private static Block registerSlab(ResourceKey<Block> id, Block block) {
        return register(id, SlabBlock::new, BlockBehaviour.Properties.ofLegacyCopy(block));
    }

    private static Block registerWall(ResourceKey<Block> id, Block block) {
        return register(id, WallBlock::new, BlockBehaviour.Properties.ofLegacyCopy(block).forceSolidOn());
    }

    private static Block registerFence(ResourceKey<Block> id, Block block) {
        return register(id, FenceBlock::new, BlockBehaviour.Properties.ofLegacyCopy(block));
    }

    private static BlockBehaviour.Properties properties() {
        return BlockBehaviour.Properties.of();
    }

    Block POOP_BLOCK = register(PooBlockKeys.POOP_BLOCK, copy(Blocks.MUD).mapColor(DyeColor.BROWN));
    Block MISSINGNO_BLOCK = register(PooBlockKeys.MISSINGNO_BLOCK,
            properties().requiresCorrectToolForDrops().mapColor(DyeColor.MAGENTA).strength(1.5f)
    );
    Block POOP_BRICKS = register(PooBlockKeys.POOP_BRICKS, copy(Blocks.MUD_BRICKS).mapColor(MapColor.COLOR_ORANGE));
    Block POOP_BRICK_STAIRS = registerStair(PooBlockKeys.POOP_BRICK_STAIRS, POOP_BRICKS);
    Block POOP_BRICK_SLAB = registerSlab(PooBlockKeys.POOP_BRICK_SLAB, POOP_BRICKS);
    Block POOP_BRICK_WALL = registerWall(PooBlockKeys.POOP_BRICK_WALL, POOP_BRICKS);
    Block RED_NETHER_BRICK_FENCE = registerFence(PooBlockKeys.RED_NETHER_BRICK_FENCE, Blocks.RED_NETHER_BRICKS);
    Block ANNOYANCE_SUS = registerAnnoyance(PooBlockKeys.ANNOYANCE_SUS, Annoyances.SUS, MapColor.COLOR_RED);
    Block DDE_BLOCK = register(PooBlockKeys.DDE_BLOCK, properties().mapColor(DyeColor.MAGENTA).strength(1f));
    Block PENIS_BLOCK = register(PooBlockKeys.PENIS_BLOCK, GrassBlock::new, copy(Blocks.GRASS_BLOCK));
    Block BANKERS_TABLE = register(PooBlockKeys.BANKERS_TABLE, copy(Blocks.FLETCHING_TABLE).mapColor(DyeColor.BROWN));
    Block RED_POO_BLOCK = register(PooBlockKeys.RED_POO_BLOCK, properties()
            .requiresCorrectToolForDrops().mapColor(DyeColor.RED)
            .strength(2.5f).sound(SoundType.BONE_BLOCK)
    );
    Block DRAGON_ANNOYANCE = registerAnnoyance(PooBlockKeys.ANNOYANCE_DRAGON, Annoyances.DRAGON, MapColor.TERRACOTTA_WHITE);
    Block FAKE_DIRT = registerFake(PooBlockKeys.FAKE_DIRT);
    Block FAKE_GRASS_BLOCK = registerFake(PooBlockKeys.FAKE_GRASS_BLOCK);
    Block FAKE_STONE = registerFake(PooBlockKeys.FAKE_STONE);
    Block DIM_MOSS_BLOCK = register(PooBlockKeys.DIM_MOSS_BLOCK,
            properties -> new BonemealableFeaturePlacerBlock(PooFeatures.DIM_MOSS_PATCH_BONEMEAL, properties),
            properties()
                    .mapColor(MapColor.COLOR_GRAY)
                    .pushReaction(PushReaction.DESTROY)
                    .sound(SoundType.MOSS)
                    .strength(0.1f)
    );
    Block DIM_MOSS_CARPET = register(PooBlockKeys.DIM_MOSS_CARPET, CarpetBlock::new, properties()
            .mapColor(MapColor.COLOR_GRAY).strength(0.1f)
            .sound(SoundType.MOSS_CARPET).pushReaction(PushReaction.DESTROY)
    );
    Block DEHEED_ANNOYANCE = registerAnnoyance(PooBlockKeys.ANNOYANCE_DEHEED, Annoyances.DEHEED, MapColor.TERRACOTTA_WHITE, Annoyances::onSoundDeheed);

    private static BlockBehaviour.Properties copy(Block block) {
        return BlockBehaviour.Properties.ofFullCopy(block);
    }

    private static Block registerAnnoyance(ResourceKey<Block> id, Annoyance annoyance, MapColor mapColor) {
        return register(id, p -> new AnnoyanceBlock(annoyance, p), properties().mapColor(mapColor).strength(1f));
    }

    private static Block registerAnnoyance(ResourceKey<Block> id, Annoyance annoyance, MapColor mapColor, AnnoyanceBlock.OnSound onSound) {
        return register(id, p -> new AnnoyanceBlock(annoyance, p, onSound), properties().mapColor(mapColor).strength(1f));
    }

    private static Block registerFake(ResourceKey<Block> id) {
        return register(id, Block::new, properties().instabreak().noCollision());
    }

    private static Block registerRigged(ResourceKey<Block> id, Block riggedOf) {
        return register(id, properties -> new RiggedBlock(riggedOf, properties), copy(riggedOf).instabreak());
    }
}
