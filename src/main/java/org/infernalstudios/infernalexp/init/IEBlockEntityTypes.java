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

package org.infernalstudios.infernalexp.init;

import net.minecraftforge.registries.RegistryObject;
import org.infernalstudios.infernalexp.InfernalExpansion;
import org.infernalstudios.infernalexp.blockentities.GlowCampfireBlockEntity;

import org.infernalstudios.infernalexp.blockentities.LuminousFungusBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class IEBlockEntityTypes {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, InfernalExpansion.MOD_ID);

    public static final RegistryObject<BlockEntityType<GlowCampfireBlockEntity>> GLOW_CAMPFIRE = BLOCK_ENTITY_TYPES.register("glow_campfire", () -> BlockEntityType.Builder.of(GlowCampfireBlockEntity::new, IEBlocks.GLOW_CAMPFIRE.get()).build(null));
    public static final RegistryObject<BlockEntityType<LuminousFungusBlockEntity>> LUMINOUS_FUNGUS = BLOCK_ENTITY_TYPES.register("luminous_fungus", () -> BlockEntityType.Builder.of(LuminousFungusBlockEntity::new, IEBlocks.LUMINOUS_FUNGUS.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITY_TYPES.register(eventBus);
        InfernalExpansion.LOGGER.info("Infernal Expansion: Tile Entity Types Registered!");
    }

}
