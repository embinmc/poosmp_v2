package embinmc.mod.poosmp.datagen.tag;

import embinmc.mod.poosmp.misc.PooBiomes;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

public class PooSMPBiomeTagProvider extends FabricTagProvider<Biome> {
    public PooSMPBiomeTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, Registries.BIOME, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.@NonNull Provider wrapperLookup) {
        this.builder(PooBiomes.Tags.IS_DIMWORLD_LAND)
                .add(PooBiomes.DIMWORLD_SURFACE)
                .add(PooBiomes.DIMWORLD_BARRENS)
                .add(PooBiomes.DIMWORLD_GLOOMY_MEADOWS)
        ;
        this.builder(PooBiomes.Tags.IS_DIMWORLD_OCEAN)
                .add(PooBiomes.DIMWORLD_GLOOMY_OCEAN)
                .add(PooBiomes.DIMWORLD_DEEP_GLOOMY_OCEAN)
        ;
        this.builder(PooBiomes.Tags.IS_DIMWORLD_SKY)
                .add(PooBiomes.DIMWORLD_SKY)
                .add(PooBiomes.DIMWORLD_WET_SKY)
        ;
        this.builder(PooBiomes.Tags.IS_DIMWORLD)
                .addTag(PooBiomes.Tags.IS_DIMWORLD_LAND)
                .addTag(PooBiomes.Tags.IS_DIMWORLD_OCEAN)
                .addTag(PooBiomes.Tags.IS_DIMWORLD_SKY)
        ;
        this.builder(BiomeTags.IS_OCEAN).setReplace(false)
                .addTag(PooBiomes.Tags.IS_DIMWORLD_OCEAN);
        this.builder(BiomeTags.IS_DEEP_OCEAN).setReplace(false)
                .add(PooBiomes.DIMWORLD_DEEP_GLOOMY_OCEAN);

        this.builder(BiomeTags.HAS_MINESHAFT).setReplace(false)
                .addTag(PooBiomes.Tags.IS_DIMWORLD)
                .add(PooBiomes.HYRULE)
        ;
        this.builder(BiomeTags.HAS_MINESHAFT_MESA).setReplace(false)
                .addTag(PooBiomes.Tags.IS_DIMWORLD_LAND)
        ;
        this.builder(BiomeTags.HAS_PILLAGER_OUTPOST).setReplace(false)
                .add(PooBiomes.DIMWORLD_BARRENS)
        ;
        this.builder(BiomeTags.HAS_PILLAGER_OUTPOST).setReplace(false)
                .add(PooBiomes.DIMWORLD_BARRENS)
        ;
        this.builder(BiomeTags.HAS_RUINED_PORTAL_STANDARD).setReplace(false)
                .addTag(PooBiomes.Tags.IS_DIMWORLD_LAND)
                .add(PooBiomes.HYRULE)
        ;
        this.builder(BiomeTags.HAS_SWAMP_HUT).setReplace(false)
                .add(PooBiomes.DIMWORLD_DEEP_GLOOMY_OCEAN)
                .add(PooBiomes.DIMWORLD_BARRENS)
        ;
        this.builder(BiomeTags.HAS_WOODLAND_MANSION).setReplace(false)
                .add(PooBiomes.DIMWORLD_GLOOMY_OCEAN)
        ;
    }
}
