package embinmc.mod.poosmp.misc;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import embinmc.mod.poosmp.PooSMPMod;
import embinmc.mod.poosmp.upgrade.Upgrade;
import embinmc.mod.poosmp.upgrade.UpgradePrice;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class PooSavedData extends SavedData {
    public static final double MAX_MONEY = 8_008_135_000D;
    public static final String KEY = "poosmp_data";
    public static final Codec<PooSavedData> CODEC = RecordCodecBuilder.create(u -> u.group(
            PooUtil.uuidMapCodec(Codec.DOUBLE).fieldOf("player_balance").forGetter(z -> z.balance),
            PooUtil.uuidMapIdCodec(Codec.INT).fieldOf("purchased_upgrades").forGetter(z -> z.purchasedUpgrades),
            PooUtil.uuidMapCodec(Codec.STRING).fieldOf("player_names").forGetter(z -> z.playerNames)
    ).apply(u, PooSavedData::new));
    public static final SavedDataType<PooSavedData> TYPE = new SavedDataType<>("poosmp", PooSavedData::new, CODEC, null);
    public Map<UUID, Map<Identifier, Integer>> purchasedUpgrades;
    public Map<UUID, Double> balance;
    public Map<UUID, String> playerNames;

    public PooSavedData(
            Map<UUID, Double> balance,
            Map<UUID, Map<Identifier, Integer>> purchases,
            Map<UUID, String> playerNames
    ) {
        this.balance = new HashMap<>(balance);
        this.purchasedUpgrades = PooUtil.fixMaps(purchases);
        this.playerNames = new HashMap<>(playerNames);
    }

    public PooSavedData() {
        this.balance = HashMap.newHashMap(16);
        this.purchasedUpgrades = HashMap.newHashMap(16);
        this.playerNames = HashMap.newHashMap(16);
    }

    public void addPlayerName(Player player) {
        if (!this.playerNames.containsKey(player.getUUID())) {
            this.playerNames.put(player.getUUID(), player.getPlainTextName());
            this.setDirty();
        }
    }

    public Optional<String> getPlayerName(UUID uuid) {
        return Optional.ofNullable(this.playerNames.get(uuid));
    }

    public Map<Identifier, Integer> getPurchasedUpgradesIdMap(Player player) {
        UUID playerUuid = player.getUUID();
        this.addPlayerName(player);
        if (!this.purchasedUpgrades.containsKey(playerUuid)) {
            this.purchasedUpgrades.put(playerUuid, HashMap.newHashMap(16));
            this.setDirty();
        }
        return this.purchasedUpgrades.get(playerUuid);
    }

    public double getBalance(Player player) {
        UUID uuid = player.getUUID();
        if (!this.balance.containsKey(uuid)) {
            this.balance.put(uuid, PooUtil.getGameRuleValue(player.level(), PooSMPGameRules.STARTING_BALANCE));
            this.addPlayerName(player);
            this.setDirty();
        }
        return PooUtil.roundTwo(this.balance.get(uuid));
    }

    public int upgradePurchaseAmount(Player player, ResourceKey<Upgrade> upgrade) {
        return this.getPurchasedUpgradesIdMap(player).getOrDefault(upgrade.identifier(), 0);
    }

    public boolean addBalance(ServerPlayer player, double amount) {
        double current = this.getBalance(player);
        double newAmount = Math.clamp(PooUtil.roundTwo(current + amount), 0D, MAX_MONEY);
        this.addPlayerName(player);
        this.balance.put(player.getUUID(), newAmount);
        this.setDirty();
        return newAmount == PooUtil.roundTwo(current + amount);
    }

    private static Registry<Upgrade> getUpgradeRegistry(Player player) {
        return player.registryAccess().lookupOrThrow(PooRegistries.Keys.UPGRADE);
    }

    public boolean buyUpgrade(ServerPlayer serverPlayer, ResourceKey<Upgrade> upgradeKey) {
        this.addPlayerName(serverPlayer);
        Registry<Upgrade> registry = getUpgradeRegistry(serverPlayer);
        Optional<? extends Holder<Upgrade>> optionalUpgrade = registry.get(upgradeKey);
        if (optionalUpgrade.isEmpty()) {
            PooSMPMod.LOGGER.error("Player {} tried to buy unknown upgrade: {}", serverPlayer.getPlainTextName(), upgradeKey.identifier());
            return false;
        }
        Holder<Upgrade> upgrade = optionalUpgrade.orElseThrow();
        Upgrade direct = upgrade.value();
        int amountPurchased = this.upgradePurchaseAmount(serverPlayer, upgradeKey);
        if (direct.properties().maxPurchases().isPresent() && direct.properties().maxPurchases().orElse(0) >= amountPurchased) {
            PooSMPMod.LOGGER.error("Player {} tried to buy upgrade that can't be purchased anymore: {}", serverPlayer.getPlainTextName(), upgradeKey.identifier());
            return false;
        }
        double currentBalance = this.getBalance(serverPlayer);
        UpgradePrice upgradePrice = direct.properties().price();
        double cost = upgradePrice.getFinalPrice(amountPurchased);
        if (cost > currentBalance) {
            PooSMPMod.LOGGER.error("Player {} tried to buy upgrade they can't afford: {}", serverPlayer.getPlainTextName(), upgradeKey.identifier());
            return false;
        }
        this.addBalance(serverPlayer, -cost);
        this.getPurchasedUpgradesIdMap(serverPlayer).put(upgradeKey.identifier(), amountPurchased + 1);
        this.setDirty();
        direct.onBuy(amountPurchased + 1, serverPlayer);
        return true;
    }

    public boolean sellUpgrade(ServerPlayer serverPlayer, ResourceKey<Upgrade> upgradeKey) {
        this.addPlayerName(serverPlayer);
        Registry<Upgrade> registry = getUpgradeRegistry(serverPlayer);
        Optional<? extends Holder<Upgrade>> optionalUpgrade = registry.get(upgradeKey);
        if (optionalUpgrade.isEmpty()) {
            PooSMPMod.LOGGER.error("Player {} tried to sell unknown upgrade: {}", serverPlayer.getPlainTextName(), upgradeKey.identifier());
            return false;
        }
        Holder<Upgrade> upgrade = optionalUpgrade.orElseThrow();
        Upgrade direct = upgrade.value();
        int amountPurchased = this.upgradePurchaseAmount(serverPlayer, upgradeKey);
        if (0 >= amountPurchased) {
            PooSMPMod.LOGGER.error("Player {} tried to sell upgrade that they don't have: {}", serverPlayer.getPlainTextName(), upgradeKey.identifier());
            return false;
        }
        UpgradePrice upgradePrice = direct.properties().price();
        double returnCost = upgradePrice.getFinalPrice(amountPurchased - 1);
        this.addBalance(serverPlayer, returnCost);
        this.getPurchasedUpgradesIdMap(serverPlayer).put(upgradeKey.identifier(), Math.max(amountPurchased - 1, 0));
        this.setDirty();
        direct.onSell(amountPurchased, serverPlayer);
        return true;
    }

    public static PooSavedData get(ServerPlayer serverPlayer) {
        return serverPlayer.level().getServer().overworld().getDataStorage().computeIfAbsent(PooSavedData.TYPE);
    }
}
