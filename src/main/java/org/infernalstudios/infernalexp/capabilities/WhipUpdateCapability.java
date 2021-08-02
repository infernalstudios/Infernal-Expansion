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

package org.infernalstudios.infernalexp.capabilities;

import org.infernalstudios.infernalexp.InfernalExpansion;
import org.infernalstudios.infernalexp.util.InjectionUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

public class WhipUpdateCapability {

    @CapabilityInject(IWhipUpdate.class)
    public static final Capability<IWhipUpdate> INSTANCE = InjectionUtil.Null();

    public static final Direction DEFAULT_FACING = null;

    public static final ResourceLocation ID = new ResourceLocation(InfernalExpansion.MOD_ID, "ticks_since_attack");

    public static void register() {
        CapabilityManager.INSTANCE.register(IWhipUpdate.class, new Capability.IStorage<IWhipUpdate>() {

            @Override
            public INBT writeNBT(Capability<IWhipUpdate> capability, IWhipUpdate instance, Direction side) {
                CompoundNBT nbt = new CompoundNBT();

                nbt.putInt("TicksSinceAttack", instance.getTicksSinceAttack());
                nbt.putBoolean("Attacking", instance.getAttacking());
                nbt.putBoolean("Charging", instance.getCharging());

                return nbt;
            }

            @Override
            public void readNBT(Capability<IWhipUpdate> capability, IWhipUpdate instance, Direction side, INBT inbt) {
                CompoundNBT nbt = (CompoundNBT) inbt;

                instance.setTicksSinceAttack(nbt.getInt("TicksSinceAttack"));
                instance.setAttacking(nbt.getBoolean("Attacking"));
                instance.setCharging(nbt.getBoolean("Charging"));
            }
        }, WhipUpdate::new);
    }

    public static LazyOptional<IWhipUpdate> getWhipUpdate(ItemStack itemStack) {
        return itemStack.getCapability(INSTANCE, DEFAULT_FACING);
    }

    public static ICapabilityProvider createProvider() {
        return new SerializableCapabilityProvider<>(INSTANCE, DEFAULT_FACING);
    }

    public static ICapabilityProvider createProvider(IWhipUpdate whipUpdate) {
        return new SerializableCapabilityProvider<>(INSTANCE, DEFAULT_FACING, whipUpdate);
    }
}
