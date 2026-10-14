package embinmc.mod.poosmp.datagen;

import embinmc.mod.poosmp.PooSMPMod;
import embinmc.mod.poosmp.block.PooBlockFamilies;
import embinmc.mod.poosmp.block.PooBlocks;
import embinmc.mod.poosmp.item.PooEquipmentAssets;
import embinmc.mod.poosmp.item.PooItems;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TexturedModel;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.equipment.EquipmentAssets;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.jspecify.annotations.NonNull;

public class PooSMPModelProvider extends FabricModelProvider {
    public static final Identifier TRIM_PREFIX_SWORD = prefixForSlotTrim("sword");
    public static final Identifier TRIM_PREFIX_PICKAXE = prefixForSlotTrim("pickaxe");
    public static final Identifier TRIM_PREFIX_AXE = prefixForSlotTrim("axe");
    public static final Identifier TRIM_PREFIX_SHOVEL = prefixForSlotTrim("shovel");
    public static final Identifier TRIM_PREFIX_HOE = prefixForSlotTrim("hoe");
    protected @NonNull BlockModelGenerators bmg = null;

    public static Identifier prefixForSlotTrim(String string) {
        return PooSMPMod.id("trims/items/" + string + "_trim");
    }

    public PooSMPModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public @NonNull String getName() {
        return "PooSMP: " + super.getName();
    }

    @Override // FOR BLOCKS, STICKS, AND PREDEFINED ITEM MODELS
    public void generateBlockStateModels(@NonNull BlockModelGenerators gen) {
        this.bmg = gen;
        Identifier stickId = ModelLocationUtils.getModelLocation(Items.STICK);
        gen.registerSimpleItemModel(PooItems.POOP_STICK, stickId);
        gen.registerSimpleItemModel(PooItems.SERVER_SAYS_WHAT_STICK, stickId);
        gen.registerSimpleItemModel(PooItems.BIOME_STICK, stickId);
        gen.registerSimpleItemModel(PooItems.BOOM_STICK, stickId);
        gen.registerSimpleItemModel(PooItems.ZOMBIE_STICK, stickId);
        gen.registerSimpleItemModel(PooItems.WEDDING_RING, PooSMPMod.id("item/wedding_ring"));
        gen.registerSimpleItemModel(PooItems.WARP_STICK, stickId);
        gen.registerSimpleItemModel(PooItems.ZAP_STICK, stickId);
        gen.registerSimpleItemModel(PooItems.VILLAGER_STICK, stickId);
        gen.registerSimpleItemModel(PooItems.NULL_STICK, stickId);
        gen.registerSimpleItemModel(PooItems.JUMPSCARE_STICK, stickId);
        gen.registerSimpleItemModel(PooItems.FUN_STICK, stickId);
        gen.registerSimpleItemModel(PooItems.DIMWORLD_STICK, stickId);
        gen.registerSimpleItemModel(PooItems.FROG_STICK, stickId);
        gen.registerSimpleItemModel(PooItems.WIND_STICK, stickId);
        gen.registerSimpleItemModel(PooItems.FAKE_DIRT, ModelLocationUtils.getModelLocation(Blocks.DIRT));
        gen.registerSimpleItemModel(PooItems.FAKE_GRASS_BLOCK, ModelLocationUtils.getModelLocation(Blocks.GRASS_BLOCK));
        gen.registerSimpleItemModel(PooItems.FAKE_STONE, ModelLocationUtils.getModelLocation(Blocks.STONE));
        gen.registerSimpleItemModel(PooItems.RIGGED_STONE, ModelLocationUtils.getModelLocation(Blocks.STONE));
        gen.registerSimpleItemModel(PooItems.PENIS_BLOCK, PooSMPMod.id("block/penis"));

        gen.createTrivialCube(PooBlocks.POOP_BLOCK);
        gen.createTrivialCube(PooBlocks.MISSINGNO_BLOCK);
        gen.family(PooBlocks.POOP_BRICKS).generateFor(PooBlockFamilies.POOP_BRICKS);
        this.createCustomFence(PooBlocks.RED_NETHER_BRICK_FENCE, Blocks.RED_NETHER_BRICKS);
        gen.createTrivialCube(PooBlocks.ANNOYANCE_SUS);
        gen.createTrivialCube(PooBlocks.DDE_BLOCK);
        gen.createTrivialCube(PooBlocks.BANKERS_TABLE);
        gen.createTrivialCube(PooBlocks.RED_POO_BLOCK);
        gen.createTrivialCube(PooBlocks.DRAGON_ANNOYANCE);
        gen.createFullAndCarpetBlocks(PooBlocks.DIM_MOSS_BLOCK, PooBlocks.DIM_MOSS_CARPET);
        gen.createTrivialCube(PooBlocks.DEHEED_ANNOYANCE);
    }

