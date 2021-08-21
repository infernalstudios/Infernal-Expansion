package org.infernalstudios.infernalexp.entities;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.GlassBlock;
import net.minecraft.block.PaneBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.network.IPacket;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import org.infernalstudios.infernalexp.init.IEBlocks;
import org.infernalstudios.infernalexp.init.IEEntityTypes;
import org.infernalstudios.infernalexp.init.IESoundEvents;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
public class ThrowableBrickEntity extends ProjectileItemEntity {

    public ThrowableBrickEntity(EntityType<? extends ProjectileItemEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public ThrowableBrickEntity(World world, LivingEntity livingEntity) {
        super(IEEntityTypes.THROWABLE_BRICK.get(), livingEntity, world);
    }

    public ThrowableBrickEntity(EntityType<? extends ProjectileItemEntity> type, LivingEntity livingEntity, World world) {
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
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    @ParametersAreNonnullByDefault
    protected void func_230299_a_(BlockRayTraceResult result) {
        super.func_230299_a_(result);

        if (!getEntityWorld().isRemote()) {
            BlockPos pos = result.getPos();
            Block block = getEntityWorld().getBlockState(pos).getBlock();

            if ((block == IEBlocks.QUARTZ_GLASS.get() || block == IEBlocks.QUARTZ_GLASS_PANE.get())) {
                getEntityWorld().playSound(null, pos, IESoundEvents.QUARTZ_GLASS_TYPE.getHitSound(), SoundCategory.BLOCKS, 1.0F, 1.0F);
            }

            if ((block instanceof GlassBlock || block instanceof PaneBlock) && !(block == Blocks.IRON_BARS || block == IEBlocks.QUARTZ_GLASS.get() || block == IEBlocks.QUARTZ_GLASS_PANE.get())) {
                getEntityWorld().destroyBlock(pos, false);
                setMotion(getMotion().scale(getSpeedMultiplier()));

            } else {
                ItemEntity itemEntity = new ItemEntity(getEntityWorld(), getPosX(), getPosY(), getPosZ(), getDefaultItem().getDefaultInstance());

                getEntityWorld().addEntity(itemEntity);
                remove();
            }
        }
    }

    @Override
    @ParametersAreNonnullByDefault
    protected void onEntityHit(EntityRayTraceResult result) {
        super.onEntityHit(result);

        if (!getEntityWorld().isRemote()) {
            Entity target = result.getEntity();
            target.attackEntityFrom(DamageSource.causeThrownDamage(this, getShooter()), getDamage());
            remove();
        }
    }
}
