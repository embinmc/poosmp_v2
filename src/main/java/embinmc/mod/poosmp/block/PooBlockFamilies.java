package embinmc.mod.poosmp.block;

import net.minecraft.data.BlockFamilies;
import net.minecraft.data.BlockFamily;

public interface PooBlockFamilies {
    BlockFamily POOP_BRICKS = BlockFamilies.familyBuilder(PooBlocks.POOP_BRICKS)
            .stairs(PooBlocks.POOP_BRICK_STAIRS)
            .slab(PooBlocks.POOP_BRICK_SLAB)
            .wall(PooBlocks.POOP_BRICK_WALL)
            .getFamily();
}
