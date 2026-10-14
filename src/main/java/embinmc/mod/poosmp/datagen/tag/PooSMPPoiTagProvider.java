package embinmc.mod.poosmp.datagen.tag;

import embinmc.mod.poosmp.misc.PooSMPPoi;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.PoiTypeTags;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

public class PooSMPPoiTagProvider extends FabricTagProvider<PoiType> {
    public PooSMPPoiTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, Registries.POINT_OF_INTEREST_TYPE, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.@NonNull Provider wrapperLookup) {
        this.builder(PoiTypeTags.ACQUIRABLE_JOB_SITE).add(PooSMPPoi.BANKER_KEY);
    }
}
