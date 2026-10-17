package embinmc.mod.poosmp.client.screen;

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
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import net.minecraft.util.CommonColors;
import net.minecraft.util.Util;
import net.minecraft.world.level.biome.Biome;
import org.jspecify.annotations.NonNull;

import java.util.List;

public class BiomeStickScreen extends Screen {
    protected final HeaderAndFooterLayout layout = new HeaderAndFooterLayout(this);

    public BiomeStickScreen() {
        super(Component.literal("Biome Stick"));
    }

    @Override
    protected void init() {
        super.init();
        this.layout.addToFooter(Button.builder(Component.literal("Exit"), button -> this.onClose()).width(160).build());
        this.layout.setHeaderHeight(64);
        this.layout.addTitleHeader(this.title, this.font);
        if (this.minecraft.player != null) {
            RegistryAccess registries = this.minecraft.player.registryAccess();
            this.layout.addToContents(new BiomeStickWidget(this.minecraft, registries));
        }
        this.layout.visitWidgets(this::addRenderableWidget);
        this.repositionElements();
    }

    @Override
    protected void repositionElements() {
        this.layout.arrangeElements();
    }

    private class BiomeStickWidget extends ContainerObjectSelectionList<BiomeStickScreen.BiomeStickWidget.AbstractEntry> {
        private final RegistryAccess registries;

        public BiomeStickWidget(Minecraft minecraftClient, RegistryAccess registries) {
            super(minecraftClient, BiomeStickScreen.this.width, BiomeStickScreen.this.layout.getContentHeight(), BiomeStickScreen.this.layout.getHeaderHeight(), 24);
            this.registries = registries;
            for (Holder<Biome> biomeHolder : this.registries.lookupOrThrow(Registries.BIOME).asHolderIdMap()) {
                this.addEntry(new BiomeStickScreen.BiomeStickWidget.BiomeStickEntry(biomeHolder));
            }
        }

        @Override
        public int getRowWidth() {
            return 240;
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
        }

        private class BiomeStickEntry extends BiomeStickScreen.BiomeStickWidget.AbstractEntry {
            private final Holder<Biome> biomeHolder;

            public BiomeStickEntry(Holder<Biome> b) {
                this.biomeHolder = b;
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
                // aa
                //a fdw
                Identifier biomeId = this.biomeHolder.unwrapKey().orElseThrow().identifier();
                Component name = Component.translatable(Util.makeDescriptionId("biome", biomeId)).withColor(ARGB.setBrightness(biomeId.getNamespace().hashCode(), 1f));
                Component id = Component.literal(this.biomeHolder.getRegisteredName()).withStyle(ChatFormatting.DARK_GRAY);
                guiGraphics.drawCenteredString(this.font(), name, guiGraphics.guiWidth() / 2, this.getY() + 4, CommonColors.WHITE);
                guiGraphics.drawCenteredString(this.font(), id, guiGraphics.guiWidth() / 2, this.getY() + 14, CommonColors.WHITE);
            }
        }
    }
}
