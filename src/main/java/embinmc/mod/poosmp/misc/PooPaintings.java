package embinmc.mod.poosmp.misc;

import embinmc.mod.poosmp.PooSMPMod;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.decoration.painting.PaintingVariant;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

public interface PooPaintings {
    ResourceKey<PaintingVariant> WEDDING = create("wedding");
    ResourceKey<PaintingVariant> PET = create("pet");
    ResourceKey<PaintingVariant> MONSTER_GENOCIDE = create("monster_genocide");
    ResourceKey<PaintingVariant> PENGUINMOD = create("penguinmod");
    ResourceKey<PaintingVariant> TECH_TIP = create("tech_tip");
    ResourceKey<PaintingVariant> THE_GLUE_FACTORY = create("the_glue_factory");
    ResourceKey<PaintingVariant> DRAGON_ANNOYANCE = create("dragon_annoyance");
    ResourceKey<PaintingVariant> THE_PURPLE_AWARDS = create("the_purple_awards");

    private static ResourceKey<PaintingVariant> create(String id) {
        return ResourceKey.create(Registries.PAINTING_VARIANT, PooSMPMod.id(id));
    }

    interface Tags {
        TagKey<PaintingVariant> POOSMP_PAINTINGS = tag("poosmp_paintings");
        TagKey<PaintingVariant> PLACEABLE_POOSMP_PAINTINGS = tag("placeable_poosmp_paintings");
        TagKey<PaintingVariant> NON_PLACEABLE_POOSMP_PAINTINGS = tag("non_placeable_poosmp_paintings");

        private static TagKey<PaintingVariant> tag(String id) {
            return TagKey.create(Registries.PAINTING_VARIANT, PooSMPMod.id(id));
        }
    }

    static void bootstrap(BootstrapContext<PaintingVariant> context) {
        QuickBootstrapLoader<PaintingVariant> bootstrapLoader = new QuickBootstrapLoader<>(context) {
            @Override
            public void bootstrap() {
                this.register(PooPaintings.MONSTER_GENOCIDE, 3, 3, "Monster Genocide", "Cubey");
                this.register(PooPaintings.WEDDING, 4, 2, "Wedding", "Cubey");
                this.register(PooPaintings.PET, 3, 3, "Pet", "Cubey");
                this.register(PooPaintings.PENGUINMOD, 2, 2, "PenguinMod", "Embin");
                this.register(PooPaintings.TECH_TIP, 4, 4, "Tech Tip", "Embin");
                this.register(PooPaintings.THE_GLUE_FACTORY, 3, 3, "The Glue Factory", "Embin");
                this.register(PooPaintings.DRAGON_ANNOYANCE, 1, 1, "Dragon Annoyance", null);
                this.register(PooPaintings.THE_PURPLE_AWARDS, 3, 3, "The Purple Awards", "Embin");
            }

            private void register(ResourceKey<PaintingVariant> key, int width, int height, String name, @Nullable String requester) {
                Component title = Component.literal(name).withStyle(ChatFormatting.YELLOW);
                Component author = requester == null ? null : Component.literal("Requested by " + requester).withStyle(ChatFormatting.GRAY);
                this.context.register(key, new PaintingVariant(width, height, key.identifier(), Optional.of(title), Optional.ofNullable(author)));
            }
        };
        bootstrapLoader.bootstrap();
    }
}
