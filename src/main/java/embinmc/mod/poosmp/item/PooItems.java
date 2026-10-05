package embinmc.mod.poosmp.item;

import embinmc.mod.poosmp.PooSMPMod;
import embinmc.mod.poosmp.item.component.BiomeStickComponent;
import embinmc.mod.poosmp.item.component.MobSummonerComponent;
import embinmc.mod.poosmp.item.component.PooComponents;
import embinmc.mod.poosmp.item.component.WeddingRing;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
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
    Item ZOMBIE_STICK = mobStick(PooItemKeys.ZOMBIE_STICK, EntityType.ZOMBIE, Rarity.RARE, MobSummonerComponent.NAMES_DEFAULT);
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

    private static Item mobStick(ResourceKey<Item> key, EntityType<?> entityType, Rarity rarity, List<String> names) {
        return snitchStick(key, new Item.Properties()
                .fireResistant().rarity(rarity)
                .component(PooComponents.FROM_CREATIVE, true)
                .component(PooComponents.MOB_SUMMONER, new MobSummonerComponent(entityType, names))
        );
    }

    private static Item food(ResourceKey<Item> key, FoodProperties food, Consumable consumable) {
        return register(key, Item::new, new Item.Properties().food(food, consumable));
    }

    private static Item.Properties quickComponent(DataComponentType<Unit> component) {
        return new Item.Properties().component(component, Unit.INSTANCE);
    }
}
