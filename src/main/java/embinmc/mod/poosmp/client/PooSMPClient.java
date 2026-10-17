package embinmc.mod.poosmp.client;

import com.mojang.blaze3d.platform.InputConstants;
import embinmc.mod.poosmp.PooSMPMod;
import embinmc.mod.poosmp.block.PooBlocks;
import embinmc.mod.poosmp.client.screen.BiomeStickScreen;
import embinmc.mod.poosmp.client.screen.ShopScreen;
import embinmc.mod.poosmp.client.screen.UpgradesScreen;
import embinmc.mod.poosmp.misc.PooSavedData;
import embinmc.mod.poosmp.networking.PooMessages;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.gui.screens.DeathScreen;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.network.chat.Component;
import net.minecraft.util.CommonColors;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.block.Blocks;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.NumberFormat;
import java.util.Locale;

public class PooSMPClient implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("poosmp/client");
    public static final OptionInstance<Boolean> EAT_PACKET_ERRORS = createBoolOption("option.poosmp.eat_packet_errors", true);
    public static final OptionInstance<Boolean> SHOW_BALANCE_ABOVE_ZERO = createBoolOption("option.poosmp.show_balance_above_zero", true);
    public static final KeyMapping.Category POOSMP_KEYS = KeyMapping.Category.register(PooSMPMod.id("poosmp_keys"));

    public static KeyMapping openUpgradesScreen = KeyBindingHelper.registerKeyBinding(new KeyMapping(
        "key.poosmp.open_upgrades_screen",
        InputConstants.Type.KEYSYM,
        GLFW.GLFW_KEY_0,
        POOSMP_KEYS
    ));

    public static KeyMapping openShopScreen = KeyBindingHelper.registerKeyBinding(new KeyMapping(
        "key.poosmp.open_shop_screen",
        InputConstants.Type.KEYSYM,
        GLFW.GLFW_KEY_MINUS,
        POOSMP_KEYS
    ));

    public static KeyMapping testKeybind = KeyBindingHelper.registerKeyBinding(new KeyMapping(
        "key.poosmp.test_keybind",
        InputConstants.Type.KEYSYM,
        GLFW.GLFW_KEY_KP_1,
        POOSMP_KEYS
    ));

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

        PooMessages.registerS2C();

        ClientTickEvents.END_CLIENT_TICK.register(PooSMPMod.id("open_upgrades_screen"), client -> {
            while (openUpgradesScreen.consumeClick()) {
                client.setScreen(new UpgradesScreen(client.screen));
            }
            while (openShopScreen.consumeClick()) {
                client.setScreen(new ShopScreen());
            }
            while (testKeybind.consumeClick()) {
                client.setScreen(new BiomeStickScreen());
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
