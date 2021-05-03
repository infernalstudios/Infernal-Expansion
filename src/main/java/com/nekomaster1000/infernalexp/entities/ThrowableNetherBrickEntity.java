package com.nekomaster1000.infernalexp.entities;

import com.nekomaster1000.infernalexp.init.IEEntityTypes;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IRendersAsItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.network.IPacket;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.Tags;
import net.minecraftforge.fml.network.NetworkHooks;

@OnlyIn(
        value = Dist.CLIENT,
        _interface = IRendersAsItem.class
)
public class ThrowableNetherBrickEntity extends ProjectileItemEntity implements IRendersAsItem {

    public ThrowableNetherBrickEntity(EntityType<? extends ThrowableNetherBrickEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public ThrowableNetherBrickEntity(World world, LivingEntity livingEntity) {
        super(IEEntityTypes.THROWABLE_NETHER_BRICK.get(), livingEntity, world);
    }

    @Override
    protected Item getDefaultItem() {
        return Items.NETHER_BRICK;
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected void onEntityHit(EntityRayTraceResult entityRayTraceResult) {
        super.onEntityHit(entityRayTraceResult);
        Entity entity = entityRayTraceResult.getEntity();

        entity.attackEntityFrom(DamageSource.GENERIC, 3.0F);
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        super.onImpact(result);
        if (result.getType() == RayTraceResult.Type.BLOCK) {
            BlockPos pos = new BlockPos(result.getHitVec());
            if (world.getBlockState(pos).getBlock().isIn(Tags.Blocks.GLASS)) {
                world.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
            } else {
                ItemEntity item = new ItemEntity(world, this.getPosX(), this.getPosY(), this.getPosZ(), Items.NETHER_BRICK.getDefaultInstance());
                world.addEntity(item);
                this.remove();
            }
        } else {
            this.remove();
        }
    }
}
