package embinmc.mod.poosmp.mixin;

import embinmc.mod.poosmp.misc.PooServer;
import embinmc.mod.poosmp.misc.VoteKickManager;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftServer.class)
public abstract class ServerMixin implements PooServer {
	private final @Unique VoteKickManager voteKickManager = new VoteKickManager();

	@Override
	public VoteKickManager poosmp$voteKickManager() {
		return this.voteKickManager;
	}
}