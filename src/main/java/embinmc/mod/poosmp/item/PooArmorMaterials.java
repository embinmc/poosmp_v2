package embinmc.mod.poosmp.item;

import com.google.common.collect.Maps;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;

import java.util.Map;

public interface PooArmorMaterials {
    ArmorMaterial A_RED_POO = new ArmorMaterial(
            40, makeDefense(4, 7, 8, 4, 16),
            25, SoundEvents.ARMOR_EQUIP_DIAMOND, 4.5f, 0.1f,
            PooItemTags.RED_POO_REPAIR_ITEMS, PooEquipmentAssets.RED_POO
    );

    private static Map<ArmorType, Integer> makeDefense(int boots, int leggings, int chestplate, int helmet, int body) {
        return Maps.newEnumMap(Map.of(
                ArmorType.BOOTS, boots,
                ArmorType.LEGGINGS, leggings,
                ArmorType.CHESTPLATE, chestplate,
                ArmorType.HELMET, helmet,
                ArmorType.BODY, body
        ));
    }
}
