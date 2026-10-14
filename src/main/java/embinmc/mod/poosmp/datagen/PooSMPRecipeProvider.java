package embinmc.mod.poosmp.datagen;

import com.tiviacz.travelersbackpack.init.ModTags;
import embinmc.mod.poosmp.PooSMPMod;
import embinmc.mod.poosmp.block.PooBlocks;
import embinmc.mod.poosmp.item.PooItemKeys;
import embinmc.mod.poosmp.item.PooItems;
import embinmc.mod.poosmp.item.component.ComponentApplicationRecipe;
import embinmc.mod.poosmp.item.component.PooComponents;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.ChatFormatting;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.*;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Unit;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ItemLore;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class PooSMPRecipeProvider extends FabricRecipeProvider {
    public PooSMPRecipeProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public @NonNull String getName() {
        return "PooSMP Recipe Provider";
    }

    @Override
    protected @NonNull RecipeProvider createRecipeProvider(HolderLookup.@NonNull Provider registryLookup, @NonNull RecipeOutput exporter) {
        return new RecipeProvider(registryLookup, exporter) {
            final HolderLookup.RegistryLookup<Item> items = this.registries.lookupOrThrow(Registries.ITEM);

            @Override
            public void buildRecipes() {
                this.smithingComponentApplication();
                this.specialStick(PooItems.SERVER_SAYS_WHAT_STICK, PooItems.DIAMOND_SHARD, Items.DIAMOND);
                this.specialStick(PooItems.POOP_STICK, PooItems.POOP_BLOCK, PooItems.DIAMOND_SHARD);
                this.slab(RecipeCategory.BUILDING_BLOCKS, PooBlocks.POOP_BRICK_SLAB, PooBlocks.POOP_BRICKS);
                this.stairs(PooBlocks.POOP_BRICK_STAIRS, PooBlocks.POOP_BRICKS);
                this.wall(RecipeCategory.BUILDING_BLOCKS, PooBlocks.POOP_BRICK_WALL, PooBlocks.POOP_BRICKS);
                this.fenceBuilderPro(PooBlocks.RED_NETHER_BRICK_FENCE, Blocks.RED_NETHER_BRICKS, PooItems.RED_NETHER_BRICK, 3)
                        .unlockedBy(getHasName(Blocks.RED_NETHER_BRICKS), this.has(Blocks.RED_NETHER_BRICKS))
                        .save(this.output);
                this.specialStick(PooItems.BIOME_STICK, ItemTags.DIRT, ItemTags.DIRT);
                this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, PooBlocks.POOP_BRICKS, PooBlocks.POOP_BRICK_SLAB, 2);
                this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, PooBlocks.POOP_BRICKS, PooBlocks.POOP_BRICK_STAIRS);
                this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, PooBlocks.POOP_BRICKS, PooBlocks.POOP_BRICK_WALL);
                this.smeltingPro(PooItems.ANNOYANCE_SUS, PooItems.POOP_BLOCK, 60, 20f);
                this.smeltingPro(PooItems.RED_POO_INGOT, PooItems.RAW_RED_POO, 120, 15f);
                this.shapeless(RecipeCategory.FOOD, PooItems.POOPLET, 4)
                        .requires(PooItems.POOP_BLOCK)
                        .save(this.output);
                this.shaped(RecipeCategory.MISC, PooItems.POOP_BLOCK)
                        .define('#', ItemTags.DIRT)
                        .pattern("##")
                        .pattern("##")
                        .unlockedBy(getHasName(Items.DIRT), this.has(ItemTags.DIRT))
                        .save(this.output);
                this.shaped(RecipeCategory.MISC, PooItems.POOP_BLOCK)
                        .define('#', PooItems.POOPLET)
                        .pattern("##")
                        .pattern("##")
                        .unlockedBy(getHasName(PooItems.POOP_BLOCK), this.has(PooItems.POOP_BLOCK))
                        .save(this.output, "poosmp:poop_block_from_pooplets");
            }

            // hardcoded, don't care
            public void smithingComponentApplication() {
                HolderLookup.RegistryLookup<Item> items = this.registries.lookupOrThrow(Registries.ITEM);
                ComponentApplicationRecipe recipe = new ComponentApplicationRecipe(
                        Ingredient.of(items.getOrThrow(ModTags.CUSTOM_TRAVELERS_BACKPACK)),
                        Ingredient.of(PooItems.ITEM_FORCING_BACKPACK_UPGRADE),
                        Optional.of(Ingredient.of(items.getOrThrow(ConventionalItemTags.LEATHERS))),
                        DataComponentPatch.builder()
                                .set(PooComponents.FORCE_ALLOW_IN_BACKPACK, Unit.INSTANCE)
                                .set(DataComponents.LORE, new ItemLore(List.of(
                                        Component.literal("Upgraded with: ")
                                                .withStyle(ChatFormatting.GRAY)
                                                .append(
                                                        Component.literal("Item Forcing Upgrade")
                                                                .withStyle(ChatFormatting.YELLOW)
                                                )
                                )))
                                .build()
                );
                ResourceKey<Recipe<?>> key = ResourceKey.create(Registries.RECIPE, PooSMPMod.id("backpack_item_forcing_upgrade_transform"));
                this.output.accept(key, recipe, null);
            }

            public void stairs(ItemLike stair, ItemLike stairMaterial) {
                this.stairBuilder(stair, Ingredient.of(stairMaterial))
                        .unlockedBy(getHasName(stairMaterial), this.has(stairMaterial))
                        .save(this.output);
            }

            public RecipeBuilder fenceBuilderPro(ItemLike fence, Ingredient main, Ingredient stick, int get) {
                return this.shaped(RecipeCategory.DECORATIONS, fence, get).define('W', main).define('#', stick).pattern("W#W").pattern("W#W");
            }

            public RecipeBuilder fenceBuilderPro(ItemLike fence, ItemLike main, ItemLike stick, int get) {
                return this.fenceBuilderPro(fence, Ingredient.of(main), Ingredient.of(stick), get);
            }

            public void specialStick(Item stick, ItemLike edge, ItemLike corner) {
                this.shapedSpecial(RecipeCategory.MISC, stick)
                        .pattern("4#4")
                        .pattern("#3#")
                        .pattern("4#4")
                        .define('3', ConventionalItemTags.WOODEN_RODS)
                        .define('#', edge)
                        .define('4', corner)
                        .unlockedBy(getHasName(Items.STICK), this.has(Items.STICK))
                        .save(this.output);
            }

            public void specialStick(Item stick, TagKey<Item> edge, TagKey<Item> corner) {
                this.shapedSpecial(RecipeCategory.MISC, stick)
                        .pattern("4#4")
                        .pattern("#3#")
                        .pattern("4#4")
                        .define('3', ConventionalItemTags.WOODEN_RODS)
                        .define('#', edge)
                        .define('4', corner)
                        .unlockedBy(getHasName(Items.STICK), this.has(Items.STICK))
                        .save(this.output);
            }

            public SpecialShapedBuilder shapedSpecial(RecipeCategory category, ItemStack result) {
                return new SpecialShapedBuilder(this.items, category, result);
            }

            public SpecialShapedBuilder shapedSpecial(RecipeCategory category, ItemLike result, int count) {
                return new SpecialShapedBuilder(this.items, category, new ItemStack(result, count));
            }

            public SpecialShapedBuilder shapedSpecial(RecipeCategory category, ItemLike result) {
                return this.shapedSpecial(category, result, 1);
            }

            public void smeltingPro(ItemLike result, ItemLike input, int ticks, float xp) {
                SimpleCookingRecipeBuilder.smelting(Ingredient.of(input), RecipeCategory.MISC, result, xp, ticks)
                        .unlockedBy(getHasName(input), this.has(input))
                        .save(this.output);
            }
        };
    }
}
