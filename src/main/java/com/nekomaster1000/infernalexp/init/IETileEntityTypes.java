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

package com.nekomaster1000.infernalexp.init;

import com.nekomaster1000.infernalexp.InfernalExpansion;
import com.nekomaster1000.infernalexp.tileentities.GlowCampfireTileEntity;

import com.nekomaster1000.infernalexp.tileentities.LuminousFungusTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class IETileEntityTypes {

    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, InfernalExpansion.MOD_ID);

    public static final RegistryObject<TileEntityType<GlowCampfireTileEntity>> GLOW_CAMPFIRE = TILE_ENTITY_TYPES.register("glow_campfire", () -> TileEntityType.Builder.create(GlowCampfireTileEntity::new, IEBlocks.GLOW_CAMPFIRE.get()).build(null));
    public static final RegistryObject<TileEntityType<LuminousFungusTileEntity>> LUMINOUS_FUNGUS_TILE_ENTITY = TILE_ENTITY_TYPES.register("luminous_fungus", () -> TileEntityType.Builder.create(LuminousFungusTileEntity::new, IEBlocks.LUMINOUS_FUNGUS.get()).build(null));

    public static void register(IEventBus eventBus) {
        TILE_ENTITY_TYPES.register(eventBus);
        InfernalExpansion.LOGGER.info("Infernal Expansion: Tile Entity Types Registered!");
    }

}
