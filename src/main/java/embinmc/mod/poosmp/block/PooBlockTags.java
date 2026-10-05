package embinmc.mod.poosmp.block;

import embinmc.mod.poosmp.PooSMPMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public interface PooBlockTags {
    TagKey<Block> INCORRECT_FOR_RED_POO_TOOLS = create("incorrect_for_red_poo_tools");

    private static TagKey<Block> create(String id) {
        return TagKey.create(Registries.BLOCK, PooSMPMod.id(id));
    }
}
