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

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.infernalstudios.infernalexp.access.FireTypeAccess;
import org.infernalstudios.infernalexp.api.FireType;
import org.infernalstudios.infernalexp.init.IEFireTypes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class MixinEntity implements FireTypeAccess {

    @Shadow
    @Final
    protected EntityDataManager dataManager;

    @Unique
    private static final DataParameter<String> FIRE_TYPE = EntityDataManager.createKey(Entity.class, DataSerializers.STRING);

    @Inject(method = "<init>", at = @At("TAIL"))
    private void IE_init(EntityType<?> entityTypeIn, World worldIn, CallbackInfo ci) {
        this.dataManager.register(FIRE_TYPE, IEFireTypes.FIRE.getName().toString());
    }

    @Inject(method = "writeWithoutTypeId", at = @At(value = "INVOKE", target = "Lnet/minecraft/nbt/CompoundNBT;putShort(Ljava/lang/String;S)V", ordinal = 0, shift = Shift.AFTER))
    private void IE_writeCustomFires(CompoundNBT tag, CallbackInfoReturnable<CompoundNBT> ci) {
        tag.putString("fireType", this.getFireType().getName().toString());
    }

    @Inject(method = "read", at = @At(value = "INVOKE", target = "Lnet/minecraft/nbt/CompoundNBT;getShort(Ljava/lang/String;)S", ordinal = 0, shift = Shift.AFTER))
    private void IE_readCustomFires(CompoundNBT tag, CallbackInfo ci) {
        this.setFireType(FireType.getOrDefault(new ResourceLocation(tag.getString("fireType")), IEFireTypes.FIRE));
    }

    @Inject(method = "setFire", at = @At("HEAD"))
    private void IE_setToDefaultFireType(int seconds, CallbackInfo ci) {
        this.setFireType(IEFireTypes.FIRE);
    }

    @Override
    public FireType getFireType() {
        return FireType.getOrDefault(new ResourceLocation(this.dataManager.get(FIRE_TYPE)), IEFireTypes.FIRE);
    }

    @Override
    public void setFireType(FireType type) {
        this.dataManager.set(FIRE_TYPE, type.getName().toString());
    }

}
