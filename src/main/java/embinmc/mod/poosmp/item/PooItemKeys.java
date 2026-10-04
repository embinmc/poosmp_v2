package embinmc.mod.poosmp.item;

import embinmc.mod.poosmp.PooSMPMod;
import embinmc.mod.poosmp.block.PooBlockKeys;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public interface PooItemKeys {
    private static ResourceKey<Item> create(String id) {
        return ResourceKey.create(Registries.ITEM, PooSMPMod.id(id));
    }

    private static ResourceKey<Item> create(ResourceKey<Block> block) {
        return ResourceKey.create(Registries.ITEM, block.identifier());
    }

    // items
    ResourceKey<Item> POOP_STICK =                    create("poop_stick");
    ResourceKey<Item> SERVER_SAYS_WHAT_STICK =        create("server_says_what_stick");
    ResourceKey<Item> BIOME_STICK =                   create("biome_stick");
    ResourceKey<Item> BOOM_STICK =                    create("boom_stick");
    ResourceKey<Item> ZOMBIE_STICK =                  create("zombie_stick");
    ResourceKey<Item> DIAMOND_SHARD =                 create("diamond_shard");
    ResourceKey<Item> WEDDING_RING =                  create("wedding_ring");
    ResourceKey<Item> RED_NETHER_BRICK =              create("red_nether_brick");
    ResourceKey<Item> POOP_BRICK =                    create("poop_brick");
    ResourceKey<Item> POOPLET =                       create("pooplet");
    ResourceKey<Item> RING =                          create("ring");
    ResourceKey<Item> TOTEM_OF_HEALTH =               create("totem_of_health");
    ResourceKey<Item> WARP_STICK =                    create("warp_stick");
    ResourceKey<Item> FILL_ARMOR_TRIM_TEMPLATE =      create("fill_armor_trim_template");
    ResourceKey<Item> DISC_TRIFECTA_CAP =             create("music_disc/trifecta_cap");
    ResourceKey<Item> DISC_BUTTERFLIES_INSTRUMENTAL = create("music_disc/butterflies_and_hurricanes_instrumental");
    ResourceKey<Item> DISC_BUDDY_HOLLY =              create("music_disc/buddy_holly");
    ResourceKey<Item> DISC_STEREO_MADNESS =           create("music_disc/stereo_madness");
    ResourceKey<Item> DISC_NOT_LIKE_US =              create("music_disc/not_like_us");
    ResourceKey<Item> DISC_RESISTANCE_INSTR =         create("music_disc/resistance_instrumental");
    ResourceKey<Item> TOTEM_OF_REACH =                create("totem_of_reach");
    ResourceKey<Item> BLANK_MUSIC_DISC =              create("blank_music_disc");
    ResourceKey<Item> ENCHANTED_TOTEM_OF_HEALTH =     create("enchanted_totem_of_health");
    ResourceKey<Item> ENCHANTED_TOTEM_OF_REACH =      create("enchanted_totem_of_reach");
    ResourceKey<Item> DISC_BLISS_INSTRUMENTAL =       create("music_disc/bliss_instrumental");
    ResourceKey<Item> DISC_ENDLESSLY_INSTRUMENTAL =   create("music_disc/endlessly_instrumental");
    ResourceKey<Item> DISC_ENDLESSLY =                create("music_disc/endlessly");
    ResourceKey<Item> DISC_ENDLESSLY_STEREO =         create("music_disc/endlessly_stereo");
    ResourceKey<Item> ZAP_STICK =                     create("zap_stick");
    ResourceKey<Item> VILLAGER_STICK =                create("villager_stick");
    ResourceKey<Item> ONE_DOLLAR_BILL =               create("one_dollar_bill");
    ResourceKey<Item> BACON_BUCKET =                  create("bacon_bucket");
    ResourceKey<Item> TWO_DOLLAR_BILL =               create("two_dollar_bill");
    ResourceKey<Item> FIVE_DOLLAR_BILL =              create("five_dollar_bill");
    ResourceKey<Item> TEN_DOLLAR_BILL =               create("ten_dollar_bill");
    ResourceKey<Item> TWENTY_FIVE_DOLLAR_BILL =       create("twenty_five_dollar_bill");
    ResourceKey<Item> FIFTY_DOLLAR_BILL =             create("fifty_dollar_bill");
    ResourceKey<Item> HUNDRED_DOLLAR_BILL =           create("hundred_dollar_bill");
    ResourceKey<Item> COW_STICK =                     create("cow_stick");
    ResourceKey<Item> DISC_STORY_OF_UNDERTALE =       create("music_disc/story_of_undertale");
    ResourceKey<Item> RAW_RED_POO =                   create("raw_red_poo");
    ResourceKey<Item> RED_POO_INGOT =                 create("red_poo_ingot");
    ResourceKey<Item> RED_POO_UPGRADE_TEMPLATE =      create("red_poo_upgrade_smithing_template");
    ResourceKey<Item> BANANA =                        create("banana");
    ResourceKey<Item> RED_POO_SWORD =                 create("red_poo_sword");
    ResourceKey<Item> RED_POO_SHOVEL =                create("red_poo_shovel");
    ResourceKey<Item> RED_POO_PICKAXE =               create("red_poo_pickaxe");
    ResourceKey<Item> RED_POO_AXE =                   create("red_poo_axe");
    ResourceKey<Item> RED_POO_HOE =                   create("red_poo_hoe");
    ResourceKey<Item> RED_POO_HELMET =                create("red_poo_helmet");
    ResourceKey<Item> RED_POO_CHESTPLATE =            create("red_poo_chestplate");
    ResourceKey<Item> RED_POO_LEGGINGS =              create("red_poo_leggings");
    ResourceKey<Item> RED_POO_BOOTS =                 create("red_poo_boots");
    ResourceKey<Item> GEAR =                          create("gear");
    ResourceKey<Item> SCREW =                         create("screw");
    ResourceKey<Item> GLASS_SHARD =                   create("glass_shard");
    ResourceKey<Item> MAGIC_DEVICE =                  create("magic_device");
    ResourceKey<Item> NULL_SHARD =                    create("null_shard");
    ResourceKey<Item> NULL_STICK =                    create("null_stick");
    ResourceKey<Item> JUMPSCARE_STICK =               create("jumpscare_stick");
    ResourceKey<Item> FUN_STICK =                     create("fun_stick");
    ResourceKey<Item> DIMWORLD_STICK =                create("dimworld_stick");
    ResourceKey<Item> FROG_STICK =                    create("frog_stick");
    ResourceKey<Item> WIND_STICK =                    create("wind_stick");
    ResourceKey<Item> ITEM_FORCING_BACKPACK_UPGRADE = create("item_forcing_upgrade");

    // blocks
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
}
