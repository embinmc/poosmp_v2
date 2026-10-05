package embinmc.mod.poosmp.item.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import embinmc.mod.poosmp.PooSMPMod;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;

import java.util.List;

public record WeddingRing(ItemAttributeModifiers notMarriedAttributes, ItemAttributeModifiers marriedAttributes) {
    public static final Codec<WeddingRing> CODEC = RecordCodecBuilder.create(wr -> wr.group(
            ItemAttributeModifiers.CODEC.fieldOf("not_married_attributes").forGetter(WeddingRing::notMarriedAttributes),
            ItemAttributeModifiers.CODEC.fieldOf("married_attributes").forGetter(WeddingRing::marriedAttributes)
    ).apply(wr, WeddingRing::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, WeddingRing> STREAM_CODEC = StreamCodec.composite(
            ItemAttributeModifiers.STREAM_CODEC, WeddingRing::notMarriedAttributes,
            ItemAttributeModifiers.STREAM_CODEC, WeddingRing::marriedAttributes,
            WeddingRing::new
    );
    public static final List<String> MARRIED_USERS = List.of("Embin", "ddededodediamant", "_thecubic_", "JeremyGamer13");

    public boolean isUserMarried(ItemStack itemStack, ServerPlayer player) {
        return itemStack.has(PooComponents.FORCE_MARRIED) || MARRIED_USERS.contains(player.getPlainTextName());
    }

    public static ItemAttributeModifiers defaultNotMarriedAttributes() {
        return ItemAttributeModifiers.builder().add(
                Attributes.ATTACK_DAMAGE,
                new AttributeModifier(
                        PooSMPMod.id("wedding_ring_attack_damage"), 2, AttributeModifier.Operation.ADD_VALUE
                ),
                EquipmentSlotGroup.HAND
        ).build();
    }

    public static ItemAttributeModifiers defaultMarriedAttributes() {
        return ItemAttributeModifiers.builder().add(
                Attributes.MAX_HEALTH,
                new AttributeModifier(
                        PooSMPMod.id("wedding_ring_hp_buff"), 10, AttributeModifier.Operation.ADD_VALUE
                ),
                EquipmentSlotGroup.OFFHAND
        ).add(
                Attributes.ENTITY_INTERACTION_RANGE,
                new AttributeModifier(
                        PooSMPMod.id("wedding_ring_entity_reach_buff"), 1.0F, AttributeModifier.Operation.ADD_VALUE
                ),
                EquipmentSlotGroup.OFFHAND
        ).add(
                Attributes.ATTACK_DAMAGE,
                new AttributeModifier(
                        PooSMPMod.id("wedding_ring_attack_damage"), 8, AttributeModifier.Operation.ADD_VALUE
                ),
                EquipmentSlotGroup.HAND
        ).add(
                Attributes.ARMOR_TOUGHNESS,
                new AttributeModifier(
                        PooSMPMod.id("wedding_ring_armor_toughness"), 6, AttributeModifier.Operation.ADD_VALUE
                ),
                EquipmentSlotGroup.OFFHAND
        ).build();
    }
}
