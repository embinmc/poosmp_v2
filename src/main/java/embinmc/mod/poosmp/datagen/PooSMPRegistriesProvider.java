package embinmc.mod.poosmp.datagen;

import embinmc.mod.poosmp.item.PooEnchantments;
import embinmc.mod.poosmp.misc.PooDamageTypes;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.advancements.criterion.DamageSourcePredicate;
import net.minecraft.advancements.criterion.TagPredicate;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.damagesource.DamageScaling;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.AddValue;
import net.minecraft.world.level.storage.loot.predicates.DamageSourceCondition;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

public class PooSMPRegistriesProvider extends FabricDynamicRegistryProvider {
    protected PooSMPRegistriesProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(dataOutput, registriesFuture);
    }

    @Override
    protected void configure(HolderLookup.@NonNull Provider registries, @NonNull Entries entries) {
        entries.addAll(registries.lookupOrThrow(Registries.DAMAGE_TYPE));
        entries.addAll(registries.lookupOrThrow(Registries.ENCHANTMENT));
    }

    public static void enchantmentBootstrap(BootstrapContext<Enchantment> context) {
        HolderGetter<Enchantment> enchantments = context.lookup(Registries.ENCHANTMENT);
        HolderGetter<Item> items = context.lookup(Registries.ITEM);
        registerCustomNameEnchantment(context, PooEnchantments.MELEE_PROTECTION,
                Component.literal("Melee Protection").withColor(0xFF6600),
                Enchantment.enchantment(
                                Enchantment.definition(
                                        items.getOrThrow(ItemTags.ARMOR_ENCHANTABLE),
                                        2, // weight
                                        4, // max level
                                        Enchantment.dynamicCost(5, 8), // min cost
                                        Enchantment.dynamicCost(13, 8), // max cost
                                        4, // anvil cost
                                        EquipmentSlotGroup.ARMOR
                                )
                        )
                        .exclusiveWith(enchantments.getOrThrow(EnchantmentTags.ARMOR_EXCLUSIVE))
                        .withEffect(
                                EnchantmentEffectComponents.DAMAGE_PROTECTION,
                                new AddValue(LevelBasedValue.perLevel(2.0F)),
                                DamageSourceCondition.hasDamageSource(
                                        DamageSourcePredicate.Builder.damageType()
                                                .tag(TagPredicate.is(PooDamageTypes.Tags.IS_MELEE))
                                                .tag(TagPredicate.isNot(DamageTypeTags.BYPASSES_INVULNERABILITY))
                                )
                        )
        );
    }

    public static void registerCustomNameEnchantment(
            @NonNull BootstrapContext<Enchantment> entries, ResourceKey<Enchantment> key,
            Component name, Enchantment.Builder builder
    ) {
        entries.register(key, new Enchantment(name, builder.definition, builder.exclusiveSet, builder.effectMapBuilder.build()));
    }

    public static void damageTypeBootstrap(BootstrapContext<DamageType> context) {
        context.register(PooDamageTypes.SUICIDE_COMMAND, new DamageType("poosmp.suicide", DamageScaling.WHEN_CAUSED_BY_LIVING_NON_PLAYER, 0f));
    }

    @Override
    public @NonNull String getName() {
        return "PooSMP Registry Entry Provider";
    }
}
