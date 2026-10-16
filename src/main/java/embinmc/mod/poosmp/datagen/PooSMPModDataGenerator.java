package embinmc.mod.poosmp.datagen;

import embinmc.mod.poosmp.PooSMPMod;
import embinmc.mod.poosmp.datagen.tag.*;
import embinmc.mod.poosmp.datagen.worldgen.PooNoiseGenSettingsProvider;
import embinmc.mod.poosmp.economy.ShopCategories;
import embinmc.mod.poosmp.misc.PooBiomes;
import embinmc.mod.poosmp.misc.PooPaintings;
import embinmc.mod.poosmp.misc.PooRegistries;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.equipment.trim.TrimPatterns;

public class PooSMPModDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(PooSMPPaintingProvider::new);
		pack.addProvider(PooSMPBlockTagProvider::new);
		pack.addProvider(PooSMPDamageTypeTagProvider::new);
		pack.addProvider(PooSMPEnchantmentTagProvider::new);
		pack.addProvider(PooSMPEntityTypeTagProvider::new);
		pack.addProvider(PooSMPItemTagProvider::new);
		pack.addProvider(PooSMPPaintingTagProvider::new);
		pack.addProvider(PooSMPPoiTagProvider::new);
		pack.addProvider(PooSMPBiomeTagProvider::new);

		pack.addProvider(PooSMPRegistriesProvider::new);

		pack.addProvider(UpgradeProvider::new);

		pack.addProvider(PooSMPBlockLootTableProvider::new);
		pack.addProvider(PooSMPRecipeProvider::new);
		pack.addProvider(PooSMPModelProvider::new);
		pack.addProvider(PooNoiseGenSettingsProvider::new);
	}

	@Override
	public void buildRegistry(RegistrySetBuilder registryBuilder) {
		registryBuilder.add(Registries.PAINTING_VARIANT, PooPaintings::bootstrap);
		registryBuilder.add(Registries.BIOME, PooBiomes::bootstrap);
		registryBuilder.add(Registries.ENCHANTMENT, PooSMPRegistriesProvider::enchantmentBootstrap);
		registryBuilder.add(Registries.DAMAGE_TYPE, PooSMPRegistriesProvider::damageTypeBootstrap);
		registryBuilder.add(Registries.TRIM_PATTERN, bootstrapContext -> {
			TrimPatterns.register(bootstrapContext, ResourceKey.create(Registries.TRIM_PATTERN, PooSMPMod.id("fill")));
		});
		registryBuilder.add(PooRegistries.Keys.SHOP_CATEGORY, ShopCategories::bootstrap);
	}
}
