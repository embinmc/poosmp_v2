package embinmc.mod.poosmp.client.screen;

import embinmc.mod.poosmp.economy.ItemValue;
import embinmc.mod.poosmp.economy.ShopCategories;
import embinmc.mod.poosmp.economy.ShopCategory;
import embinmc.mod.poosmp.misc.PooRegistries;
import embinmc.mod.poosmp.networking.c2s.PurchaseItemPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.CommonColors;
import net.minecraft.world.item.Item;
import org.jspecify.annotations.NonNull;

import java.util.List;

public class ShopScreen extends Screen {
    protected HeaderAndFooterLayout layout;
    protected BalanceWidget widget;

    public ShopScreen() {
        super(Component.literal("Shop"));
    }

    @Override
    protected void init() {
        this.layout = new HeaderAndFooterLayout(this);
        this.widget = new BalanceWidget(4, 16);
        this.layout.addToFooter(Button.builder(Component.literal("Exit"), button -> this.onClose()).width(160).build());
        this.layout.addTitleHeader(this.title, this.font);
        if (this.minecraft.player != null) {
            RegistryAccess registries = this.minecraft.player.registryAccess();
            this.layout.addToContents(new ShopCategoryWidget(this.minecraft, registries));
        }
        this.layout.visitWidgets(this::addRenderableWidget);
        this.addRenderableWidget(this.widget);
        this.layout.arrangeElements();
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void render(@NonNull GuiGraphics guiGraphics, int i, int j, float f) {
        this.widget.setX(guiGraphics.guiWidth() / 2);
        this.widget.setY(22);
        super.render(guiGraphics, i, j, f);
    }

    private class ShopCategoryWidget extends ContainerObjectSelectionList<ShopCategoryWidget.AbstractEntry> {
        private final RegistryAccess registries;

        public ShopCategoryWidget(Minecraft minecraftClient, RegistryAccess registries) {
            super(minecraftClient, ShopScreen.this.width, ShopScreen.this.layout.getContentHeight(), ShopScreen.this.layout.getHeaderHeight(), 24);
            this.registries = registries;
            for (Holder<ShopCategory> shopCategoryHolder : this.registries.lookupOrThrow(PooRegistries.Keys.SHOP_CATEGORY).asHolderIdMap()) {
                if (shopCategoryHolder.is(ShopCategories.BANK_NOTES))
                    continue;
                this.addEntry(new ShopCategoryEntry(shopCategoryHolder));
                for (HolderSet<Item> items : shopCategoryHolder.value().keys()) {
                    for (Holder<Item> item : items) {
                        this.addEntry(new ShopEntry(shopCategoryHolder, item));
                    }
                }
            }
        }

        @Override
        public int getRowWidth() {
            return 240;
        }

        @Override
        public void renderWidget(@NonNull GuiGraphics guiGraphics, int mouseX, int mouseY, float f) {
            super.renderWidget(guiGraphics, mouseX, mouseY, f);
            AbstractEntry hovered = this.getHovered();
            if (hovered == null)
                return;
            if (mouseX < this.getRowLeft() || mouseX > this.getRowLeft() + 32)
                return;
            if (hovered instanceof ShopEntry shopEntry) {
                Component name = shopEntry.item.value().getName();
                Component id = Component.literal(shopEntry.item.getRegisteredName()).withStyle(ChatFormatting.DARK_GRAY);
                guiGraphics.setComponentTooltipForNextFrame(this.minecraft.font, List.of(name, id), mouseX, mouseY);
            }
        }

        private abstract class AbstractEntry extends ContainerObjectSelectionList.Entry<ShopCategoryWidget.AbstractEntry> {
            protected final RegistryAccess registries() {
                return ShopCategoryWidget.this.registries;
            }
            protected final Font font() {
                return ShopCategoryWidget.this.minecraft.font;
            }
            protected final int fromCenter(GuiGraphics guiGraphics, int offset) {
                return (guiGraphics.guiWidth() / 2) + offset;
            }
        }

        private class ShopCategoryEntry extends AbstractEntry {
            private final Holder<ShopCategory> shopCategory;

            public ShopCategoryEntry(Holder<ShopCategory> shopCategory) {
                this.shopCategory = shopCategory;
                this.setHeight(8);
            }

            @Override
            public @NonNull List<? extends NarratableEntry> narratables() {
                return List.of();
            }

            @Override
            public @NonNull List<? extends GuiEventListener> children() {
                return List.of();
            }

            @Override
            public void renderContent(GuiGraphics guiGraphics, int mouseX, int mouseY, boolean hovered, float f) {
                guiGraphics.drawCenteredString(this.font(), this.shopCategory.value().displayName(), guiGraphics.guiWidth() / 2, this.getY() + 10, CommonColors.WHITE);
            }
        }

        private class ShopEntry extends AbstractEntry {
            protected static final Identifier SLOT_TEXTURE = Identifier.withDefaultNamespace("container/slot");
            private final Holder<ShopCategory> shopCategory;
            private final Holder<Item> item;
            private final Button buyOne, buyStack;

            public ShopEntry(Holder<ShopCategory> shopCategory, Holder<Item> item) {
                this.shopCategory = shopCategory;
                this.item = item;
                this.setHeight(4);
                this.buyOne = Button.builder(
                    Component.literal("Buy 1"),
                    button -> {
                        int itemId = this.registries().lookupOrThrow(Registries.ITEM).getId(this.item.value());
                        int shopId = this.registries().lookupOrThrow(PooRegistries.Keys.SHOP_CATEGORY).getId(this.shopCategory.value());
                        ClientPlayNetworking.send(new PurchaseItemPacket(shopId, itemId, (byte) 1));
                    }
                ).pos(this.getX(), this.getY()).size(48, 16).build();
                this.buyStack = Button.builder(
                    Component.literal("Buy 64"),
                    button -> {
                        int itemId = this.registries().lookupOrThrow(Registries.ITEM).getId(this.item.value());
                        int shopId = this.registries().lookupOrThrow(PooRegistries.Keys.SHOP_CATEGORY).getId(this.shopCategory.value());
                        ClientPlayNetworking.send(new PurchaseItemPacket(shopId, itemId, (byte) 64));
                    }
                ).pos(this.getX(), this.getY()).size(48, 16).build();
            }

            @Override
            public @NonNull List<? extends NarratableEntry> narratables() {
                return List.of();
            }

            @Override
            public @NonNull List<? extends GuiEventListener> children() {
                return List.of(this.buyOne, this.buyStack);
            }

            @Override
            public void renderContent(GuiGraphics guiGraphics, int mouseX, int mouseY, boolean hovered, float f) {
                // dddd
                this.buyOne.setPosition(this.fromCenter(guiGraphics, 4), this.getY() + 4);
                this.buyStack.setPosition(this.fromCenter(guiGraphics, 64), this.getY() + 4);
                guiGraphics.fill(this.getX(), this.getY() + 1, this.getX() + this.getWidth(), this.getY() + this.getHeight() - 1, 0x44FFAAFF);
                guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, SLOT_TEXTURE, this.fromCenter(guiGraphics, -112) - 1, this.getY() + 3, 18, 18);
                guiGraphics.renderFakeItem(this.item.value().getDefaultInstance(), this.fromCenter(guiGraphics, -112), this.getY() + 4);
                ItemValue value = this.shopCategory.value().getItemValue(this.item);
                guiGraphics.drawCenteredString(this.font(), "$%,.2f".formatted(value == null ? -1 : value.price()), this.fromCenter(guiGraphics, -48), this.getY() + 8, CommonColors.WHITE);
                this.buyOne.render(guiGraphics, mouseX, mouseY, f);
                this.buyStack.render(guiGraphics, mouseX, mouseY, f);
            }
        }
    }
}
