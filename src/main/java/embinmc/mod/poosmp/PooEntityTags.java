package embinmc.mod.poosmp;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

public interface PooEntityTags {
    TagKey<EntityType<?>> IMMUNE_TO_EXPLOSIONS = create("immune_to_explosions");
    TagKey<EntityType<?>> IMMUNE_TO_EXPLOSIONS_WHEN_NAMED = create("immune_to_explosions_when_named");

    private static TagKey<EntityType<?>> create(String id) {
        return TagKey.create(Registries.ENTITY_TYPE, PooSMPMod.id(id));
    }
}
