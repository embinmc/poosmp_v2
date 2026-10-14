package embinmc.mod.poosmp.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;

public class RiggedBlock extends Block implements ImpersonatingBlock {
    private final Block fakeOf;

    public RiggedBlock(Block block, Properties settings) {
        super(settings);
        this.fakeOf = block;
    }

    @Override
    public @NonNull BlockState playerWillDestroy(Level world, @NonNull BlockPos pos, @NonNull BlockState state, Player player) {
        world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
        if (player.isShiftKeyDown()) return super.playerWillDestroy(world, pos, state, player);

        this.spawnDestroyParticles(world, player, pos, state);
        Vec3 vec3d = pos.getCenter();
        DamageSource dmgSrc = world.damageSources().badRespawnPointExplosion(vec3d);
        world.explode(null, dmgSrc, null, vec3d, 4.0F, true, Level.ExplosionInteraction.BLOCK);
        return state;
    }

    @Override
    protected void onProjectileHit(Level world, @NonNull BlockState state, BlockHitResult hit, Projectile projectile) {
        world.destroyBlock(hit.getBlockPos(), false, projectile.getOwner());
        Vec3 vec3d = hit.getBlockPos().getCenter();
        DamageSource dmgSrc = world.damageSources().badRespawnPointExplosion(vec3d);
        world.explode(projectile, dmgSrc, null, vec3d, 4.0F, true, Level.ExplosionInteraction.BLOCK);
    }

    @Override
    public Block getBlockImpersonatingAs() {
        return this.fakeOf;
    }
}
