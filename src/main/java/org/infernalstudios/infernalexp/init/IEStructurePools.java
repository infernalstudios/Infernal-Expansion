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

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Holder;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import org.infernalstudios.infernalexp.InfernalExpansion;
import org.infernalstudios.infernalexp.world.gen.structures.PoolUtil;

public class IEStructurePools {

    public static final Holder<StructureTemplatePool> GLOWSTONE_CANYON_RUIN = registerPool(new StructureTemplatePool(
            new ResourceLocation(InfernalExpansion.MOD_ID, "glowstone_canyon_ruin/start_pool"), new ResourceLocation("empty"), ImmutableList.of(
            Pair.of(PoolUtil.legacy("glowstone_canyon/half_arch_ruin"), 1),
            Pair.of(PoolUtil.legacy("glowstone_canyon/cross_altar_1"), 1),
            Pair.of(PoolUtil.legacy("glowstone_canyon/cross_altar_2"), 1),
            Pair.of(PoolUtil.legacy("glowstone_canyon/cross_altar_3", IEProcessors.LOOT_CHEST_LIST), 1),
            Pair.of(PoolUtil.legacy("glowstone_canyon/shrine_altar"), 1),
            Pair.of(PoolUtil.legacy("glowstone_canyon/portal_ruin", IEProcessors.LOOT_CHEST_LIST), 1),
            Pair.of(PoolUtil.legacy("glowstone_canyon/grand_heaven_portal_1", IEProcessors.LOOT_CHEST_LIST), 1),
            Pair.of(PoolUtil.legacy("glowstone_canyon/grand_heaven_portal_2", IEProcessors.LOOT_CHEST_LIST), 1),
            Pair.of(PoolUtil.legacy("glowstone_canyon/grand_heaven_portal_3", IEProcessors.LOOT_CHEST_LIST), 1),
            Pair.of(PoolUtil.legacy("glowstone_canyon/grand_heaven_portal_4", IEProcessors.LOOT_CHEST_LIST), 1),
            Pair.of(PoolUtil.legacy("glowstone_canyon/grand_heaven_portal_5", IEProcessors.LOOT_CHEST_LIST), 1),
            Pair.of(PoolUtil.legacy("glowstone_canyon/heaven_1", IEProcessors.LOOT_CHEST_LIST), 1),
            Pair.of(PoolUtil.legacy("glowstone_canyon/heaven_2", IEProcessors.LOOT_CHEST_LIST), 1),
            Pair.of(PoolUtil.legacy("glowstone_canyon/heaven_3", IEProcessors.LOOT_CHEST_LIST), 1),
            Pair.of(PoolUtil.legacy("glowstone_canyon/heaven_4", IEProcessors.LOOT_CHEST_LIST), 1),
            Pair.of(PoolUtil.legacy("glowstone_canyon/heaven_5"), 1),
            Pair.of(PoolUtil.legacy("glowstone_canyon/heaven_6"), 1),
            Pair.of(PoolUtil.legacy("glowstone_canyon/heaven_7"), 1),
            Pair.of(PoolUtil.legacy("glowstone_canyon/heaven_8"), 1),
            Pair.of(PoolUtil.legacy("glowstone_canyon/heaven_9", IEProcessors.LOOT_CHEST_LIST), 1),
            Pair.of(PoolUtil.legacy("glowstone_canyon/heaven_10", IEProcessors.LOOT_CHEST_LIST), 1),
            Pair.of(PoolUtil.legacy("glowstone_canyon/heaven_11", IEProcessors.LOOT_CHEST_LIST), 1),
            Pair.of(PoolUtil.legacy("glowstone_canyon/heaven_12", IEProcessors.LOOT_CHEST_LIST), 1),
            Pair.of(PoolUtil.legacy("glowstone_canyon/mosquito_altar", IEProcessors.LOOT_CHEST_LIST), 1),
            Pair.of(PoolUtil.legacy("glowstone_canyon/platform_altar", IEProcessors.LOOT_CHEST_LIST), 1)
        ), StructureTemplatePool.Projection.RIGID)
    );

