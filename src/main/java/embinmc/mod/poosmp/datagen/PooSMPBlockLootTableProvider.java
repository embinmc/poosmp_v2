package embinmc.mod.poosmp.datagen;

import embinmc.mod.poosmp.block.PooBlocks;
import embinmc.mod.poosmp.item.PooItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.concurrent.CompletableFuture;

public class PooSMPBlockLootTableProvider extends FabricBlockLootTableProvider {
    protected PooSMPBlockLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        this.createSingleItemTableWithSilkTouch(PooBlocks.POOP_BLOCK, PooItems.POOPLET, ConstantValue.exactly(4f));
        this.dropSelf(PooBlocks.MISSINGNO_BLOCK);
        this.dropSelf(PooBlocks.POOP_BRICKS);
        this.dropSelf(PooBlocks.POOP_BRICK_STAIRS);
        this.add(PooBlocks.POOP_BRICK_SLAB, this::createSlabItemTable);
        this.dropSelf(PooBlocks.POOP_BRICK_WALL);
        this.dropSelf(PooBlocks.RED_NETHER_BRICK_FENCE);
        this.dropSelf(PooBlocks.ANNOYANCE_SUS);
        this.dropSelf(PooBlocks.DDE_BLOCK);
        this.add(PooBlocks.PENIS_BLOCK, block -> this.createSingleItemTableWithSilkTouch(block, Blocks.DIRT));
        this.dropSelf(PooBlocks.BANKERS_TABLE);
        this.dropSelf(PooBlocks.RED_POO_BLOCK);
        this.dropSelf(PooBlocks.DRAGON_ANNOYANCE);
        this.dropSelf(PooBlocks.FAKE_DIRT);
        this.dropSelf(PooBlocks.FAKE_GRASS_BLOCK);
        this.dropSelf(PooBlocks.FAKE_STONE);
        this.dropSelf(PooBlocks.RIGGED_STONE);
        this.dropSelf(PooBlocks.DIM_MOSS_BLOCK);
        this.dropSelf(PooBlocks.DIM_MOSS_CARPET);
        this.dropSelf(PooBlocks.DEHEED_ANNOYANCE);
        this.add(Blocks.GLASS, glass -> this.createSingleItemTableWithSilkTouch(glass, PooItems.GLASS_SHARD, UniformGenerator.between(5f, 9f)));
        this.add(Blocks.GLASS_PANE, glass -> this.createSingleItemTableWithSilkTouch(glass, PooItems.GLASS_SHARD, UniformGenerator.between(1f, 3f)));
    }
}
