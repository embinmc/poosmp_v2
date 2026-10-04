package embinmc.mod.poosmp.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import embinmc.mod.poosmp.PooEntityTags;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Shadow public abstract EntityType<?> getType();

    @ModifyReturnValue(method = "isInvulnerableToBase", at = @At("RETURN"))
    private boolean poosmp$allowImmuneToExplosion(boolean original, @Local(argsOnly = true) DamageSource damageSource) {
        return original || (damageSource.is(DamageTypeTags.IS_EXPLOSION) && this.getType().is(PooEntityTags.IMMUNE_TO_EXPLOSIONS));
    }
}
