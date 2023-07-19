package org.infernalstudios.infernalexp.entities;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.infernalstudios.infernalexp.init.IEEntityTypes;

import javax.annotation.Nullable;
import org.jetbrains.annotations.NotNull;

public class RockEntity extends Projectile {
    private static final EntityDataAccessor<ItemStack> ITEM = SynchedEntityData.defineId(RockEntity.class, EntityDataSerializers.ITEM_STACK);

    public RockEntity(EntityType<? extends RockEntity> entityType, Level level) {
        super(entityType, level);
    }

    public RockEntity(EntityType<? extends RockEntity> entityType, Level level, double xPos, double yPos, double zPos) {
        this(entityType, level);
        this.setPos(xPos, yPos, zPos);
    }

    public RockEntity(Level level, LivingEntity thrower) {
        this(IEEntityTypes.ROCK.get(), level, thrower.getX(), thrower.getEyeY() - (double)0.1F, thrower.getZ());
        this.setOwner(thrower);
        this.entityData.set(ITEM, new ItemStack(thrower.level.getBlockState(thrower.getOnPos()).getBlock().asItem()));
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(ITEM, new ItemStack(Items.BLACKSTONE));
    }

    @Override
    public void lerpTo(double posX, double posY, double posZ, float rotY, float rotX, int p_36733_, boolean p_36734_) {
        this.setPos(posX, posY, posZ);
        this.setRot(rotY, rotX);
    }

    @Override
    public void tick() {
        super.tick();
        Vec3 movement = this.getDeltaMovement();
        if (this.xRotO == 0.0F && this.yRotO == 0.0F) {
            double d0 = movement.horizontalDistance();
            this.setYRot((float) (Mth.atan2(movement.x, movement.z) * (double) (180F / (float) Math.PI)));
            this.setXRot((float) (Mth.atan2(movement.y, d0) * (double) (180F / (float) Math.PI)));
            this.yRotO = this.getYRot();
            this.xRotO = this.getXRot();
        }

        if (this.isInWaterOrRain()) {
            this.clearFire();
        }

        Vec3 rockPos = this.position();
        Vec3 posAddedMovement = rockPos.add(movement);
        HitResult hitresult = this.level.clip(new ClipContext(rockPos, posAddedMovement, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
        if (hitresult.getType() != HitResult.Type.MISS) {
            posAddedMovement = hitresult.getLocation();
        }

        while (!this.isRemoved()) {
            EntityHitResult entityhitresult = this.findHitEntity(rockPos, posAddedMovement);
            if (entityhitresult != null) {
                hitresult = entityhitresult;
            }

            if (hitresult != null && hitresult.getType() == HitResult.Type.ENTITY) {
                Entity hitEntity = ((EntityHitResult) hitresult).getEntity();
                Entity shooter = this.getOwner();
                if (hitEntity instanceof Player && shooter instanceof Player && !((Player) shooter).canHarmPlayer((Player) hitEntity)) {
                    hitresult = null;
                    entityhitresult = null;
                }
            }

            if (hitresult != null && hitresult.getType() != HitResult.Type.MISS && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, hitresult)) {
                this.onHit(hitresult);
                this.hasImpulse = true;
            }

            if (entityhitresult == null) {
                break;
            }

            hitresult = null;
        }

        movement = this.getDeltaMovement();
        double d5 = movement.x;
        double d6 = movement.y;
        double d1 = movement.z;

        double d7 = this.getX() + d5;
        double d2 = this.getY() + d6;
        double d3 = this.getZ() + d1;
        double d4 = movement.horizontalDistance();
        this.setYRot((float) (Mth.atan2(d5, d1) * (double) (180F / (float) Math.PI)));

        this.setXRot((float) (Mth.atan2(d6, d4) * (double) (180F / (float) Math.PI)));
        this.setXRot(lerpRotation(this.xRotO, this.getXRot()));
        this.setYRot(lerpRotation(this.yRotO, this.getYRot()));
        float f = 0.99F;
        if (this.isInWater()) {
            for (int j = 0; j < 4; ++j) {
                this.level.addParticle(ParticleTypes.BUBBLE, d7 - d5 * 0.25D, d2 - d6 * 0.25D, d3 - d1 * 0.25D, d5, d6, d1);
            }

            f = 0.6F;
        }

        this.setDeltaMovement(movement.scale(f));
        if (!this.isNoGravity()) {
            Vec3 vec34 = this.getDeltaMovement();
            this.setDeltaMovement(vec34.x, vec34.y - (double) 0.05F, vec34.z);
        }

        this.setPos(d7, d2, d3);
        this.checkInsideBlocks();
    }

    @Override
    protected void onHitEntity(@NotNull EntityHitResult hitResult) {
        super.onHitEntity(hitResult);
        Entity hitEntity = hitResult.getEntity();
        float f = (float)this.getDeltaMovement().length();
        int i = Mth.ceil(Mth.clamp((double)f * 5.0D, 0.0D, 2.147483647E9D));

        Entity shooter = this.getOwner();
        DamageSource damagesource;
        if (shooter instanceof LivingEntity livingShooter) {
            damagesource = DamageSource.indirectMobAttack(this, livingShooter);
            livingShooter.setLastHurtMob(hitEntity);
        } else {
            damagesource = DamageSource.indirectMobAttack(this, null);
        }

        boolean flag = hitEntity.getType() == EntityType.ENDERMAN;
        if (this.isOnFire() && !flag) {
            hitEntity.setSecondsOnFire(5);
        }

        if (hitEntity.hurt(damagesource, (float)i)) {
            if (flag) {
                return;
            }

            if (hitEntity instanceof LivingEntity livingHitEntity) {
                if (!this.level.isClientSide && shooter instanceof LivingEntity) {
                    EnchantmentHelper.doPostHurtEffects(livingHitEntity, shooter);
                    EnchantmentHelper.doPostDamageEffects((LivingEntity)shooter, livingHitEntity);
                }
            }

            this.playSound(SoundEvents.STONE_BREAK, 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
        }

        this.discard();
    }

    @Override
    protected void onHitBlock(@NotNull BlockHitResult blockHitResult) {
        super.onHitBlock(blockHitResult);
        this.discard();
    }

    @Nullable
    protected EntityHitResult findHitEntity(Vec3 firstBound, Vec3 secondBound) {
        return ProjectileUtil.getEntityHitResult(this.level, this, firstBound, secondBound, this.getBoundingBox().expandTowards(this.getDeltaMovement()).inflate(1.0D), this::canHitEntity);
    }

    public ItemStack getItem() {
        return this.entityData.get(ITEM);
    }

}
