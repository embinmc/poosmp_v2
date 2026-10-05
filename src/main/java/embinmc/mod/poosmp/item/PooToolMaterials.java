package embinmc.mod.poosmp.item;

import embinmc.mod.poosmp.block.PooBlockTags;
import net.minecraft.world.item.ToolMaterial;

public interface PooToolMaterials {
    ToolMaterial RED_POO = new ToolMaterial(
            PooBlockTags.INCORRECT_FOR_RED_POO_TOOLS,
            4096, // durability
            10.0F, // speed (yarn: miningSpeedMultiplier)
            5.0f, // attack damage
            25, // enchantability
            PooItemTags.RED_POO_REPAIR_ITEMS
    );
}
