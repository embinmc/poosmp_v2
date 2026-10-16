package embinmc.mod.poosmp.client.screen;

import embinmc.mod.poosmp.misc.PooSavedData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.util.CommonColors;
import org.jspecify.annotations.NonNull;

public class BalanceWidget extends AbstractWidget {
    public BalanceWidget(int x, int y) {
        super(x, y, 4, 4, Component.empty());
    }

    @Override
    protected void renderWidget(@NonNull GuiGraphics context, int mouseX, int mouseY, float delta) {
        Minecraft minecraft = Minecraft.getInstance();
        PooSavedData savedData = PooSavedData.Client.INSTANCE;
        if (minecraft.player != null) {
            String balance = "$%,.2f".formatted(savedData.getBalance(minecraft.player));
            context.drawCenteredString(minecraft.font, balance, this.getX(), this.getY(), CommonColors.WHITE);
        }
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput builder) {
    }
}
