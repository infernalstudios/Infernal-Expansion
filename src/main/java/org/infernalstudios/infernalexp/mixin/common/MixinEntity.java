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

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import org.infernalstudios.infernalexp.access.FireTypeAccess;
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
    protected SynchedEntityData entityData;

    @Unique
    private static final EntityDataAccessor<String> FIRE_TYPE = SynchedEntityData.defineId(Entity.class, EntityDataSerializers.STRING);

    @Inject(method = "<init>", at = @At("TAIL"))
    private void IE_init(EntityType<?> entityTypeIn, Level worldIn, CallbackInfo ci) {
        this.entityData.define(FIRE_TYPE, KnownFireTypes.FIRE.getName());
    }

    @Inject(method = "saveWithoutId", at = @At(value = "INVOKE", target = "Lnet/minecraft/nbt/CompoundTag;putShort(Ljava/lang/String;S)V", ordinal = 0, shift = Shift.AFTER), remap = false)
    private void IE_writeCustomFires(CompoundTag tag, CallbackInfoReturnable<CompoundTag> ci) {
        tag.putString("fireType", this.getFireType().getName());
    }

    @Inject(method = "load", at = @At(value = "INVOKE", target = "Lnet/minecraft/nbt/CompoundTag;getShort(Ljava/lang/String;)S", ordinal = 0, shift = Shift.AFTER), remap = false)
    private void IE_readCustomFires(CompoundTag tag, CallbackInfo ci) {
        this.setFireType(KnownFireTypes.byName(tag.getString("fireType")));
    }

    @Inject(method = "lavaHurt", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;setSecondsOnFire(I)V", shift = Shift.BEFORE), remap = false)
    private void IE_setCustomFireFromLava(CallbackInfo ci) {
        this.setFireType(KnownFireTypes.FIRE);
    }

    @Override
    public KnownFireTypes getFireType() {
        return KnownFireTypes.byName(this.entityData.get(FIRE_TYPE));
    }

    @Override
    public void setFireType(KnownFireTypes type) {
        this.entityData.set(FIRE_TYPE, type.getName());
    }

}
