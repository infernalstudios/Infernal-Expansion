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

package org.infernalstudios.infernalexp.mixin.common;

import org.infernalstudios.infernalexp.access.AbstractArrowEntityAccess;
import org.infernalstudios.infernalexp.client.DynamicLightingHandler;

import org.infernalstudios.infernalexp.init.IEEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractArrow.class)
public class MixinAbstractArrowEntity implements AbstractArrowEntityAccess {
    @Unique
    private static final EntityDataAccessor<Boolean> GLOW = SynchedEntityData.defineId(AbstractArrow.class, EntityDataSerializers.BOOLEAN);

    @Unique
    private static final EntityDataAccessor<Boolean> LUMINOUS = SynchedEntityData.defineId(AbstractArrow.class, EntityDataSerializers.BOOLEAN);

    @Unique
    private static final EntityDataAccessor<Boolean> INFECTION = SynchedEntityData.defineId(AbstractArrow.class, EntityDataSerializers.BOOLEAN);

    @Unique
    private static final EntityDataAccessor<Boolean> INFECTED_SOURCE = SynchedEntityData.defineId(AbstractArrow.class, EntityDataSerializers.BOOLEAN);

    @OnlyIn(Dist.CLIENT)
    @Inject(at = @At("RETURN"), method = "tick", remap = false)
    private void arrowTickInfernalExpansion(CallbackInfo ci) {
        DynamicLightingHandler.tick(((AbstractArrow) (Object) this));
    }

    @Inject(at = @At("RETURN"), method = "defineSynchedData", remap = false)
    private void registerDataInfernalExpansion(CallbackInfo ci) {
        ((AbstractArrow) (Object) this).getEntityData().define(LUMINOUS, false);
        ((AbstractArrow) (Object) this).getEntityData().define(INFECTION, false);
        ((AbstractArrow) (Object) this).getEntityData().define(GLOW, false);
        ((AbstractArrow) (Object) this).getEntityData().define(INFECTED_SOURCE, false);
    }

    @Inject(at = @At("RETURN"), method = "addAdditionalSaveData", remap = false)
    private void writeAdditionalInfernalExpansion(CompoundTag compound, CallbackInfo ci) {
        compound.putBoolean("Luminous", ((AbstractArrow) (Object) this).getEntityData().get(LUMINOUS));
        compound.putBoolean("Infection", ((AbstractArrow) (Object) this).getEntityData().get(INFECTION));
        compound.putBoolean("Glow", ((AbstractArrow) (Object) this).getEntityData().get(GLOW));
        compound.putBoolean("InfectedSource", ((AbstractArrow) (Object) this).getEntityData().get(INFECTED_SOURCE));
    }

    @Inject(at = @At("RETURN"), method = "readAdditionalSaveData", remap = false)
    private void readAdditionalInfernalExpansion(CompoundTag compound, CallbackInfo ci) {
        setLuminous(compound.getBoolean("Luminous"));
        setInfection(compound.getBoolean("Infection"));
        setGlow(compound.getBoolean("Glow"));
        setInfectedSource(compound.getBoolean("InfectedSource"));
    }

    @Inject(at = @At("RETURN"), method = "setOwner", remap = false)
    private void setShooterInfernalExpansion(Entity entityIn, CallbackInfo ci) {
        if (entityIn instanceof LivingEntity livingEntity) {
            if (livingEntity.hasEffect(IEEffects.INFECTION.get())) {
                this.setInfectedSource(true);
                this.setInfection(true);
            }
        }
    }

    @Override
    public boolean getLuminous() {
        return ((AbstractArrow) (Object) this).getEntityData().get(LUMINOUS);
    }

    @Override
    public void setLuminous(boolean isLuminous) {
        ((AbstractArrow) (Object) this).getEntityData().set(LUMINOUS, isLuminous);
    }

    @Override
    public boolean getInfection() {
        return ((AbstractArrow) (Object) this).getEntityData().get(INFECTION);
    }

    @Override
    public void setInfection(boolean isInfection) {
        ((AbstractArrow) (Object) this).getEntityData().set(INFECTION, isInfection);
    }

    @Override
    public boolean getInfectedSource() {
        return ((AbstractArrow) (Object) this).getEntityData().get(INFECTED_SOURCE);
    }

    @Override
    public void setInfectedSource(boolean isInfectedSource) {
        ((AbstractArrow) (Object) this).getEntityData().set(INFECTED_SOURCE, isInfectedSource);
    }

    @Override
    public boolean getGlow() {
        return ((AbstractArrow) (Object) this).getEntityData().get(GLOW);
    }

    @Override
    public void setGlow(boolean shouldGlow) {
        ((AbstractArrow) (Object) this).getEntityData().set(GLOW, shouldGlow);
    }
}
