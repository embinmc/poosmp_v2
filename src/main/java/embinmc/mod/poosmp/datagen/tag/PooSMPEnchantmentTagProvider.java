package embinmc.mod.poosmp.datagen.tag;

import embinmc.mod.poosmp.item.PooEnchantments;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

public class PooSMPEnchantmentTagProvider extends FabricTagProvider<Enchantment> {
    public PooSMPEnchantmentTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, Registries.ENCHANTMENT, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.@NonNull Provider wrapperLookup) {
        this.builder(EnchantmentTags.ARMOR_EXCLUSIVE).setReplace(true);
        this.builder(EnchantmentTags.BOW_EXCLUSIVE).setReplace(true);
        this.builder(PooEnchantments.Tags.BANKER_LOW)
            .add(Enchantments.EFFICIENCY)
            .add(Enchantments.PROJECTILE_PROTECTION)
            .add(Enchantments.SWEEPING_EDGE)
            .add(Enchantments.SILK_TOUCH)
            .add(Enchantments.AQUA_AFFINITY)
            .add(Enchantments.FIRE_ASPECT)
        ;
        this.builder(PooEnchantments.Tags.BANKER_MID)
            .add(Enchantments.DEPTH_STRIDER)
            .add(Enchantments.FIRE_PROTECTION)
            .add(Enchantments.BLAST_PROTECTION)
            .add(Enchantments.PROTECTION)
            .add(Enchantments.FEATHER_FALLING)
            .add(Enchantments.RESPIRATION)
            .add(Enchantments.SMITE)
        ;
        this.builder(PooEnchantments.Tags.BANKER_HIGH)
            .add(PooEnchantments.MELEE_PROTECTION)
            .add(Enchantments.UNBREAKING)
            .add(Enchantments.MENDING)
            .add(Enchantments.SWIFT_SNEAK)
            .add(Enchantments.WIND_BURST)
            .add(Enchantments.SHARPNESS)
            .add(Enchantments.LOOTING)
        ;
    }
}