    @Override // FOR NEW ITEMS
    public void generateItemModels(@NonNull ItemModelGenerators gen) {
        this.generateTrimmedTools(gen);
        gen.generateFlatItem(PooItems.DIAMOND_SHARD, ModelTemplates.FLAT_HANDHELD_ITEM);
        gen.generateFlatItem(PooItems.RED_NETHER_BRICK, ModelTemplates.FLAT_HANDHELD_ITEM);
        gen.generateFlatItem(PooItems.POOP_BRICK, ModelTemplates.FLAT_HANDHELD_ITEM);
        gen.generateFlatItem(PooItems.POOPLET, ModelTemplates.FLAT_HANDHELD_ITEM);
        gen.generateFlatItem(PooItems.RING, ModelTemplates.FLAT_HANDHELD_ITEM);
        gen.generateFlatItem(PooItems.TOTEM_OF_HEALTH, ModelTemplates.FLAT_HANDHELD_ITEM);
        gen.generateFlatItem(PooItems.FILL_ARMOR_TRIM_TEMPLATE, ModelTemplates.FLAT_HANDHELD_ITEM);
        gen.generateFlatItem(PooItems.DISC_TRIFECTA_CAP, ModelTemplates.FLAT_HANDHELD_ITEM);
        gen.generateFlatItem(PooItems.DISC_BUTTERFLIES_INSTRUMENTAL, ModelTemplates.FLAT_HANDHELD_ITEM);
        gen.generateFlatItem(PooItems.DISC_BUDDY_HOLLY, ModelTemplates.FLAT_HANDHELD_ITEM);
        gen.generateFlatItem(PooItems.DISC_STEREO_MADNESS, ModelTemplates.FLAT_HANDHELD_ITEM);
        gen.generateFlatItem(PooItems.DISC_NOT_LIKE_US, ModelTemplates.FLAT_HANDHELD_ITEM);
        gen.generateFlatItem(PooItems.DISC_RESISTANCE_INSTR, ModelTemplates.FLAT_HANDHELD_ITEM);
        gen.generateFlatItem(PooItems.TOTEM_OF_REACH, ModelTemplates.FLAT_HANDHELD_ITEM);
        gen.generateFlatItem(PooItems.BLANK_MUSIC_DISC, ModelTemplates.FLAT_HANDHELD_ITEM);
        gen.generateFlatItem(PooItems.ENCHANTED_TOTEM_OF_REACH, PooItems.TOTEM_OF_REACH, ModelTemplates.FLAT_HANDHELD_ITEM);
        gen.generateFlatItem(PooItems.ENCHANTED_TOTEM_OF_HEALTH, PooItems.TOTEM_OF_HEALTH, ModelTemplates.FLAT_HANDHELD_ITEM);
        gen.generateFlatItem(PooItems.DISC_BLISS_INSTRUMENTAL, ModelTemplates.FLAT_HANDHELD_ITEM);
        gen.generateFlatItem(PooItems.DISC_ENDLESSLY_INSTRUMENTAL, ModelTemplates.FLAT_HANDHELD_ITEM);
        gen.generateFlatItem(PooItems.DISC_ENDLESSLY, ModelTemplates.FLAT_HANDHELD_ITEM);
        gen.generateFlatItem(PooItems.DISC_ENDLESSLY_STEREO, ModelTemplates.FLAT_HANDHELD_ITEM);
        gen.generateFlatItem(PooItems.ONE_DOLLAR_BILL, ModelTemplates.FLAT_HANDHELD_ITEM);
        gen.generateFlatItem(PooItems.TWO_DOLLAR_BILL, ModelTemplates.FLAT_HANDHELD_ITEM);
        gen.generateFlatItem(PooItems.FIVE_DOLLAR_BILL, ModelTemplates.FLAT_HANDHELD_ITEM);
        gen.generateFlatItem(PooItems.TEN_DOLLAR_BILL, ModelTemplates.FLAT_HANDHELD_ITEM);
        gen.generateFlatItem(PooItems.TWENTY_FIVE_DOLLAR_BILL, ModelTemplates.FLAT_HANDHELD_ITEM);
        gen.generateFlatItem(PooItems.FIFTY_DOLLAR_BILL, ModelTemplates.FLAT_HANDHELD_ITEM);
        gen.generateFlatItem(PooItems.HUNDRED_DOLLAR_BILL, ModelTemplates.FLAT_HANDHELD_ITEM);
        gen.generateFlatItem(PooItems.DISC_STORY_OF_UNDERTALE, ModelTemplates.FLAT_HANDHELD_ITEM);
        gen.generateFlatItem(PooItems.RAW_RED_POO, ModelTemplates.FLAT_HANDHELD_ITEM);
        gen.generateFlatItem(PooItems.RED_POO_INGOT, ModelTemplates.FLAT_HANDHELD_ITEM);
        gen.generateFlatItem(PooItems.RED_POO_UPGRADE_TEMPLATE, ModelTemplates.FLAT_HANDHELD_ITEM);
        gen.generateFlatItem(PooItems.BANANA, ModelTemplates.FLAT_HANDHELD_ITEM);
        gen.generateFlatItem(PooItems.GEAR, ModelTemplates.FLAT_HANDHELD_ITEM);
        gen.generateFlatItem(PooItems.SCREW, ModelTemplates.FLAT_HANDHELD_ITEM);
        gen.generateFlatItem(PooItems.GLASS_SHARD, ModelTemplates.FLAT_HANDHELD_ITEM);
        gen.generateFlatItem(PooItems.MAGIC_DEVICE, ModelTemplates.FLAT_HANDHELD_ITEM);
        gen.generateFlatItem(PooItems.NULL_SHARD, ModelTemplates.FLAT_HANDHELD_ITEM);
        gen.generateFlatItem(PooItems.ITEM_FORCING_BACKPACK_UPGRADE, ModelTemplates.FLAT_HANDHELD_ITEM);
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

        gen.generateTrimmableItem(PooItems.RED_POO_SWORD     , PooEquipmentAssets.RED_POO, TRIM_PREFIX_SWORD  , false);
        gen.generateTrimmableItem(PooItems.RED_POO_PICKAXE   , PooEquipmentAssets.RED_POO, TRIM_PREFIX_PICKAXE, false);
        gen.generateTrimmableItem(PooItems.RED_POO_AXE       , PooEquipmentAssets.RED_POO, TRIM_PREFIX_AXE    , false);
        gen.generateTrimmableItem(PooItems.RED_POO_SHOVEL    , PooEquipmentAssets.RED_POO, TRIM_PREFIX_SHOVEL , false);
        gen.generateTrimmableItem(PooItems.RED_POO_HOE       , PooEquipmentAssets.RED_POO, TRIM_PREFIX_HOE    , false);
        gen.generateTrimmableItem(PooItems.RED_POO_HELMET    , PooEquipmentAssets.RED_POO, ItemModelGenerators.TRIM_PREFIX_HELMET    , false);
        gen.generateTrimmableItem(PooItems.RED_POO_CHESTPLATE, PooEquipmentAssets.RED_POO, ItemModelGenerators.TRIM_PREFIX_CHESTPLATE, false);
        gen.generateTrimmableItem(PooItems.RED_POO_LEGGINGS  , PooEquipmentAssets.RED_POO, ItemModelGenerators.TRIM_PREFIX_LEGGINGS  , false);
        gen.generateTrimmableItem(PooItems.RED_POO_BOOTS     , PooEquipmentAssets.RED_POO, ItemModelGenerators.TRIM_PREFIX_BOOTS     , false);
    }

