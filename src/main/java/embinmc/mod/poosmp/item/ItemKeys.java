package embinmc.mod.poosmp.item;

import embinmc.mod.poosmp.PooSMPMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;

public interface ItemKeys {
    ResourceKey<Item> POOP_STICK = create("poop_stick");
    ResourceKey<Item> SERVER_SAYS_WHAT_STICK = create("server_says_what_stick");

    private static ResourceKey<Item> create(String id) {
        return ResourceKey.create(Registries.ITEM, PooSMPMod.id(id));
    }
}
