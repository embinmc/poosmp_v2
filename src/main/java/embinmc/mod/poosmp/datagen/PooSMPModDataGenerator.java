package embinmc.mod.poosmp.datagen;

import embinmc.mod.poosmp.datagen.tag.*;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class PooSMPModDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(PooSMPBlockTagProvider::new);
		pack.addProvider(PooSMPDamageTypeTagProvider::new);
		pack.addProvider(PooSMPEnchantmentTagProvider::new);
		pack.addProvider(PooSMPEntityTypeTagProvider::new);
		pack.addProvider(PooSMPItemTagProvider::new);
		pack.addProvider(PooSMPPaintingTagProvider::new);
		pack.addProvider(PooSMPPoiTagProvider::new);

		pack.addProvider(PooSMPRegistriesProvider::new);

		pack.addProvider(UpgradeProvider::new);

		pack.addProvider(PooSMPBlockLootTableProvider::new);
		pack.addProvider(PooSMPRecipeProvider::new);
		pack.addProvider(PooSMPModelProvider::new);
		//pack.addProvider(PooNoiseGenSettingsProvider::new);
	}
}
