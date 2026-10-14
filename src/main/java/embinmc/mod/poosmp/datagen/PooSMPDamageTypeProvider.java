package embinmc.mod.poosmp.datagen;

import embinmc.mod.poosmp.misc.PooDamageTypes;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageScaling;
import net.minecraft.world.damagesource.DamageType;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

public class PooSMPDamageTypeProvider extends PooCodecDataProvider<DamageType> {
    protected PooSMPDamageTypeProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(dataOutput, registriesFuture, Registries.DAMAGE_TYPE, DamageType.DIRECT_CODEC);
    }

    @Override
    public void generate(HolderLookup.@NonNull Provider provider) {
        DamageType damageType = new DamageType("poosmp.suicide", DamageScaling.WHEN_CAUSED_BY_LIVING_NON_PLAYER, 0f);
        this.provider.accept(PooDamageTypes.SUICIDE_COMMAND.identifier(), damageType);
    }
}
