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

import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.infernalstudios.infernalexp.InfernalExpansion;
import org.infernalstudios.infernalexp.capabilities.IWhipUpdate;
import org.infernalstudios.infernalexp.capabilities.WhipUpdate;
import org.infernalstudios.infernalexp.items.WhipItem;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class IECapabilities {

    public static final Capability<IWhipUpdate> WHIP_UPDATE = CapabilityManager.get(new CapabilityToken<>() {
    });

    @SubscribeEvent
    public void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.register(IWhipUpdate.class);
    }

    @SubscribeEvent
    public void onItemAttach(final AttachCapabilitiesEvent<ItemStack> event) {
        if (!(event.getObject().getItem() instanceof WhipItem)) return;

        WhipUpdate whipUpdate = new WhipUpdate();
        LazyOptional<IWhipUpdate> optionalWhipUpdate = LazyOptional.of(() -> whipUpdate);

        ICapabilityProvider provider = new ICapabilityProvider() {
            @Nonnull
            @Override
            public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction side) {
                if (capability == WHIP_UPDATE) {
                    return optionalWhipUpdate.cast();
                }

                return LazyOptional.empty();
            }
        };

        event.addCapability(new ResourceLocation(InfernalExpansion.MOD_ID, "whip_update"), provider);
        event.addListener(optionalWhipUpdate::invalidate);
    }

}
