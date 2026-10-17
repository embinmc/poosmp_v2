package embinmc.mod.poosmp.client.screen;

import embinmc.mod.poosmp.item.component.BiomeStickComponent;
import embinmc.mod.poosmp.item.component.PooComponents;
import embinmc.mod.poosmp.networking.c2s.ModifyBiomeStickPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ActiveTextCollector;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import net.minecraft.util.CommonColors;
import net.minecraft.util.Mth;
import net.minecraft.util.Util;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.biome.Biome;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.Objects;

public class BiomeStickScreen extends Screen {
    protected HeaderAndFooterLayout layout;
    protected Button hAddOne, hAddFive, hAddTen;
    protected Button hRemoveOne, hRemoveFive, hRemoveTen;
    protected Button vAddOne, vAddFive, vAddTen;
    protected Button vRemoveOne, vRemoveFive, vRemoveTen;

    public BiomeStickScreen() {
        super(Component.literal("Biome Stick"));
    }

    protected Button getButton(boolean horizontal, int change) {
        return Button.builder(Component.literal((change >= 0 ? "+" : "") + change), button -> {
            int type = horizontal ? ModifyBiomeStickPacket.TYPE_HORIZONTAL_DIAMETER : ModifyBiomeStickPacket.TYPE_VERTICAL_DIAMETER;
            ItemStack heldItem = Objects.requireNonNull(this.minecraft.player).getMainHandItem();
            BiomeStickComponent component = Objects.requireNonNull(heldItem.get(PooComponents.BIOME_TRANSFORMER));
            int base = horizontal ? component.horizontalDiameter() : component.verticalDiameter();
            ModifyBiomeStickPacket packet = new ModifyBiomeStickPacket(type, Math.clamp(base + change, 1, 127));
            ClientPlayNetworking.send(packet);
        }).size(24, 18).build();
    }

    @Override
    protected void init() {
        super.init();
        if (!this.minecraft.player.getMainHandItem().has(PooComponents.BIOME_TRANSFORMER)) {
            this.onClose();
            return;
        }
        this.layout = new HeaderAndFooterLayout(this);
        this.layout.addToFooter(Button.builder(Component.literal("Exit"), button -> this.onClose()).width(160).build());
        this.layout.addTitleHeader(this.title, this.font);
        if (this.minecraft.player != null) {
            RegistryAccess registries = this.minecraft.player.registryAccess();
            this.layout.addToContents(new BiomeStickWidget(this.minecraft, registries), layoutSettings -> {
                layoutSettings.align(0.0f, 0.5f);
                layoutSettings.paddingHorizontal(10);
            });
        }
        this.layout.visitWidgets(this::addRenderableWidget);
        this.layout.arrangeElements();
        this.layout.setX(-50);

        this.hAddOne = this.getButton( true, 1);
        this.hAddFive = this.getButton(true, 5);
        this.hAddTen = this.getButton( true, 10);

        this.hRemoveOne = this.getButton( true, -1);
        this.hRemoveFive = this.getButton(true, -5);
        this.hRemoveTen = this.getButton( true, -10);

        this.vAddOne = this.getButton( false, 1);
        this.vAddFive = this.getButton(false, 5);
        this.vAddTen = this.getButton( false, 10);

        this.vRemoveOne = this.getButton( false, -1);
        this.vRemoveFive = this.getButton(false, -5);
        this.vRemoveTen = this.getButton( false, -10);

        this.addWidget(this.hAddOne);
        this.addWidget(this.hAddFive);
        this.addWidget(this.hAddTen);
        this.addWidget(this.hRemoveOne);
        this.addWidget(this.hRemoveFive);
        this.addWidget(this.hRemoveTen);

        this.addWidget(this.vAddOne);
        this.addWidget(this.vAddFive);
        this.addWidget(this.vAddTen);
        this.addWidget(this.vRemoveOne);
        this.addWidget(this.vRemoveFive);
        this.addWidget(this.vRemoveTen);
    }

