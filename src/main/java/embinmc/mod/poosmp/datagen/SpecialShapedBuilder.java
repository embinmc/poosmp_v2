package embinmc.mod.poosmp.datagen;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import embinmc.mod.poosmp.item.component.PooComponents;
import net.minecraft.advancements.*;
import net.minecraft.advancements.criterion.RecipeUnlockedTrigger;
import net.minecraft.core.HolderGetter;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.crafting.ShapedRecipePattern;
import net.minecraft.world.level.ItemLike;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SpecialShapedBuilder implements RecipeBuilder {
    protected final HolderGetter<Item> items;
    protected final RecipeCategory category;
    protected final ItemStack result;
    protected final List<String> rows = Lists.newArrayList();
    protected final Map<Character, Ingredient> key = Maps.newLinkedHashMap();
    protected final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
    protected @Nullable String group;
    protected boolean showNotification = true;

    protected SpecialShapedBuilder(HolderGetter<Item> items, RecipeCategory category, ItemStack result) {
        this.items = items;
        this.category = category;
        if (result.has(PooComponents.FROM_CREATIVE))
            result.set(PooComponents.FROM_CREATIVE, false);
        this.result = result;
    }

    @Override
    public @NonNull SpecialShapedBuilder unlockedBy(@NonNull String string, @NonNull Criterion<?> criterion) {
        this.criteria.put(string, criterion);
        return this;
    }

    @Override
    public @NonNull SpecialShapedBuilder group(@Nullable String string) {
        this.group = string;
        return this;
    }

    @Override
    public @NonNull Item getResult() {
        return this.result.getItem();
    }

    public SpecialShapedBuilder define(Character character, TagKey<Item> tagKey) {
        return this.define(character, Ingredient.of(this.items.getOrThrow(tagKey)));
    }

    public SpecialShapedBuilder define(Character character, ItemLike itemLike) {
        return this.define(character, Ingredient.of(itemLike));
    }

    public SpecialShapedBuilder define(Character character, ResourceKey<Item> itemKey) {
        return this.define(character, Ingredient.of(this.items.getOrThrow(itemKey).value()));
    }

    public SpecialShapedBuilder define(Character character, Ingredient ingredient) {
        if (this.key.containsKey(character)) {
            throw new IllegalArgumentException("Symbol '" + character + "' is already defined!");
        }

        if (character == ' ') {
            throw new IllegalArgumentException("Symbol ' ' (whitespace) is reserved and cannot be defined");
        }

        this.key.put(character, ingredient);
        return this;
    }

    public SpecialShapedBuilder pattern(String string) {
        if (!this.rows.isEmpty() && string.length() != this.rows.get(0).length()) {
            throw new IllegalArgumentException("Pattern must be the same width on every line!");
        }

        this.rows.add(string);
        return this;
    }

    public SpecialShapedBuilder showNotification(boolean bl) {
        this.showNotification = bl;
        return this;
    }

    @Override
    public void save(@NonNull RecipeOutput recipeOutput, @NonNull ResourceKey<Recipe<?>> resourceKey) {
        ShapedRecipePattern shapedRecipePattern = ShapedRecipePattern.of(this.key, this.rows);
        Advancement.Builder builder;
        if (this.criteria.isEmpty()) {
            builder = null;
        } else {
             builder = recipeOutput.advancement()
                    .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(resourceKey))
                    .rewards(AdvancementRewards.Builder.recipe(resourceKey))
                    .requirements(AdvancementRequirements.Strategy.OR);
             this.criteria.forEach(builder::addCriterion);
        }
        ShapedRecipe shapedRecipe = new ShapedRecipe(
                Objects.requireNonNullElse(this.group, ""),
                RecipeBuilder.determineBookCategory(this.category),
                shapedRecipePattern,
                this.result.copy(),
                this.showNotification
        );
        AdvancementHolder adv = builder == null ?
                null :
                builder.build(resourceKey.identifier().withPrefix("recipes/" + this.category.getFolderName() + "/"));
        recipeOutput.accept(resourceKey, shapedRecipe, adv);
    }
}
