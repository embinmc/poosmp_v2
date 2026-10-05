package embinmc.mod.poosmp.item;

import embinmc.mod.poosmp.PooSMPMod;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.component.ItemAttributeModifiers;

public class PooTotems {

    public static ItemAttributeModifiers healthTotemAttributes(int hp, String id_suffix) {
        return ItemAttributeModifiers.builder().add(
                Attributes.MAX_HEALTH,
                new AttributeModifier(
                        PooSMPMod.id("health_totem_buff" + id_suffix), hp, AttributeModifier.Operation.ADD_VALUE
                ),
                EquipmentSlotGroup.HAND
        ).build();
    }

    public static ItemAttributeModifiers reachTotemAttributes(float amount, String id_suffix) {
        return ItemAttributeModifiers.builder().add(
                Attributes.BLOCK_INTERACTION_RANGE,
                new AttributeModifier(
                        PooSMPMod.id("reach_totem_blocks" + id_suffix), amount, AttributeModifier.Operation.ADD_VALUE
                ),
                EquipmentSlotGroup.HAND
        ).add(
                Attributes.ENTITY_INTERACTION_RANGE,
                new AttributeModifier(
                        PooSMPMod.id("reach_totem_entities" + id_suffix), amount, AttributeModifier.Operation.ADD_VALUE
                ),
                EquipmentSlotGroup.HAND
        ).build();
    }
}