    @Override
    public void render(@NonNull GuiGraphics guiGraphics, int mouseX, int mouseY, float f) {
        super.render(guiGraphics, mouseX, mouseY, f);
        ItemStack heldItem = Objects.requireNonNull(this.minecraft.player).getMainHandItem();
        BiomeStickComponent component = Objects.requireNonNull(heldItem.get(PooComponents.BIOME_TRANSFORMER));

        int buttonAnchorX = Mth.floor(guiGraphics.guiWidth() * 0.70f);
        int buttonAnchorYH = Mth.floor(guiGraphics.guiHeight() * 0.35f);
        int buttonAnchorYV = Mth.floor(guiGraphics.guiHeight() * 0.65f);
        int buttonXPad = 25;

        this.hAddOne.setPosition (buttonAnchorX - buttonXPad, buttonAnchorYH - 10);
        this.hAddFive.setPosition(buttonAnchorX, buttonAnchorYH - 10);
        this.hAddTen.setPosition (buttonAnchorX + buttonXPad, buttonAnchorYH - 10);
        this.hRemoveOne.setPosition (buttonAnchorX - buttonXPad, buttonAnchorYH + 10);
        this.hRemoveFive.setPosition(buttonAnchorX, buttonAnchorYH + 10);
        this.hRemoveTen.setPosition (buttonAnchorX + buttonXPad, buttonAnchorYH + 10);

        this.vAddOne.setPosition (buttonAnchorX - buttonXPad, buttonAnchorYV - 10);
        this.vAddFive.setPosition(buttonAnchorX, buttonAnchorYV - 10);
        this.vAddTen.setPosition (buttonAnchorX + buttonXPad, buttonAnchorYV - 10);
        this.vRemoveOne.setPosition (buttonAnchorX - buttonXPad, buttonAnchorYV + 10);
        this.vRemoveFive.setPosition(buttonAnchorX, buttonAnchorYV + 10);
        this.vRemoveTen.setPosition (buttonAnchorX + buttonXPad, buttonAnchorYV + 10);

        this.hAddOne.render(guiGraphics, mouseX, mouseY, f);
        this.hAddFive.render(guiGraphics, mouseX, mouseY, f);
        this.hAddTen.render(guiGraphics, mouseX, mouseY, f);
        this.hRemoveOne.render(guiGraphics, mouseX, mouseY, f);
        this.hRemoveFive.render(guiGraphics, mouseX, mouseY, f);
        this.hRemoveTen.render(guiGraphics, mouseX, mouseY, f);

        this.vAddOne.render(guiGraphics, mouseX, mouseY, f);
        this.vAddFive.render(guiGraphics, mouseX, mouseY, f);
        this.vAddTen.render(guiGraphics, mouseX, mouseY, f);
        this.vRemoveOne.render(guiGraphics, mouseX, mouseY, f);
        this.vRemoveFive.render(guiGraphics, mouseX, mouseY, f);
        this.vRemoveTen.render(guiGraphics, mouseX, mouseY, f);

        guiGraphics.drawCenteredString(this.font, "Horizontal Diameter: %,d".formatted(component.horizontalDiameter()), buttonAnchorX + 12, buttonAnchorYH - 24, CommonColors.WHITE);
        guiGraphics.drawCenteredString(this.font, "Vertical Diameter: %,d".formatted(component.verticalDiameter()), buttonAnchorX + 12, buttonAnchorYV - 24, CommonColors.WHITE);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public boolean isInGameUi() {
        return true;
    }

    protected boolean getActiveState(boolean horizontal, boolean greaterThan, int max, BiomeStickComponent component, int change) {
        int current = horizontal ? component.horizontalDiameter() : component.verticalDiameter();
        return greaterThan ? (current + change) >= max : (current + change) <= max;
    }

    @Override
    public void tick() {
        if (this.minecraft.player == null) {
            this.onClose();
            return;
        }
        if (!this.minecraft.player.getMainHandItem().has(PooComponents.BIOME_TRANSFORMER)) {
            this.onClose();
        }
        super.tick();
        ItemStack heldItem = this.minecraft.player.getMainHandItem();
        BiomeStickComponent component = Objects.requireNonNull(heldItem.get(PooComponents.BIOME_TRANSFORMER));
        this.hAddOne.active =  this.getActiveState(true, false, 80, component, 1);
        this.hAddFive.active = this.getActiveState(true, false, 80, component, 5);
        this.hAddTen.active =  this.getActiveState(true, false, 80, component, 10);
        this.hRemoveOne.active =  this.getActiveState(true, true, 1, component, -1);
        this.hRemoveFive.active = this.getActiveState(true, true, 1, component, -5);
        this.hRemoveTen.active =  this.getActiveState(true, true, 1, component, -10);

        this.vAddOne.active =  this.getActiveState(false, false, 80, component, 1);
        this.vAddFive.active = this.getActiveState(false, false, 80, component, 5);
        this.vAddTen.active =  this.getActiveState(false, false, 80, component, 10);
        this.vRemoveOne.active =  this.getActiveState(false, true, 1, component, -1);
        this.vRemoveFive.active = this.getActiveState(false, true, 1, component, -5);
        this.vRemoveTen.active =  this.getActiveState(false, true, 1, component, -10);
    }

    private class BiomeStickWidget extends ContainerObjectSelectionList<BiomeStickScreen.BiomeStickWidget.AbstractEntry> {
        private final RegistryAccess registries;

        public BiomeStickWidget(Minecraft minecraftClient, RegistryAccess registries) {
            super(minecraftClient, (BiomeStickScreen.this.width / 2) - 10, BiomeStickScreen.this.layout.getContentHeight(), BiomeStickScreen.this.layout.getHeaderHeight(), 24);
            this.registries = registries;
            for (Holder<Biome> biomeHolder : this.registries.lookupOrThrow(Registries.BIOME).asHolderIdMap()) {
                this.addEntry(new BiomeStickScreen.BiomeStickWidget.BiomeStickEntry(biomeHolder));
            }
        }

        @Override
        public int getRowWidth() {
            return this.getWidth() - 29;
        }

        private abstract class AbstractEntry extends ContainerObjectSelectionList.Entry<BiomeStickScreen.BiomeStickWidget.AbstractEntry> {
            protected final RegistryAccess registries() {
                return BiomeStickScreen.BiomeStickWidget.this.registries;
            }
            protected final Font font() {
                return BiomeStickScreen.BiomeStickWidget.this.minecraft.font;
            }
            protected final int fromCenter(GuiGraphics guiGraphics, int offset) {
                return (guiGraphics.guiWidth() / 2) + offset;
            }
            protected final ItemStack heldItem() {
                return Objects.requireNonNull(BiomeStickScreen.this.minecraft.player).getMainHandItem();
            }
        }

        private class BiomeStickEntry extends BiomeStickScreen.BiomeStickWidget.AbstractEntry {
            private final Holder<Biome> biomeHolder;
            private final StringWidget biomeName, biomeId;
            private final Button setBiomeButton;

            public BiomeStickEntry(Holder<Biome> b) {
                this.biomeHolder = b;
                this.setHeight(8);
                Identifier biomeId = this.biomeHolder.unwrapKey().orElseThrow().identifier();
                Component name = Component.translatable(Util.makeDescriptionId("biome", biomeId)).withColor(ARGB.setBrightness(biomeId.getNamespace().hashCode(), 1f));
                Component id = Component.literal(this.biomeHolder.getRegisteredName()).withStyle(ChatFormatting.DARK_GRAY);
                this.biomeName = new StringWidget(0, 0, 72, 10, name, BiomeStickWidget.this.minecraft.font) {
                    @Override
                    protected void renderScrollingStringOverContents(@NonNull ActiveTextCollector activeTextCollector, @NonNull Component component, int i) {
                        // why 2 pixel offset when scrolling
                        super.renderScrollingStringOverContents(activeTextCollector, component, 0);
                    }
                };
                this.biomeId = new StringWidget(0, 0, 72, 10, id, BiomeStickWidget.this.minecraft.font) {
                    @Override
                    protected void renderScrollingStringOverContents(@NonNull ActiveTextCollector activeTextCollector, @NonNull Component component, int i) {
                        // why 2 pixel offset when scrolling
                        super.renderScrollingStringOverContents(activeTextCollector, component, 0);
                    }
                };
                this.setBiomeButton = Button.builder(Component.literal("Set"), button -> {
                    int biome = this.registries().lookupOrThrow(Registries.BIOME).getIdOrThrow(this.biomeHolder.value());
                    ModifyBiomeStickPacket packet = new ModifyBiomeStickPacket(ModifyBiomeStickPacket.TYPE_BIOME, biome);
                    ClientPlayNetworking.send(packet);
                }).size(32, 16).build();
            }

            @Override
            public @NonNull List<? extends NarratableEntry> narratables() {
                return List.of();
            }

            @Override
            public @NonNull List<? extends GuiEventListener> children() {
                return List.of(this.setBiomeButton);
            }

            @Override
            public void renderContent(GuiGraphics guiGraphics, int mouseX, int mouseY, boolean hovered, float f) {
                int pos = 50;
                this.setBiomeButton.setPosition(pos - 36, this.getY() + 5);
                this.setBiomeButton.active = !this.heldItem().get(PooComponents.BIOME_TRANSFORMER).selectedBiome().key().equals(this.biomeHolder.unwrapKey());
                this.biomeName.setX(pos);
                this.biomeName.setY(this.getY() + 4);
                this.biomeName.setMaxWidth((guiGraphics.guiWidth() / 2) - pos - 7, StringWidget.TextOverflow.SCROLLING);
                this.biomeName.render(guiGraphics, mouseX, mouseY, f);
                this.biomeId.setX(pos);
                this.biomeId.setY(this.getY() + 14);
                this.biomeId.setMaxWidth((guiGraphics.guiWidth() / 2) - pos - 7, StringWidget.TextOverflow.SCROLLING);
                this.biomeId.render(guiGraphics, mouseX, mouseY, f);
                this.setBiomeButton.render(guiGraphics, mouseX, mouseY, f);
                //guiGraphics.drawString(this.font(), name, 50, this.getY() + 4, CommonColors.WHITE);
                //guiGraphics.drawString(this.font(), id, 50, this.getY() + 14, CommonColors.WHITE); adwadadadawdaqawdawfdafhbhsyjkrg
            }
        }
    }
}
