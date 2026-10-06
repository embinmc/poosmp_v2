package embinmc.mod.poosmp.datagen;

import embinmc.mod.poosmp.misc.PooRegistries;
import embinmc.mod.poosmp.misc.Temporary;
import embinmc.mod.poosmp.upgrade.*;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Util;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class UpgradeProvider extends PooCodecDataProvider<Upgrade> {
    protected UpgradeProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(dataOutput, registriesFuture, PooRegistries.Keys.UPGRADE, Upgrade.DIRECT_CODEC);
    }

    @Override
    public @NonNull String getName() {
        return "PooSMP Upgrade Provider";
    }

    @Override
    public void generate(HolderLookup.@NonNull Provider provider) {
        this.attributeUpgrade(Upgrades.HEALTH_INCREASE)
                .addAttribute(Attributes.MAX_HEALTH, 2, AttributeModifier.Operation.ADD_VALUE)
                .setIcon(Items.ENCHANTED_GOLDEN_APPLE)
                .setPrice(4000D)
                .setPriceMult(0.25D)
                .build();
        this.attributeUpgrade(Upgrades.ENTITY_REACH_INCREASE)
                .addAttribute(Attributes.ENTITY_INTERACTION_RANGE, 1, AttributeModifier.Operation.ADD_VALUE)
                .setMaxPurchases(10)
                .setIcon(Items.ZOMBIE_HEAD)
                .setPrice(1600D)
                .build();
        this.attributeUpgrade(Upgrades.BLOCK_REACH_INCREASE)
                .addAttribute(Attributes.ENTITY_INTERACTION_RANGE, 1, AttributeModifier.Operation.ADD_VALUE)
                .setMaxPurchases(10)
                .setIcon(Items.STONE)
                .setPrice(1200D)
                .build();
        this.statusEffectUpgrade(Upgrades.FIRE_RESISTANCE)
                .setEffect(MobEffects.FIRE_RESISTANCE, 0)
                .setIcon(Items.MAGMA_BLOCK)
                .setPrice(120_000D)
                .build();
        this.statusEffectUpgrade(Upgrades.RESISTANCE_2)
                .setEffect(MobEffects.RESISTANCE, 1)
                .setIcon(Items.SHIELD)
                .setPrice(180_000D)
                .build();
        this.attributeUpgrade(Upgrades.MINING_SPEED_INCREASE)
                .addAttribute(Attributes.MINING_EFFICIENCY, 2, AttributeModifier.Operation.ADD_VALUE)
                .setMaxPurchases(20)
                .setIcon(Items.IRON_PICKAXE)
                .setPrice(1000D)
                .setPriceMult(0.15D)
                .build();
        this.statusEffectUpgrade(Upgrades.STRENGTH_2)
                .setEffect(MobEffects.STRENGTH, 1)
                .setIcon(Items.NETHERITE_SWORD)
                .setPrice(180_000D)
                .build();
        this.statusEffectUpgrade(Upgrades.TRIAL_OMEN)
                .setEffect(MobEffects.TRIAL_OMEN, 0)
                .setIcon(Items.OMINOUS_TRIAL_KEY)
                .setPrice(24_000D)
                .build();
        this.statusEffectUpgrade(Upgrades.WATER_BREATHING)
                .setEffect(MobEffects.WATER_BREATHING, 0)
                .setIcon(Items.WATER_BUCKET)
                .setPrice(120_000D)
                .build();
    }

    protected AttributesUpgradeBuilder attributeUpgrade(ResourceKey<Upgrade> key) {
        return new AttributesUpgradeBuilder(key);
    }

    protected StatusEffectUpgradeBuilder statusEffectUpgrade(ResourceKey<Upgrade> key) {
        return new StatusEffectUpgradeBuilder(key);
    }

    protected ExplodeEntityUpgradeBuilder explodeEntityOnHitUpgrade(ResourceKey<Upgrade> key) {
        return new ExplodeEntityUpgradeBuilder(key);
    }

    public abstract class UpgradeBuilder {
        protected final ResourceKey<Upgrade> key;
        protected final Temporary<Item> icon = Temporary.empty();
        protected final Temporary<Integer> maxPurchases = Temporary.empty();
        protected final Temporary<Double> price = Temporary.empty();
        protected final Temporary<Double> price2 = Temporary.with(0.2D);

        protected UpgradeBuilder(ResourceKey<Upgrade> key) {
            this.key = key;
        }

        protected abstract Upgrade buildUpgrade();

        protected Upgrade.Properties buildProperties() {
            Component text = Component.translatable(Util.makeDescriptionId("upgrade", this.key.identifier()));
            Optional<Integer> maxPurchases = this.maxPurchases.getValue();
            double p1 = this.price.orElseThrow();
            double p2 = this.price2.orElseThrow();
            UpgradePrice upgradePrice = new UpgradePrice(p1, p2);
            Holder<Item> icon = this.icon.orElseThrow().builtInRegistryHolder();
            return new Upgrade.Properties(icon, text, upgradePrice, true, maxPurchases);
        }

        public void build() {
            Upgrade upgrade = this.buildUpgrade();
            UpgradeProvider.this.provider.accept(this.key.identifier(), upgrade);
        }

        public UpgradeBuilder setIcon(Item icon) {
            this.icon.setNewValue(icon);
            return this;
        }

        public UpgradeBuilder setPrice(double price) {
            this.price.setNewValue(price);
            return this;
        }

        public UpgradeBuilder setPriceMult(double price) {
            this.price2.setNewValue(price);
            return this;
        }

        public UpgradeBuilder setMaxPurchases(int amount) {
            this.maxPurchases.setNewValue(amount);
            return this;
        }
    }

    public class ExplodeEntityUpgradeBuilder extends UpgradeBuilder {
        protected ExplodeEntityUpgradeBuilder(ResourceKey<Upgrade> key) {
            super(key);
        }

        @Override
        protected Upgrade buildUpgrade() {
            return new ExplodeEntityWhenHitUpgrade(this.buildProperties());
        }
    }

    public class AttributesUpgradeBuilder extends UpgradeBuilder {
        private final List<Attrib> attribs = new ArrayList<>(5);

        protected AttributesUpgradeBuilder(ResourceKey<Upgrade> key) {
            super(key);
        }

        @Override
        protected Upgrade buildUpgrade() {
            ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();
            for (Attrib attrib : this.attribs) {
                Identifier modId = this.key.identifier().withSuffix("/" + attrib.attribute().unwrapKey().orElseThrow().identifier().getPath());
                AttributeModifier modifier = new AttributeModifier(modId, attrib.val(), attrib.op());
                builder.add(attrib.attribute(), modifier, EquipmentSlotGroup.ANY);
            }
            return new AttributeUpgrade(this.buildProperties(), builder.build());
        }

        public AttributesUpgradeBuilder addAttribute(Holder<Attribute> attribute, double value, AttributeModifier.Operation op) {
            this.attribs.add(new Attrib(attribute, value, op));
            return this;
        }

        private record Attrib(Holder<Attribute> attribute, double val, AttributeModifier.Operation op) {}
    }

    public class StatusEffectUpgradeBuilder extends UpgradeBuilder {
        protected final Temporary<MobEffectInstance> instance = Temporary.empty();

        protected StatusEffectUpgradeBuilder(ResourceKey<Upgrade> key) {
            super(key);
            this.setMaxPurchases(1);
        }

        @Override
        protected Upgrade buildUpgrade() {
            return new StatusEffectUpgrade(this.buildProperties(), this.instance.orElseThrow());
        }

        public StatusEffectUpgradeBuilder setEffect(Holder<MobEffect> mobEffect, int amplifier) {
            this.instance.setNewValue(new MobEffectInstance(mobEffect, -1, amplifier, true, false, true));
            return this;
        }
    }
}
