package embinmc.mod.poosmp.upgrade;

import com.mojang.serialization.MapCodec;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;

public class StatusEffectUpgrade extends AbstractUpgrade {
    public static final MapCodec<StatusEffectUpgrade> MAP_CODEC = simpleCodec(
            StatusEffectUpgrade::new,
            MobEffectInstance.CODEC.fieldOf("status_effect").forGetter(s -> s.effectInstance)
    );
    public static final UpgradeType<StatusEffectUpgrade> TYPE = new UpgradeType<>("StatusEffectUpgrade", MAP_CODEC);
    protected final MobEffectInstance effectInstance;

    public StatusEffectUpgrade(Properties properties, MobEffectInstance effect) {
        super(properties);
        this.effectInstance = effect;
    }

    @Override
    public void onTick(int amountPurchased, ServerPlayer player, ServerLevel level, MinecraftServer server) {
        if (amountPurchased > 0) {
            if (!player.getActiveEffects().contains(this.effectInstance)) {
                player.addEffect(this.effectInstance);
            }
        }
    }

    @Override
    public void onSell(int amountPurchased, ServerPlayer player, ServerLevel level, MinecraftServer server) {
        player.removeEffect(this.effectInstance.getEffect());
    }

    @Override
    public UpgradeType<?> getType() {
        return TYPE;
    }

    @Override
    public void onRespawn(int amountPurchased, ServerPlayer player, ServerLevel level, MinecraftServer server) {}

    @Override
    public void onBuy(int amountPurchased, ServerPlayer player, ServerLevel level, MinecraftServer server) {}
}
