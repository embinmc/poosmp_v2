package embinmc.mod.poosmp.datagen.tag;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.world.item.enchantment.Enchantment;
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
    }
}
