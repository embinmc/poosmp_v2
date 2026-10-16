package embinmc.mod.poosmp.misc;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;

public abstract class QuickBootstrapLoader<T> {
    protected final BootstrapContext<T> context;

    public QuickBootstrapLoader(BootstrapContext<T> context) {
        this.context = context;
    }

    public BootstrapContext<T> context() {
        return this.context;
    }

    public <R> HolderGetter<R> holderGetter(ResourceKey<Registry<R>> registry) {
        return this.context().lookup(registry);
    }

    public abstract void bootstrap();
}
