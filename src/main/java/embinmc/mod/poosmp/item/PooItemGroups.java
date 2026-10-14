package embinmc.mod.poosmp.item;

import embinmc.mod.poosmp.PooSMPMod;
import embinmc.mod.poosmp.misc.PooPaintings;
import embinmc.mod.poosmp.misc.PooUtil;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;

public class PooItemGroups {
    public static void register() {
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, PooSMPMod.id("poosmp_items"), FabricItemGroup.builder()
                .icon(PooItems.POOPLET::getDefaultInstance)
                .title(Component.literal("PooSMP"))
                .displayItems((itemDisplayParameters, output) -> {
                    HolderLookup.Provider provider = itemDisplayParameters.holders();

                    // building blocks
                    output.accept(PooItems.POOP_BLOCK);
                    output.accept(PooItems.POOP_BRICK);
                    output.accept(PooItems.POOP_BRICKS);
                    output.accept(PooItems.POOP_BRICK_STAIRS);
                    output.accept(PooItems.POOP_BRICK_SLAB);
                    output.accept(PooItems.POOP_BRICK_WALL);
                    output.accept(PooItems.RED_NETHER_BRICK_FENCE);
                    output.accept(PooItems.DDE_BLOCK);
                    output.accept(PooItems.MISSINGNO_BLOCK);
                    output.accept(PooItems.RED_POO_BLOCK);
                    output.accept(PooItems.DIM_MOSS_BLOCK);
                    output.accept(PooItems.DIM_MOSS_CARPET);

                    // materials
                    output.accept(PooItems.DIAMOND_SHARD);
                    output.accept(PooItems.RED_NETHER_BRICK);
                    output.accept(PooItems.RING);
                    output.accept(PooItems.BLANK_MUSIC_DISC);
                    output.accept(PooItems.GEAR);
                    output.accept(PooItems.SCREW);
                    output.accept(PooItems.GLASS_SHARD);
                    output.accept(PooItems.RAW_RED_POO);
                    output.accept(PooItems.RED_POO_INGOT);
                    output.accept(PooItems.NULL_SHARD);

                    // utility items
                    output.accept(PooItems.WEDDING_RING);
                    output.accept(PooItems.TOTEM_OF_HEALTH);
                    output.accept(PooItems.TOTEM_OF_REACH);
                    output.accept(PooItems.ENCHANTED_TOTEM_OF_HEALTH);
                    output.accept(PooItems.ENCHANTED_TOTEM_OF_REACH);
                    output.accept(PooItems.MAGIC_DEVICE);
                    output.accept(PooItems.RIGGED_STONE);

                    // warp sticks
                    output.accept(PooItems.WARP_STICK);
                    output.accept(PooItems.DIMWORLD_STICK);
                    output.accept(PooItems.NULL_STICK);

                    // utility blocks
                    output.accept(PooItems.BANKERS_TABLE);
                    output.accept(PooItems.FAKE_DIRT);
                    output.accept(PooItems.FAKE_GRASS_BLOCK);
                    output.accept(PooItems.FAKE_STONE);

                    // foods
                    output.accept(PooItems.POOPLET);
                    output.accept(PooItems.BANANA);

                    // paintings
                    PooUtil.getPaintingStacks(PooPaintings.Tags.POOSMP_PAINTINGS, provider, output::accept);

                    // tools & armors
                    output.accept(PooItems.RED_POO_HELMET);
                    output.accept(PooItems.RED_POO_CHESTPLATE);
                    output.accept(PooItems.RED_POO_LEGGINGS);
                    output.accept(PooItems.RED_POO_BOOTS);
                    output.accept(PooItems.RED_POO_SWORD);
                    output.accept(PooItems.RED_POO_PICKAXE);
                    output.accept(PooItems.RED_POO_AXE);
                    output.accept(PooItems.RED_POO_SHOVEL);
                    output.accept(PooItems.RED_POO_HOE);

                    // sticks
                    output.accept(PooItems.POOP_STICK);
                    output.accept(PooItems.SERVER_SAYS_WHAT_STICK);
                    output.accept(PooItems.BIOME_STICK);
                    output.accept(PooItems.BOOM_STICK);
                    output.accept(PooItems.JUMPSCARE_STICK);
                    output.accept(PooItems.FUN_STICK);
                    output.accept(PooItems.WIND_STICK);

                    // upgrades and smithing templates
                    output.accept(PooItems.FILL_ARMOR_TRIM_TEMPLATE);
                    output.accept(PooItems.RED_POO_UPGRADE_TEMPLATE);
                    output.accept(PooItems.ITEM_FORCING_BACKPACK_UPGRADE);

                    // discs
                    output.accept(PooItems.DISC_TRIFECTA_CAP);
                    output.accept(PooItems.DISC_BUTTERFLIES_INSTRUMENTAL);
                    output.accept(PooItems.DISC_BUDDY_HOLLY);
                    output.accept(PooItems.DISC_STEREO_MADNESS);
                    output.accept(PooItems.DISC_NOT_LIKE_US);
                    output.accept(PooItems.DISC_RESISTANCE_INSTR);
                    output.accept(PooItems.DISC_BLISS_INSTRUMENTAL);
                    output.accept(PooItems.DISC_ENDLESSLY_INSTRUMENTAL);
                    output.accept(PooItems.DISC_ENDLESSLY);
                    output.accept(PooItems.DISC_ENDLESSLY_STEREO);
                    output.accept(PooItems.DISC_STORY_OF_UNDERTALE);

                    // mob sticks
                    output.accept(PooItems.ZOMBIE_STICK);
                    output.accept(PooItems.VILLAGER_STICK);
                    output.accept(PooItems.COW_STICK);
                    output.accept(PooItems.FROG_STICK);

                    // annoyances
                    output.accept(PooItems.ANNOYANCE_SUS);
                    output.accept(PooItems.DRAGON_ANNOYANCE);
                    output.accept(PooItems.DEHEED_ANNOYANCE);

                    // economy
                    output.accept(PooItems.ONE_DOLLAR_BILL);
                    output.accept(PooItems.TWO_DOLLAR_BILL);
                    output.accept(PooItems.FIVE_DOLLAR_BILL);
                    output.accept(PooItems.TEN_DOLLAR_BILL);
                    output.accept(PooItems.TWENTY_FIVE_DOLLAR_BILL);
                    output.accept(PooItems.FIFTY_DOLLAR_BILL);
                    output.accept(PooItems.HUNDRED_DOLLAR_BILL);

                    // misc
                    output.accept(PooItems.PENIS_BLOCK);
                }).build()
        );
    }
}
