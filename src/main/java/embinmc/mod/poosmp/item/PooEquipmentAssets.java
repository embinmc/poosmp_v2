package embinmc.mod.poosmp.item;

import embinmc.mod.poosmp.PooSMPMod;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;

public interface PooEquipmentAssets {
    ResourceKey<EquipmentAsset> RED_POO = create("red_poo");

    private static ResourceKey<EquipmentAsset> create(String id) {
        return ResourceKey.create(EquipmentAssets.ROOT_ID, PooSMPMod.id(id));
    }

}
