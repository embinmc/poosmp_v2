package embinmc.mod.poosmp.datagen;

import com.tiviacz.travelersbackpack.init.ModTags;
import embinmc.mod.poosmp.PooSMPMod;
import embinmc.mod.poosmp.block.PooBlocks;
import embinmc.mod.poosmp.item.PooItemKeys;
import embinmc.mod.poosmp.item.PooItemTags;
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
                this.shapedSpecial(RecipeCategory.MISC, PooItems.BANKERS_TABLE)
                        .pattern("##")
                        .pattern("33")
                        .pattern("33")
                        .define('#', PooItemTags.BANK_NOTES)
                        .define('3', ItemTags.PLANKS)
                        .unlockedBy("has_bank_note", this.has(PooItemTags.BANK_NOTES))
                        .save(this.output);
                this.specialStick(PooItems.BIOME_STICK, ItemTags.DIRT, ItemTags.DIRT);
                this.shapedSpecial(RecipeCategory.BUILDING_BLOCKS, PooItems.BLANK_MUSIC_DISC, 1)
                        .pattern("###")
                        .pattern("#3#")
                        .pattern("###")
                        .define('#', Items.IRON_NUGGET)
                        .define('3', Items.GOLD_NUGGET)
                        .unlockedBy(getHasName(Items.GOLD_NUGGET), this.has(Items.GOLD_NUGGET))
                        .save(this.output);
                this.specialStick(PooItems.BOOM_STICK, Items.GUNPOWDER, Items.DIAMOND);
                this.specialStick(PooItems.COW_STICK, Items.LEATHER, Items.DIAMOND);
                this.shapedSpecial(RecipeCategory.BUILDING_BLOCKS, PooItems.DDE_BLOCK, 8)
                        .pattern("###")
                        .pattern("#3#")
                        .pattern("###")
                        .define('#', PooItems.POOP_BLOCK)
                        .define('3', PooItems.DIAMOND_SHARD)
                        .unlockedBy(getHasName(PooItems.DIAMOND_SHARD), this.has(PooItems.DIAMOND_SHARD))
                        .save(this.output);
                this.shapedSpecial(RecipeCategory.MISC, Items.DIAMOND)
                        .pattern("###")
                        .pattern("###")
                        .pattern("###")
                        .define('#', PooItems.DIAMOND_SHARD)
                        .unlockedBy(getHasName(PooItems.DIAMOND_SHARD), this.has(PooItems.DIAMOND_SHARD))
                        .save(this.output, "poosmp:diamond_from_diamond_shards");
                this.simpleConversion(PooItems.DIAMOND_SHARD, 9, Items.DIAMOND);
                this.carpet(PooItems.DIM_MOSS_CARPET, PooItems.DIM_MOSS_BLOCK);
                this.shapedSpecial(RecipeCategory.MISC, PooItems.ENCHANTED_TOTEM_OF_HEALTH)
                        .pattern("###")
                        .pattern("#3#")
                        .pattern("###")
                        .define('#', Items.DIAMOND)
                        .define('3', PooItems.TOTEM_OF_HEALTH)
                        .unlockedBy(getHasName(PooItems.TOTEM_OF_HEALTH), this.has(PooItems.TOTEM_OF_HEALTH))
                        .save(this.output);
                this.shapedSpecial(RecipeCategory.MISC, PooItems.ENCHANTED_TOTEM_OF_REACH)
                        .pattern("###")
                        .pattern("#3#")
                        .pattern("###")
                        .define('#', Items.DIAMOND)
                        .define('3', PooItems.TOTEM_OF_REACH)
                        .unlockedBy(getHasName(PooItems.TOTEM_OF_REACH), this.has(PooItems.TOTEM_OF_REACH))
                        .save(this.output);
                this.simpleCornerless(RecipeCategory.BUILDING_BLOCKS, PooItems.FAKE_DIRT, 4, Items.GLOW_INK_SAC, Items.DIRT);
                this.simpleCornerless(RecipeCategory.BUILDING_BLOCKS, PooItems.FAKE_GRASS_BLOCK, 4, Items.GLOW_INK_SAC, Items.GRASS_BLOCK);
                this.simpleCornerless(RecipeCategory.BUILDING_BLOCKS, PooItems.FAKE_STONE, 4, Items.GLOW_INK_SAC, Items.STONE);
                this.shapedSpecial(RecipeCategory.MISC, PooItems.FILL_ARMOR_TRIM_TEMPLATE)
                        .pattern("3#3")
                        .pattern("3#3")
                        .pattern("333")
                        .define('3', Items.COPPER_INGOT)
                        .define('#', PooItems.DIAMOND_SHARD)
                        .unlockedBy(getHasName(Items.COPPER_INGOT), this.has(Items.COPPER_INGOT))
                        .save(this.output);
                ResourceKey<Recipe<?>> trimRecipeId = ResourceKey.create(Registries.RECIPE, PooSMPMod.id("fill_armor_trim_smithing_template_smithing_trim"));
                this.trimSmithing(PooItems.FILL_ARMOR_TRIM_TEMPLATE, ResourceKey.create(Registries.TRIM_PATTERN, PooSMPMod.id("fill")), trimRecipeId);
                this.specialStick(PooItems.FROG_STICK, Items.SLIME_BALL, Items.NETHERITE_SCRAP);
                this.shapedSpecial(RecipeCategory.MISC, PooItems.FUN_STICK)
                        .pattern("###")
                        .pattern("#3#")
                        .pattern("###")
                        .define('#', Items.COPPER_NUGGET)
                        .define('3', ConventionalItemTags.WOODEN_RODS)
                        .unlockedBy(getHasName(Items.COPPER_NUGGET), this.has(Items.COPPER_NUGGET))
                        .save(this.output);
                this.simpleCornerless(RecipeCategory.REDSTONE, PooItems.GEAR, 2, Items.IRON_NUGGET, Items.IRON_INGOT);
                this.shapedSpecial(RecipeCategory.BUILDING_BLOCKS, Items.GLASS)
                        .define('#', PooItems.GLASS_SHARD)
                        .pattern("###")
                        .pattern("###")
                        .pattern("###")
                        .unlockedBy(getHasName(PooItems.GLASS_SHARD), this.has(PooItems.GLASS_SHARD))
                        .save(this.output, "poosmp:glass_from_glass_shards");
                this.shapedSpecial(RecipeCategory.REDSTONE, PooItems.DEHEED_ANNOYANCE)
                        .pattern("###")
                        .pattern("#3#")
                        .pattern("###")
                        .define('#', ConventionalItemTags.LEATHERS)
                        .define('3', PooItems.ANNOYANCE_SUS)
                        .unlockedBy(getHasName(PooItems.ANNOYANCE_SUS), this.has(PooItems.ANNOYANCE_SUS))
                        .save(this.output);
                this.shapedSpecial(RecipeCategory.MISC, PooItems.ITEM_FORCING_BACKPACK_UPGRADE)
                        .pattern("4#4")
                        .pattern("#3#")
                        .pattern("4#4")
                        .define('3', Items.NETHER_STAR)
                        .define('#', Items.AMETHYST_SHARD)
                        .define('4', ConventionalItemTags.LEATHERS)
                        .unlockedBy(getHasName(Items.NETHER_STAR), this.has(Items.NETHER_STAR))
                        .save(this.output);
                this.shapedSpecial(RecipeCategory.MISC, PooItems.JUMPSCARE_STICK)
                        .pattern("###")
                        .pattern("#3#")
                        .pattern("###")
                        .define('#', Items.REDSTONE)
                        .define('3', ConventionalItemTags.WOODEN_RODS)
                        .unlockedBy(getHasName(Items.REDSTONE), this.has(Items.REDSTONE))
                        .save(this.output);
                this.specialStick(PooItems.ZAP_STICK, Items.COPPER_INGOT, PooItems.DIAMOND_SHARD);
                this.shapedSpecial(RecipeCategory.REDSTONE, PooItems.MAGIC_DEVICE)
                        .pattern(" 4 ")
                        .pattern("535")
                        .pattern("#6#")
                        .define('3', Items.REDSTONE)
                        .define('4', Items.IRON_INGOT)
                        .define('6', Items.GUNPOWDER)
                        .define('5', PooItems.SCREW)
                        .define('#', PooItems.GEAR)
                        .unlockedBy(getHasName(PooItems.GEAR), this.has(PooItems.GEAR))
                        .save(this.output);
                this.simpleCornerless(RecipeCategory.BUILDING_BLOCKS, PooItems.MISSINGNO_BLOCK, 16, Items.STONE, PooItems.DIAMOND_SHARD);
                this.shapedSpecial(RecipeCategory.MISC, Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE)
                        .pattern("#2#")
                        .pattern("#3#")
                        .pattern("###")
                        .define('#', Items.NETHERRACK)
                        .define('3', Items.NETHERITE_SCRAP)
                        .define('2', Items.DIAMOND)
                        .unlockedBy(getHasName(Items.NETHERITE_SCRAP), this.has(Items.NETHERITE_SCRAP))
                        .save(this.output, "poosmp:netherite_upgrade_smithing_template");
                this.simpleConversion(PooItems.NULL_SHARD, 4, PooItems.MISSINGNO_BLOCK);
                this.specialStick(PooItems.NULL_STICK, PooItems.NULL_SHARD, PooItems.NULL_SHARD);
                this.shapedSpecial(RecipeCategory.MISC, PooItems.POOP_BLOCK)
                        .define('#', ItemTags.DIRT)
                        .pattern("##")
                        .pattern("##")
                        .unlockedBy(getHasName(Items.DIRT), this.has(ItemTags.DIRT))
                        .save(this.output);
                this.shapedSpecial(RecipeCategory.MISC, PooItems.POOP_BLOCK)
                        .define('#', PooItems.POOPLET)
                        .pattern("##")
                        .pattern("##")
                        .unlockedBy(getHasName(PooItems.POOP_BLOCK), this.has(PooItems.POOP_BLOCK))
                        .save(this.output, "poosmp:poop_block_from_pooplets");
                this.smeltingPro(PooItems.POOP_BRICK, PooItems.POOPLET, 100, 2f);
                poopBrickSlab: {
                    this.slab(RecipeCategory.BUILDING_BLOCKS, PooBlocks.POOP_BRICK_SLAB, PooBlocks.POOP_BRICKS);
                    this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, PooBlocks.POOP_BRICKS, PooBlocks.POOP_BRICK_SLAB, 2);
                }
                poopBrickStairs: {
                    this.stairs(PooBlocks.POOP_BRICK_STAIRS, PooBlocks.POOP_BRICKS);
                    this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, PooBlocks.POOP_BRICKS, PooBlocks.POOP_BRICK_STAIRS);
                }
                poopBrickWall: {
                    this.wall(RecipeCategory.BUILDING_BLOCKS, PooBlocks.POOP_BRICK_WALL, PooBlocks.POOP_BRICKS);
                    this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, PooBlocks.POOP_BRICKS, PooBlocks.POOP_BRICK_WALL);
                }
                this.shapedSpecial(RecipeCategory.BUILDING_BLOCKS, PooItems.POOP_BRICKS)
                        .define('#', PooItems.POOP_BRICK)
                        .pattern("##")
                        .pattern("##")
                        .unlockedBy(getHasName(PooItems.POOP_BRICK), this.has(PooItems.POOP_BRICK))
                        .save(this.output);
                this.specialStick(PooItems.POOP_STICK, PooItems.POOP_BLOCK, PooItems.DIAMOND_SHARD);
                this.simpleConversion(PooItems.POOPLET, 4, PooItems.POOP_BLOCK);
                this.shapedSpecial(RecipeCategory.BUILDING_BLOCKS, PooItems.RED_NETHER_BRICK, 8)
                        .pattern("###")
                        .pattern("#3#")
                        .pattern("###")
                        .define('#', Items.NETHER_BRICK)
                        .define('3', PooItemTags.MAKES_NETHER_BRICKS_RED)
                        .unlockedBy(getHasName(Items.NETHER_BRICK), this.has(Items.NETHER_BRICK))
                        .save(this.output);
                this.fenceBuilderPro(PooBlocks.RED_NETHER_BRICK_FENCE, Blocks.RED_NETHER_BRICKS, PooItems.RED_NETHER_BRICK, 3)
                        .unlockedBy(getHasName(Blocks.RED_NETHER_BRICKS), this.has(Blocks.RED_NETHER_BRICKS))
                        .save(this.output);
                this.shapedSpecial(RecipeCategory.BUILDING_BLOCKS, Items.RED_NETHER_BRICKS)
                        .pattern("##")
                        .pattern("##")
                        .define('#', PooItems.RED_NETHER_BRICK)
                        .unlockedBy(getHasName(PooItems.RED_NETHER_BRICK), this.has(PooItems.RED_NETHER_BRICK))
                        .save(this.output, "poosmp:red_nether_bricks");
                this.convertSmithing(this.tag(PooItemTags.RED_POO_CONVERTIBLE_SWORD), PooItems.RED_POO_SWORD, PooItemTags.RED_POO_REPAIR_ITEMS);
                this.convertSmithing(this.tag(PooItemTags.RED_POO_CONVERTIBLE_PICKAXE), PooItems.RED_POO_PICKAXE, PooItemTags.RED_POO_REPAIR_ITEMS);
                this.convertSmithing(this.tag(PooItemTags.RED_POO_CONVERTIBLE_AXE), PooItems.RED_POO_AXE, PooItemTags.RED_POO_REPAIR_ITEMS);
                this.convertSmithing(this.tag(PooItemTags.RED_POO_CONVERTIBLE_SHOVEL), PooItems.RED_POO_SHOVEL, PooItemTags.RED_POO_REPAIR_ITEMS);
                this.convertSmithing(this.tag(PooItemTags.RED_POO_CONVERTIBLE_HOE), PooItems.RED_POO_HOE, PooItemTags.RED_POO_REPAIR_ITEMS);
                this.convertSmithing(this.tag(PooItemTags.RED_POO_CONVERTIBLE_HELMET), PooItems.RED_POO_HELMET, PooItemTags.RED_POO_REPAIR_ITEMS);
                this.convertSmithing(this.tag(PooItemTags.RED_POO_CONVERTIBLE_CHESTPLATE), PooItems.RED_POO_CHESTPLATE, PooItemTags.RED_POO_REPAIR_ITEMS);
                this.convertSmithing(this.tag(PooItemTags.RED_POO_CONVERTIBLE_LEGGINGS), PooItems.RED_POO_LEGGINGS, PooItemTags.RED_POO_REPAIR_ITEMS);
                this.convertSmithing(this.tag(PooItemTags.RED_POO_CONVERTIBLE_BOOTS), PooItems.RED_POO_BOOTS, PooItemTags.RED_POO_REPAIR_ITEMS);
                this.smeltingPro(PooItems.RED_POO_INGOT, PooItems.RAW_RED_POO, 120, 15f);
                this.nineBlockStorageRecipes(
                        RecipeCategory.MISC, PooItems.RED_POO_INGOT,
                        RecipeCategory.BUILDING_BLOCKS, PooItems.RED_POO_BLOCK,
                        "poosmp:red_poo_block", null,
                        "poosmp:red_poo_ingot_from_block", null
                );
                this.simpleCornerless(RecipeCategory.REDSTONE, PooItems.RIGGED_STONE, 1, Items.STONE, Items.GUNPOWDER);
                this.shapedSpecial(RecipeCategory.MISC, PooItems.RING)
                        .pattern("3#3")
                        .pattern("# #")
                        .pattern("3#3")
                        .define('#', Items.IRON_NUGGET)
                        .define('3', Items.QUARTZ)
                        .unlockedBy(getHasName(Items.QUARTZ), this.has(Items.QUARTZ))
                        .save(this.output);
                this.shapedSpecial(RecipeCategory.MISC, PooItems.SCREW)
                        .pattern("#")
                        .pattern("3")
                        .pattern("3")
                        .define('#', Items.IRON_INGOT)
                        .define('3', Items.IRON_NUGGET)
                        .unlockedBy(getHasName(Items.IRON_NUGGET), this.has(Items.IRON_NUGGET))
                        .save(this.output);
                this.specialStick(PooItems.SERVER_SAYS_WHAT_STICK, PooItems.DIAMOND_SHARD, Items.DIAMOND);
                this.smeltingPro(PooItems.ANNOYANCE_SUS, PooItems.POOP_BLOCK, 60, 20f);
                this.shapedSpecial(RecipeCategory.MISC, PooItems.TOTEM_OF_HEALTH)
                        .pattern("343")
                        .pattern(" # ")
                        .define('#', PooItems.RED_NETHER_BRICK)
                        .define('3', Items.DIAMOND)
                        .define('4', Items.ENCHANTED_GOLDEN_APPLE)
                        .unlockedBy(getHasName(Items.ENCHANTED_GOLDEN_APPLE), this.has(Items.ENCHANTED_GOLDEN_APPLE))
                        .save(this.output);
                this.shapedSpecial(RecipeCategory.MISC, PooItems.TOTEM_OF_REACH)
                        .pattern("4")
                        .pattern("3")
                        .pattern("#")
                        .define('#', Items.DIAMOND)
                        .define('3', Items.GOLDEN_APPLE)
                        .define('4', Items.PISTON)
                        .unlockedBy(getHasName(Items.GOLDEN_APPLE), this.has(Items.GOLDEN_APPLE))
                        .save(this.output);
                this.specialStick(PooItems.VILLAGER_STICK, Items.EMERALD, Items.NETHERITE_SCRAP);
                this.specialStick(PooItems.WARP_STICK, Items.ENDER_EYE, Items.DIAMOND);
                this.shapedSpecial(RecipeCategory.MISC, PooItems.WEDDING_RING)
                        .pattern("3")
                        .pattern("#")
                        .define('3', Items.DIAMOND)
                        .define('#', PooItems.RING)
                        .unlockedBy(getHasName(PooItems.RING), this.has(PooItems.RING))
                        .save(this.output);
                this.specialStick(PooItems.WIND_STICK, Items.BREEZE_ROD, PooItems.DIAMOND_SHARD);
                this.specialStick(PooItems.ZOMBIE_STICK, Items.ROTTEN_FLESH, Items.DIAMOND);
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
                return this.shaped(RecipeCategory.DECORATIONS, fence, get)
                        .define('W', main)
                        .define('#', stick)
                        .pattern("W#W")
                        .pattern("W#W");
            }

            public RecipeBuilder fenceBuilderPro(ItemLike fence, ItemLike main, ItemLike stick, int get) {
                return this.fenceBuilderPro(fence, Ingredient.of(main), Ingredient.of(stick), get);
            }

            public void specialStick(Item stick, ItemLike edge, ItemLike corner) {
                this.shapedSpecial(RecipeCategory.TOOLS, stick)
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
                this.shapedSpecial(RecipeCategory.TOOLS, stick)
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

            public void simpleConversion(ItemLike result, int amount, ItemLike input) {
                this.shapeless(RecipeCategory.MISC, result, amount)
                        .requires(input)
                        .unlockedBy(getHasName(input), this.has(input))
                        .save(this.output);
            }

            public void simpleCornerless(RecipeCategory category, ItemLike result, int amount, ItemLike center, ItemLike edges) {
                this.shapedSpecial(category, result, amount)
                        .pattern(" # ")
                        .pattern("#3#")
                        .pattern(" # ")
                        .define('3', center)
                        .define('#', edges)
                        .unlockedBy(getHasName(center), this.has(center))
                        .save(this.output);
            }

            public void convertSmithing(Ingredient input, Item output, TagKey<Item> material) {
                SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE),
                        input,
                        this.tag(material),
                        RecipeCategory.TOOLS,
                        output
                )
                .unlocks("has_smithing_ingredient", this.has(material))
                .save(this.output, "poosmp:" + getItemName(output) + "_smithing");
            }
        };
    }
}
