package embinmc.mod.poosmp.item.component;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import embinmc.mod.poosmp.client.screen.BiomeStickScreen;
import embinmc.mod.poosmp.misc.PooUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.commands.FillBiomeCommand;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.EitherHolder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.gamerules.GameRules;

import java.util.Optional;
import java.util.function.Consumer;

public record BiomeStickComponent(byte horizontalDiameter, byte verticalDiameter, EitherHolder<Biome> selectedBiome) implements TooltipProvider {
    public static final Codec<BiomeStickComponent> CODEC = RecordCodecBuilder.create(bsc -> bsc.group(
            PooUtil.byteRange((byte) 1, (byte) 127).fieldOf("horizontal_diameter").forGetter(BiomeStickComponent::horizontalDiameter),
        PooUtil.byteRange((byte) 1, (byte) 127).fieldOf("vertical_diameter").forGetter(BiomeStickComponent::horizontalDiameter),
            PooUtil.quickEitherHolderCodec(Registries.BIOME).fieldOf("biome").forGetter(BiomeStickComponent::selectedBiome)
    ).apply(bsc, BiomeStickComponent::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, BiomeStickComponent> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.BYTE, BiomeStickComponent::horizontalDiameter,
        ByteBufCodecs.BYTE, BiomeStickComponent::verticalDiameter,
        PooUtil.quickEitherHolderStreamCodec(Registries.BIOME), BiomeStickComponent::selectedBiome,
        BiomeStickComponent::new
    );

    public BiomeStickComponent withHorizontalDiameter(byte diameter) {
        return new BiomeStickComponent(diameter < 1 ? 1 : diameter, this.verticalDiameter(), this.selectedBiome());
    }

    public BiomeStickComponent withVerticalDiameter(byte diameter) {
        return new BiomeStickComponent(this.horizontalDiameter(), diameter < 1 ? 1 : diameter, this.selectedBiome());
    }

    public BiomeStickComponent withBiome(EitherHolder<Biome> biome) {
        return new BiomeStickComponent(this.horizontalDiameter(), this.verticalDiameter(), biome);
    }

    public BiomeStickComponent withBiome(ResourceKey<Biome> biome) {
        return this.withBiome(new EitherHolder<>(biome));
    }

    @Override
    public void addToTooltip(Item.TooltipContext tooltipContext, Consumer<Component> consumer, TooltipFlag tooltipFlag, DataComponentGetter dataComponentGetter) {
        if (tooltipContext.registries() == null)
            return;
        Optional<Holder<Biome>> optionalBiome = this.selectedBiome().unwrap(tooltipContext.registries());
        if (optionalBiome.isEmpty()) {
            consumer.accept(Component.literal("Invalid biome selected").withStyle(ChatFormatting.RED));
            return;
        }
        Holder<Biome> biome = optionalBiome.orElseThrow();
        if (biome.unwrapKey().isEmpty()) {
            consumer.accept(Component.literal("Invalid biome selected").withStyle(ChatFormatting.RED));
            return;
        }
        ResourceKey<Biome> biomeKey = biome.unwrapKey().orElseThrow();
        Component intText = PooUtil.formattedIntText(this.horizontalDiameter, ChatFormatting.YELLOW);
        Component intText2 = PooUtil.formattedIntText(this.verticalDiameter, ChatFormatting.YELLOW);
        Component biomeText = Component.literal(biomeKey.identifier().toString()).withStyle(ChatFormatting.YELLOW);
        consumer.accept(Component.translatable("poosmp.biome_stick.diameter", intText).withStyle(ChatFormatting.GRAY));
        consumer.accept(Component.translatable("poosmp.biome_stick.diameter_vertical", intText2).withStyle(ChatFormatting.GRAY));
        consumer.accept(Component.translatable("poosmp.biome_stick.current_biome", biomeText).withStyle(ChatFormatting.GRAY));
        String crouchName = Minecraft.getInstance().options.keyShift.getName();
        consumer.accept(Component.translatable("poosmp.biome_stick.crouch_tip", Component.keybind(crouchName).withStyle(ChatFormatting.GOLD)).withStyle(ChatFormatting.GRAY));
    }

    public static InteractionResult onUse(Level level, Player player, InteractionHand hand) {
        ItemStack stackInHand = player.getItemInHand(hand);
        BiomeStickComponent biomeStick = stackInHand.get(PooComponents.BIOME_TRANSFORMER);
        if (player.isCrouching()) {
            if (level.isClientSide()) {
                Minecraft.getInstance().setScreen(new BiomeStickScreen());
            }
            return InteractionResult.SUCCESS;
        }
        Optional<Holder<Biome>> optionalBiome = biomeStick.selectedBiome().unwrap(level.registryAccess());
        if (optionalBiome.isEmpty()) {
            if (player instanceof ServerPlayer serverPlayer)
                serverPlayer.sendSystemMessage(Component.literal("Invalid biome").withStyle(ChatFormatting.RED));
            return InteractionResult.FAIL;
        }
        if (level instanceof ServerLevel serverLevel && player instanceof ServerPlayer serverPlayer) {
            Holder<Biome> biome = optionalBiome.orElseThrow();
            int d = biomeStick.horizontalDiameter();
            int dv = biomeStick.verticalDiameter();
            int minY = serverLevel.getMinY() + dv;
            int maxY = serverLevel.getHeight() - serverLevel.getMinY() - dv;
            int posY = Math.clamp(serverPlayer.getBlockY(), minY, maxY);
            BlockPos corner1 = new BlockPos(serverPlayer.getBlockX() - d, posY - dv, serverPlayer.getBlockZ() - d);
            BlockPos corner2 = new BlockPos(serverPlayer.getBlockX() + d, posY + dv, serverPlayer.getBlockZ() + d);
            Either<Integer, CommandSyntaxException> result = FillBiomeCommand.fill(serverLevel, corner1, corner2, biome, holder -> true, text -> {
                Component component = text.get();
                serverPlayer.sendSystemMessage(component, true);
                serverLevel.playSound(null, serverPlayer.blockPosition(), SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.PLAYERS);
            });
            if (result.right().isPresent()) {
                serverPlayer.sendSystemMessage(Component.literal("An error occurred, likely gamerule " + GameRules.MAX_BLOCK_MODIFICATIONS.getIdentifier() + " being too low").withStyle(ChatFormatting.RED));
                serverPlayer.sendSystemMessage(Component.literal("The error: " + result.right().orElseThrow().getMessage()).withStyle(ChatFormatting.RED));
                serverLevel.playSound(null, serverPlayer.blockPosition(), SoundEvents.ANVIL_DESTROY, SoundSource.PLAYERS);
            }
        }
        player.getCooldowns().addCooldown(stackInHand, 2);
        player.awardStat(Stats.ITEM_USED.get(stackInHand.getItem()));
        return InteractionResult.SUCCESS;
    }
}
