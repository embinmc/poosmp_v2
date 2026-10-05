package embinmc.mod.poosmp.datagen;

import com.mojang.serialization.Codec;
import embinmc.mod.poosmp.upgrade.Upgrade;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricCodecDataProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class UpgradeProvider extends FabricCodecDataProvider<Upgrade> {
    private BiConsumer<Identifier, Upgrade> provider;
    private HolderLookup.Provider lookup;

    protected UpgradeProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(dataOutput, registriesFuture, PackOutput.Target.DATA_PACK, "upgrade", Upgrade.DIRECT_CODEC);
    }

    @Override
    protected void configure(@NonNull BiConsumer<Identifier, Upgrade> provider, HolderLookup.@NonNull Provider lookup) {
        this.provider = provider;
        this.lookup = lookup;
        this.generate();
    }

    @Override
    public @NonNull String getName() {
        return "PooSMP Upgrade Provider";
    }

    public void generate() {

    }

    public abstract class UpgradeBuilder {
        protected final ResourceKey<Upgrade> key;

        protected UpgradeBuilder(ResourceKey<Upgrade> key) {
            this.key = key;
        }

        protected abstract Upgrade buildUpgrade();

        public void build() {
            Upgrade upgrade = this.buildUpgrade();
            UpgradeProvider.this.provider.accept(this.key.identifier(), upgrade);
        }
    }
}
