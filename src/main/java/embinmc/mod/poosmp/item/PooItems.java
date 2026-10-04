package embinmc.mod.poosmp.item;

import embinmc.mod.poosmp.item.component.PooComponents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.EitherHolder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;

import java.util.function.Function;

public interface PooItems {
    private static Item register(ResourceKey<Item> key, Function<Item.Properties, Item> item, Item.Properties properties, Identifier modelId) {
        return Registry.register(BuiltInRegistries.ITEM, key, item.apply(properties.setId(key).modelId(modelId)));
    }

    private static Item register(ResourceKey<Item> key, Function<Item.Properties, Item> item, Item.Properties properties) {
        return register(key, item, properties, key.identifier());
    }

    private static Item register(ResourceKey<Item> key) {
        return register(key, Item::new, new Item.Properties());
    }

    private static Item warpStick(ResourceKey<Item> key, ResourceKey<Level> dimension) {
        return register(
                key, Item::new,
                new Item.Properties()
                        .rarity(Rarity.EPIC)
                        .component(PooComponents.WARP_DIMENSION, new EitherHolder<>(dimension)),
                BuiltInRegistries.ITEM.getKey(Items.STICK)
        );
    }
}
