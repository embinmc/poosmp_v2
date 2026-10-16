package embinmc.mod.poosmp.client.screen;

import embinmc.mod.poosmp.misc.PooRegistries;
import embinmc.mod.poosmp.misc.PooSavedData;
import embinmc.mod.poosmp.networking.c2s.BuyUpgradePacket;
import embinmc.mod.poosmp.networking.c2s.SellUpgradePacket;
import embinmc.mod.poosmp.upgrade.Upgrade;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.StatFormatter;
import net.minecraft.util.CommonColors;
import net.minecraft.world.entity.player.Player;

import java.util.List;

public class UpgradesListWidget extends ContainerObjectSelectionList<UpgradesListWidget.UpgradeEntry> {
    private UpgradesScreen parent;
    private Registry<Upgrade> upgradeRegistry;
    //private final
    public UpgradesListWidget(Minecraft client, UpgradesScreen parent) {
        super(client, parent.width, parent.layout.getContentHeight(), parent.layout.getHeaderHeight(), 22);
        this.parent = parent;
        RegistryAccess registryLookup = client.player.registryAccess();
        this.upgradeRegistry = registryLookup.lookupOrThrow(PooRegistries.Keys.UPGRADE);

        for (Upgrade upgrade : this.upgradeRegistry) {
            this.addEntry(new UpgradeEntry(upgrade, client.player, this.upgradeRegistry));
        }
    }

    @Override
    public int getRowWidth() {
        return 240;
    }

    protected void renderDecorations(GuiGraphics context, int mouseX, int mouseY) {
        if (mouseY >= this.getY() && mouseY <= this.getBottom()) {
            UpgradeEntry entry = this.getHovered();
            if (entry != null) {
                if (mouseX < this.getRowLeft() || mouseX > this.getRowLeft() + 18) {
                    return;
                }
                Upgrade upgrade = entry.getUpgrade();
                List<Component> tooltip = List.of(upgrade.properties().name(), Component.literal(this.upgradeRegistry.getKey(upgrade).toString()).withStyle(ChatFormatting.DARK_GRAY));
                context.setComponentTooltipForNextFrame(this.minecraft.font, tooltip, mouseX, mouseY);
            }
        }
    }

    public void update() {
        this.children().forEach(UpgradeEntry::update);
    }

    @Override
    public void renderWidget(GuiGraphics guiGraphics, int i, int j, float f) {
        super.renderWidget(guiGraphics, i, j, f);
        this.renderDecorations(guiGraphics, i, j);
    }

    public class UpgradeEntry extends Entry<UpgradeEntry> {
        protected static final Identifier SLOT_TEXTURE = Identifier.withDefaultNamespace("container/slot");
        private final Upgrade upgrade;
        private final Player player;
        private final Button buyButton;
        private final Button sellButton;
        private int ticksSincePurchase = 0;
        //private final NarratedMultilineTextWidget text;

        UpgradeEntry(Upgrade upgrade, Player player, Registry<Upgrade> upgradeRegistry) {
            this.upgrade = upgrade;
            this.player = player;
            this.buyButton = Button.builder(Component.literal("+"), button -> {
                ClientPlayNetworking.send(new BuyUpgradePacket(upgradeRegistry.getId(this.upgrade)));
                this.ticksSincePurchase = 0;
                button.active = false;
                UpgradesListWidget.this.update();
            }).bounds(50, 50, 20, 20).build();
            this.sellButton = Button.builder(Component.literal("-"), button -> {
                ClientPlayNetworking.send(new SellUpgradePacket(upgradeRegistry.getId(this.upgrade)));
                player.makeSound(SoundEvents.AMETHYST_BLOCK_BREAK);
                UpgradesListWidget.this.update();
            }).bounds(30, 50, 20, 20).build();
            //this.text = new NarratedMultilineTextWidget(150, Text.literal(StatFormatter.DEFAULT.format(PriceObject.getCurrentPrice(this.upgrade, this.player))), UpgradesListWidget.this.client.textRenderer, true, 3);
            //this.text.setCentered(true);
            //this.text.setTextColor(Colors.BLACK);
            this.update();
        }

