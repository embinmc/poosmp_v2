package embinmc.mod.poosmp.block;

import embinmc.mod.poosmp.PooSMPMod;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

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

    private static BlockBehaviour.Properties copy(Block block) {
        return BlockBehaviour.Properties.ofFullCopy(block);
    }
}
