package embinmc.mod.poosmp.misc;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import embinmc.mod.poosmp.PooSMPMod;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.ExtraCodecs;

import java.util.List;

public record Marriage(List<String> marriedUsers) {
    public static final Codec<Marriage> DIRECT_CODEC = RecordCodecBuilder.create(mi -> mi.group(
        ExtraCodecs.PLAYER_NAME.listOf(1, 16).fieldOf("married_users").forGetter(Marriage::marriedUsers)
    ).apply(mi, Marriage::new));
    public static final ResourceKey<Marriage> EMBIN_AND_DDE = createKey("embin_and_ddededodediamante");
    public static final ResourceKey<Marriage> CUBEY = createKey("cubey");

    public boolean arePlayersMarried(ServerPlayer player1, ServerPlayer player2) {
        return this.marriedUsers().contains(player1.getPlainTextName()) && this.marriedUsers().contains(player2.getPlainTextName());
    }

    private static ResourceKey<Marriage> createKey(String id) {
        return ResourceKey.create(PooRegistries.Keys.MARRIAGE, PooSMPMod.id(id));
    }
}
