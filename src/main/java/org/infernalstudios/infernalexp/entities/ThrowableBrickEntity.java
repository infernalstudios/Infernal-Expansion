package org.infernalstudios.infernalexp.entities;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.Packet;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GlassBlock;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.infernalstudios.infernalexp.init.IEBlocks;
import org.infernalstudios.infernalexp.init.IEEntityTypes;
import org.infernalstudios.infernalexp.init.IESoundEvents;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
public class ThrowableBrickEntity extends ThrowableItemProjectile {

    public ThrowableBrickEntity(EntityType<? extends ThrowableItemProjectile> type, Level worldIn) {
        super(type, worldIn);
    }

    public ThrowableBrickEntity(Level world, LivingEntity livingEntity) {
        super(IEEntityTypes.THROWABLE_BRICK.get(), livingEntity, world);
    }

    public ThrowableBrickEntity(EntityType<? extends ThrowableItemProjectile> type, LivingEntity livingEntity, Level world) {
        super(type, livingEntity, world);
    }

    @Override
    protected Item getDefaultItem() {
        return Items.BRICK;
    }

    protected double getSpeedMultiplier() {
        return 0.79D;
    }

    protected float getDamage() {
        return 2.0F;
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    @ParametersAreNonnullByDefault
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);

        if (!getCommandSenderWorld().isClientSide()) {
            BlockPos pos = result.getBlockPos();
            Block block = getCommandSenderWorld().getBlockState(pos).getBlock();

            if ((block == IEBlocks.QUARTZ_GLASS.get() || block == IEBlocks.QUARTZ_GLASS_PANE.get())) {
                getCommandSenderWorld().playSound(null, pos, IESoundEvents.QUARTZ_GLASS_TYPE.getHitSound(), SoundSource.BLOCKS, 1.0F, 1.0F);
            }

            if ((block instanceof GlassBlock || block instanceof IronBarsBlock) && !(block == Blocks.IRON_BARS || block == IEBlocks.QUARTZ_GLASS.get() || block == IEBlocks.QUARTZ_GLASS_PANE.get())) {
                getCommandSenderWorld().destroyBlock(pos, false);
                setDeltaMovement(getDeltaMovement().scale(getSpeedMultiplier()));

            } else {
                ItemEntity itemEntity = new ItemEntity(getCommandSenderWorld(), getX(), getY(), getZ(), getDefaultItem().getDefaultInstance());

                getCommandSenderWorld().addFreshEntity(itemEntity);
                remove(RemovalReason.DISCARDED);
            }
        }
    }

    @Override
    @ParametersAreNonnullByDefault
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);

        if (!getCommandSenderWorld().isClientSide()) {
            Entity target = result.getEntity();
            target.hurt(DamageSource.thrown(this, getOwner()), getDamage());
            remove(RemovalReason.DISCARDED);
        }
    }
}
