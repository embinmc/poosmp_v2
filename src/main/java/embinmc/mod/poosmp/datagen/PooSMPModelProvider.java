package embinmc.mod.poosmp.datagen;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.jspecify.annotations.NonNull;

public class PooSMPModelProvider extends FabricModelProvider {
    public PooSMPModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public String getName() {
        return "PooSMP: " + super.getName();
    }

    @Override
    public void generateBlockStateModels(@NonNull BlockModelGenerators gen) {
    }

    @Override
    public void generateItemModels(@NonNull ItemModelGenerators gen) {
        gen.createFlatItemModel(null, Items.STICK, ModelTemplates.FLAT_HANDHELD_ITEM);
        gen.createFlatItemModel(null, Items.STICK, ModelTemplates.FLAT_HANDHELD_ITEM);
    }
}
