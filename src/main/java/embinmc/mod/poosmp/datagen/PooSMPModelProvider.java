package embinmc.mod.poosmp.datagen;

import embinmc.mod.poosmp.item.PooItems;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.equipment.EquipmentAssets;
import org.jspecify.annotations.NonNull;

public class PooSMPModelProvider extends FabricModelProvider {
    public static final Identifier TRIM_PREFIX_SWORD = ItemModelGenerators.prefixForSlotTrim("sword");
    public static final Identifier TRIM_PREFIX_PICKAXE = ItemModelGenerators.prefixForSlotTrim("pickaxe");
    public static final Identifier TRIM_PREFIX_AXE = ItemModelGenerators.prefixForSlotTrim("axe");
    public static final Identifier TRIM_PREFIX_SHOVEL = ItemModelGenerators.prefixForSlotTrim("shovel");
    public static final Identifier TRIM_PREFIX_HOE = ItemModelGenerators.prefixForSlotTrim("hoe");

    public PooSMPModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public @NonNull String getName() {
        return "PooSMP: " + super.getName();
    }

    @Override
    public void generateBlockStateModels(@NonNull BlockModelGenerators gen) {
    }

    @Override
    public void generateItemModels(@NonNull ItemModelGenerators gen) {
        this.generateTrimmedTools(gen);
        gen.createFlatItemModel(PooItems.POOP_STICK, Items.STICK, ModelTemplates.FLAT_HANDHELD_ITEM);
        gen.createFlatItemModel(PooItems.SERVER_SAYS_WHAT_STICK, Items.STICK, ModelTemplates.FLAT_HANDHELD_ITEM);
    }

    private void generateTrimmedTools(@NonNull ItemModelGenerators gen) {
        gen.generateTrimmableItem(Items.WOODEN_SWORD  , EquipmentAssets.LEATHER, TRIM_PREFIX_SWORD  , false);
        gen.generateTrimmableItem(Items.WOODEN_PICKAXE, EquipmentAssets.LEATHER, TRIM_PREFIX_PICKAXE, false);
        gen.generateTrimmableItem(Items.WOODEN_AXE    , EquipmentAssets.LEATHER, TRIM_PREFIX_AXE    , false);
        gen.generateTrimmableItem(Items.WOODEN_SHOVEL , EquipmentAssets.LEATHER, TRIM_PREFIX_SHOVEL , false);
        gen.generateTrimmableItem(Items.WOODEN_HOE    , EquipmentAssets.LEATHER, TRIM_PREFIX_HOE    , false);

        gen.generateTrimmableItem(Items.STONE_SWORD  , EquipmentAssets.CHAINMAIL, TRIM_PREFIX_SWORD  , false);
        gen.generateTrimmableItem(Items.STONE_PICKAXE, EquipmentAssets.CHAINMAIL, TRIM_PREFIX_PICKAXE, false);
        gen.generateTrimmableItem(Items.STONE_AXE    , EquipmentAssets.CHAINMAIL, TRIM_PREFIX_AXE    , false);
        gen.generateTrimmableItem(Items.STONE_SHOVEL , EquipmentAssets.CHAINMAIL, TRIM_PREFIX_SHOVEL , false);
        gen.generateTrimmableItem(Items.STONE_HOE    , EquipmentAssets.CHAINMAIL, TRIM_PREFIX_HOE    , false);

        gen.generateTrimmableItem(Items.IRON_SWORD  , EquipmentAssets.IRON, TRIM_PREFIX_SWORD  , false);
        gen.generateTrimmableItem(Items.IRON_PICKAXE, EquipmentAssets.IRON, TRIM_PREFIX_PICKAXE, false);
        gen.generateTrimmableItem(Items.IRON_AXE    , EquipmentAssets.IRON, TRIM_PREFIX_AXE    , false);
        gen.generateTrimmableItem(Items.IRON_SHOVEL , EquipmentAssets.IRON, TRIM_PREFIX_SHOVEL , false);
        gen.generateTrimmableItem(Items.IRON_HOE    , EquipmentAssets.IRON, TRIM_PREFIX_HOE    , false);

        gen.generateTrimmableItem(Items.GOLDEN_SWORD  , EquipmentAssets.GOLD, TRIM_PREFIX_SWORD  , false);
        gen.generateTrimmableItem(Items.GOLDEN_PICKAXE, EquipmentAssets.GOLD, TRIM_PREFIX_PICKAXE, false);
        gen.generateTrimmableItem(Items.GOLDEN_AXE    , EquipmentAssets.GOLD, TRIM_PREFIX_AXE    , false);
        gen.generateTrimmableItem(Items.GOLDEN_SHOVEL , EquipmentAssets.GOLD, TRIM_PREFIX_SHOVEL , false);
        gen.generateTrimmableItem(Items.GOLDEN_HOE    , EquipmentAssets.GOLD, TRIM_PREFIX_HOE    , false);

        gen.generateTrimmableItem(Items.DIAMOND_SWORD  , EquipmentAssets.DIAMOND, TRIM_PREFIX_SWORD  , false);
        gen.generateTrimmableItem(Items.DIAMOND_PICKAXE, EquipmentAssets.DIAMOND, TRIM_PREFIX_PICKAXE, false);
        gen.generateTrimmableItem(Items.DIAMOND_AXE    , EquipmentAssets.DIAMOND, TRIM_PREFIX_AXE    , false);
        gen.generateTrimmableItem(Items.DIAMOND_SHOVEL , EquipmentAssets.DIAMOND, TRIM_PREFIX_SHOVEL , false);
        gen.generateTrimmableItem(Items.DIAMOND_HOE    , EquipmentAssets.DIAMOND, TRIM_PREFIX_HOE    , false);

        gen.generateTrimmableItem(Items.NETHERITE_SWORD  , EquipmentAssets.NETHERITE, TRIM_PREFIX_SWORD  , false);
        gen.generateTrimmableItem(Items.NETHERITE_PICKAXE, EquipmentAssets.NETHERITE, TRIM_PREFIX_PICKAXE, false);
        gen.generateTrimmableItem(Items.NETHERITE_AXE    , EquipmentAssets.NETHERITE, TRIM_PREFIX_AXE    , false);
        gen.generateTrimmableItem(Items.NETHERITE_SHOVEL , EquipmentAssets.NETHERITE, TRIM_PREFIX_SHOVEL , false);
        gen.generateTrimmableItem(Items.NETHERITE_HOE    , EquipmentAssets.NETHERITE, TRIM_PREFIX_HOE    , false);
    }
}
