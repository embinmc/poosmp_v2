package embinmc.mod.poosmp;

import embinmc.mod.poosmp.block.PooBlocks;
import embinmc.mod.poosmp.event.PooSMPEvents;
import embinmc.mod.poosmp.item.PooItemGroups;
import embinmc.mod.poosmp.item.PooItems;
import embinmc.mod.poosmp.item.component.ComponentApplicationRecipe;
import embinmc.mod.poosmp.item.component.PooComponents;
import embinmc.mod.poosmp.misc.PooRegistries;
import embinmc.mod.poosmp.misc.PooSMPGameRules;
import embinmc.mod.poosmp.misc.PooSMPPoi;
import embinmc.mod.poosmp.misc.PooSoundEvents;
import net.fabricmc.api.ModInitializer;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PooSMPMod implements ModInitializer {
	public static final String MOD_ID = "poosmp";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final ResourceKey<Level> HYRULE = dimension("hyrule");
	public static final ResourceKey<Level> MISSINGNO = dimension("missingno");
	public static final ResourceKey<Level> DIMWORLD = dimension("dimworld");

	@Override
	public void onInitialize() {
		LOGGER.info("Welcome to PooSMP!");
		Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, PooSMPMod.id("item_component_application"), ComponentApplicationRecipe.SERIALIZER);
		PooRegistries.acknowledge();
		PooSoundEvents.init();
		PooComponents.init();
		PooSMPGameRules.acknowledge();
		PooBlocks.init();
		PooItems.init();
		PooSMPEvents.register();
		PooItemGroups.register();
		PooSMPPoi.init();
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}

	public static ResourceKey<Level> dimension(String id) {
		return ResourceKey.create(Registries.DIMENSION, id(id));
	}
}
