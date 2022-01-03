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

import net.minecraft.world.entity.decoration.Painting;
import net.minecraft.world.entity.decoration.Motive;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.List;

@Mixin(Painting.class)
public class MixinPaintingEntity {

    @Shadow
    public Motive motive;

    // Minecraft always wants to place the biggest painting possible, this makes sure that it doesn't think that an Infernal Expansion painting is the biggest possible
    @ModifyVariable(at = @At(value = "STORE"), method = "<init>(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/Direction;)V", ordinal = 1)
    public int IE_changeMaxSurfaceArea(int maxSurfaceArea) {
        return motive.getRegistryName().getNamespace().equals("infernalexp") ? 0 : maxSurfaceArea;
    }

    @ModifyVariable(at = @At(value = "INVOKE", target = "Ljava/util/List;isEmpty()Z", shift = Shift.BEFORE), method = "<init>(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/Direction;)V", ordinal = 0)
    public List<Motive> IE_changePaintingList(List<Motive> list) {
        list.removeIf(paintingType -> paintingType.getRegistryName().getNamespace().equals("infernalexp"));

        return list;
    }

}
