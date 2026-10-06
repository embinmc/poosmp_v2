package embinmc.mod.poosmp.datagen;

import com.mojang.serialization.Codec;
import embinmc.mod.poosmp.upgrade.Upgrade;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricCodecDataProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public abstract class PooCodecDataProvider<T> extends FabricCodecDataProvider<T> {
    protected BiConsumer<Identifier, T> provider;
    protected HolderLookup.Provider lookup;
    protected final ResourceKey<Registry<T>> registryKey;

    protected PooCodecDataProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registriesFuture, ResourceKey<Registry<T>> key, Codec<T> codec) {
        super(dataOutput, registriesFuture, PackOutput.Target.DATA_PACK, key.toString().replace(':', '/'), codec);
        this.registryKey = key;
    }

    @Override
    protected void configure(@NonNull BiConsumer<Identifier, T> provider, HolderLookup.@NonNull Provider lookup) {
        this.provider = provider;
        this.lookup = lookup;
        this.generate(lookup);
    }

    @Override
    public @NonNull String getName() {
        return "PooSMP Data Provider: " + this.registryKey;
    }

    public abstract void generate(HolderLookup.@NonNull Provider provider);
}