    private void createCustomFence(Block fence, Block fenceOf) {
        TextureMapping textureMapping = TextureMapping.customParticle(fenceOf);
        MultiVariant multiVariant = BlockModelGenerators.plainVariant(
                ModelTemplates.CUSTOM_FENCE_POST.create(fence, textureMapping, this.bmg.modelOutput)
        );
        MultiVariant multiVariant2 = BlockModelGenerators.plainVariant(
                ModelTemplates.CUSTOM_FENCE_SIDE_NORTH.create(fence, textureMapping, this.bmg.modelOutput)
        );
        MultiVariant multiVariant3 = BlockModelGenerators.plainVariant(
                ModelTemplates.CUSTOM_FENCE_SIDE_EAST.create(fence, textureMapping, this.bmg.modelOutput)
        );
        MultiVariant multiVariant4 = BlockModelGenerators.plainVariant(
                ModelTemplates.CUSTOM_FENCE_SIDE_SOUTH.create(fence, textureMapping, this.bmg.modelOutput)
        );
        MultiVariant multiVariant5 = BlockModelGenerators.plainVariant(
                ModelTemplates.CUSTOM_FENCE_SIDE_WEST.create(fence, textureMapping, this.bmg.modelOutput)
        );
        this.bmg.blockStateOutput
                .accept(BlockModelGenerators.createCustomFence(fence, multiVariant, multiVariant2, multiVariant3, multiVariant4, multiVariant5));
        Identifier identifier = ModelTemplates.CUSTOM_FENCE_INVENTORY.create(fence, textureMapping, this.bmg.modelOutput);
        this.bmg.registerSimpleItemModel(fence, identifier);
    }

    private void createWall(Block wall, Block wallOf) {
        TextureMapping mapping = TexturedModel.CUBE.get(wallOf).getMapping();
        MultiVariant multiVariant = BlockModelGenerators.plainVariant(ModelTemplates.WALL_POST.create(wallOf, mapping, this.bmg.modelOutput));
        MultiVariant multiVariant2 = BlockModelGenerators.plainVariant(
                ModelTemplates.WALL_LOW_SIDE.create(wallOf, mapping, this.bmg.modelOutput)
        );
        MultiVariant multiVariant3 = BlockModelGenerators.plainVariant(
                ModelTemplates.WALL_TALL_SIDE.create(wallOf, mapping, this.bmg.modelOutput)
        );
        this.bmg.blockStateOutput.accept(BlockModelGenerators.createWall(wall, multiVariant, multiVariant2, multiVariant3));
        Identifier identifier = ModelTemplates.WALL_INVENTORY.create(wall, mapping, this.bmg.modelOutput);
        this.bmg.registerSimpleItemModel(wall, identifier);
    }
}
