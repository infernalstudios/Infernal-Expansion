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

package org.infernalstudios.infernalexp.config;

import net.minecraftforge.fml.config.ModConfig;
import org.infernalstudios.infernalexp.config.InfernalExpansionConfig.ClientConfig;
import org.infernalstudios.infernalexp.config.InfernalExpansionConfig.Miscellaneous;
import org.infernalstudios.infernalexp.config.InfernalExpansionConfig.MobSpawning;
import org.infernalstudios.infernalexp.config.InfernalExpansionConfig.WorldGeneration;
import org.infernalstudios.infernalexp.util.NoiseChunkGeneratorUtil;

import javax.annotation.Nullable;

public final class ConfigHelper {

    //Client
    public static void bakeClient(@Nullable final ModConfig config) {
        //Luminous Effect
        ClientConfig.LUMINOUS_REFRESH_DELAY.set(ConfigHolder.CLIENT.luminousRefreshDelay.get());
    }

    //Common
    public static void bakeCommon(@Nullable final ModConfig config) {
        //Mob Spawnable Biomes
        MobSpawning.VOLINE.setSpawnableBiomes(ConfigHolder.COMMON.volineBiomes.get());
        MobSpawning.WARPBEETLE.setSpawnableBiomes(ConfigHolder.COMMON.warpbeetleBiomes.get());
        MobSpawning.SHROOMLOIN.setSpawnableBiomes(ConfigHolder.COMMON.shroomloinBiomes.get());
        MobSpawning.BASALT_GIANT.setSpawnableBiomes(ConfigHolder.COMMON.basaltGiantBiomes.get());
        MobSpawning.EMBODY.setSpawnableBiomes(ConfigHolder.COMMON.embodyBiomes.get());
        MobSpawning.GLOWSQUITO.setSpawnableBiomes(ConfigHolder.COMMON.glowsquitoBiomes.get());
        MobSpawning.GLOWSILK_MOTH.setSpawnableBiomes(ConfigHolder.COMMON.glowsilkMothBiomes.get());
        MobSpawning.BLINDSIGHT.setSpawnableBiomes(ConfigHolder.COMMON.blindsightBiomes.get());
        MobSpawning.BLACKSTONE_DWARF.setSpawnableBiomes(ConfigHolder.COMMON.blackstoneDwarfBiomes.get());

        // World Generation
        WorldGeneration.BIOMES_LIST_IS_WHITELIST.set(ConfigHolder.COMMON.biomesListIsWhitelist.get());
        WorldGeneration.BIOMES_LIST.set(ConfigHolder.COMMON.biomesList.get());

        // We need to change the behaviour ourselves, so let's do it here.
        final Boolean prevReplaceNetherBiomeProvider = (Boolean) WorldGeneration.REPLACE_NETHER_BIOME_PROVIDER.get();
        WorldGeneration.REPLACE_NETHER_BIOME_PROVIDER.set(ConfigHolder.COMMON.replaceNetherBiomeProvider.get());
        if (prevReplaceNetherBiomeProvider != (Boolean) WorldGeneration.REPLACE_NETHER_BIOME_PROVIDER.get()) {
            if ((boolean) WorldGeneration.REPLACE_NETHER_BIOME_PROVIDER.get()) {
                NoiseChunkGeneratorUtil.useCustomNetherBiomeProvider();
            } else {
                NoiseChunkGeneratorUtil.useDefaultNetherBiomeProvider();
            }
        }

        //Bonemeal Behaviour
        Miscellaneous.SHROOMLIGHT_GROWABLE.set(ConfigHolder.COMMON.isShroomlightGrowable.get());
        Miscellaneous.SHROOMLIGHT_GROW_CHANCE.set(ConfigHolder.COMMON.shroomlightGrowChance.get());

        //Miscellaneous
        Miscellaneous.FIRE_CHARGE_EXPLOSION.set(ConfigHolder.COMMON.fireChargeExplosion.get());
        Miscellaneous.JERKY_EFFECT_DURATION.set(ConfigHolder.COMMON.jerkyEffectDuration.get());
        Miscellaneous.JERKY_EFFECT_AMPLIFIER.set(ConfigHolder.COMMON.jerkyEffectAmplifier.get());

        Miscellaneous.LUMINOUS_FUNGUS_ACTIVATE_DISTANCE.set(ConfigHolder.COMMON.luminousFungusActivateDistance.get());

        Miscellaneous.LUMINOUS_FUNGUS_GIVES_EFFECT.set(ConfigHolder.COMMON.luminousFungusGivesEffect.get());

        Miscellaneous.USE_THROWABLE_BRICKS.set(ConfigHolder.COMMON.useThrowableBricks.get());
    }

