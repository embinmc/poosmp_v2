package embinmc.mod.poosmp.block;

import embinmc.mod.poosmp.PooSMPMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;

public interface PooBlockKeys {
    ResourceKey<Block> POOP_BLOCK = create("poop_block");
    ResourceKey<Block> MISSINGNO_BLOCK = create("missingno");

    private static ResourceKey<Block> create(String id) {
        return ResourceKey.create(Registries.BLOCK, PooSMPMod.id(id));
    }
}
