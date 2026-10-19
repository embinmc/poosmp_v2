package embinmc.mod.poosmp.command;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import embinmc.mod.poosmp.economy.ItemValue;
import embinmc.mod.poosmp.item.PooItems;
import embinmc.mod.poosmp.item.component.PooComponents;
import embinmc.mod.poosmp.misc.*;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.ComponentArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.NameAndId;
import net.minecraft.server.players.PlayerList;
import net.minecraft.stats.ServerStatsCounter;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.text.NumberFormat;
import java.util.*;
import java.util.function.BiFunction;

public interface PooSMPCommands {
    static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext registries) {
        // tell player's balance
        dispatcher.register(Commands.literal("getbal").executes(context -> {
            MinecraftServer server = context.getSource().getServer();
            PooSavedData savedData = PooSavedData.get(server);
            if (savedData.balance.isEmpty()) {
                context.getSource().sendFailure(Component.literal("Everybody is broke"));
                return 0;
            }
            var nf = NumberFormat.getCurrencyInstance(Locale.US);
            final int[] index = {1};
            final double[] totalMoney = {0D};
            savedData.balance.values().forEach(d -> totalMoney[0] += d);
            if (context.getSource().getPlayer() != null)
                context.getSource().getPlayer().sendSystemMessage(Component.literal("Total money: " + nf.format(totalMoney[0])).withStyle(ChatFormatting.GOLD));
            savedData.balance.keySet().stream().sorted(Comparator.comparingDouble(k -> savedData.balance.get(k)).reversed()).forEach(uuid -> {
                double balance = savedData.balance.get(uuid);
                ServerPlayer player = server.getPlayerList().getPlayer(uuid);
                String formattedBalance = nf.format(balance);

                String playerName;
                if (player != null) {
                    playerName = player.getPlainTextName();
                } else if (savedData.getPlayerName(uuid).isPresent()) {
                    playerName = savedData.getPlayerName(uuid).orElseThrow();
                } else playerName = uuid.toString(); // fallback to player's uuid

                String message = index[0] + ": " + playerName + " -> " + formattedBalance;
                if (context.getSource().getPlayer() != null)
                    context.getSource().getPlayer().sendSystemMessage(Component.literal(message).withStyle(ChatFormatting.GOLD));
                index[0]++;
            });
            return Command.SINGLE_SUCCESS;
        }));

        BiFunction<ItemStack, CommandContext<CommandSourceStack>, Boolean> sellItem = (itemStack, context) -> {
            Optional<ItemValue> optional = PooUtil.getValueOfStack(itemStack, context.getSource().registryAccess());
            if (optional.isEmpty())
                return false;
            ItemValue itemValue = optional.orElseThrow();
            ServerPlayer player = context.getSource().getPlayer();
            if (player == null)
                return false;
            double profit = itemValue.sellValue() * itemStack.getCount();
            PooSavedData savedData = PooSavedData.get(context.getSource().getServer());
            savedData.addBalance(player, profit);
            player.sendSystemMessage(Component.literal("Sold " + itemStack + " for $%,.2f".formatted(profit)));
            itemStack.setCount(0);
            return true;
        };

        // sell items
        dispatcher.register(Commands.literal("sell")
            .then(Commands.literal("hand").executes(context -> {
                ServerPlayer player = context.getSource().getPlayerOrException();
                ItemStack stackInHand = player.getMainHandItem();
                if (stackInHand.isEmpty())
                    return -1;
                if (sellItem.apply(stackInHand, context))
                    return 1;
                else
                    return -2;
            }).then(Commands.literal("all").executes(context -> {
                ServerPlayer player = context.getSource().getPlayerOrException();
                ItemStack stackInHand = player.getMainHandItem();
                Item itemInHand = stackInHand.getItem();
                sellItem.apply(stackInHand, context);
                for (ItemStack itemStack : player.getInventory()) {
                    if (itemStack.getItem() == itemInHand)
                        sellItem.apply(itemStack, context);
                }
                return 1;
            })
                )
            ).then(Commands.literal("inv").executes(context -> {
                ServerPlayer player = context.getSource().getPlayerOrException();
                for (ItemStack itemStack : player.getInventory()) {
                    sellItem.apply(itemStack, context);
                }
                return 1;
            })
            )
        );

        // public /tellraw command
        dispatcher.register(Commands.literal("tellraw2")
            // target argument only allows single player names when not op status, so we just won't have it and broadcast to everyone
            .then(Commands.argument("message", ComponentArgument.textComponent(registries))
                .executes(commandContext -> {
                    MinecraftServer server = commandContext.getSource().getServer();
                    List<ServerPlayer> players = server.getPlayerList().getPlayers();
                    for (ServerPlayer player : players) {
                        player.sendSystemMessage(ComponentArgument.getResolvedComponent(commandContext, "message", player));
                    }
                    return players.size();
                })
            )
        );

        dispatcher.register(Commands.literal("suicide").executes(context -> {
            ServerPlayer player = context.getSource().getPlayerOrException();
            ServerLevel level = context.getSource().getLevel();
            player.sendSystemMessage(Component.literal("Attempting to KILL YOU!!!!!"));
            player.hurtServer(context.getSource().getLevel(), level.damageSources().source(PooDamageTypes.SUICIDE_COMMAND), Float.MAX_VALUE);
            return 1;
        }));

        dispatcher.register(Commands.literal("randomentity").executes(context -> {
            List<Entity> entities = PooUtil.listFromEnumeration(context.getSource().getLevel().getAllEntities().iterator());
            int index = context.getSource().getLevel().getRandom().nextInt(0, entities.size());
            try {
                Entity entity = entities.get(index);
                context.getSource().getPlayerOrException().sendSystemMessage(entity.getName());
                context.getSource().getPlayerOrException().sendSystemMessage(Component.literal(entity.toString()));
                context.getSource().getPlayerOrException().sendSystemMessage(ComponentUtils.copyOnClickText(entity.getStringUUID()));
                return index;
            } catch (Exception e) {
                context.getSource().getPlayerOrException().sendSystemMessage(Component.literal("failed").withStyle(ChatFormatting.RED));
                return -1;
            }
        }));

        // death count
        dispatcher.register(Commands.literal("deathcount").executes(context -> {
            ServerPlayer player = context.getSource().getPlayer();
            if (player == null)
                return -5;
            int deaths = getDeathCount(context, player.nameAndId());
            player.sendSystemMessage(Component.literal("You have died " + deaths + " time(s)."));
            return deaths;
        }).then(Commands.literal("list").executes(context -> {
            ServerPlayer asker = context.getSource().getPlayer();
            if (asker == null)
                return -5;
            MinecraftServer server = context.getSource().getServer();
            if (!(server.services().nameToIdCache() instanceof PooNameCache pooNameCache)) {
                context.getSource().sendFailure(Component.literal("Couldn't fetch death counts").withStyle(ChatFormatting.RED));
                return -10;
            }
            Collection<NameAndId> cache = pooNameCache.poosmp$getCachedNames();
            Map<NameAndId, Integer> deathCounts = HashMap.newHashMap(cache.size());
            int totalDeaths = 0;
            for (NameAndId player : cache) {
                int result = getDeathCount(context, player);
                if (result < 0)
                    continue;
                deathCounts.put(player, result);
                totalDeaths += result;
            }
            final int[] index = {1};
            asker.sendSystemMessage(Component.literal("Total deaths: " + totalDeaths));
            deathCounts.keySet().stream().sorted(Comparator.comparingInt(deathCounts::get)).forEach(nameAndId -> {
                int deaths = deathCounts.get(nameAndId);
                Component text = Component.literal(index[0] + ": " + nameAndId.name() + " -> " + deaths);
                asker.sendSystemMessage(text);
                index[0]++;
            });
            return totalDeaths;
        })));

        dispatcher.register(Commands.literal("withdraw")
            .then(Commands.literal("cheque")
                .then(
                    Commands.argument("amount", DoubleArgumentType.doubleArg(0.01D))
                        .executes(context -> {
                            double amount = PooUtil.roundTwo(DoubleArgumentType.getDouble(context, "amount"));
                            ServerPlayer player = context.getSource().getPlayerOrException();
                            PooSavedData savedData = PooSavedData.get(context.getSource().getServer());
                            if (savedData.getBalance(player) < amount) {
                                player.sendSystemMessage(Component.literal("You are too broke to write this cheque.").withStyle(ChatFormatting.RED));
                                return -2;
                            }
                            ItemStack cheque = new ItemStack(PooItems.CHEQUE, 1);
                            cheque.set(PooComponents.CHEQUE, amount);
                            savedData.addBalance(player, -amount);
                            ServerLevel level = player.level();
                            level.addFreshEntity(new ItemEntity(level, player.getX(), player.getY(), player.getZ(), cheque));
                            return 1;
                        })
                )
            )
            .then(Commands.literal("cash")
                .then(withdrawMoneyItem("1", PooItems.ONE_DOLLAR_BILL))
                .then(withdrawMoneyItem("2", PooItems.TWO_DOLLAR_BILL))
                .then(withdrawMoneyItem("5", PooItems.FIVE_DOLLAR_BILL))
                .then(withdrawMoneyItem("10", PooItems.TEN_DOLLAR_BILL))
                .then(withdrawMoneyItem("25", PooItems.TWENTY_FIVE_DOLLAR_BILL))
                .then(withdrawMoneyItem("50", PooItems.FIFTY_DOLLAR_BILL))
                .then(withdrawMoneyItem("100", PooItems.HUNDRED_DOLLAR_BILL))
            )
        );
    }

    private static int getDeathCount(CommandContext<CommandSourceStack> context, NameAndId player) {
        if (player == null) return 0;
        PlayerList playerList = context.getSource().getServer().getPlayerList();
        if (!(playerList instanceof PooPlayerList pooPlayerList)) {
            context.getSource().sendFailure(Component.literal("Failed to get stats for " + player.name()).withStyle(ChatFormatting.RED));
            return -10;
        }
        boolean isRealPlayer = playerList.getPlayer(player.name()) != null || pooPlayerList.poosmp$getPlayerDataStorage().load(player).isPresent();
        if (!isRealPlayer)
            return -1;
        //PooSMPMod.LOGGER.info("Trying to get stats for player {} ({})", player.name(), player.id());
        ServerStatsCounter stats = pooPlayerList.poosmp$getPlayerStats(new GameProfile(player.id(), player.name()));
        return stats.getValue(Stats.CUSTOM.get(Stats.DEATHS));
    }

    private static ArgumentBuilder<CommandSourceStack, LiteralArgumentBuilder<CommandSourceStack>> withdrawMoneyItem(String name, Item moneyItem) {
        var com = Commands.literal(name).executes(c -> getMoneyItem(c.getSource(), moneyItem));
        return com.then(Commands.argument("amount", IntegerArgumentType.integer(1, 99)).executes(context -> {
            final int amount = IntegerArgumentType.getInteger(context, "amount");
            int remaining = amount;
            while (remaining > 0) {
                int result = getMoneyItem(context.getSource(), moneyItem);
                remaining--;
                if (result < 1)
                    return result;
            }
            return amount;
        }));
    }

    private static int getMoneyItem(CommandSourceStack context, Item moneyItem) {
        ItemStack moneyStack = moneyItem.getDefaultInstance();
        Optional<ItemValue> optional = PooUtil.getValueOfStack(moneyStack, context.registryAccess());
        if (optional.isPresent()) {
            final double moneyAmount = optional.orElseThrow().sellValue();
            MinecraftServer server = context.getServer();
            ServerPlayer player = context.getPlayer();
            if (player == null) return 0;
            PooSavedData savedData = PooSavedData.get(server);
            final double playerBalance = savedData.getBalance(player);
            if (moneyAmount > playerBalance) {
                context.sendFailure(Component.literal("You are too broke to withdraw this"));
                return -1;
            }
            savedData.addBalance(player, -moneyAmount);
            ServerLevel level = player.level();
            level.addFreshEntity(new ItemEntity(level, player.getX(), player.getY(), player.getZ(), moneyStack.copy()));
            return Command.SINGLE_SUCCESS;
        } else return -50;
    }
}
