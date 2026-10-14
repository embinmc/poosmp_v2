package embinmc.mod.poosmp.client;

import embinmc.mod.poosmp.PooSMPMod;
import embinmc.mod.poosmp.block.PooBlocks;
import embinmc.mod.poosmp.misc.PooSavedData;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.util.CommonColors;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.block.Blocks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.NumberFormat;
import java.util.Locale;

public class PooSMPClient implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("poosmp/client");
    public static final OptionInstance<Boolean> EAT_PACKET_ERRORS = createBoolOption("option.poosmp.eat_packet_errors", true);
    public static final OptionInstance<Boolean> SHOW_BALANCE_ABOVE_ZERO = createBoolOption("option.poosmp.show_balance_above_zero", true);

    @Override
    public void onInitializeClient() {
        PooClientConfig.read();

        BlockRenderLayerMap.putBlocks(ChunkSectionLayer.CUTOUT,
                Blocks.MOSS_CARPET,
                Blocks.RED_CARPET,
                Blocks.YELLOW_CARPET,
                Blocks.ORANGE_CARPET,
                Blocks.BLACK_CARPET,
                Blocks.BLUE_CARPET,
                Blocks.CYAN_CARPET,
                Blocks.LIGHT_BLUE_CARPET,
                Blocks.LIME_CARPET,
                Blocks.LIGHT_GRAY_CARPET,
                Blocks.GRAY_CARPET,
                Blocks.BROWN_CARPET,
                Blocks.GREEN_CARPET,
                Blocks.MAGENTA_CARPET,
                Blocks.PINK_CARPET,
                Blocks.MAGENTA_CARPET,
                Blocks.WHITE_CARPET,
                PooBlocks.FAKE_GRASS_BLOCK
        );

        ColorProviderRegistry.BLOCK.register(
                (state, world, pos, tintIndex) -> world != null && pos != null ? BiomeColors.getAverageGrassColor(world, pos) : GrassColor.getDefaultColor(),
                PooBlocks.FAKE_GRASS_BLOCK
        );

        HudElementRegistry.addLast(PooSMPMod.id("balance"), (guiGraphics, deltaTracker) -> {
            Minecraft minecraft = Minecraft.getInstance();
            PooSavedData savedData = PooSavedData.Client.INSTANCE;
            if (minecraft.player != null) {
                if (savedData.getBalance(minecraft.player) > 0 || PooSMPClient.showBalanceAboveZero()) {
                    String balance = NumberFormat.getCurrencyInstance(Locale.US).format(savedData.getBalance(minecraft.player));
                    guiGraphics.drawString(minecraft.font, balance, 4, guiGraphics.guiHeight() - 10, CommonColors.WHITE);
                }
            }
        });
    }

    private static OptionInstance<Boolean> createBoolOption(String id, boolean defaultValue) {
        return OptionInstance.createBoolean("option.poosmp." + id, defaultValue, PooClientConfig::save);
    }

    public static boolean eatPacketErrors() {
        return EAT_PACKET_ERRORS.get();
    }

    public static boolean showBalanceAboveZero() {
        return SHOW_BALANCE_ABOVE_ZERO.get();
    }
}
