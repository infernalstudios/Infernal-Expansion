package com.nekomaster1000.infernalexp.entities;

import com.nekomaster1000.infernalexp.config.InfernalExpansionConfig;
import com.nekomaster1000.infernalexp.init.IEEntityTypes;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractFireballEntity;
import net.minecraft.network.IPacket;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class ThrowableFireChargeEntity extends AbstractFireballEntity {

    public ThrowableFireChargeEntity(EntityType<? extends ThrowableFireChargeEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public ThrowableFireChargeEntity(World worldIn, LivingEntity shooter, double accelX, double accelY, double accelZ) {
        super(IEEntityTypes.THROWABLE_FIRE_CHARGE.get(), shooter.getPosX(), shooter.getPosYEye(), shooter.getPosZ(), accelX, accelY, accelZ, worldIn);
        this.setShooter(shooter);
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        RayTraceResult.Type raytraceresult$type = result.getType();
        if (raytraceresult$type == RayTraceResult.Type.ENTITY) {
            this.onEntityHit((EntityRayTraceResult)result);
        } else if (raytraceresult$type == RayTraceResult.Type.BLOCK) {
            this.func_230299_a_((BlockRayTraceResult)result);
        }

        if (!this.world.isRemote) {
            boolean flag = InfernalExpansionConfig.Miscellaneous.FIRE_CHARGE_EXPLOSION.getBool() && net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.world, this.getShooter());
            this.world.createExplosion(null, this.getPosX(), this.getPosY(), this.getPosZ(), 1.0F, flag, flag ? Explosion.Mode.DESTROY : Explosion.Mode.NONE);
            this.remove();
        }
    }

    @Override
    protected void onEntityHit(EntityRayTraceResult result) {
        super.onEntityHit(result);
        if (!this.world.isRemote) {
            Entity entity = result.getEntity();
            Entity entity1 = this.getShooter();
            entity.attackEntityFrom(DamageSource.causeOnFireDamage(this, entity1), 6.0F);
            if (entity1 instanceof LivingEntity) {
                this.applyEnchantments((LivingEntity)entity1, entity);
            }

        }
    }
}
