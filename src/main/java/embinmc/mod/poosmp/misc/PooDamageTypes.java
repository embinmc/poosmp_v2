package embinmc.mod.poosmp.misc;

import embinmc.mod.poosmp.PooSMPMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;

public interface PooDamageTypes {
    ResourceKey<DamageType> SUICIDE_COMMAND = create("suicide");

    private static ResourceKey<DamageType> create(String id) {
        return ResourceKey.create(Registries.DAMAGE_TYPE, PooSMPMod.id(id));
    }

    interface Tags {
        TagKey<DamageType> IS_MELEE = tag("is_melee");

        private static TagKey<DamageType> tag(String id) {
            return TagKey.create(Registries.DAMAGE_TYPE, PooSMPMod.id(id));
        }
    }
}
