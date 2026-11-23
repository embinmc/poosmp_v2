package embinmc.mod.poosmp.misc;

import com.mojang.logging.LogUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.UserBanListEntry;
import net.minecraft.stats.StatFormatter;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;

import java.time.Instant;
import java.util.*;

@WorkInProgress // TODO: FINISH THIS
public class VoteKickManager {
    private static final Logger LOGGER = LogUtils.getLogger();
    private final Temporary<VoteKick> activeVoteKick = Temporary.empty();
    private int remainingTicks = -1;
    private int yesKicks = 0, noKicks = 0;

    private void setNewVoteKick(VoteKick voteKick) {
        this.activeVoteKick.setNewValue(voteKick);
        this.remainingTicks = 600;
        this.yesKicks = 0;
        this.noKicks = 0;
    }

    public void callVoteKick(ServerPlayer player) {
        this.setNewVoteKick(VoteKick.createKick(player));
    }

    public void callVoteBan(ServerPlayer player, int durationTicks) {
        this.setNewVoteKick(VoteKick.createBan(player, durationTicks));
    }

    public void tick(MinecraftServer minecraftServer) {
        if (!this.activeVoteKick.isPresent())
            return;
        if (this.remainingTicks > 0) {
            this.remainingTicks--;
        }
        if (this.remainingTicks == 0) {
            this.remainingTicks--;
            if (this.yesKicks > this.noKicks) {
                this.activeVoteKick.orElseThrow().onSuccess(minecraftServer);
                this.activeVoteKick.clear();
            }
        }
    }

    private record VoteKick(ServerPlayer target, Optional<String> reason, boolean isBan, OptionalInt banTimeTicks) {
        private VoteKick {}

        public static VoteKick createKick(ServerPlayer target, @Nullable String reason) {
            return new VoteKick(target, Optional.ofNullable(reason), false, OptionalInt.empty());
        }

        public static VoteKick createKick(ServerPlayer target) {
            return VoteKick.createKick(target, null);
        }

        public static VoteKick createBan(ServerPlayer target, int lengthTicks, @Nullable String reason) {
            return new VoteKick(target, Optional.ofNullable(reason), true, OptionalInt.of(lengthTicks));
        }

        public static VoteKick createBan(ServerPlayer target, int lengthTicks) {
            return VoteKick.createBan(target, lengthTicks, null);
        }

        public void onSuccess(MinecraftServer server) {
            if (!server.getPlayerList().getPlayers().contains(this.target())) {
                LOGGER.warn("Can't perform vote kick on player that doesn't exist anymore...");
                return;
            }
            Component kickedName = this.target().getName();
            if (this.isBan()) {
                if (this.banTimeTicks().isEmpty()) {
                    LOGGER.warn("Can't perform vote ban on player without a duration specified...");
                    return;
                }
                Date currentTime = Date.from(Instant.now());
                Date endTime = Date.from(Instant.now().plusMillis(50L * this.banTimeTicks().getAsInt()));
                UserBanListEntry ban = new UserBanListEntry(this.target().nameAndId(), currentTime, "PooSMP Vote Ban", endTime, "You were vote banned");
                server.getPlayerList().getBans().add(ban);
                server.getPlayerList().broadcastSystemMessage(
                    Component.literal("")
                        .append(kickedName)
                        .append(" has been successfully vote banned for ")
                        .append(StatFormatter.TIME.format(this.banTimeTicks().getAsInt()))
                    , false
                );
            } else {
                this.target().connection.disconnect(Component.literal("You were vote kicked."));
                server.getPlayerList().broadcastSystemMessage(Component.literal("").append(kickedName).append(" has been successfully vote kicked."), false);
            }
        }
    }
}
