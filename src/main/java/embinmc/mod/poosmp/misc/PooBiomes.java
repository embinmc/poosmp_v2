package embinmc.mod.poosmp.misc;

import embinmc.mod.poosmp.PooSMPMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public interface PooBiomes {
    ResourceKey<Biome> DDE_PLAINS = create("ddededodediamante_plains"),
    SKY_ISLANDS = create("sky_islands"),
    OUTER_HYRULE = create("outer_hyrule"),
    GRASSLANDS = create("grasslands"),
    HYRULE = create("hyrule"),
    HYRULE_CASTLE = create("hyrule_castle"),
    MISSINGNO = create("missingno"),
    SLENDERMAN_LAND = create("slenderman_land"),
    DEPTHS = create("depths"),
    HYRULE_OCEAN = create("hyrule_ocean"),
    DIMWORLD_SURFACE = create("dimworld_surface"),
    DIMWORLD_DEPTHS = create("dimworld_depths"),
    DIMWORLD_SKY = create("dimworld_sky"),
    DIMWORLD_GLOOMY_MEADOWS = create("gloomy_meadows"),
    DIMWORLD_GLOOMY_OCEAN = create("gloomy_ocean"),
    DIMWORLD_DEEP_GLOOMY_OCEAN = create("deep_gloomy_ocean"),
    DIMWORLD_GRAVELY_FIELDS = create("gravely_fields"),
    DIMWORLD_WET_SKY = create("wet_sky"),
    DIMWORLD_BARRENS = create("dim_barrens");

    private static ResourceKey<Biome> create(String id) {
        return ResourceKey.create(Registries.BIOME, PooSMPMod.id(id));
    }

    interface Tags {
        TagKey<Biome> IS_DIMWORLD = tag("is_dimworld");
        TagKey<Biome> IS_DIMWORLD_LAND = tag("is_dimworld_land");
        TagKey<Biome> IS_DIMWORLD_OCEAN = tag("is_dimworld_ocean");
        TagKey<Biome> IS_DIMWORLD_SKY = tag("is_dimworld_sky");

        private static TagKey<Biome> tag(String id) {
            return TagKey.create(Registries.BIOME, PooSMPMod.id(id));
        }
    }
}
