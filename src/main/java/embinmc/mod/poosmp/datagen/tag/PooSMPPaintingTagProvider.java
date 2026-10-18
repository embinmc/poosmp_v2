package embinmc.mod.poosmp.datagen.tag;

import embinmc.mod.poosmp.misc.PooPaintings;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.PaintingVariantTags;
import net.minecraft.world.entity.decoration.painting.PaintingVariant;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

public class PooSMPPaintingTagProvider extends FabricTagProvider<PaintingVariant> {
    public PooSMPPaintingTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, Registries.PAINTING_VARIANT, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.@NonNull Provider wrapperLookup) {
        this.builder(PooPaintings.Tags.PLACEABLE_POOSMP_PAINTINGS)
                .add(PooPaintings.WEDDING)
                .add(PooPaintings.PET)
                .add(PooPaintings.MONSTER_GENOCIDE)
                .add(PooPaintings.PENGUINMOD)
                .add(PooPaintings.TECH_TIP)
                .add(PooPaintings.THE_GLUE_FACTORY)
            .add(PooPaintings.THE_PURPLE_AWARDS)
        ;
        this.builder(PooPaintings.Tags.NON_PLACEABLE_POOSMP_PAINTINGS)
                .add(PooPaintings.DRAGON_ANNOYANCE);
        this.builder(PooPaintings.Tags.POOSMP_PAINTINGS)
                .addTag(PooPaintings.Tags.PLACEABLE_POOSMP_PAINTINGS)
                .addTag(PooPaintings.Tags.NON_PLACEABLE_POOSMP_PAINTINGS)
        ;
        this.builder(PaintingVariantTags.PLACEABLE).addTag(PooPaintings.Tags.POOSMP_PAINTINGS);
    }
}
