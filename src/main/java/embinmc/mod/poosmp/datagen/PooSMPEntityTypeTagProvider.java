package embinmc.mod.poosmp.datagen;

import embinmc.mod.poosmp.PooEntityTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.entity.EntityType;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

public class PooSMPEntityTypeTagProvider extends FabricTagProvider.EntityTypeTagProvider {
    public PooSMPEntityTypeTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public String toString() {
        return "PooSMP: " + super.toString();
    }

    @Override
    protected void addTags(HolderLookup.@NonNull Provider provider) {
        this.valueLookupBuilder(PooEntityTags.IMMUNE_TO_EXPLOSIONS)
                .add(EntityType.HAPPY_GHAST)
                .add(EntityType.ITEM_FRAME)
                .add(EntityType.PAINTING);
        this.valueLookupBuilder(PooEntityTags.IMMUNE_TO_EXPLOSIONS_WHEN_NAMED)
                .add(EntityType.PIG)
                .add(EntityType.WOLF)
                .add(EntityType.CAT);
    }
}
