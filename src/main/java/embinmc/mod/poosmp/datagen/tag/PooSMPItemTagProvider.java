package embinmc.mod.poosmp.datagen.tag;

import embinmc.mod.poosmp.item.PooItemKeys;
import embinmc.mod.poosmp.item.PooItemTags;
import embinmc.mod.poosmp.item.PooItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
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
                .add(PooItemKeys.RED_NETHER_BRICK_FENCE)
        ;
        this.builder(ItemTags.STAIRS).setReplace(false)
                .add(PooItemKeys.POOP_BRICK_STAIRS)
        ;
        this.builder(ItemTags.SLABS).setReplace(false)
                .add(PooItemKeys.POOP_BRICK_SLAB)
        ;
        this.builder(ItemTags.WALLS).setReplace(false)
                .add(PooItemKeys.POOP_BRICK_WALL)
        ;

        this.builder(ItemTags.SWORDS).setReplace(false)
                .add(PooItemKeys.RED_POO_SWORD)
        ;
        this.builder(ItemTags.PICKAXES).setReplace(false)
                .add(PooItemKeys.RED_POO_PICKAXE)
        ;
        this.builder(ItemTags.AXES).setReplace(false)
                .add(PooItemKeys.RED_POO_AXE)
        ;
        this.builder(ItemTags.SHOVELS).setReplace(false)
                .add(PooItemKeys.RED_POO_SHOVEL)
        ;
        this.builder(ItemTags.HOES).setReplace(false)
                .add(PooItemKeys.RED_POO_HOE)
        ;
        this.builder(ItemTags.HEAD_ARMOR).setReplace(false)
                .add(PooItemKeys.RED_POO_HELMET)
        ;
        this.builder(ItemTags.CHEST_ARMOR).setReplace(false)
                .add(PooItemKeys.RED_POO_CHESTPLATE)
        ;
        this.builder(ItemTags.LEG_ARMOR).setReplace(false)
                .add(PooItemKeys.RED_POO_LEGGINGS)
        ;
        this.builder(ItemTags.FOOT_ARMOR).setReplace(false)
                .add(PooItemKeys.RED_POO_BOOTS)
        ;

        this.builder(PooItemTags.RED_POO_ARMOR_SET)
                .add(PooItemKeys.RED_POO_HELMET)
                .add(PooItemKeys.RED_POO_CHESTPLATE)
                .add(PooItemKeys.RED_POO_LEGGINGS)
                .add(PooItemKeys.RED_POO_BOOTS)
        ;
        this.builder(PooItemTags.RED_POO_TOOL_SET)
                .add(PooItemKeys.RED_POO_SWORD)
                .add(PooItemKeys.RED_POO_PICKAXE)
                .add(PooItemKeys.RED_POO_AXE)
                .add(PooItemKeys.RED_POO_SHOVEL)
                .add(PooItemKeys.RED_POO_HOE)
        ;
        this.builder(PooItemTags.RED_POO_SET)
                .addTag(PooItemTags.RED_POO_TOOL_SET)
                .addTag(PooItemTags.RED_POO_ARMOR_SET)
        ;

        this.valueLookupBuilder(ItemTags.TRIMMABLE_ARMOR).setReplace(false)
                .add(Items.WOODEN_SWORD)
                .add(Items.WOODEN_PICKAXE)
                .add(Items.WOODEN_AXE)
                .add(Items.WOODEN_SHOVEL)
                .add(Items.WOODEN_HOE)
                .add(Items.STONE_SWORD)
                .add(Items.STONE_PICKAXE)
                .add(Items.STONE_AXE)
                .add(Items.STONE_SHOVEL)
                .add(Items.STONE_HOE)
                .add(Items.COPPER_SWORD)
                .add(Items.COPPER_PICKAXE)
                .add(Items.COPPER_AXE)
                .add(Items.COPPER_SHOVEL)
                .add(Items.COPPER_HOE)
                .add(Items.IRON_SWORD)
                .add(Items.IRON_PICKAXE)
                .add(Items.IRON_AXE)
                .add(Items.IRON_SHOVEL)
                .add(Items.IRON_HOE)
                .add(Items.GOLDEN_SWORD)
                .add(Items.GOLDEN_PICKAXE)
                .add(Items.GOLDEN_AXE)
                .add(Items.GOLDEN_SHOVEL)
                .add(Items.GOLDEN_HOE)
                .add(Items.DIAMOND_SWORD)
                .add(Items.DIAMOND_PICKAXE)
                .add(Items.DIAMOND_AXE)
                .add(Items.DIAMOND_SHOVEL)
                .add(Items.DIAMOND_HOE)
                .add(Items.NETHERITE_SWORD)
                .add(Items.NETHERITE_PICKAXE)
                .add(Items.NETHERITE_AXE)
                .add(Items.NETHERITE_SHOVEL)
                .add(Items.NETHERITE_HOE)
                .addTag(PooItemTags.RED_POO_SET)
        ;

        this.builder(PooItemTags.BANK_NOTES)
            .add(PooItemKeys.ONE_DOLLAR_BILL)
            .add(PooItemKeys.TWO_DOLLAR_BILL)
            .add(PooItemKeys.FIVE_DOLLAR_BILL)
            .add(PooItemKeys.TEN_DOLLAR_BILL)
            .add(PooItemKeys.TWENTY_FIVE_DOLLAR_BILL)
            .add(PooItemKeys.FIFTY_DOLLAR_BILL)
            .add(PooItemKeys.HUNDRED_DOLLAR_BILL)
            .add(PooItemKeys.CHEQUE)
        ;
        this.builder(PooItemTags.DOESNT_SHOW_SELL_VALUE_TOOLTIP)
                .addTag(PooItemTags.BANK_NOTES)
        ;
        this.valueLookupBuilder(PooItemTags.CONVERTIBLE_DISCS)
                .add(PooItems.BLANK_MUSIC_DISC)
                .add(Items.MUSIC_DISC_13)
        ;
        this.valueLookupBuilder(PooItemTags.MAKES_NETHER_BRICKS_RED)
                .add(Items.RED_DYE)
                .add(Items.NETHER_WART)
                .addOptionalTag(ConventionalItemTags.RED_DYES)
        ;
        this.builder(PooItemTags.POOSMP_DISCS)
                .add(PooItemKeys.DISC_TRIFECTA_CAP)
                .add(PooItemKeys.DISC_BUTTERFLIES_INSTRUMENTAL)
                .add(PooItemKeys.DISC_BUDDY_HOLLY)
                .add(PooItemKeys.DISC_STEREO_MADNESS)
                .add(PooItemKeys.DISC_NOT_LIKE_US)
                .add(PooItemKeys.DISC_RESISTANCE_INSTR)
                .add(PooItemKeys.DISC_BLISS_INSTRUMENTAL)
                .add(PooItemKeys.DISC_ENDLESSLY_INSTRUMENTAL)
                .add(PooItemKeys.DISC_ENDLESSLY)
                .add(PooItemKeys.DISC_ENDLESSLY_STEREO)
                .add(PooItemKeys.DISC_STORY_OF_UNDERTALE)
        ;
        this.builder(ItemTags.CREEPER_DROP_MUSIC_DISCS).setReplace(false).addTag(PooItemTags.POOSMP_DISCS);

        this.valueLookupBuilder(PooItemTags.RED_POO_CONVERTIBLE_SWORD)
                .add(Items.  DIAMOND_SWORD)
                .add(Items.NETHERITE_SWORD);
        this.valueLookupBuilder(PooItemTags.RED_POO_CONVERTIBLE_PICKAXE)
                .add(Items.  DIAMOND_PICKAXE)
                .add(Items.NETHERITE_PICKAXE);
        this.valueLookupBuilder(PooItemTags.RED_POO_CONVERTIBLE_AXE)
                .add(Items.  DIAMOND_AXE)
                .add(Items.NETHERITE_AXE);
        this.valueLookupBuilder(PooItemTags.RED_POO_CONVERTIBLE_SHOVEL)
                .add(Items.  DIAMOND_SHOVEL)
                .add(Items.NETHERITE_SHOVEL);
        this.valueLookupBuilder(PooItemTags.RED_POO_CONVERTIBLE_HOE)
                .add(Items.  DIAMOND_HOE)
                .add(Items.NETHERITE_HOE);
        this.valueLookupBuilder(PooItemTags.RED_POO_CONVERTIBLE_HELMET)
                .add(Items.  DIAMOND_HELMET)
                .add(Items.NETHERITE_HELMET);
        this.valueLookupBuilder(PooItemTags.RED_POO_CONVERTIBLE_CHESTPLATE)
                .add(Items.  DIAMOND_CHESTPLATE)
                .add(Items.NETHERITE_CHESTPLATE);
        this.valueLookupBuilder(PooItemTags.RED_POO_CONVERTIBLE_LEGGINGS)
                .add(Items.  DIAMOND_LEGGINGS)
                .add(Items.NETHERITE_LEGGINGS);
        this.valueLookupBuilder(PooItemTags.RED_POO_CONVERTIBLE_BOOTS)
                .add(Items.  DIAMOND_BOOTS)
                .add(Items.NETHERITE_BOOTS);
        this.builder(PooItemTags.RED_POO_REPAIR_ITEMS).add(PooItemKeys.RED_POO_INGOT);
    }
}
