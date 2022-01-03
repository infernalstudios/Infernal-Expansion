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

package org.infernalstudios.infernalexp.mixin.common;

import org.infernalstudios.infernalexp.access.AbstractArrowEntityAccess;
import org.infernalstudios.infernalexp.client.DynamicLightingHandler;

import org.infernalstudios.infernalexp.init.IEEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractArrowEntity.class)
public class MixinAbstractArrowEntity implements AbstractArrowEntityAccess {
    @Unique
    private static final DataParameter<Boolean> GLOW = EntityDataManager.createKey(AbstractArrowEntity.class, DataSerializers.BOOLEAN);

    @Unique
    private static final DataParameter<Boolean> LUMINOUS = EntityDataManager.createKey(AbstractArrowEntity.class, DataSerializers.BOOLEAN);

    @Unique
    private static final DataParameter<Boolean> INFECTION = EntityDataManager.createKey(AbstractArrowEntity.class, DataSerializers.BOOLEAN);

    @Unique
    private static final DataParameter<Boolean> INFECTED_SOURCE = EntityDataManager.createKey(AbstractArrowEntity.class, DataSerializers.BOOLEAN);

    @OnlyIn(Dist.CLIENT)
    @Inject(at = @At("RETURN"), method = "tick")
    private void arrowTickInfernalExpansion(CallbackInfo ci) {
        DynamicLightingHandler.tick(((AbstractArrowEntity) (Object) this));
    }

    @Inject(at = @At("RETURN"), method = "registerData")
    private void registerDataInfernalExpansion(CallbackInfo ci) {
        ((AbstractArrowEntity) (Object) this).getDataManager().register(LUMINOUS, false);
        ((AbstractArrowEntity) (Object) this).getDataManager().register(INFECTION, false);
        ((AbstractArrowEntity) (Object) this).getDataManager().register(GLOW, false);
        ((AbstractArrowEntity) (Object) this).getDataManager().register(INFECTED_SOURCE, false);
    }

    @Inject(at = @At("RETURN"), method = "writeAdditional")
    private void writeAdditionalInfernalExpansion(CompoundNBT compound, CallbackInfo ci) {
        compound.putBoolean("Luminous", ((AbstractArrowEntity) (Object) this).getDataManager().get(LUMINOUS));
        compound.putBoolean("Infection", ((AbstractArrowEntity) (Object) this).getDataManager().get(INFECTION));
        compound.putBoolean("Glow", ((AbstractArrowEntity) (Object) this).getDataManager().get(GLOW));
        compound.putBoolean("InfectedSource", ((AbstractArrowEntity) (Object) this).getDataManager().get(INFECTED_SOURCE));
    }

    @Inject(at = @At("RETURN"), method = "readAdditional")
    private void readAdditionalInfernalExpansion(CompoundNBT compound, CallbackInfo ci) {
        setLuminous(compound.getBoolean("Luminous"));
        setInfection(compound.getBoolean("Infection"));
        setGlow(compound.getBoolean("Glow"));
        setInfectedSource(compound.getBoolean("InfectedSource"));
    }

    @Inject(at = @At("RETURN"), method = "setShooter")
    private void setShooterInfernalExpansion(Entity entityIn, CallbackInfo ci) {
        if (entityIn instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity) entityIn;
            if (livingEntity.isPotionActive(IEEffects.INFECTION.get())) {
                this.setInfectedSource(true);
                this.setInfection(true);
            }
        }
    }

    @Override
    public boolean getLuminous() {
        return ((AbstractArrowEntity) (Object) this).getDataManager().get(LUMINOUS);
    }

    @Override
    public void setLuminous(boolean isLuminous) {
        ((AbstractArrowEntity) (Object) this).getDataManager().set(LUMINOUS, isLuminous);
    }

    @Override
    public boolean getInfection() {
        return ((AbstractArrowEntity) (Object) this).getDataManager().get(INFECTION);
    }

    @Override
    public void setInfection(boolean isInfection) {
        ((AbstractArrowEntity) (Object) this).getDataManager().set(INFECTION, isInfection);
    }

    @Override
    public boolean getInfectedSource() {
        return ((AbstractArrowEntity) (Object) this).getDataManager().get(INFECTED_SOURCE);
    }

    @Override
    public void setInfectedSource(boolean isInfectedSource) {
        ((AbstractArrowEntity) (Object) this).getDataManager().set(INFECTED_SOURCE, isInfectedSource);
    }

    @Override
    public boolean getGlow() {
        return ((AbstractArrowEntity) (Object) this).getDataManager().get(GLOW);
    }

    @Override
    public void setGlow(boolean shouldGlow) {
        ((AbstractArrowEntity) (Object) this).getDataManager().set(GLOW, shouldGlow);
    }
}
