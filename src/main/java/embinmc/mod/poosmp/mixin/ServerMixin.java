package embinmc.mod.poosmp.mixin;

import embinmc.mod.poosmp.misc.PooServer;
import embinmc.mod.poosmp.misc.VoteKickManager;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(MinecraftServer.class)
public abstract class ServerMixin implements PooServer {
	private final @Unique VoteKickManager voteKickManager = new VoteKickManager();

	@Override
	public VoteKickManager poosmp$voteKickManager() {
		return this.voteKickManager;
	}
}