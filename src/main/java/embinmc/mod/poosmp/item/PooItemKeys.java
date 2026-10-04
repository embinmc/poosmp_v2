package embinmc.mod.poosmp.item;

import embinmc.mod.poosmp.PooSMPMod;
import embinmc.mod.poosmp.block.PooBlockKeys;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public interface PooItemKeys {
    ResourceKey<Item> POOP_STICK = create("poop_stick");
    ResourceKey<Item> SERVER_SAYS_WHAT_STICK = create("server_says_what_stick");

    // block items
    ResourceKey<Item> POOP_BLOCK = create(PooBlockKeys.POOP_BLOCK);
    ResourceKey<Item> MISSINGNO_BLOCK = create(PooBlockKeys.MISSINGNO_BLOCK);

    private static ResourceKey<Item> create(String id) {
        return ResourceKey.create(Registries.ITEM, PooSMPMod.id(id));
    }

    private static ResourceKey<Item> create(ResourceKey<Block> block) {
        return ResourceKey.create(Registries.ITEM, block.identifier());
    }
}
