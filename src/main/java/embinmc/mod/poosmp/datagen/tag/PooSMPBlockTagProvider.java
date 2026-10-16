package embinmc.mod.poosmp.datagen.tag;

import embinmc.mod.poosmp.block.PooBlockTags;
import embinmc.mod.poosmp.block.PooBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
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
                .add(PooBlocks.MISSINGNO_BLOCK)
                .add(PooBlocks.POOP_BRICKS)
                .add(PooBlocks.POOP_BRICK_STAIRS)
                .add(PooBlocks.POOP_BRICK_SLAB)
                .add(PooBlocks.RED_NETHER_BRICK_FENCE)
                .add(PooBlocks.ANNOYANCE_SUS)
                .add(PooBlocks.DDE_BLOCK)
                .add(PooBlocks.RED_POO_BLOCK)
                .add(PooBlocks.RIGGED_STONE)
                .add(PooBlocks.DRAGON_ANNOYANCE)
                .add(PooBlocks.DEHEED_ANNOYANCE)
        ;
        this.valueLookupBuilder(BlockTags.FENCES).setReplace(false)
                .add(PooBlocks.RED_NETHER_BRICK_FENCE)
        ;
        this.valueLookupBuilder(BlockTags.STAIRS).setReplace(false)
                .add(PooBlocks.POOP_BRICK_STAIRS)
        ;
        this.valueLookupBuilder(BlockTags.SLABS).setReplace(false)
                .add(PooBlocks.POOP_BRICK_SLAB)
        ;
        this.valueLookupBuilder(BlockTags.WALLS).setReplace(false)
                .add(PooBlocks.POOP_BRICK_WALL)
        ;
        this.valueLookupBuilder(BlockTags.SMALL_DRIPLEAF_PLACEABLE).setReplace(false)
                .add(Blocks.MUD)
                .add(PooBlocks.POOP_BLOCK)
                .add(Blocks.PALE_MOSS_BLOCK)
        ;
        this.valueLookupBuilder(BlockTags.MINEABLE_WITH_AXE).setReplace(false)
                .add(PooBlocks.BANKERS_TABLE)
        ;
        this.valueLookupBuilder(BlockTags.MINEABLE_WITH_SHOVEL).setReplace(false)
                .add(PooBlocks.POOP_BLOCK)
        ;
        this.valueLookupBuilder(BlockTags.MINEABLE_WITH_HOE).setReplace(false)
                .add(PooBlocks.DIM_MOSS_BLOCK)
                .add(PooBlocks.DIM_MOSS_CARPET)
        ;
        this.valueLookupBuilder(BlockTags.MOSS_REPLACEABLE).setReplace(false)
                .add(PooBlocks.POOP_BLOCK)
                .add(PooBlocks.ANNOYANCE_SUS)
        ;
        this.valueLookupBuilder(BlockTags.DIRT).setReplace(false)
                .add(PooBlocks.DIM_MOSS_BLOCK)
        ;
        this.builder(PooBlockTags.INCORRECT_FOR_RED_POO_TOOLS);
    }
}
