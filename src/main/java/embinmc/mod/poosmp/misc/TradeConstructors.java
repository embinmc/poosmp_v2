package embinmc.mod.poosmp.misc;

import embinmc.mod.poosmp.block.PooBlocks;
import embinmc.mod.poosmp.item.PooItems;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.villager.VillagerProfession;
import net.minecraft.world.entity.npc.villager.VillagerTrades;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.OminousBottleAmplifier;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.ItemLike;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

import static embinmc.mod.poosmp.item.PooEnchantments.Tags.*;

public class TradeConstructors {

    public static void register_villager_trades() {
        TradeOfferHelper.registerVillagerOffers(PooSMPVillagers.BANKER_KEY, 1, factories -> {
            factories.add(enchantedBook(BANKER_LOW, PooItems.TWENTY_FIVE_DOLLAR_BILL, 2));

            ItemStack omBottle = new ItemStack(Items.OMINOUS_BOTTLE);
            omBottle.set(DataComponents.OMINOUS_BOTTLE_AMPLIFIER, new OminousBottleAmplifier(2));
            factories.add(trade(omBottle, PooItems.ONE_DOLLAR_BILL, 8));
        });
        TradeOfferHelper.registerVillagerOffers(PooSMPVillagers.BANKER_KEY, 2, factories -> {
            factories.add(enchantedBook(BANKER_MID, PooItems.TWENTY_FIVE_DOLLAR_BILL, 2));
            factories.add(trade(PooItems.WARP_STICK, 1, PooItems.TEN_DOLLAR_BILL, 5));

            ItemStack omBottle = new ItemStack(Items.OMINOUS_BOTTLE);
            omBottle.set(DataComponents.OMINOUS_BOTTLE_AMPLIFIER, new OminousBottleAmplifier(4));
            factories.add(trade(omBottle, PooItems.ONE_DOLLAR_BILL, 12));
        });
        TradeOfferHelper.registerVillagerOffers(PooSMPVillagers.BANKER_KEY, 3, factories -> {
            factories.add(trade(PooBlocks.DRAGON_ANNOYANCE, 8, PooItems.FIVE_DOLLAR_BILL, 3));
            factories.add(enchantedBook(BANKER_MID, PooItems.HUNDRED_DOLLAR_BILL, 1));
            factories.add(trade(Items.BLUE_ICE, 1, PooItems.ONE_DOLLAR_BILL, 18));
            factories.add(trade(Items.ECHO_SHARD, 1, PooItems.ONE_DOLLAR_BILL, 64));
        });
        TradeOfferHelper.registerVillagerOffers(PooSMPVillagers.BANKER_KEY, 4, factories -> {
            factories.add(trade(Items.NETHERITE_SCRAP, 1, PooItems.HUNDRED_DOLLAR_BILL, 4));
            factories.add(enchantedBook(BANKER_HIGH, PooItems.HUNDRED_DOLLAR_BILL, 4));
        });
        TradeOfferHelper.registerVillagerOffers(PooSMPVillagers.BANKER_KEY, 5, factories -> {
            factories.add(trade(Items.ELYTRA, 1, PooItems.HUNDRED_DOLLAR_BILL, 40));
            factories.add(trade(PooItems.RAW_RED_POO, 1, PooItems.HUNDRED_DOLLAR_BILL, 64));
            factories.add(trade(PooItems.RED_POO_UPGRADE_TEMPLATE, 1, PooItems.HUNDRED_DOLLAR_BILL, 10));
        });
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.FARMER, 5, factories -> {
            factories.add(trade(Items.SHULKER_SHELL, 2, PooItems.HUNDRED_DOLLAR_BILL, 1));
            factories.add(trade(PooItems.BANANA, 4, Items.EMERALD, 2));
        });
    }

    public static VillagerTrades.ItemListing trade(ItemStack buying, Item cost, int costAmount) {
        return (serverLevel, entity, randomSource) -> new ToMoney(buying, cost, costAmount).getOffer(serverLevel, entity, randomSource);
    }

    public static VillagerTrades.ItemListing trade(ItemStackSupplier buying, Item cost, int costAmount) {
        return (serverLevel, entity, randomSource) -> new ToMoney(buying.get(serverLevel, entity, randomSource), cost, costAmount).getOffer(serverLevel, entity, randomSource);
    }

    public static VillagerTrades.ItemListing trade(ItemLike buying, int buyingAmount, Item cost, int costAmount) {
        return trade(new ItemStack(buying, buyingAmount), cost, costAmount);
    }

    public static VillagerTrades.ItemListing trade(ItemLike buying, int buyingAmount, Item cost) {
        return trade(buying, buyingAmount, cost, 1);
    }

    public static VillagerTrades.ItemListing enchantedBook(TagKey<Enchantment> tag, ItemLike cost, int costAmount) {
        return (level, entity, random) -> {
            Optional<Holder<Enchantment>> enchantment = level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getRandomElementOf(tag, random);
            if (enchantment.isPresent()) {
                Holder<Enchantment> holder = enchantment.orElseThrow();
                boolean shouldBe1Less = holder.is(Enchantments.EFFICIENCY) && random.nextBoolean();
                final int maxLevel = shouldBe1Less ? holder.value().getMaxLevel() - 1 : holder.value().getMaxLevel();
                return new ToMoney(
                        EnchantmentHelper.createBook(new EnchantmentInstance(enchantment.orElseThrow(), maxLevel)),
                        cost, costAmount
                ).getOffer(level, entity, random);
            } else {
                return new ToMoney(new ItemStack(Items.BOOK), cost, 1).getOffer(level, entity, random);
            }
        };
    }

    @FunctionalInterface
    public interface ItemStackSupplier {
        ItemStack get(ServerLevel level, Entity entity, RandomSource random);
    }

    @Deprecated
    public static class ToMoney implements VillagerTrades.ItemListing {
        private final ItemCost stack;
        private final int max_uses;
        private final int experience;
        private final ItemStack price;
        private final float multiplier;

        public ToMoney(ItemCost stack, ItemStack price) {
            this.stack = stack;
            this.max_uses = 999999;
            this.experience = 20;
            this.price = price;
            this.multiplier = 0.05F;
        }

        public ToMoney(ItemLike item, ItemLike sell_price) {
            this(new ItemCost(sell_price.asItem(), 1), new ItemStack(item));
        }

        public ToMoney(ItemLike item, int amount, ItemLike sell_price) {
            this(new ItemCost(sell_price.asItem(), 1), new ItemStack(item, amount));
        }

        public ToMoney(ItemLike item, int amount, ItemLike sell_price, int amount2) {
            this(new ItemCost(sell_price.asItem(), amount2), new ItemStack(item, amount));
        }

        public ToMoney(ItemStack item, ItemLike sell_price, int amount2) {
            this(new ItemCost(sell_price.asItem(), amount2), item);
        }

        @Override
        public @Nullable MerchantOffer getOffer(ServerLevel serverLevel, Entity entity, RandomSource randomSource) {
            return new MerchantOffer(this.stack, this.price, this.max_uses, this.experience, this.multiplier);
        }
    }
}
