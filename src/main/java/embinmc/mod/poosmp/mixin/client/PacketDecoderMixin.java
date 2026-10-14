package embinmc.mod.poosmp.mixin.client;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketDecoder;
import net.minecraft.network.PacketListener;
import net.minecraft.network.ProtocolInfo;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.PacketFlow;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@Mixin(PacketDecoder.class)
public abstract class PacketDecoderMixin<T extends PacketListener> {
    @Shadow private @Final static Logger LOGGER;

    @Shadow private @Final ProtocolInfo<T> protocolInfo;

    @WrapMethod(method = "decode")
    private void poosmp$wrapDecode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list, Operation<Void> original) {
        if (this.protocolInfo.flow() != PacketFlow.CLIENTBOUND) {
            original.call(channelHandlerContext, byteBuf, list);
            return;
        }
        try {
            original.call(channelHandlerContext, byteBuf, list);
        } catch (Exception e) {
            //LOGGER.error("Ignoring packet decoding exception", e);
            Minecraft.getInstance().doRunTask(() -> {
                Minecraft minecraft = Minecraft.getInstance();
                if (minecraft.player == null)
                    return;
                //minecraft.player.connection.sendChat("Ignoring packet decoding exception: " + e.getClass().getSimpleName());
                //minecraft.player.connection.sendChat(e.getMessage());
                Component h = Component.literal("Ignoring packet decoding exception: " + e.getClass().getSimpleName())
                        .withStyle(ChatFormatting.RED, ChatFormatting.BOLD);
                Component b = Component.literal(e.getMessage()).withStyle(ChatFormatting.RED);
                minecraft.gui.getChat().addMessage(h);
                minecraft.gui.getChat().addMessage(b);
                //minecraft.player.displayClientMessage(h, false);
                //minecraft.player.displayClientMessage(b, false);
            });
        }
    }
}
