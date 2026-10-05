package embinmc.mod.poosmp.datagen;

import com.tiviacz.travelersbackpack.init.ModTags;
import com.tiviacz.travelersbackpack.item.TravelersBackpackItem;
import embinmc.mod.poosmp.PooSMPMod;
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
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Unit;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ItemLore;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;
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
                this.shapedSpecial(RecipeCategory.MISC, this.notFromCreative(PooItems.SERVER_SAYS_WHAT_STICK, 1))
                        .pattern("4#4")
                        .pattern("#3#")
                        .pattern("4#4")
                        .define('3', ConventionalItemTags.WOODEN_RODS)
                        .define('#', PooItemKeys.DIAMOND_SHARD)
                        .define('4', Items.DIAMOND)
                        .unlockedBy(getHasName(Items.DIAMOND), this.has(Items.DIAMOND))
                        .save(this.output);
                this.shapedSpecial(RecipeCategory.MISC, this.notFromCreative(PooItems.POOP_STICK, 1))
                        .pattern("4#4")
                        .pattern("#3#")
                        .pattern("4#4")
                        .define('3', ConventionalItemTags.WOODEN_RODS)
                        .define('#', PooItemKeys.POOP_BLOCK)
                        .define('4', PooItemKeys.DIAMOND_SHARD)
                        .unlockedBy(getHasName(Items.STICK), this.has(Items.STICK))
                        .save(this.output);
            }

            // hardcoded, don't care
            public void smithingComponentApplication() {
                HolderLookup.RegistryLookup<Item> items = this.registries.lookupOrThrow(Registries.ITEM);
                ComponentApplicationRecipe recipe = new ComponentApplicationRecipe(
                        Ingredient.of(items.getOrThrow(ModTags.CUSTOM_TRAVELERS_BACKPACK)),
                        Ingredient.of((ItemLike) null),
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

            public ItemStack notFromCreative(Item item, int count) {
                ItemStack itemStack = new ItemStack(item, count);
                itemStack.set(PooComponents.FROM_CREATIVE, false);
                return itemStack;
            }

            public SpecialShapedBuilder shapedSpecial(RecipeCategory category, ItemStack result) {
                return new SpecialShapedBuilder(this.items, category, result);
            }
        };
    }
}
