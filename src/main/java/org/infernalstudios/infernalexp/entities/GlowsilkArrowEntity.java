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

import net.minecraft.network.protocol.Packet;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;
import org.infernalstudios.infernalexp.init.IEEffects;
import org.infernalstudios.infernalexp.init.IEEntityTypes;
import org.infernalstudios.infernalexp.init.IEParticleTypes;
import org.jetbrains.annotations.NotNull;

public class GlowsilkArrowEntity extends Arrow {

    public GlowsilkArrowEntity(EntityType<? extends Arrow> p_36858_, Level p_36859_) {
        super(p_36858_, p_36859_);
    }

    public GlowsilkArrowEntity(Level level, ItemStack arrow, LivingEntity owner) {
        this(IEEntityTypes.GLOWSILK_ARROW.get(), level);

        this.setPos(owner.getX(), owner.getEyeY() - 0.1D, owner.getZ());
        this.setOwner(owner);
        this.setEffectsFromItem(arrow);

        if (owner instanceof Player)
            this.pickup = Pickup.ALLOWED;
    }

    @Override
    protected void makeParticle(int particleCount) {
        for (int j = 0; j < particleCount; ++j) {
            this.getLevel().addParticle(IEParticleTypes.GLOWSTONE_SPARKLE.get(), this.getRandomX(0.5D), this.getRandomY(), this.getRandomZ(0.5D), 0.0D, 0.0D, 0.0D);
        }

        super.makeParticle(particleCount);
    }

    @Override
    protected void doPostHurtEffects(@NotNull LivingEntity entity) {
        super.doPostHurtEffects(entity);
        this.addEffect(new MobEffectInstance(IEEffects.LUMINOUS.get(), 3600));
    }

    @Override
    public @NotNull Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

}
