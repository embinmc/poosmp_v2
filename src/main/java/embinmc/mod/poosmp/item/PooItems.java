package embinmc.mod.poosmp.item;

import embinmc.mod.poosmp.PooSMPMod;
import embinmc.mod.poosmp.item.component.*;
import embinmc.mod.poosmp.misc.PooSongs;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.component.Consumables;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;

import java.util.List;
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
    Item VILLAGER_STICK = mobStick(PooItemKeys.VILLAGER_STICK, EntityType.ZOMBIE, Rarity.RARE, MobSummonerComponent.NAMES_DEFAULT, true);

    private static Item warpStick(ResourceKey<Item> key, ResourceKey<Level> dimension) {
        return register(
                key, Item::new,
                new Item.Properties()
                        .rarity(Rarity.EPIC)
                        .component(PooComponents.DIMENSION_WARPER, new EitherHolder<>(dimension)),
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

    private static Item.Properties quickComponent(DataComponentType<Unit> component) {
        return new Item.Properties().component(component, Unit.INSTANCE);
    }

    private static Item.Properties quickRarity(Rarity rarity) {
        return new Item.Properties().rarity(rarity);
    }
}
