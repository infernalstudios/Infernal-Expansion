package com.nekomaster1000.infernalexp.entities;

import com.nekomaster1000.infernalexp.init.IEEntityTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.network.IPacket;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class ThrowableBrickEntity extends ProjectileItemEntity {

    public ThrowableBrickEntity(EntityType<? extends ProjectileItemEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public ThrowableBrickEntity(World world, LivingEntity livingEntity) {
        super(IEEntityTypes.THROWABLE_BRICK.get(), livingEntity, world);
    }

    @Override
    protected Item getDefaultItem() {
        return Items.BRICK;
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected void onEntityHit(EntityRayTraceResult result) {
        super.onEntityHit(result);
        if (!this.world.isRemote) {
            Entity entity = result.getEntity();
            entity.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getShooter()), 2);
            this.remove();
        }
    }
}