    public static final Holder<StructureTemplatePool> SOUL_SAND_VALLEY_RUIN = registerPool(new StructureTemplatePool(
            new ResourceLocation(InfernalExpansion.MOD_ID, "soul_sand_valley_ruin/start_pool"), new ResourceLocation("empty"), ImmutableList.of(
            Pair.of(PoolUtil.legacy("soul_sand_valley/soul_brazier_1"), 1),
            Pair.of(PoolUtil.legacy("soul_sand_valley/soul_brazier_2"), 1),
            Pair.of(PoolUtil.legacy("soul_sand_valley/soul_brazier_3"), 1),
            Pair.of(PoolUtil.legacy("soul_sand_valley/soul_pike_1"), 1),
            Pair.of(PoolUtil.legacy("soul_sand_valley/soul_pike_2"), 1)
        ), StructureTemplatePool.Projection.RIGID)
    );

    public static final Holder<StructureTemplatePool> BASTION_OUTPOST = registerPool(new StructureTemplatePool(
            new ResourceLocation(InfernalExpansion.MOD_ID, "bastion_outpost/start_pool"), new ResourceLocation("empty"), ImmutableList.of(
            Pair.of(PoolUtil.legacy("glowstone_canyon/desolate_bastion_outpost_1", IEProcessors.LOOT_CHEST_LIST), 1),
            Pair.of(PoolUtil.legacy("glowstone_canyon/desolate_bastion_outpost_2", IEProcessors.LOOT_CHEST_LIST), 1),
            Pair.of(PoolUtil.legacy("glowstone_canyon/desolate_bastion_outpost_3", IEProcessors.LOOT_CHEST_LIST), 1),
            Pair.of(PoolUtil.legacy("glowstone_canyon/desolate_bastion_outpost_4", IEProcessors.LOOT_CHEST_LIST), 1),
            Pair.of(PoolUtil.legacy("glowstone_canyon/desolate_bastion_outpost_5", IEProcessors.LOOT_CHEST_LIST), 1)
        ), StructureTemplatePool.Projection.RIGID)
    );

    public static final Holder<StructureTemplatePool> BASALT_GIANT_MOAI = registerPool(new StructureTemplatePool(
        new ResourceLocation(InfernalExpansion.MOD_ID, "basalt_giant_moai/start_pool"), new ResourceLocation("empty"), ImmutableList.of(
            Pair.of(PoolUtil.legacy("delta_shores/moai_1"), 1),
            Pair.of(PoolUtil.legacy("delta_shores/moai_2"), 1),
            Pair.of(PoolUtil.legacy("delta_shores/moai_3"), 1),
            Pair.of(PoolUtil.legacy("delta_shores/moai_4"), 1),
            Pair.of(PoolUtil.legacy("delta_shores/moai_5"), 1),
            Pair.of(PoolUtil.legacy("delta_shores/moai_6"), 1),
            Pair.of(PoolUtil.legacy("delta_shores/moai_7"), 1)
        ), StructureTemplatePool.Projection.RIGID)
    );

    public static final Holder<StructureTemplatePool> STRIDER_ALTAR = registerPool(new StructureTemplatePool(
            new ResourceLocation(InfernalExpansion.MOD_ID, "strider_altar/start_pool"), new ResourceLocation("empty"), ImmutableList.of(
            Pair.of(PoolUtil.legacy("basalt_deltas/strider_altar_1", IEProcessors.LOOT_CHEST_LIST), 1),
            Pair.of(PoolUtil.legacy("basalt_deltas/strider_altar_2", IEProcessors.LOOT_CHEST_LIST), 1),
            Pair.of(PoolUtil.legacy("basalt_deltas/strider_altar_3", IEProcessors.LOOT_CHEST_LIST), 1),
            Pair.of(PoolUtil.legacy("basalt_deltas/strider_altar_4", IEProcessors.LOOT_CHEST_LIST), 1),
            Pair.of(PoolUtil.legacy("basalt_deltas/strider_altar_5", IEProcessors.LOOT_CHEST_LIST), 1)
        ), StructureTemplatePool.Projection.RIGID)
    );

    private static Holder<StructureTemplatePool> registerPool(StructureTemplatePool pool) {
        if (BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE.keySet().contains(pool.getName()))
            throw new IllegalStateException("Structure Pool ID: \"" + pool.getName() + "\" is already in the registry!");

        return BuiltinRegistries.register(BuiltinRegistries.TEMPLATE_POOL, pool.getName(), pool);
    }

}
