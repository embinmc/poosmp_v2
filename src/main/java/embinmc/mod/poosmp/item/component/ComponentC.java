package embinmc.mod.poosmp.item.component;

import embinmc.mod.poosmp.client.screen.BiomeStickScreen;
import net.minecraft.client.Minecraft;

public class ComponentC {
    static void openBiomeStickScreen() {
        // cant reference screen class in biome stick component, will crash on server startup
        Minecraft.getInstance().setScreen(new BiomeStickScreen());
    }
}
