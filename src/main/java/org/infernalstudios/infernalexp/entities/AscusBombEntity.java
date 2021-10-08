/*
 * Copyright 2021 Infernal Studios
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

import net.minecraftforge.fmllegacy.network.NetworkHooks;
import org.infernalstudios.infernalexp.init.IEEffects;
import org.infernalstudios.infernalexp.init.IEEntityTypes;
import org.infernalstudios.infernalexp.init.IEItems;
import org.infernalstudios.infernalexp.init.IEParticleTypes;

import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.network.protocol.Packet;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

@OnlyIn(
    value = Dist.CLIENT,
    _interface = ItemSupplier.class
)
public class AscusBombEntity extends ThrowableItemProjectile implements ItemSupplier {

    public AscusBombEntity(EntityType<? extends AscusBombEntity> typeIn, Level worldIn) {
        super(typeIn, worldIn);
    }

    public AscusBombEntity(Level world, LivingEntity livingEntity) {
        super(IEEntityTypes.ASCUS_BOMB.get(), livingEntity, world);
    }

    @Override
    protected Item getDefaultItem() {
        return IEItems.ASCUS_BOMB.get();
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected void onHit(HitResult result) {
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
        AreaEffectCloud areaeffectcloudentity = new AreaEffectCloud(this.level, this.getX(), this.getY() + 0.6, this.getZ());

        areaeffectcloudentity.setRadius(0.1F);
        areaeffectcloudentity.setWaitTime(0);
        areaeffectcloudentity.setDuration(10);
        areaeffectcloudentity.setRadiusPerTick(0);
        areaeffectcloudentity.setParticle(ParticleTypes.EXPLOSION);

        this.level.addFreshEntity(areaeffectcloudentity);
    }

    private void spawnLingeringCloud() {
        AreaEffectCloud areaeffectcloudentity = new AreaEffectCloud(this.level, this.getX(), this.getY(), this.getZ());

        areaeffectcloudentity.setRadius(3.0F);
        areaeffectcloudentity.setRadiusOnUse(-0.5F);
        areaeffectcloudentity.setWaitTime(10);
        areaeffectcloudentity.setDuration(areaeffectcloudentity.getDuration() / 8);
        areaeffectcloudentity.setRadiusPerTick(-areaeffectcloudentity.getRadius() / (float) areaeffectcloudentity.getDuration());
        areaeffectcloudentity.addEffect(new MobEffectInstance(IEEffects.INFECTION.get(), 300));
        areaeffectcloudentity.setParticle(IEParticleTypes.INFECTION.get());

        this.level.addFreshEntity(areaeffectcloudentity);
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
