package embinmc.mod.poosmp.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

public class PooSMPBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public PooSMPBlockTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public String toString() {
        return "PooSMP: " + super.toString();
    }

    @Override
    protected void addTags(HolderLookup.@NonNull Provider wrapperLookup) {

    }
}
