package embinmc.mod.poosmp.misc;

import embinmc.mod.poosmp.PooSMPMod;
import embinmc.mod.poosmp.annoyance.Annoyance;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public sealed interface PooRegistries {
    non-sealed interface Keys extends PooRegistries {
        ResourceKey<Registry<Annoyance>> ANNOYANCE = ResourceKey.createRegistryKey(PooSMPMod.id("annoyance"));
    }

    static void acknowledge() {
        PooSMPMod.LOGGER.info("Creating PooSMP registries");
    }

    Registry<Annoyance> ANNOYANCE = FabricRegistryBuilder.createSimple(Keys.ANNOYANCE).buildAndRegister();
}
