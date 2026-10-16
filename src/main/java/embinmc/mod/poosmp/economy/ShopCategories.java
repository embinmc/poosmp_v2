package embinmc.mod.poosmp.economy;

import embinmc.mod.poosmp.PooSMPMod;
import embinmc.mod.poosmp.item.PooItemKeys;
import embinmc.mod.poosmp.item.PooItems;
import embinmc.mod.poosmp.misc.PooRegistries;
import embinmc.mod.poosmp.misc.QuickBootstrapLoader;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.OptionalDouble;

public interface ShopCategories {
    ResourceKey<ShopCategory> MINERALS = key("minerals");
    ResourceKey<ShopCategory> BUILDING_BLOCKS = key("building_blocks");
    ResourceKey<ShopCategory> BANK_NOTES = key("bank_notes");
    ResourceKey<ShopCategory> NATURE = key("nature");
    ResourceKey<ShopCategory> MISC = key("misc");
    ResourceKey<ShopCategory> DYES = key("dyes");

    private static ResourceKey<ShopCategory> key(String id) {
        return ResourceKey.create(PooRegistries.Keys.SHOP_CATEGORY, PooSMPMod.id(id));
    }

    static void bootstrap(BootstrapContext<ShopCategory> bootstrapContext) {
        QuickBootstrapLoader<ShopCategory> bootstrapLoader = new QuickBootstrapLoader<>(bootstrapContext) {
            @Override
            public void bootstrap() {
                double emeraldPrice = 5.00D;
                double diamondPrice = 9.00D;
                double ironPrice = 4.50D;
                double goldPrice = 7.00D;
                double copperPrice = 2.50D;
                double netheriteScrapPrice = 400.00D;
                double honeycombPrice = 0.50D;
                double stonePrice = 0.1D;
                this.category(MINERALS, "Minerals", Items.DIAMOND)
                        .setVal(Items.HONEYCOMB, honeycombPrice)
                        .setVal(Items.COAL, 0.2)
                        .setVal(Items.COAL_BLOCK, 0.2 * 9)
                        .setVal(Items.DIAMOND, diamondPrice)
                        .setVal(PooItems.DIAMOND_SHARD, diamondPrice / 9)
                        .setVal(Items.DIAMOND_BLOCK, diamondPrice * 9)
                        .setVal(Items.IRON_INGOT, ironPrice)
                        .setVal(Items.IRON_NUGGET, ironPrice / 9)
                        .setVal(Items.IRON_BLOCK, ironPrice * 9)
                        .setVal(Items.GOLD_INGOT, goldPrice)
                        .setVal(Items.GOLD_NUGGET, goldPrice / 9)
                        .setVal(Items.GOLD_BLOCK, goldPrice * 9)
                        .setVal(Items.COPPER_INGOT, copperPrice)
                        .setVal(Items.COPPER_NUGGET, copperPrice / 9)
                        .setVal(Items.COPPER_BLOCK, copperPrice * 9)
                        .setVal(Items.WAXED_COPPER_BLOCK, (copperPrice * 9) + honeycombPrice)
                        .setVal(Items.OXIDIZED_COPPER, (copperPrice * 9) + 4)
                        .setVal(Items.WAXED_OXIDIZED_COPPER, (copperPrice * 9) + 4 + honeycombPrice)
                        .setVal(Items.EMERALD, emeraldPrice)
                        .setVal(Items.EMERALD_BLOCK, emeraldPrice * 9)
                        .setVal(Items.NETHERITE_SCRAP, netheriteScrapPrice)
                        .setVal(Items.NETHERITE_INGOT, (netheriteScrapPrice * 4) + (goldPrice * 4))
                        .setVal(Items.NETHERITE_BLOCK, ((netheriteScrapPrice * 4) + (goldPrice * 4)) * 9)
                        .setVal(Items.ECHO_SHARD, 64)
                        .setVal(Items.REDSTONE, 1)
                        .setVal(Items.REDSTONE_BLOCK, 9)
                        .setVal(Items.STICK, 0.02)
                        .setVal(Items.STRING, 0.06)
                        .setVal(Items.FEATHER, 0.04)
                        .setVal(Items.SPIDER_EYE, 0.04)
                        .setVal(Items.ROTTEN_FLESH, 0.02)
                        .setVal(Items.BONE, 0.06)
                        .setVal(Items.BONE_MEAL, 0.02)
                        .setVal(Items.BONE_BLOCK, 0.18)
                        .setVal(Items.NETHER_STAR, 1000, 1000)
                        .setVal(Items.GUNPOWDER, 0.10)
                        .setVal(Items.GLOW_INK_SAC, 0.04)
                        .setVal(Items.INK_SAC, 0.02)
                        .setVal(Items.LAPIS_LAZULI, 0.2)
                        .setVal(Items.LAPIS_BLOCK, 1.8)
                        .build();
                this.category(BANK_NOTES, "Bank Notes", PooItems.ONE_DOLLAR_BILL)
                        .banknote(PooItemKeys.ONE_DOLLAR_BILL, 1)
                        .banknote(PooItemKeys.TWO_DOLLAR_BILL, 2)
                        .banknote(PooItemKeys.FIVE_DOLLAR_BILL, 5)
                        .banknote(PooItemKeys.TEN_DOLLAR_BILL, 10)
                        .banknote(PooItemKeys.TWENTY_FIVE_DOLLAR_BILL, 25)
                        .banknote(PooItemKeys.FIFTY_DOLLAR_BILL, 50)
                        .banknote(PooItemKeys.HUNDRED_DOLLAR_BILL, 100)
                        .build();
                double mossPrice = 0.3;
                this.category(NATURE, "Nature", Items.GRASS_BLOCK)
                        .setVal(Items.GRASS_BLOCK, 0.06)
                        .setVal(Items.DIRT, 0.04)
                        .setVal(ItemTags.LOGS, 0.40)
                        .setVal(ItemTags.PLANKS, 0.10)
                        .setVal(Items.STONE, stonePrice)
                        .setVal(Items.COBBLESTONE, stonePrice)
                        .setVal(Items.ANDESITE, stonePrice)
                        .setVal(Items.DIORITE, stonePrice)
                        .setVal(Items.GRANITE, stonePrice)
                        .setVal(Items.DEEPSLATE, stonePrice * 2)
                        .setVal(Items.COBBLED_DEEPSLATE, stonePrice * 2)
                        .setVal(Items.MOSS_BLOCK, mossPrice)
                        .setVal(Items.PALE_MOSS_BLOCK, mossPrice)
                        .setVal(PooItems.DIM_MOSS_BLOCK, mossPrice)
                        .setVal(ItemTags.SAND, 0.10)
                        .setVal(Items.GRAVEL, 0.16)
                        .setVal(Items.LEATHER, 0.10)
                        .setVal(Items.SUGAR_CANE, 0.10)
                        .build();
                this.category(MISC, "Misc.", Items.TRIAL_KEY)
                        .setVal(PooItems.ANNOYANCE_SUS, 0.5)
                        .setVal(Items.TRIAL_KEY, 50, 40)
                        .setVal(Items.OMINOUS_TRIAL_KEY, 200, 180)
                        .build();
                this.category(DYES, "Dyes & Colored Blocks", Items.RED_DYE)
                        .setVal(Items.WHITE_DYE,      0.02)
                        .setVal(Items.LIGHT_GRAY_DYE, 0.02)
                        .setVal(Items.GRAY_DYE,   0.02)
                        .setVal(Items.BLACK_DYE,  0.02)
                        .setVal(Items.BROWN_DYE,  0.02)
                        .setVal(Items.RED_DYE,    0.02)
                        .setVal(Items.ORANGE_DYE, 0.02)
                        .setVal(Items.YELLOW_DYE, 0.02)
                        .setVal(Items.LIME_DYE,   0.02)
                        .setVal(Items.GREEN_DYE,  0.02)
                        .setVal(Items.CYAN_DYE,   0.02)
                        .setVal(Items.LIGHT_BLUE_DYE, 0.02)
                        .setVal(Items.BLUE_DYE,    0.02)
                        .setVal(Items.PURPLE_DYE,  0.02)
                        .setVal(Items.MAGENTA_DYE, 0.02)
                        .setVal(Items.PINK_DYE,    0.02)
                        .setVal(ItemTags.WOOL, 0.04)
                        .setVal(Items.WHITE_CONCRETE,      0.08)
                        .setVal(Items.LIGHT_GRAY_CONCRETE, 0.08)
                        .setVal(Items.GRAY_CONCRETE,   0.08)
                        .setVal(Items.BLACK_CONCRETE,  0.08)
                        .setVal(Items.BROWN_CONCRETE,  0.08)
                        .setVal(Items.RED_CONCRETE,    0.08)
                        .setVal(Items.ORANGE_CONCRETE, 0.08)
                        .setVal(Items.YELLOW_CONCRETE, 0.08)
                        .setVal(Items.LIME_CONCRETE,   0.08)
                        .setVal(Items.GREEN_CONCRETE,  0.08)
                        .setVal(Items.CYAN_CONCRETE,   0.08)
                        .setVal(Items.LIGHT_BLUE_CONCRETE, 0.08)
                        .setVal(Items.BLUE_CONCRETE,    0.08)
                        .setVal(Items.PURPLE_CONCRETE,  0.08)
                        .setVal(Items.MAGENTA_CONCRETE, 0.08)
                        .setVal(Items.PINK_CONCRETE,    0.08)
                        .setVal(Items.WHITE_CONCRETE_POWDER,      0.08)
                        .setVal(Items.LIGHT_GRAY_CONCRETE_POWDER, 0.08)
                        .setVal(Items.GRAY_CONCRETE_POWDER,   0.08)
                        .setVal(Items.BLACK_CONCRETE_POWDER,  0.08)
                        .setVal(Items.BROWN_CONCRETE_POWDER,  0.08)
                        .setVal(Items.RED_CONCRETE_POWDER,    0.08)
                        .setVal(Items.ORANGE_CONCRETE_POWDER, 0.08)
                        .setVal(Items.YELLOW_CONCRETE_POWDER, 0.08)
                        .setVal(Items.LIME_CONCRETE_POWDER,   0.08)
                        .setVal(Items.GREEN_CONCRETE_POWDER,  0.08)
                        .setVal(Items.CYAN_CONCRETE_POWDER,   0.08)
                        .setVal(Items.LIGHT_BLUE_CONCRETE_POWDER, 0.08)
                        .setVal(Items.BLUE_CONCRETE_POWDER,    0.08)
                        .setVal(Items.PURPLE_CONCRETE_POWDER,  0.08)
                        .setVal(Items.MAGENTA_CONCRETE_POWDER, 0.08)
                        .setVal(Items.PINK_CONCRETE_POWDER,    0.08)
                        .build();
            }

            private Builder category(ResourceKey<ShopCategory> key, String name, Item icon) {
                return new Builder(key, this.context(), icon.builtInRegistryHolder(), name);
            }

            private static class Builder {
                protected final BootstrapContext<ShopCategory> bootstrapContext;
                protected final HolderGetter<Item> items;
                protected final Holder<Item> icon;
                protected final Component name;
                protected final Map<HolderSet<Item>, ItemValue> priceMap;
                protected final ResourceKey<ShopCategory> key;

                private Builder(ResourceKey<ShopCategory> key, BootstrapContext<ShopCategory> bootstrapContext, Holder<Item> icon, String name) {
                    this.icon = icon;
                    this.name = Component.literal(name);
                    this.priceMap = LinkedHashMap.newLinkedHashMap(64);
                    this.bootstrapContext = bootstrapContext;
                    this.items = this.bootstrapContext.lookup(Registries.ITEM);
                    this.key = key;
                }

                public void build() {
                    this.bootstrapContext.register(this.key, new ShopCategory(this.name, this.icon, this.priceMap));
                }

                public Builder setVal(TagKey<Item> tag, ItemValue value) {
                    HolderSet<Item> set = this.items.getOrThrow(tag);
                    this.priceMap.put(set, value);
                    return this;
                }

                public Builder setVal(Item item, ItemValue value) {
                    HolderSet<Item> set = HolderSet.direct(item.builtInRegistryHolder());
                    this.priceMap.put(set, value);
                    return this;
                }

                public Builder banknote(ResourceKey<Item> item, double value) {
                    HolderSet<Item> banknote = HolderSet.direct(this.items.getOrThrow(item));
                    this.priceMap.put(banknote, new ItemValue(value, OptionalDouble.of(value)));
                    return this;
                }

                public Builder setVal(TagKey<Item> tag, double price) {
                    return this.setVal(tag, new ItemValue(price, OptionalDouble.empty()));
                }

                public Builder setVal(Item item, double price) {
                    return this.setVal(item, new ItemValue(price, OptionalDouble.empty()));
                }

                public Builder setVal(TagKey<Item> tag, double price, double sellValue) {
                    return this.setVal(tag, new ItemValue(price, OptionalDouble.of(sellValue)));
                }

                public Builder setVal(Item item, double price, double sellValue) {
                    return this.setVal(item, new ItemValue(price, OptionalDouble.of(sellValue)));
                }
            }
        };
        bootstrapLoader.bootstrap();
    }
}
