package embinmc.mod.poosmp.item;

import embinmc.mod.poosmp.PooSMPMod;
import embinmc.mod.poosmp.block.PooBlockKeys;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public interface PooItemKeys {
    ResourceKey<Item> POOP_STICK =             create("poop_stick");
    ResourceKey<Item> SERVER_SAYS_WHAT_STICK = create("server_says_what_stick");

    // block items
    ResourceKey<Item> POOP_BLOCK =             create(PooBlockKeys.POOP_BLOCK);
    ResourceKey<Item> MISSINGNO_BLOCK =        create(PooBlockKeys.MISSINGNO_BLOCK);
    ResourceKey<Item> POOP_BRICKS =            create(PooBlockKeys.POOP_BRICKS);
    ResourceKey<Item> POOP_BRICK_STAIRS =      create(PooBlockKeys.POOP_BRICK_STAIRS);
    ResourceKey<Item> POOP_BRICK_SLAB =        create(PooBlockKeys.POOP_BRICK_SLAB);
    ResourceKey<Item> POOP_BRICK_WALL =        create(PooBlockKeys.POOP_BRICK_WALL);
    ResourceKey<Item> RED_NETHER_BRICK_FENCE = create(PooBlockKeys.RED_NETHER_BRICK_FENCE);
    ResourceKey<Item> ANNOYANCE_SUS =          create(PooBlockKeys.ANNOYANCE_SUS);
    ResourceKey<Item> DDE_BLOCK =              create(PooBlockKeys.DDE_BLOCK);
    ResourceKey<Item> PENIS_BLOCK =            create(PooBlockKeys.PENIS_BLOCK);
    ResourceKey<Item> BANKERS_TABLE =          create(PooBlockKeys.BANKERS_TABLE);
    ResourceKey<Item> RED_POO_BLOCK =          create(PooBlockKeys.RED_POO_BLOCK);
    ResourceKey<Item> ANNOYANCE_DRAGON =       create(PooBlockKeys.ANNOYANCE_DRAGON);
    ResourceKey<Item> FAKE_DIRT =              create(PooBlockKeys.FAKE_DIRT);
    ResourceKey<Item> FAKE_GRASS_BLOCK =       create(PooBlockKeys.FAKE_GRASS_BLOCK);
    ResourceKey<Item> FAKE_STONE =             create(PooBlockKeys.FAKE_STONE);
    ResourceKey<Item> RIGGED_STONE =           create(PooBlockKeys.RIGGED_STONE);
    ResourceKey<Item> DIM_MOSS_BLOCK =         create(PooBlockKeys.DIM_MOSS_BLOCK);
    ResourceKey<Item> DIM_MOSS_CARPET =        create(PooBlockKeys.DIM_MOSS_CARPET);
    ResourceKey<Item> ANNOYANCE_DEHEED =       create(PooBlockKeys.ANNOYANCE_DEHEED);

    private static ResourceKey<Item> create(String id) {
        return ResourceKey.create(Registries.ITEM, PooSMPMod.id(id));
    }

    private static ResourceKey<Item> create(ResourceKey<Block> block) {
        return ResourceKey.create(Registries.ITEM, block.identifier());
    }
}
