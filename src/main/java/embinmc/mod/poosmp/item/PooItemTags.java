package embinmc.mod.poosmp.item;

import embinmc.mod.poosmp.PooSMPMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public interface PooItemTags {
    TagKey<Item> POOSMP_DISCS = create("poosmp_discs");
    TagKey<Item> RED_POO_REPAIR_ITEMS = create("red_poo_repair_items");
    TagKey<Item> BANK_NOTES = create("bank_notes");
    TagKey<Item> DOESNT_SHOW_SELL_VALUE_TOOLTIP = create("doesnt_show_sell_value_tooltip");
    TagKey<Item> MAKES_NETHER_BRICKS_RED = create("makes_nether_bricks_red");
    TagKey<Item> CONVERTIBLE_DISCS = create("convertible_discs");

    TagKey<Item> RED_POO_SET = create("red_poo_set");
    TagKey<Item> RED_POO_ARMOR_SET = create("red_poo_armor_set");
    TagKey<Item> RED_POO_TOOL_SET = create("red_poo_tool_set");

    private static TagKey<Item> create(String id) {
        return TagKey.create(Registries.ITEM, PooSMPMod.id(id));
    }
}
