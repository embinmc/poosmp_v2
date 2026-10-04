package embinmc.mod.poosmp.item;

import embinmc.mod.poosmp.PooSMPMod;
import embinmc.mod.poosmp.item.component.PooComponents;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Unit;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

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
        return Registry.register(BuiltInRegistries.ITEM, key, item.apply(properties.setId(key).modelId(modelId)));
    }

    private static Item registerBlock(ResourceKey<Item> key, Block block, Item.Properties properties) {
        return registerBlock(key, block, properties, key.identifier());
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

    private static Item.Properties quickComponent(DataComponentType<Unit> component) {
        return new Item.Properties().component(component, Unit.INSTANCE);
    }
}