    public static void saveToClient() {
        ConfigHolder.CLIENT.luminousRefreshDelay.set((int) ClientConfig.LUMINOUS_REFRESH_DELAY.getDouble());
    }

    public static void saveToCommon() {
        //Mob Spawnable Biomes
        ConfigHolder.COMMON.volineBiomes.set(MobSpawning.VOLINE.getSpawnableBiomes());
        ConfigHolder.COMMON.warpbeetleBiomes.set(InfernalExpansionConfig.MobSpawning.WARPBEETLE.getSpawnableBiomes());
        ConfigHolder.COMMON.shroomloinBiomes.set(InfernalExpansionConfig.MobSpawning.SHROOMLOIN.getSpawnableBiomes());
        ConfigHolder.COMMON.basaltGiantBiomes.set(InfernalExpansionConfig.MobSpawning.BASALT_GIANT.getSpawnableBiomes());
        ConfigHolder.COMMON.embodyBiomes.set(InfernalExpansionConfig.MobSpawning.EMBODY.getSpawnableBiomes());
        ConfigHolder.COMMON.glowsquitoBiomes.set(InfernalExpansionConfig.MobSpawning.GLOWSQUITO.getSpawnableBiomes());
        ConfigHolder.COMMON.glowsilkMothBiomes.set(InfernalExpansionConfig.MobSpawning.GLOWSILK_MOTH.getSpawnableBiomes());
        ConfigHolder.COMMON.blindsightBiomes.set(InfernalExpansionConfig.MobSpawning.BLINDSIGHT.getSpawnableBiomes());
        ConfigHolder.COMMON.blackstoneDwarfBiomes.set(MobSpawning.BLACKSTONE_DWARF.getSpawnableBiomes());

        // World Generation
        ConfigHolder.COMMON.biomesListIsWhitelist.set((Boolean) WorldGeneration.BIOMES_LIST_IS_WHITELIST.get());
        ConfigHolder.COMMON.biomesList.set((String) WorldGeneration.BIOMES_LIST.get());
        ConfigHolder.COMMON.replaceNetherBiomeProvider.set((Boolean) WorldGeneration.REPLACE_NETHER_BIOME_PROVIDER.get());

        ConfigHolder.COMMON.isShroomlightGrowable.set(Miscellaneous.SHROOMLIGHT_GROWABLE.getBool());
        ConfigHolder.COMMON.shroomlightGrowChance.set(Miscellaneous.SHROOMLIGHT_GROW_CHANCE.getDouble());

        ConfigHolder.COMMON.fireChargeExplosion.set(Miscellaneous.FIRE_CHARGE_EXPLOSION.getBool());
        ConfigHolder.COMMON.jerkyEffectDuration.set(Miscellaneous.JERKY_EFFECT_DURATION.getInt());
        ConfigHolder.COMMON.jerkyEffectAmplifier.set(Miscellaneous.JERKY_EFFECT_AMPLIFIER.getInt());

        ConfigHolder.COMMON.luminousFungusActivateDistance.set(Miscellaneous.LUMINOUS_FUNGUS_ACTIVATE_DISTANCE.getDouble());

        ConfigHolder.COMMON.luminousFungusGivesEffect.set(Miscellaneous.LUMINOUS_FUNGUS_GIVES_EFFECT.getBool());

        ConfigHolder.COMMON.useThrowableBricks.set(Miscellaneous.USE_THROWABLE_BRICKS.getBool());

        ConfigHolder.COMMON_SPEC.save();
    }
}