        @Override
        public void renderContent(GuiGraphics guiGraphics, int mouseX, int mouseY, boolean hovered, float f) {
            final int x = this.getX();
            final int y = this.getY();
            guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, SLOT_TEXTURE, x + 1, y + 1, 18, 18);
            guiGraphics.renderFakeItem(this.upgrade.properties().icon().value().getDefaultInstance(), x + 2, y + 2);
            this.buyButton.setPosition(x + 205, y);
            this.sellButton.setPosition(x + 80, y);
            final int amountPurchased = PooSavedData.Client.INSTANCE.upgradePurchaseAmount(this.player, this.upgrade.getKey(UpgradesListWidget.this.upgradeRegistry));
            String upgradePrice = "$%,.2f".formatted(this.upgrade.properties().price().getFinalPrice(amountPurchased));
            String upgradePriceMinusOne = "$%,.2f".formatted(this.upgrade.properties().price().getFinalPrice(amountPurchased - 1));
            int offset = UpgradesListWidget.this.minecraft.font.width(upgradePrice) / 2;
            if (amountPurchased > 0) {
                Tooltip tooltip = Tooltip.create(Component.literal("+" + upgradePriceMinusOne));
                this.sellButton.setTooltip(tooltip);
            } else {
                this.sellButton.setTooltip(null);
            }
            if (!canBeBought(this.upgrade, this.player)) {
                this.buyButton.setTooltip(Tooltip.create(Component.literal("Out of stock or insufficient funds!")));
            } else if (this.upgrade.properties().maxPurchases().isPresent()) {
                String tooltip = String.format("%s/%s", amountPurchased, this.upgrade.properties().maxPurchases().get());
                this.buyButton.setTooltip(Tooltip.create(Component.literal(tooltip)));
            } else {
                this.buyButton.setTooltip(null);
            }
            //context.fill(x - (offset * 2) + 9, y + 3, x + 103 + offset, y + 16, Colors.WHITE);
            //context.fill(x - (offset * 2) + 10, y + 4, x + 102 + offset, y + 15, Colors.BLACK);
            //this.text.setPosition(x + 100 - offset, y + 5);
            //this.text.render(context, mouseX, mouseY, tickDelta);
            boolean useOneLess = this.upgrade.properties().maxPurchases().orElse(Integer.MAX_VALUE) <= amountPurchased;
            guiGraphics.drawString(UpgradesListWidget.this.minecraft.font, Component.literal(useOneLess ? upgradePriceMinusOne : upgradePrice), x + 152 - offset, y + 6, CommonColors.WHITE);
            guiGraphics.drawCenteredString(UpgradesListWidget.this.minecraft.font, StatFormatter.DEFAULT.format(amountPurchased), x + 50, y + 6, CommonColors.WHITE);
            this.buyButton.render(guiGraphics, mouseX, mouseY, f);
            this.sellButton.render(guiGraphics, mouseX, mouseY, f);
            guiGraphics.drawCenteredString(UpgradesListWidget.this.minecraft.font, "0", UpgradesListWidget.this.width / 2, 10, CommonColors.WHITE);
            this.update();
        }

        public Upgrade getUpgrade() {
            return this.upgrade;
        }

        protected boolean canBeSold(Upgrade upgrade, Player player) {
            if (PooSavedData.Client.INSTANCE.upgradePurchaseAmount(player, this.upgrade.getKey(UpgradesListWidget.this.upgradeRegistry)) <= 0) return false;
            return upgrade.properties().canBeSold();
        }

        // client side check :trollface:
        protected boolean canBeBought(Upgrade upgrade, Player player) {
            int amountPurchased = PooSavedData.Client.INSTANCE.upgradePurchaseAmount(player, upgrade.getKey(UpgradesListWidget.this.upgradeRegistry));
            double upgradePrice = upgrade.properties().price().getFinalPrice(amountPurchased);
            if (PooSavedData.Client.INSTANCE.getBalance(player) < upgradePrice) return false;
            return PooSavedData.Client.INSTANCE.upgradePurchaseAmount(player, upgrade.getKey(UpgradesListWidget.this.upgradeRegistry)) < upgrade.properties().maxPurchases().orElse(Integer.MAX_VALUE);
        }

        protected void update() {
            this.sellButton.active = canBeSold(this.upgrade, this.player) && this.ticksSincePurchase >= 120;
            this.buyButton.active = canBeBought(this.upgrade,this.player) && this.ticksSincePurchase >= 120;
            this.ticksSincePurchase++;
            //this.text.setCentered(true);
            //this.text.setTextColor(Colors.BLACK);
        }

        @Override
        public List<? extends NarratableEntry> narratables() {
            return List.of(this.buyButton, this.sellButton);
        }

        @Override
        public List<? extends GuiEventListener> children() {
            return List.of(this.buyButton, this.sellButton);
        }
    }
}
