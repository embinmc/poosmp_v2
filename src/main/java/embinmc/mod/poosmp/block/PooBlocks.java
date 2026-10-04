package embinmc.mod.poosmp.block;

import embinmc.mod.poosmp.PooSMPMod;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Function;

public interface PooBlocks {
    static void init() {
        PooSMPMod.LOGGER.info("Making PooSMP blocks!!!");
    }

    private static Block register(ResourceKey<Block> id, Function<BlockBehaviour.Properties, Block> block) {
        return null;
    }
}
