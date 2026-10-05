package embinmc.mod.poosmp.misc;

import com.mojang.serialization.MapCodec;
import embinmc.mod.poosmp.PooSMPMod;
import embinmc.mod.poosmp.annoyance.Annoyance;
import embinmc.mod.poosmp.annoyance.Annoyances;
import embinmc.mod.poosmp.economy.ShopCategory;
import embinmc.mod.poosmp.upgrade.Upgrade;
import embinmc.mod.poosmp.upgrade.UpgradeType;
import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public sealed interface PooRegistries {
    non-sealed interface Keys extends PooRegistries {
        ResourceKey<Registry<Annoyance>> ANNOYANCE = ResourceKey.createRegistryKey(PooSMPMod.id("annoyance"));
        ResourceKey<Registry<ShopCategory>> SHOP_CATEGORY = ResourceKey.createRegistryKey(PooSMPMod.id("shop_category"));
        ResourceKey<Registry<UpgradeType<?>>> UPGRADE_TYPE = ResourceKey.createRegistryKey(PooSMPMod.id("upgrade_type"));
    }

    static void acknowledge() {
        PooSMPMod.LOGGER.info("Creating PooSMP registries");
        Annoyances.init();
        DynamicRegistries.registerSynced(Keys.SHOP_CATEGORY, ShopCategory.MAP_CODEC.codec(), DynamicRegistries.SyncOption.SKIP_WHEN_EMPTY);
    }

    Registry<Annoyance> ANNOYANCE = FabricRegistryBuilder.createSimple(Keys.ANNOYANCE).buildAndRegister();
    Registry<UpgradeType<?>> UPGRADE_TYPE = FabricRegistryBuilder.createSimple(Keys.UPGRADE_TYPE).buildAndRegister();
}
