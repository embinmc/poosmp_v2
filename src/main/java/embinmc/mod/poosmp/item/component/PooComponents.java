package embinmc.mod.poosmp.item.component;

import com.mojang.serialization.Codec;
import embinmc.mod.poosmp.PooSMPMod;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.RegistryFixedCodec;
import net.minecraft.util.Unit;
import net.minecraft.world.item.EitherHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;

import java.util.function.Consumer;

public interface PooComponents {
    static void init() {
        PooSMPMod.LOGGER.info("Creating PooSMP Item Components");
    }

    private static <T> DataComponentType<T> register(String id, Consumer<DataComponentType.Builder<T>> builder) {
        DataComponentType.Builder<T> component = DataComponentType.builder();
        builder.accept(component);
        return Registry.register(
                BuiltInRegistries.DATA_COMPONENT_TYPE,
                PooSMPMod.id(id),
                component.build()
        );
    }

    private static DataComponentType<Unit> registerUnit(String id) {
        return register(id, builder -> builder.persistent(Unit.CODEC).networkSynchronized(Unit.STREAM_CODEC));
    }

    private static <T> DataComponentType<T> registerBasic(String id, Codec<T> codec) {
        return register(id, builder -> builder.persistent(codec));
    }

    private static <T> DataComponentType<T> registerBasic(String id, Codec<T> codec, StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec) {
        return register(id, builder -> builder.persistent(codec).networkSynchronized(streamCodec));
    }

    //DataComponentType<EitherHolder<Biome>> SELECTED_BIOME = register("selected_biome", builder -> {
    //    builder.persistent(EitherHolder.codec(Registries.BIOME, RegistryFixedCodec.create(Registries.BIOME)));
    //    builder.networkSynchronized(EitherHolder.streamCodec(Registries.BIOME, ByteBufCodecs.holderRegistry(Registries.BIOME)));
    //});
    DataComponentType<Boolean> MARRIED = register("married", builder -> {
        builder.persistent(Codec.BOOL);
        builder.networkSynchronized(ByteBufCodecs.BOOL);
    });
    DataComponentType<Boolean> FROM_CREATIVE = register("from_creative", builder -> {
        builder.persistent(Codec.BOOL);
        builder.networkSynchronized(ByteBufCodecs.BOOL);
    });

    DataComponentType<EitherHolder<Level>> DIMENSION_WARPER = register("warp_dimension", builder -> {
        builder.persistent(EitherHolder.codec(Registries.DIMENSION, RegistryFixedCodec.create(Registries.DIMENSION)));
        builder.networkSynchronized(EitherHolder.streamCodec(Registries.DIMENSION, ByteBufCodecs.holderRegistry(Registries.DIMENSION)));
    });
    DataComponentType<Unit> JUMPSCARE_STICK = registerUnit("jumpscare_stick");
    DataComponentType<Unit> FUN_STICK = registerUnit("fun_stick");
    DataComponentType<Unit> FORCE_ALLOW_IN_BACKPACK = registerUnit("force_allow_in_backpack");
    DataComponentType<RequestedDiscComponent> REQUESTED_DISC = registerBasic("requested_disc", RequestedDiscComponent.CODEC);
    DataComponentType<Unit> SERVER_SAYS_WHAT = registerUnit("server_says_what");
    DataComponentType<Unit> WIND_SHOOTER = registerUnit("wind_shooter");
    DataComponentType<Unit> LIGHTNING_SUMMONER = registerUnit("lightning_summoner");
    DataComponentType<Unit> POOP_STICK = registerUnit("poop_stick");
    DataComponentType<BiomeStickComponent> BIOME_TRANSFORMER = registerBasic("biome_transformer", BiomeStickComponent.CODEC);
    DataComponentType<Unit> BOOM_STICK = registerUnit("boom_stick");
    DataComponentType<Unit> EXPLOSION_SPAWNER = registerUnit("explosion_spawner");
}
