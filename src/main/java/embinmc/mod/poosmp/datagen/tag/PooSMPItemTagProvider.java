package embinmc.mod.poosmp.datagen.tag;

import embinmc.mod.poosmp.item.PooItemKeys;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.ItemTags;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

public class PooSMPItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public PooSMPItemTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public String toString() {
        return "PooSMP: " + super.toString();
    }

    @Override
    protected void addTags(HolderLookup.@NonNull Provider wrapperLookup) {
        this.builder(ItemTags.FENCES).setReplace(false)
                .add(PooItemKeys.RED_NETHER_BRICK_FENCE);
        this.builder(ItemTags.STAIRS).setReplace(false)
                .add(PooItemKeys.POOP_BRICK_STAIRS);
        this.builder(ItemTags.SLABS).setReplace(false)
                .add(PooItemKeys.POOP_BRICK_SLAB);
        this.builder(ItemTags.WALLS).setReplace(false)
                .add(PooItemKeys.POOP_BRICK_WALL);
    }
}
