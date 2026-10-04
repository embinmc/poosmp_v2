package embinmc.mod.poosmp.block;

import embinmc.mod.poosmp.PooSMPMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;

public interface PooBlockKeys {
    ResourceKey<Block> POOP_BLOCK =             create("poop_block");
    ResourceKey<Block> MISSINGNO_BLOCK =        create("missingno");
    ResourceKey<Block> POOP_BRICKS =            create("poop_bricks");
    ResourceKey<Block> POOP_BRICK_STAIRS =      create("poop_brick_stairs");
    ResourceKey<Block> POOP_BRICK_SLAB =        create("poop_brick_slab");
    ResourceKey<Block> POOP_BRICK_WALL =        create("poop_brick_wall");
    ResourceKey<Block> RED_NETHER_BRICK_FENCE = create("red_nether_brick_fence");
    ResourceKey<Block> ANNOYANCE_SUS =          create("im_gonna_kill_myself");
    ResourceKey<Block> DDE_BLOCK =              create("ddededodediamante_block");
    ResourceKey<Block> PENIS_BLOCK =            create(Identifier.DEFAULT_NAMESPACE, "penis");
    ResourceKey<Block> BANKERS_TABLE =          create("bankers_table");
    ResourceKey<Block> RED_POO_BLOCK =          create("red_poo_block");
    ResourceKey<Block> ANNOYANCE_DRAGON =       create("ear_destroyer_9000");
    ResourceKey<Block> FAKE_DIRT =              create("fake_dirt");
    ResourceKey<Block> FAKE_GRASS_BLOCK =       create("fake_grass_block");
    ResourceKey<Block> FAKE_STONE =             create("fake_stone");
    ResourceKey<Block> RIGGED_STONE =           create("rigged_stone");
    ResourceKey<Block> DIM_MOSS_BLOCK =         create("dim_moss_block");
    ResourceKey<Block> DIM_MOSS_CARPET =        create("dim_moss_carpet");
    ResourceKey<Block> ANNOYANCE_DEHEED =       create("glue_factory");

    private static ResourceKey<Block> create(String namespace, String id) {
        return ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(namespace, id));
    }

    private static ResourceKey<Block> create(String id) {
        return create(PooSMPMod.MOD_ID, id);
    }
}
