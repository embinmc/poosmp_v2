package embinmc.mod.poosmp.item.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import embinmc.mod.poosmp.misc.PooUtil;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.EitherHolder;
import net.minecraft.world.level.Level;

import java.util.Optional;

public record DimensionWarperComponent(EitherHolder<Level> dimension, Optional<MobEffectInstance> statusEffect) {
    public static final MobEffectInstance DEFAULT_EFFECT = new MobEffectInstance(MobEffects.RESISTANCE, 8, 5, false, true);
    public static final Codec<DimensionWarperComponent> CODEC = RecordCodecBuilder.create(dw -> dw.group(
            PooUtil.quickEitherHolderCodec(Registries.DIMENSION).fieldOf("dimension").forGetter(DimensionWarperComponent::dimension),
            MobEffectInstance.CODEC.optionalFieldOf("status_effect").forGetter(DimensionWarperComponent::statusEffect)
    ).apply(dw, DimensionWarperComponent::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, DimensionWarperComponent> STREAM_CODEC = StreamCodec.composite(
            PooUtil.quickEitherHolderStreamCodec(Registries.DIMENSION), DimensionWarperComponent::dimension,
            MobEffectInstance.STREAM_CODEC.apply(ByteBufCodecs::optional), DimensionWarperComponent::statusEffect,
            DimensionWarperComponent::new
    );
}
