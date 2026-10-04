package embinmc.mod.poosmp.datagen.tag;

import embinmc.mod.poosmp.block.PooBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;
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
        this.valueLookupBuilder(BlockTags.MINEABLE_WITH_PICKAXE).setReplace(false)
                .add(PooBlocks.POOP_BLOCK)
                .add(PooBlocks.MISSINGNO_BLOCK)
                .add(PooBlocks.POOP_BRICKS)
                .add(PooBlocks.POOP_BRICK_STAIRS)
                .add(PooBlocks.POOP_BRICK_SLAB)
                .add(PooBlocks.POOP_BRICK_WALL)
                .add(PooBlocks.RED_NETHER_BRICK_FENCE)
        ;
    }
}
