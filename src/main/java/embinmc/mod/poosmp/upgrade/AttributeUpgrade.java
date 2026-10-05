package embinmc.mod.poosmp.upgrade;

import com.mojang.serialization.MapCodec;
import embinmc.mod.poosmp.PooSMPMod;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.component.ItemAttributeModifiers;

import java.util.ArrayList;
import java.util.List;

public class AttributeUpgrade extends AbstractUpgrade {
    public static final MapCodec<AttributeUpgrade> MAP_CODEC = simpleCodec(
            AttributeUpgrade::new,
            ItemAttributeModifiers.CODEC.fieldOf("attributes").forGetter(a -> a.modifiers)
    );
    public static final UpgradeType<AttributeUpgrade> TYPE = new UpgradeType<>("AttributeUpgrade", MAP_CODEC);
    protected final ItemAttributeModifiers modifiers;

    protected AttributeUpgrade(Properties properties, ItemAttributeModifiers modifiers) {
        super(properties);
        this.modifiers = modifiers;
    }

    @Override
    public void onSell(int amountPurchased, ServerPlayer player, ServerLevel level, MinecraftServer server) {
        List<ItemAttributeModifiers.Entry> alive = this.getAttributesForAmount(amountPurchased - 1);
        List<ItemAttributeModifiers.Entry> dead = this.getAttributesForAmount(amountPurchased).stream().filter(alive::contains).toList();
        for (ItemAttributeModifiers.Entry entry : dead) {
            this.removeAttribute(player, entry);
        }
    }

    @Override
    public void onTick(int amountPurchased, ServerPlayer player, ServerLevel level, MinecraftServer server) {
        for (ItemAttributeModifiers.Entry entry : this.getAttributesForAmount(amountPurchased)) {
            this.addAttribute(player, entry);
        }
    }

    @Override
    public void onRespawn(int amountPurchased, ServerPlayer player, ServerLevel level, MinecraftServer server) {}

    @Override
    public void onBuy(int amountPurchased, ServerPlayer player, ServerLevel level, MinecraftServer server) {}

    @Override
    public UpgradeType<?> getType() {
        return TYPE;
    }

    protected void addAttribute(ServerPlayer player, ItemAttributeModifiers.Entry entry) {
        if (player.getAttributes().hasModifier(entry.attribute(), entry.modifier().id()))
            return;
        AttributeInstance instance = player.getAttributes().getInstance(entry.attribute());
        if (instance == null)
            return;
        instance.addTransientModifier(entry.modifier());
        Identifier attributeId = entry.attribute().unwrapKey().orElseThrow().identifier();
        PooSMPMod.LOGGER.info("Added attribute {}/{} to {}", attributeId, entry.modifier().id(), player.getPlainTextName());
    }

    protected void removeAttribute(ServerPlayer player, ItemAttributeModifiers.Entry entry) {
        if (!player.getAttributes().hasModifier(entry.attribute(), entry.modifier().id()))
            return;
        AttributeInstance instance = player.getAttributes().getInstance(entry.attribute());
        if (instance == null)
            return;
        instance.removeModifier(entry.modifier().id());
        Identifier attributeId = entry.attribute().unwrapKey().orElseThrow().identifier();
        PooSMPMod.LOGGER.info("Removed attribute {}/{} from {}", attributeId, entry.modifier().id(), player.getPlainTextName());
    }

    protected List<ItemAttributeModifiers.Entry> getAttributesForAmount(int amount) {
        if (amount < 0) return List.of();
        List<ItemAttributeModifiers.Entry> attributes = new ArrayList<>(amount * this.modifiers.modifiers().size());
        for (ItemAttributeModifiers.Entry attributeEntry : this.modifiers.modifiers()) {
            //int amountPerLevel = upgradeRegistry.getEntry(this).isIn(PooSMPTags.Upgrades.DOUBLE_ATTRIBUTE_GIVE) ? 2 : 1;
            Identifier id = attributeEntry.modifier().id();
            attributes.addAll(UpgradeAttributeModifiersEntry.of(attributeEntry.attribute(), attributeEntry.modifier().amount()).build(id, amount));
        }
        return attributes;
    }

    protected record UpgradeAttributeModifiersEntry(Holder<Attribute> attribute, double amountPerLevel) {
        public static UpgradeAttributeModifiersEntry of(Holder<Attribute> attribute, double amountPerLevel) {
            return new UpgradeAttributeModifiersEntry(attribute, amountPerLevel);
        }

        public List<ItemAttributeModifiers.Entry> build(Identifier upgrade, int amountPurchased) {
            List<ItemAttributeModifiers.Entry> attributes = new ArrayList<>(amountPurchased);
            for (int i = 1; i <= amountPurchased; i++) {
                attributes.add(new ItemAttributeModifiers.Entry(
                        this.attribute,
                        new AttributeModifier(upgrade.withSuffix("_" + i), this.amountPerLevel, AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.ANY
                ));
            }
            return attributes;
        }
    }
}
