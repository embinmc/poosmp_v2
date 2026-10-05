package embinmc.mod.poosmp.upgrade;

import com.mojang.datafixers.util.Function3;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.function.BiFunction;
import java.util.function.Function;

public abstract class AbstractUpgrade implements Upgrade {
    protected final Properties properties;

    protected AbstractUpgrade(Properties properties) {
        this.properties = properties;
    }

    protected static <T extends AbstractUpgrade> RecordCodecBuilder<T, Properties> propertiesCodec() {
        return Properties.MAP_CODEC.forGetter(AbstractUpgrade::properties);
    }

    protected static <T extends AbstractUpgrade> MapCodec<T> simpleCodec(Function<Properties, T> constructor) {
        return RecordCodecBuilder.mapCodec(a -> a.group(propertiesCodec()).apply(a, constructor));
    }

    protected static <T extends AbstractUpgrade, A> MapCodec<T> simpleCodec(
            BiFunction<Properties, A, T> constructor,
            RecordCodecBuilder<T, A> thing1
    ) {
        return RecordCodecBuilder.mapCodec(a -> a.group(propertiesCodec(), thing1).apply(a, constructor));
    }

    protected static <T extends AbstractUpgrade, A, B> MapCodec<T> simpleCodec(
            Function3<Properties, A, B, T> constructor,
            RecordCodecBuilder<T, A> thing1,
            RecordCodecBuilder<T, B> thing2
    ) {
        return RecordCodecBuilder.mapCodec(a -> a.group(propertiesCodec(), thing1, thing2).apply(a, constructor));
    }

    @Override
    public Properties properties() {
        return this.properties;
    }
}
