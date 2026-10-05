package embinmc.mod.poosmp.upgrade;

import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public class ExplodeEntityWhenHitUpgrade extends AbstractUpgrade {
    public static final UpgradeType<ExplodeEntityWhenHitUpgrade> TYPE = new UpgradeType<>("Explode", simpleCodec(ExplodeEntityWhenHitUpgrade::new));

    public ExplodeEntityWhenHitUpgrade(Properties properties) {
        super(properties);
    }

    @Override
    public void onBuy(int amountPurchased, ServerPlayer player, ServerLevel level, MinecraftServer server) {
        player.sendSystemMessage(Component.literal("Your fists feel weird..."));
    }

    @Override
    public void onSell(int amountPurchased, ServerPlayer player, ServerLevel level, MinecraftServer server) {
        player.sendSystemMessage(Component.literal("You feel normal again..."));
    }

    @Override
    public void onRespawn(int amountPurchased, ServerPlayer player, ServerLevel level, MinecraftServer server) {}

    @Override
    public void onTick(int amountPurchased, ServerPlayer player, ServerLevel level, MinecraftServer server) {}

    @Override
    public void onHitEntity(int amountPurchased, ServerPlayer player, LivingEntity hitEntity, ServerLevel level) {
        super.onHitEntity(amountPurchased, player, hitEntity);
        level.explode(player, hitEntity.getX(), hitEntity.getY(), hitEntity.getZ(), 0.85f * amountPurchased, Level.ExplosionInteraction.MOB);
    }

    @Override
    public UpgradeType<?> getType() {
        return TYPE;
    }
}
