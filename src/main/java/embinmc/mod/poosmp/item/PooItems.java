package embinmc.mod.poosmp.item;

import embinmc.mod.poosmp.PooSMPMod;
import embinmc.mod.poosmp.block.PooBlocks;
import embinmc.mod.poosmp.item.component.*;
import embinmc.mod.poosmp.misc.PooSongs;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Unit;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.component.Consumables;
import net.minecraft.world.item.component.ItemLore;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.dimension.LevelStem;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface PooItems {
    static void init() {
        PooSMPMod.LOGGER.info("Making PooSMP items!!!");
    }

    private static Item register(ResourceKey<Item> key, Function<Item.Properties, Item> item, Item.Properties properties, Identifier modelId) {
        return Registry.register(BuiltInRegistries.ITEM, key, item.apply(properties.setId(key).modelId(modelId)));
    }

    private static Item register(ResourceKey<Item> key, Function<Item.Properties, Item> item, Item.Properties properties) {
        return register(key, item, properties, key.identifier());
    }

    private static Item registerBlock(ResourceKey<Item> key, Block block, Item.Properties properties, Identifier modelId) {
        Function<Item.Properties, Item> item = properties1 -> new BlockItem(block, properties1);
        return Registry.register(BuiltInRegistries.ITEM, key, item.apply(properties.setId(key).modelId(modelId).useBlockDescriptionPrefix()));
    }

    private static Item registerBlock(ResourceKey<Item> key, Block block, Item.Properties properties) {
        return registerBlock(key, block, properties, key.identifier());
    }

    private static Item register(ResourceKey<Item> key, Item.Properties properties) {
        return register(key, Item::new, properties, key.identifier());
    }

    private static Item register(ResourceKey<Item> key) {
        return register(key, Item::new, new Item.Properties());
    }

    private static Item registerBlock(ResourceKey<Item> key, Block block) {
        return registerBlock(key, block, new Item.Properties());
    }

    Item POOP_STICK = stick(PooItemKeys.POOP_STICK,
            quickComponent(PooComponents.POOP_STICK).rarity(Rarity.UNCOMMON).fireResistant().stacksTo(1)
    );
    Item SERVER_SAYS_WHAT_STICK = stick(PooItemKeys.SERVER_SAYS_WHAT_STICK,
            quickComponent(PooComponents.SERVER_SAYS_WHAT).rarity(Rarity.UNCOMMON).fireResistant().stacksTo(1)
    );
    Item BIOME_STICK = stick(PooItemKeys.BIOME_STICK, new Item.Properties().rarity(Rarity.UNCOMMON).fireResistant()
            .component(PooComponents.BIOME_TRANSFORMER, new BiomeStickComponent((byte) 8, new EitherHolder<>(Biomes.PLAINS)))
    );
    Item BOOM_STICK = stick(PooItemKeys.BOOM_STICK,
            quickComponent(PooComponents.BOOM_STICK).rarity(Rarity.RARE).fireResistant()
    );
    Item ZOMBIE_STICK = mobStick(PooItemKeys.ZOMBIE_STICK, EntityType.ZOMBIE, Rarity.RARE, MobSummonerComponent.NAMES_DEFAULT, false);
    Item DIAMOND_SHARD = register(PooItemKeys.DIAMOND_SHARD);
    Item WEDDING_RING = register(PooItemKeys.WEDDING_RING, new Item.Properties()
            .component(PooComponents.WEDDING_RING, new WeddingRing(
                    WeddingRing.defaultNotMarriedAttributes(),
                    WeddingRing.defaultMarriedAttributes()
            )).rarity(Rarity.RARE).stacksTo(1).fireResistant()
    );
    Item RED_NETHER_BRICK = register(PooItemKeys.RED_NETHER_BRICK);
    Item POOP_BRICK = register(PooItemKeys.POOP_BRICK);
    Item POOPLET = food(PooItemKeys.POOPLET, PooFoods.POOPLET, Consumables.DRIED_KELP);
    Item RING = register(PooItemKeys.RING);
    Item TOTEM_OF_HEALTH = register(PooItemKeys.TOTEM_OF_HEALTH, new Item.Properties()
            .attributes(PooTotems.healthTotemAttributes(4, ""))
            .component(PooComponents.FROM_CREATIVE, true)
            .rarity(Rarity.UNCOMMON).stacksTo(1)
    );
    Item WARP_STICK = warpStick(PooItemKeys.WARP_STICK, PooSMPMod.HYRULE);
    Item FILL_ARMOR_TRIM_TEMPLATE = register(PooItemKeys.FILL_ARMOR_TRIM_TEMPLATE, SmithingTemplateItem::createArmorTrimTemplate, quickRarity(Rarity.UNCOMMON));
    Item DISC_TRIFECTA_CAP = requestedDisc(PooItemKeys.DISC_TRIFECTA_CAP, PooSongs.TRIFECTA_CAP, "Embin", false);
    Item DISC_BUTTERFLIES_INSTRUMENTAL = requestedDisc(PooItemKeys.DISC_BUTTERFLIES_INSTRUMENTAL, PooSongs.BUTTERFLIES_INSTRUMENTAL, "Embin", false);
    Item DISC_BUDDY_HOLLY = requestedDisc(PooItemKeys.DISC_BUDDY_HOLLY, PooSongs.BUDDY_HOLLY, "ianyourgod", false);
    Item DISC_STEREO_MADNESS = requestedDisc(PooItemKeys.DISC_STEREO_MADNESS, PooSongs.STEREO_MADNESS, "a_pc", false);
    Item DISC_NOT_LIKE_US = requestedDisc(PooItemKeys.DISC_NOT_LIKE_US, PooSongs.NOT_LIKE_US, "a_pc", false);
    Item DISC_RESISTANCE_INSTR = requestedDisc(PooItemKeys.DISC_RESISTANCE_INSTR, PooSongs.RESISTANCE_INSTRUMENTAL, "Embin", false);
    Item TOTEM_OF_REACH = register(PooItemKeys.TOTEM_OF_REACH, new Item.Properties()
            .attributes(PooTotems.reachTotemAttributes(1f, ""))
            .component(PooComponents.FROM_CREATIVE, true)
            .rarity(Rarity.UNCOMMON).stacksTo(1)
    );
    Item BLANK_MUSIC_DISC = register(PooItemKeys.BLANK_MUSIC_DISC, quickRarity(Rarity.UNCOMMON));
    Item ENCHANTED_TOTEM_OF_REACH = register(PooItemKeys.ENCHANTED_TOTEM_OF_REACH, new Item.Properties()
            .attributes(PooTotems.reachTotemAttributes(2f, ""))
            .component(PooComponents.FROM_CREATIVE, true)
            .rarity(Rarity.RARE).stacksTo(1)
            .component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true)
    );
    Item ENCHANTED_TOTEM_OF_HEALTH = register(PooItemKeys.ENCHANTED_TOTEM_OF_HEALTH, new Item.Properties()
            .attributes(PooTotems.healthTotemAttributes(6, "_enchanted"))
            .component(PooComponents.FROM_CREATIVE, true)
            .rarity(Rarity.RARE).stacksTo(1)
            .component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true)
    );
    Item DISC_BLISS_INSTRUMENTAL = requestedDisc(PooItemKeys.DISC_BLISS_INSTRUMENTAL, PooSongs.BLISS_INSTRUMENTAL, "Embin", false);
    Item DISC_ENDLESSLY_INSTRUMENTAL = requestedDisc(PooItemKeys.DISC_ENDLESSLY_INSTRUMENTAL, PooSongs.ENDLESSLY_INSTRUMENTAL, "Embin", false);
    Item DISC_ENDLESSLY = requestedDisc(PooItemKeys.DISC_ENDLESSLY, PooSongs.ENDLESSLY, "Embin", false);
    Item DISC_ENDLESSLY_STEREO = requestedDisc(PooItemKeys.DISC_ENDLESSLY_STEREO, PooSongs.ENDLESSLY_STEREO, "Embin", true);
    Item ZAP_STICK = snitchStick(PooItemKeys.ZAP_STICK, quickComponent(PooComponents.LIGHTNING_SUMMONER).rarity(Rarity.UNCOMMON));
    Item VILLAGER_STICK = mobStick(PooItemKeys.VILLAGER_STICK, EntityType.VILLAGER, Rarity.EPIC, MobSummonerComponent.NAMES_VILLAGER, true);

    Item POOP_BLOCK = registerBlock(PooItemKeys.POOP_BLOCK, PooBlocks.POOP_BLOCK);
    Item MISSINGNO_BLOCK = registerBlock(PooItemKeys.MISSINGNO_BLOCK, PooBlocks.MISSINGNO_BLOCK, quickRarity(Rarity.EPIC));
    Item POOP_BRICKS = registerBlock(PooItemKeys.POOP_BRICKS, PooBlocks.POOP_BRICKS);
    Item POOP_BRICK_STAIRS = registerBlock(PooItemKeys.POOP_BRICK_STAIRS, PooBlocks.POOP_BRICK_STAIRS);
    Item POOP_BRICK_SLAB = registerBlock(PooItemKeys.POOP_BRICK_SLAB, PooBlocks.POOP_BRICK_SLAB);
    Item POOP_BRICK_WALL = registerBlock(PooItemKeys.POOP_BRICK_WALL, PooBlocks.POOP_BRICK_WALL);
    Item RED_NETHER_BRICK_FENCE = registerBlock(PooItemKeys.RED_NETHER_BRICK_FENCE, PooBlocks.RED_NETHER_BRICK_FENCE);
    Item ANNOYANCE_SUS = registerBlock(PooItemKeys.ANNOYANCE_SUS, PooBlocks.ANNOYANCE_SUS);
    Item DDE_BLOCK = registerBlock(PooItemKeys.DDE_BLOCK, PooBlocks.DDE_BLOCK);
    Item PENIS_BLOCK = registerBlock(PooItemKeys.PENIS_BLOCK, PooBlocks.PENIS_BLOCK);
    Item BANKERS_TABLE = registerBlock(PooItemKeys.BANKERS_TABLE, PooBlocks.BANKERS_TABLE);
    Item RED_POO_BLOCK = registerBlock(PooItemKeys.RED_POO_BLOCK, PooBlocks.RED_POO_BLOCK, quickRarity(Rarity.UNCOMMON));
    Item DRAGON_ANNOYANCE = registerBlock(PooItemKeys.ANNOYANCE_DRAGON, PooBlocks.DRAGON_ANNOYANCE);
    Item FAKE_DIRT = registerBlock(PooItemKeys.FAKE_DIRT, PooBlocks.FAKE_DIRT);
    Item FAKE_GRASS_BLOCK = registerBlock(PooItemKeys.FAKE_GRASS_BLOCK, PooBlocks.FAKE_GRASS_BLOCK);
    Item FAKE_STONE = registerBlock(PooItemKeys.FAKE_STONE, PooBlocks.FAKE_STONE);
    Item RIGGED_STONE = registerBlock(PooItemKeys.RIGGED_STONE, PooBlocks.RIGGED_STONE);
    Item DIM_MOSS_BLOCK = registerBlock(PooItemKeys.DIM_MOSS_BLOCK, PooBlocks.DIM_MOSS_BLOCK, quickRarity(Rarity.UNCOMMON));
    Item DIM_MOSS_CARPET = registerBlock(PooItemKeys.DIM_MOSS_CARPET, PooBlocks.DIM_MOSS_CARPET, quickRarity(Rarity.UNCOMMON));
    Item DEHEED_ANNOYANCE = registerBlock(PooItemKeys.ANNOYANCE_DEHEED, PooBlocks.DEHEED_ANNOYANCE);

    Item DECODE_EXCEPTION_DUMMY = register(PooItemKeys.DECODE_EXCEPTION_DUMMY);

    private static Item warpStick(ResourceKey<Item> key, ResourceKey<Level> dimension) {
        MobEffectInstance effect = new MobEffectInstance(DimensionWarperComponent.DEFAULT_EFFECT);
        DimensionWarperComponent warper = new DimensionWarperComponent(new EitherHolder<>(dimension), Optional.of(effect));
        return register(
                key, Item::new,
                new Item.Properties()
                        .rarity(Rarity.EPIC)
                        .component(PooComponents.DIMENSION_WARPER, warper),
                BuiltInRegistries.ITEM.getKey(Items.STICK)
        );
    }

    private static Item stick(ResourceKey<Item> key, Item.Properties properties) {
        return register(key, Item::new, properties, Items.STICK.builtInRegistryHolder().key().identifier());
    }

    private static Item snitchStick(ResourceKey<Item> key, Item.Properties properties) {
        return stick(key, properties.component(PooComponents.FROM_CREATIVE, true));
    }

    private static Item mobStick(ResourceKey<Item> key, EntityType<?> entityType, Rarity rarity, List<String> names, boolean snitch) {
        Item.Properties properties = new Item.Properties()
                .fireResistant().rarity(rarity)
                .component(PooComponents.MOB_SUMMONER, new MobSummonerComponent(entityType, names));
        return snitch ? snitchStick(key, properties) : stick(key, properties);
    }

    private static Item food(ResourceKey<Item> key, FoodProperties food, Consumable consumable) {
        return register(key, Item::new, new Item.Properties().food(food, consumable));
    }

    private static Item requestedDisc(ResourceKey<Item> key, ResourceKey<JukeboxSong> song, String requester, boolean stereo) {
        return register(key, new Item.Properties()
                .jukeboxPlayable(song).stacksTo(1).rarity(Rarity.RARE)
                .component(PooComponents.REQUESTED_DISC, new RequestedDiscComponent(requester, stereo))
        );
    }

    private static Item dollar(ResourceKey<Item> key, double amount, Rarity rarity) {
        return register(key, new Item.Properties()
                .stacksTo(99)
                .component(DataComponents.LORE, new ItemLore(List.of(
                        Component.literal("Worth %,.2f".formatted(amount)).withStyle(ChatFormatting.GREEN)
                )))
                .rarity(rarity)
        );
    }

    private static Item.Properties quickComponent(DataComponentType<Unit> component) {
        return new Item.Properties().component(component, Unit.INSTANCE);
    }

    private static Item.Properties quickRarity(Rarity rarity) {
        return new Item.Properties().rarity(rarity);
    }
}
