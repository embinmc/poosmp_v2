package embinmc.mod.poosmp.item;

import embinmc.mod.poosmp.PooSMPMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public interface PooItemTags {
    TagKey<Item> POOSMP_DISCS = create("poosmp_discs");
    TagKey<Item> RED_POO_REPAIR_ITEMS = create("red_poo_repair_items");

    private static TagKey<Item> create(String id) {
        return TagKey.create(Registries.ITEM, PooSMPMod.id(id));
    }
}
