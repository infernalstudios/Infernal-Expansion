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

package org.infernalstudios.infernalexp.init;

import it.crystalnest.soul_fire_d.api.Fire;
import it.crystalnest.soul_fire_d.api.FireManager;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import org.infernalstudios.infernalexp.InfernalExpansion;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.ModList;
import org.infernalstudios.infernalexp.config.InfernalExpansionConfig;

public class IEFireTypes {
    public static final ResourceLocation GLOW_FIRE_TYPE = new ResourceLocation(InfernalExpansion.MOD_ID, "glow");
    public static final ResourceLocation ENDER_FIRE_TYPE = new ResourceLocation("endergetic", "ender");

    public static void register() {
        Fire.Builder fireBuilder = FireManager.fireBuilder(GLOW_FIRE_TYPE);
        FireManager.registerFire(
            fireBuilder
                .setDamage(2)
                .setComponent(Fire.Component.FLAME_PARTICLE, new ResourceLocation(InfernalExpansion.MOD_ID, "glowstone_sparkle"))
                .setComponent(Fire.Component.WALL_TORCH_BLOCK, new ResourceLocation(InfernalExpansion.MOD_ID, "glow_torch_wall"))
                .setBehavior(entity -> {
                    if (!entity.level.isClientSide() && entity.isAlive() && entity instanceof LivingEntity livingEntity && InfernalExpansionConfig.Miscellaneous.LUMINOUS_FUNGUS_GIVES_EFFECT.getBool()) {
                        livingEntity.addEffect(new MobEffectInstance(IEEffects.LUMINOUS.get(), 600, 0, true, true));
                    }
                })
                .removeFireAspect()
                .removeFlame()
                .build()
        );
        if (ModList.get().isLoaded(ENDER_FIRE_TYPE.getNamespace())) {
            FireManager.registerFire(
                fireBuilder
                    .reset(ENDER_FIRE_TYPE)
                    .setDamage(3)
                    .removeFireAspect()
                    .removeFlame()
                    .build()
            );
        }
    }
}
