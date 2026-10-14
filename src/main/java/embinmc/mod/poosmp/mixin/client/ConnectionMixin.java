package embinmc.mod.poosmp.mixin.client;

import embinmc.mod.poosmp.client.PooSMPClient;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.ChatFormatting;
import net.minecraft.network.Connection;
import net.minecraft.network.SkipPacketException;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.game.ClientboundSystemChatPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Connection.class)
public abstract class ConnectionMixin {
    @Shadow
    public abstract void send(Packet<?> packet);

    @Shadow
    public abstract PacketFlow getSending();

    @Inject(method = "exceptionCaught", at = @At("HEAD"), cancellable = true)
    private void poosmp$handlePacketException(ChannelHandlerContext channelHandlerContext, Throwable throwable, CallbackInfo ci) {
        if (!PooSMPClient.eatPacketErrors() || throwable instanceof SkipPacketException)
            return;
        if (this.getSending() == PacketFlow.CLIENTBOUND) {
            Component h = Component.literal("Ate packet exception: " + throwable.getClass().getSimpleName()).withStyle(ChatFormatting.RED, ChatFormatting.BOLD);
            Component b = Component.literal(throwable.getMessage()).withStyle(ChatFormatting.RED);
            this.send(new ClientboundSystemChatPacket(h, false));
            this.send(new ClientboundSystemChatPacket(b, false));
        }
        ci.cancel();
    }
}
