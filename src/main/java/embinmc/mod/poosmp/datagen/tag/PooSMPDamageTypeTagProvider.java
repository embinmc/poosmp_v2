package embinmc.mod.poosmp.datagen.tag;

import embinmc.mod.poosmp.misc.PooDamageTypes;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageType;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

public class PooSMPDamageTypeTagProvider extends FabricTagProvider<DamageType> {
    public PooSMPDamageTypeTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, Registries.DAMAGE_TYPE, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.@NonNull Provider wrapperLookup) {
        this.builder(DamageTypeTags.BYPASSES_ARMOR)
                .addOptional(PooDamageTypes.SUICIDE_COMMAND);
        this.builder(DamageTypeTags.BYPASSES_INVULNERABILITY)
                .addOptional(PooDamageTypes.SUICIDE_COMMAND);
        this.builder(DamageTypeTags.BYPASSES_RESISTANCE)
                .addOptional(PooDamageTypes.SUICIDE_COMMAND);
    }
}
