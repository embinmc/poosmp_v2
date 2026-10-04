package embinmc.mod.poosmp.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.fabricmc.fabric.api.event.player.ItemEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import org.jspecify.annotations.Nullable;

public final class PooItemEvents {
    private PooItemEvents() {
        throw new IllegalStateException("Cannot create instance of PooItemEvents");
    }

    /// Called before the item's [Item#inventoryTick(ItemStack, ServerLevel, Entity, EquipmentSlot)] method is called,
    /// and can cancel it and the [#INVENTORY_TICK_POST_ITEM] event.
    public static final Event<PreInvItemTick> INVENTORY_TICK_PRE_ITEM = EventFactory.createArrayBacked(PreInvItemTick.class,
            l -> (itemStack, serverLevel, entity, slot) -> {
                for (PreInvItemTick listener : l) {
                    if (!listener.onTick(itemStack, serverLevel, entity, slot))
                        return false;
                }
                return true;
            }
    );

    /// Called after the item's [Item#inventoryTick(ItemStack, ServerLevel, Entity, EquipmentSlot)] method is called.
    public static final Event<PostInvItemTick> INVENTORY_TICK_POST_ITEM = EventFactory.createArrayBacked(PostInvItemTick.class,
            l -> (itemStack, serverLevel, entity, slot) -> {
                for (PostInvItemTick listener : l) {
                    if (!listener.onTick(itemStack, serverLevel, entity, slot))
                        return false;
                }
                return true;
            }
    );

    public static final Event<LivingEntityInteraction> INTERACT_LIVING_ENTITY = EventFactory.createArrayBacked(LivingEntityInteraction.class,
            (listeners) -> (level, player, entity, interactionHand) -> {
                for (LivingEntityInteraction event : listeners) {
                    InteractionResult result = event.interact(level, player, entity, interactionHand);

                    if (result != null) {
                        return result;
                    }
                }

                return null;
            }
    );

    public interface PreInvItemTick {
        boolean onTick(ItemStack itemStack, ServerLevel serverLevel, Entity entity, @Nullable EquipmentSlot slot);
    }

    public interface PostInvItemTick {
        boolean onTick(ItemStack itemStack, ServerLevel serverLevel, Entity entity, @Nullable EquipmentSlot slot);
    }

    public interface LivingEntityInteraction {
        @Nullable InteractionResult interact(ItemStack stack, Player player, LivingEntity entity, InteractionHand hand);
    }
}
