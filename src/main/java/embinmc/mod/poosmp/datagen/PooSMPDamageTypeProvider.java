package embinmc.mod.poosmp.datagen;

import embinmc.mod.poosmp.misc.PooDamageTypes;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.damagesource.DamageScaling;
import net.minecraft.world.damagesource.DamageType;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

public class PooSMPDamageTypeProvider extends FabricDynamicRegistryProvider {
    protected PooSMPDamageTypeProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(dataOutput, registriesFuture);
    }

    @Override
    protected void configure(HolderLookup.@NonNull Provider registries, @NonNull Entries entries) {
        entries.add(PooDamageTypes.SUICIDE_COMMAND, new DamageType("poosmp.suicide", DamageScaling.WHEN_CAUSED_BY_LIVING_NON_PLAYER, 0f));
    }

    @Override
    public @NonNull String getName() {
        return "PooSMP Damage Type Provider";
    }
}
