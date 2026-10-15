package embinmc.mod.poosmp.datagen;

import embinmc.mod.poosmp.item.PooEnchantments;
import embinmc.mod.poosmp.misc.PooBiomes;
import embinmc.mod.poosmp.misc.PooDamageTypes;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.advancements.criterion.DamageSourcePredicate;
import net.minecraft.advancements.criterion.TagPredicate;
import net.minecraft.client.particle.FallingLeavesParticle;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.attribute.AmbientParticle;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.damagesource.DamageScaling;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.AddValue;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.storage.loot.predicates.DamageSourceCondition;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class PooSMPRegistriesProvider extends FabricDynamicRegistryProvider {
    protected @NonNull Entries entries = null;

    protected PooSMPRegistriesProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(dataOutput, registriesFuture);
    }

    @Override
    protected void configure(HolderLookup.@NonNull Provider registries, @NonNull Entries entries) {
        this.entries = entries;
        HolderGetter<Enchantment> enchantments = registries.lookupOrThrow(Registries.ENCHANTMENT);
        HolderGetter<Item> items = registries.lookupOrThrow(Registries.ITEM);

        entries.add(PooDamageTypes.SUICIDE_COMMAND, new DamageType("poosmp.suicide", DamageScaling.WHEN_CAUSED_BY_LIVING_NON_PLAYER, 0f));
        registerCustomNameEnchantment(entries, PooEnchantments.MELEE_PROTECTION,
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
        this.registerBiomes(registries);
    }

    protected void registerBiomes(HolderLookup.@NonNull Provider registries) {
        this.registerBiome(PooBiomes.DDE_PLAINS, new Biome.BiomeBuilder()
                .downfall(0.8f)
                .temperature(0.85f)
                .hasPrecipitation(true)
                .specialEffects(new BiomeSpecialEffects(
                        14237668,
                        Optional.of(16098068),
                        Optional.empty(),
                        Optional.empty(),
                        BiomeSpecialEffects.GrassColorModifier.NONE
                ))
                .setAttribute(EnvironmentAttributes.SKY_COLOR, 0xf07aff)
                .setAttribute(EnvironmentAttributes.FOG_COLOR, 0xffc2fd)
                .setAttribute(EnvironmentAttributes.WATER_FOG_COLOR, 0x330532)
                .setAttribute(EnvironmentAttributes.AMBIENT_PARTICLES, List.of(
                        new AmbientParticle(ParticleOptions)
                ))
        );
    }

    protected void registerBiome(ResourceKey<Biome> key, Biome.BiomeBuilder builder) {
        this.entries.add(key, builder.build());
    }

    public static void registerCustomNameEnchantment(
            @NonNull Entries entries, ResourceKey<Enchantment> key,
            Component name, Enchantment.Builder builder
    ) {
        entries.add(key, new Enchantment(name, builder.definition, builder.exclusiveSet, builder.effectMapBuilder.build()));
    }

    @Override
    public @NonNull String getName() {
        return "PooSMP Registry Entry Provider";
    }
}
