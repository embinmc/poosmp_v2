package embinmc.mod.poosmp.misc;

import net.minecraft.world.item.crafting.PlacementInfo;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SmithingRecipe;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public abstract class SimpleSmithingRecipe implements SmithingRecipe {
    private @Nullable PlacementInfo placementInfo;

    protected SimpleSmithingRecipe() {
    }

    public abstract @NonNull RecipeSerializer<? extends SimpleSmithingRecipe> getSerializer();

    public @NonNull PlacementInfo placementInfo() {
        if (this.placementInfo == null) {
            this.placementInfo = this.createPlacementInfo();
        }

        return this.placementInfo;
    }

    protected abstract PlacementInfo createPlacementInfo();

    @Override
    public @NonNull String group() {
        return "";
    }

    @Override
    public final boolean showNotification() {
        return true;
    }
}