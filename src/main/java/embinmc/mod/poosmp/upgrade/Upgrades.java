package embinmc.mod.poosmp.upgrade;

import embinmc.mod.poosmp.PooSMPMod;
import embinmc.mod.poosmp.misc.PooRegistries;
import net.minecraft.resources.ResourceKey;

public interface Upgrades {
    ResourceKey<Upgrade> HEALTH_INCREASE = create("health_increase");
    ResourceKey<Upgrade> BLOCK_REACH_INCREASE = create("block_reach_increase");
    ResourceKey<Upgrade> ENTITY_REACH_INCREASE = create("entity_reach_increase");
    ResourceKey<Upgrade> FIRE_RESISTANCE = create("fire_resistance");
    ResourceKey<Upgrade> RESISTANCE_2 = create("resistance_2");
    ResourceKey<Upgrade> MINING_SPEED_INCREASE = create("mining_speed_increase");
    ResourceKey<Upgrade> STRENGTH_2 = create("strength_2");
    ResourceKey<Upgrade> TRIAL_OMEN = create("trial_omen");
    ResourceKey<Upgrade> WATER_BREATHING = create("water_breathing");

    private static ResourceKey<Upgrade> create(String id) {
        return ResourceKey.create(PooRegistries.Keys.UPGRADE, PooSMPMod.id(id));
    }
}
