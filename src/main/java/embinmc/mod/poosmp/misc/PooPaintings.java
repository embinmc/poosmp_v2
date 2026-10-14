package embinmc.mod.poosmp.misc;

import embinmc.mod.poosmp.PooSMPMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.decoration.painting.PaintingVariant;

public interface PooPaintings {
    ResourceKey<PaintingVariant> WEDDING = create("wedding");
    ResourceKey<PaintingVariant> PET = create("pet");
    ResourceKey<PaintingVariant> MONSTER_GENOCIDE = create("monster_genocide");
    ResourceKey<PaintingVariant> PENGUINMOD = create("penguinmod");
    ResourceKey<PaintingVariant> TECH_TIP = create("tech_tip");
    ResourceKey<PaintingVariant> THE_GLUE_FACTORY = create("the_glue_factory");
    ResourceKey<PaintingVariant> DRAGON_ANNOYANCE = create("dragon_annoyance");

    private static ResourceKey<PaintingVariant> create(String id) {
        return ResourceKey.create(Registries.PAINTING_VARIANT, PooSMPMod.id(id));
    }

    interface Tags {
        TagKey<PaintingVariant> POOSMP_PAINTINGS = tag("poosmp_paintings");
        TagKey<PaintingVariant> PLACEABLE_POOSMP_PAINTINGS = tag("placeable_poosmp_paintings");
        TagKey<PaintingVariant> NON_PLACEABLE_POOSMP_PAINTINGS = tag("placeable_poosmp_paintings");

        private static TagKey<PaintingVariant> tag(String id) {
            return TagKey.create(Registries.PAINTING_VARIANT, PooSMPMod.id(id));
        }
    }
}
