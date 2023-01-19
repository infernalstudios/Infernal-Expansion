/*
 * Copyright 2022 Infernal Studios
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.infernalstudios.infernalexp.entities;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;
import org.infernalstudios.infernalexp.init.IEEffects;
import org.infernalstudios.infernalexp.init.IEEntityTypes;
import org.infernalstudios.infernalexp.init.IEItems;
import org.infernalstudios.infernalexp.init.IEParticleTypes;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@OnlyIn(value = Dist.CLIENT)
public class AscusBombEntity extends ThrowableItemProjectile {

    public AscusBombEntity(EntityType<? extends AscusBombEntity> typeIn, Level worldIn) {
        super(typeIn, worldIn);
    }

    public AscusBombEntity(Level world, LivingEntity livingEntity) {
        super(IEEntityTypes.ASCUS_BOMB.get(), livingEntity, world);
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return IEItems.ASCUS_BOMB.get();
    }

    @Override
    public @NotNull Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected void onHit(@NotNull HitResult result) {
        super.onHit(result);

        if (!this.level.isClientSide) {
            this.playSound(SoundEvents.GENERIC_EXPLODE, 1.0F, 0.5F);
            this.spawnExplosionCloud();
            this.remove(RemovalReason.DISCARDED);

            this.initialEffect(result);
            this.spawnLingeringCloud();
        }

    }

    private void spawnExplosionCloud() {
        AreaEffectCloud areaEffectCloud = new AreaEffectCloud(this.level, this.getX(), this.getY() + 0.6, this.getZ());

        areaEffectCloud.setRadius(0.1F);
        areaEffectCloud.setWaitTime(0);
        areaEffectCloud.setDuration(10);
        areaEffectCloud.setRadiusPerTick(0);
        areaEffectCloud.setParticle(ParticleTypes.EXPLOSION);

        this.level.addFreshEntity(areaEffectCloud);
    }

    private void spawnLingeringCloud() {
        AreaEffectCloud areaEffectCloud = new AreaEffectCloud(this.level, this.getX(), this.getY(), this.getZ());

        areaEffectCloud.setRadius(3.0F);
        areaEffectCloud.setRadiusOnUse(-0.5F);
        areaEffectCloud.setWaitTime(10);
        areaEffectCloud.setDuration(areaEffectCloud.getDuration() / 8);
        areaEffectCloud.setRadiusPerTick(-areaEffectCloud.getRadius() / (float) areaEffectCloud.getDuration());
        areaEffectCloud.addEffect(new MobEffectInstance(IEEffects.INFECTION.get(), 300));
        areaEffectCloud.setParticle(IEParticleTypes.INFECTION.get());

        this.level.addFreshEntity(areaEffectCloud);
    }

    private void initialEffect(HitResult result) {
        if (!this.level.isClientSide()) {
            Entity entity = result.getType() == HitResult.Type.ENTITY ? ((EntityHitResult) result).getEntity() : null;

            AABB axisAlignedBB = this.getBoundingBox().inflate(4, 2, 4);
            List<LivingEntity> livingEntities = this.level.getEntitiesOfClass(LivingEntity.class, axisAlignedBB);

            if (!livingEntities.isEmpty()) {
                for (LivingEntity livingEntity : livingEntities) {
                    double distanceSq = this.distanceToSqr(livingEntity);

                    if (distanceSq < 16) {
                        double durationMultiplier = 1 - Math.sqrt(distanceSq) / 4;

                        if (livingEntity == entity) {
                            durationMultiplier = 1;
                        }

                        int duration = (int) (durationMultiplier * 300 + 0.5);

                        livingEntity.addEffect(new MobEffectInstance(IEEffects.INFECTION.get(), duration));
                    }
                }
            }
        }
    }
}
