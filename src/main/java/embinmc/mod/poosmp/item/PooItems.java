package embinmc.mod.poosmp.item;

import embinmc.mod.poosmp.PooSMPMod;
import embinmc.mod.poosmp.item.component.PooComponents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
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
