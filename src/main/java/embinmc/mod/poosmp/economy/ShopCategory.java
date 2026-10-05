package embinmc.mod.poosmp.economy;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import embinmc.mod.poosmp.misc.PooUtil;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.resources.RegistryFixedCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class ShopCategory {
    public static final MapCodec<ShopCategory> MAP_CODEC = RecordCodecBuilder.mapCodec(sc -> sc.group(
            ComponentSerialization.CODEC.fieldOf("display_name").forGetter(ShopCategory::displayName),
            RegistryFixedCodec.create(Registries.ITEM).fieldOf("display_icon").forGetter(s -> s.itemIcon),
            Codec.unboundedMap(PooUtil.STRICT_ITEM_SET_CODEC, ItemValue.CODEC).fieldOf("price_map").forGetter(s -> s.priceMap)
    ).apply(sc, ShopCategory::new));

    protected final Component displayName;
    protected final Holder<Item> itemIcon;
    protected final Map<HolderSet<Item>, ItemValue> priceMap;
    protected final Map<Holder<Item>, ItemValue> priceCache = HashMap.newHashMap(256);

    public ShopCategory(Component displayName, Holder<Item> itemIcon, Map<HolderSet<Item>, ItemValue> priceMap) {
        this.displayName = displayName;
        this.itemIcon = itemIcon;
        this.priceMap = priceMap;
    }

    public Component displayName() {
        return this.displayName;
    }

    public @Nullable ItemValue getItemValue(Holder<Item> itemHolder) {
        ItemValue value = this.priceCache.computeIfAbsent(itemHolder, item -> {
            for (HolderSet<Item> items: this.priceMap.keySet()) {
                if (items.contains(itemHolder))
                    return this.priceMap.get(items);
            }
            return null;
        });
        // it won't be put in automatically if it's null
        if (!this.priceCache.containsKey(itemHolder) && value == null)
            this.priceCache.put(itemHolder, null);
        return value;
    }

    public @Nullable ItemValue getItemValue(ItemStack stack) {
        return this.getItemValue(stack.getItemHolder());
    }

    public @Nullable ItemValue getItemValue(Item item) {
        return this.getItemValue(item.builtInRegistryHolder());
    }
}
