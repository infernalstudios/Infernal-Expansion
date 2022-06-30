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

package org.infernalstudios.infernalexp.world.biome.modifiers;

import com.mojang.serialization.Codec;
import net.minecraft.ResourceLocationException;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ModifiableBiomeInfo;
import net.minecraftforge.registries.ForgeRegistries;
import org.infernalstudios.infernalexp.InfernalExpansion;
import org.infernalstudios.infernalexp.config.InfernalExpansionConfig;
import org.infernalstudios.infernalexp.data.SpawnrateManager;
import org.infernalstudios.infernalexp.events.MiscEvents;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public record AddConfigurableSpawnsBiomeModifier() implements BiomeModifier {

    public static final Codec<AddConfigurableSpawnsBiomeModifier> CODEC = Codec.unit(AddConfigurableSpawnsBiomeModifier::new);

    @Override
    public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        Optional<ResourceKey<Biome>> biomeKey = biome.unwrapKey();

        if (phase != Phase.ADD || biomeKey.isEmpty()) return;

        this.addSpawns(builder, biomeKey.get().location().toString());
    }

    @Override
    public Codec<? extends BiomeModifier> codec() {
        return CODEC;
    }

    private void addSpawns(ModifiableBiomeInfo.BiomeInfo.Builder builder, String biomeLocation) {
        MiscEvents.getSpawnrateManager().forEach((entity, spawnInfo) -> {
            // Get the biomes the entity is allowed to spawn in from InfernalExpansionConfig
            List<String> spawnableBiomes = Arrays.asList(InfernalExpansionConfig.MobSpawning.getByName(entity.split(":")[1]).getSpawnableBiomes().replace(" ", "").split(","));

            // Check if the current biome getting loaded is in the spawnable biomes, if not, return
            if (!spawnableBiomes.contains(biomeLocation)) return;

            // Get the entity type from the name
            EntityType<?> entityType = ForgeRegistries.ENTITIES.getValue(new ResourceLocation(entity));

            // Check if the entity type exists
            if (entityType == null) {
                throw new ResourceLocationException("Invalid EntityType resource location " + entity);
            }

            // Find either the default spawn info or the spawn info for a specific biome from SpawnrateManager
            if (spawnInfo.containsKey(biomeLocation) && spawnableBiomes.contains(biomeLocation)) {
                addSpawn(builder, entityType, spawnInfo.get(biomeLocation));
            } else if (spawnInfo.containsKey("default") && spawnableBiomes.contains(biomeLocation)) {
                addSpawn(builder, entityType, spawnInfo.get("default"));
            } else {
                InfernalExpansion.LOGGER.error("{} doesn't have a default spawn entry", entity);
            }
        });
    }


    private void addSpawn(ModifiableBiomeInfo.BiomeInfo.Builder builder, EntityType<?> entityType, SpawnrateManager.SpawnInfo spawnInfo) {
        builder.getMobSpawnSettings().addSpawn(entityType.getCategory(), new MobSpawnSettings.SpawnerData(entityType, spawnInfo.spawnRate(), spawnInfo.minCount(), spawnInfo.maxCount()));

        // Change spawn costs
        if (spawnInfo.charge() != 0.0D && spawnInfo.energyBudget() != 0.0D) {
            builder.getMobSpawnSettings().addMobCharge(entityType, spawnInfo.charge(), spawnInfo.energyBudget());
        } else if (spawnInfo.charge() != 0.0D || spawnInfo.energyBudget() != 0.0D) {
            InfernalExpansion.LOGGER.error("EntityType {} has incomplete spawn cost data. When editing spawn costs, make sure to set both \"spawn_cost_per_entity\" and \"max_spawn_cost\"", entityType.toString());
        }
    }

}
