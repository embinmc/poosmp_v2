package embinmc.mod.poosmp.item;

import embinmc.mod.poosmp.PooSMPMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.enchantment.Enchantment;

public interface PooEnchantments {
    ResourceKey<Enchantment> MELEE_PROTECTION = ResourceKey.create(Registries.ENCHANTMENT, PooSMPMod.id("melee_protection"));

    interface Tags {
        TagKey<Enchantment> BANKER_LOW = TagKey.create(Registries.ENCHANTMENT, PooSMPMod.id("banker_tradeable/low"));
        TagKey<Enchantment> BANKER_MID = TagKey.create(Registries.ENCHANTMENT, PooSMPMod.id("banker_tradeable/mid"));
        TagKey<Enchantment> BANKER_HIGH = TagKey.create(Registries.ENCHANTMENT, PooSMPMod.id("banker_tradeable/high"));
    }
}
