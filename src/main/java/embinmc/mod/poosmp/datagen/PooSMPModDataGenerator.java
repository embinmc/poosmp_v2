package embinmc.mod.poosmp.datagen;

import embinmc.mod.poosmp.datagen.tag.PooSMPBlockTagProvider;
import embinmc.mod.poosmp.datagen.tag.PooSMPEntityTypeTagProvider;
import embinmc.mod.poosmp.datagen.tag.PooSMPItemTagProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class PooSMPModDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(PooSMPEntityTypeTagProvider::new);
		pack.addProvider(PooSMPItemTagProvider::new);
		pack.addProvider(PooSMPBlockTagProvider::new);

		pack.addProvider(UpgradeProvider::new);

		pack.addProvider(PooSMPModelProvider::new);
		pack.addProvider(PooSMPRecipeProvider::new);
	}
}
