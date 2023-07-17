/*
 * Copyright 2023 Infernal Studios
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

package org.infernalstudios.infernalexp.data.providers;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

import com.mojang.datafixers.util.Pair;
import net.minecraft.data.loot.ChestLoot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;
import org.infernalstudios.infernalexp.InfernalExpansion;
import org.jetbrains.annotations.NotNull;

public class IEChestLoot extends ChestLoot {

    private final List<Pair<ResourceLocation, LootTable.Builder>> lootProviders = new ArrayList<>();

    private void addTables() {
        add("basalt_delta_ruin", IELootProviders.basaltDeltaRuin());
        add("desolate_bastion_outpost", IELootProviders.desolateBastionOutpost());
        add("glowstone_canyon_ruin", IELootProviders.glowstoneCanyonRuin());
    }

    private void add(String name, LootTable.Builder builder) {
        lootProviders.add(Pair.of(new ResourceLocation(InfernalExpansion.MOD_ID, "chests/" + name), builder));
    }

    @Override
    public void accept(@NotNull BiConsumer<ResourceLocation, LootTable.Builder> consumer) {
        addTables();
        lootProviders.forEach(pair -> consumer.accept(pair.getFirst(), pair.getSecond()));
    }

}
