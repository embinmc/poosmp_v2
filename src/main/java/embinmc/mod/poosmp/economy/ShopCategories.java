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
            static final double EMERALD = 5.00D;
            static final double DIAMOND = 18.00D;
            static final double IRON = 4.50D;
            static final double GOLD = 7.00D;
            static final double COPPER = 2.50D;
            static final double NETHERITE_SCRAP = 400.00D;
            static final double HONEYCOMB = 0.50D;
            static final double STONE = 0.1D;
            static final double RARE_STONE = STONE * 2;
            static final double DYE = 0.02D;
            static final double MOSS = 0.3D;
            static final double PLANKS = 0.1D;
            static final double LOG = PLANKS * 4;
            static final double SUGAR_CANE = 0.1D;
            static final double LEATHER = 0.1D;
            static final double BOOK = LEATHER + (SUGAR_CANE * 3);

            @Override
            public void bootstrap() {
                this.category(MINERALS, "Minerals", Items.DIAMOND)
                    .setVal(Items.HONEYCOMB, HONEYCOMB)
                    .setVal(Items.COAL, 0.2)
                    .setVal(Items.COAL_BLOCK, 0.2 * 9)
                    .setVal(Items.DIAMOND, DIAMOND)
                    .setVal(PooItems.DIAMOND_SHARD, DIAMOND / 9)
                    .setVal(Items.DIAMOND_BLOCK, DIAMOND * 9)
                    .setVal(Items.IRON_INGOT, IRON)
                    .setVal(Items.IRON_NUGGET, IRON / 9)
                    .setVal(Items.IRON_BLOCK, IRON * 9)
                    .setVal(Items.GOLD_INGOT, GOLD)
                    .setVal(Items.GOLD_NUGGET, GOLD / 9)
                    .setVal(Items.GOLD_BLOCK, GOLD * 9)
                    .setVal(Items.COPPER_INGOT, COPPER)
                    .setVal(Items.COPPER_NUGGET, COPPER / 9)
                    .setVal(Items.COPPER_BLOCK, COPPER * 9)
                    .setVal(Items.WAXED_COPPER_BLOCK, (COPPER * 9) + HONEYCOMB)
                    .setVal(Items.OXIDIZED_COPPER, (COPPER * 9) + 3)
                    .setVal(Items.WAXED_OXIDIZED_COPPER, (COPPER * 9) + 3 + HONEYCOMB)
                    .setVal(Items.EMERALD, EMERALD)
                    .setVal(Items.EMERALD_BLOCK, EMERALD * 9)
                    .setVal(Items.NETHERITE_SCRAP, NETHERITE_SCRAP)
                    .setVal(Items.NETHERITE_INGOT, (NETHERITE_SCRAP * 4) + (GOLD * 4))
                    .setVal(Items.NETHERITE_BLOCK, ((NETHERITE_SCRAP * 4) + (GOLD * 4)) * 9)
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
                    .setVal(Items.SLIME_BALL, 0.10)
                    .setVal(Items.RESIN_CLUMP, 0.10)
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
                this.category(NATURE, "Nature", Items.GRASS_BLOCK)
                    .setVal(Items.GRASS_BLOCK, 0.06)
                    .setVal(Items.DIRT, 0.04)
                    .setVal(ItemTags.LOGS, LOG)
                    .setVal(ItemTags.PLANKS, PLANKS)
                    .setVal(Items.STONE, STONE)
                    .setVal(Items.COBBLESTONE, STONE)
                    .setVal(Items.ANDESITE, STONE)
                    .setVal(Items.DIORITE, STONE)
                    .setVal(Items.GRANITE, STONE)
                    .setVal(Items.DEEPSLATE, RARE_STONE)
                    .setVal(Items.COBBLED_DEEPSLATE, RARE_STONE)
                    .setVal(Items.MOSS_BLOCK, MOSS)
                    .setVal(Items.PALE_MOSS_BLOCK, MOSS)
                    .setVal(PooItems.DIM_MOSS_BLOCK, MOSS)
                    .setVal(Items.SAND, 0.10)
                    .setVal(Items.RED_SAND, 0.20)
                    .setVal(Items.GRAVEL, 0.16)
                    .setVal(Items.LEATHER, LEATHER)
                    .setVal(Items.SUGAR_CANE, SUGAR_CANE)
                    .setVal(Items.SHROOMLIGHT, 0.2)
                    .setVal(Items.GLOWSTONE, 0.1)
                    .setVal(Items.NETHERRACK, 0.02)
                    .setVal(Items.BASALT, 0.06)
                    .setVal(Items.BLACKSTONE, 0.06)
                    .setVal(Items.WHEAT, 0.06)
                    .setVal(Items.OBSIDIAN, 1)
                    .setVal(Items.TUFF, RARE_STONE)
                    .build();
                this.category(MISC, "Misc.", Items.TRIAL_KEY)
                    .setVal(PooItems.ANNOYANCE_SUS, 0.5)
                    .setVal(Items.TRIAL_KEY, 50, 40)
                    .setVal(Items.OMINOUS_TRIAL_KEY, 200, 180)
                    .setVal(Items.ENDER_EYE, 32)
                    .setVal(Items.ECHO_SHARD, 64)
                    .setVal(Items.RESIN_BRICK, 0.10)
                    .setVal(Items.BRICK, 0.10)
                    .setVal(Items.NETHER_BRICK, 0.02)
                    .setVal(PooItems.RED_NETHER_BRICK, 0.04)
                    .setVal(Items.BOOK, BOOK)
                    .build();
                this.category(DYES, "Dyes & Colored Blocks", Items.RED_DYE)
                    .setVal(Items.WHITE_DYE,      DYE)
                    .setVal(Items.LIGHT_GRAY_DYE, DYE)
                    .setVal(Items.GRAY_DYE,       DYE)
                    .setVal(Items.BLACK_DYE,      DYE)
                    .setVal(Items.BROWN_DYE,      DYE)
                    .setVal(Items.RED_DYE,        DYE)
                    .setVal(Items.ORANGE_DYE,     DYE)
                    .setVal(Items.YELLOW_DYE,     DYE)
                    .setVal(Items.LIME_DYE,       DYE)
                    .setVal(Items.GREEN_DYE,      DYE)
                    .setVal(Items.CYAN_DYE,       DYE)
                    .setVal(Items.LIGHT_BLUE_DYE, DYE)
                    .setVal(Items.BLUE_DYE,       DYE)
                    .setVal(Items.PURPLE_DYE,     DYE)
                    .setVal(Items.MAGENTA_DYE,    DYE)
                    .setVal(Items.PINK_DYE,       DYE)
                    .setVal(ItemTags.WOOL, 0.04)
                    .setVal(Items.WHITE_CONCRETE,      0.08)
                    .setVal(Items.LIGHT_GRAY_CONCRETE, 0.08)
                    .setVal(Items.GRAY_CONCRETE,       0.08)
                    .setVal(Items.BLACK_CONCRETE,      0.08)
                    .setVal(Items.BROWN_CONCRETE,      0.08)
                    .setVal(Items.RED_CONCRETE,        0.08)
                    .setVal(Items.ORANGE_CONCRETE,     0.08)
                    .setVal(Items.YELLOW_CONCRETE,     0.08)
                    .setVal(Items.LIME_CONCRETE,       0.08)
                    .setVal(Items.GREEN_CONCRETE,      0.08)
                    .setVal(Items.CYAN_CONCRETE,       0.08)
                    .setVal(Items.LIGHT_BLUE_CONCRETE, 0.08)
                    .setVal(Items.BLUE_CONCRETE,       0.08)
                    .setVal(Items.PURPLE_CONCRETE,     0.08)
                    .setVal(Items.MAGENTA_CONCRETE,    0.08)
                    .setVal(Items.PINK_CONCRETE,       0.08)
                    .setVal(Items.WHITE_CONCRETE_POWDER,      0.08)
                    .setVal(Items.LIGHT_GRAY_CONCRETE_POWDER, 0.08)
                    .setVal(Items.GRAY_CONCRETE_POWDER,       0.08)
                    .setVal(Items.BLACK_CONCRETE_POWDER,      0.08)
                    .setVal(Items.BROWN_CONCRETE_POWDER,      0.08)
                    .setVal(Items.RED_CONCRETE_POWDER,        0.08)
                    .setVal(Items.ORANGE_CONCRETE_POWDER,     0.08)
                    .setVal(Items.YELLOW_CONCRETE_POWDER,     0.08)
                    .setVal(Items.LIME_CONCRETE_POWDER,       0.08)
                    .setVal(Items.GREEN_CONCRETE_POWDER,      0.08)
                    .setVal(Items.CYAN_CONCRETE_POWDER,       0.08)
                    .setVal(Items.LIGHT_BLUE_CONCRETE_POWDER, 0.08)
                    .setVal(Items.BLUE_CONCRETE_POWDER,       0.08)
                    .setVal(Items.PURPLE_CONCRETE_POWDER,     0.08)
                    .setVal(Items.MAGENTA_CONCRETE_POWDER,    0.08)
                    .setVal(Items.PINK_CONCRETE_POWDER,       0.08)
                    .build();
                this.category(BUILDING_BLOCKS, "Building Blocks", Items.BRICKS)
                    .setVal(Items.BRICKS, 0.4)
                    .setVal(Items.DEEPSLATE_BRICKS, RARE_STONE)
                    .setVal(Items.TUFF_BRICKS, RARE_STONE)
                    .setVal(Items.RESIN_BRICKS, 0.4)
                    .setVal(Items.BOOKSHELF, (BOOK * 3) + (PLANKS * 6))
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
