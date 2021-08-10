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

import org.infernalstudios.infernalexp.InfernalExpansion;
import org.infernalstudios.infernalexp.config.values.CachedConfigValue;

import java.util.EnumMap;

public class IEConfig {

    // This class will replace InfernalExpansionConfig because it has a shorter name. The aim of this class is to make it easier to access config options without having to remember if they're from the client or common configs

    public enum MobInteractions {
        PIGLIN_FEAR_WARPBEETLE,
        PIGLIN_FEAR_EMBODY,
        PIGLIN_FEAR_DWARF,
        HOGLIN_FEAR_WARPBEETLE,
        HOGLIN_FEAR_EMBODY,
        SPIDER_ATTACK_WARPBEETLE,
        SKELETON_ATTACK_PIGLIN,
        SKELETON_ATTACK_BRUTE,
        SKELETON_ATTACK_EMBODY,
        SKELETON_ATTACK_GIANT,
        PIGLIN_ATTACK_SKELETON,
        PIGLIN_ATTACK_VOLINE,
        BRUTE_ATTACK_SKELETON,
        BRUTE_ATTACK_VOLINE,
        GHAST_ATTACK_EMBODY,
        GHAST_ATTACK_VOLINE,
        GHAST_ATTACK_SKELETON,
        GHAST_ATTACK_GLOWSQUITO,
        GLOWSQUITO_ATTACK_DWARF,
        GLOWSQUITO_ATTACK_LUMINOUS,
        DWARF_ATTACK_PIGLIN,
        DWARF_ATTACK_ZOMBIE_PIGLIN,
        DWARF_ATTACK_PLAYER,
        BLINDSIGHT_ATTACK_GLOWSQUITO,
        BLINDSIGHT_ATTACK_PLAYER,
        GIANT_ATTACK_MAGMA_CUBE,
        EMBODY_ATTACK_PIGLIN,
        EMBODY_ATTACK_PLAYER,
        VOLINE_ATTACK_FIRE_RESISTANCE,
        VOLINE_ATTACK_PLAYER,
        VOLINE_ATTACK_MAGMA_CUBE,
        USE_HOGCHOPS,
        GLOWSILK_SPEED
    }

    public enum WorldGeneration {
        BIOMES_LIST_IS_WHITELIST,
        BIOMES_LIST,
        REPLACE_NETHER_BIOME_PROVIDER // Dangerous
    }

    public static final EnumMap<MobInteractions, CachedConfigValue<?>> mobInteractions = new EnumMap<>(MobInteractions.class);
    public static final EnumMap<WorldGeneration, CachedConfigValue<?>> worldGeneration = new EnumMap<>(WorldGeneration.class);

    public static Boolean getBoolean(Enum<?> configOption) {
        if (configOption instanceof MobInteractions) {
            return (Boolean) mobInteractions.get(configOption).get();
        } else if (configOption instanceof WorldGeneration) {
            return (Boolean) worldGeneration.get(configOption).get();
        }

        InfernalExpansion.LOGGER.error("Couldn't find Enum constant {}", configOption);
        return false;
    }

    public static Double getDouble(Enum<?> configOption) {
        if (configOption instanceof MobInteractions) {
            return (Double) mobInteractions.get(configOption).get();
        } else if (configOption instanceof WorldGeneration) {
            return (Double) worldGeneration.get(configOption).get();
        }

        InfernalExpansion.LOGGER.error("Couldn't find Enum constant {}", configOption);
        return 0.0D;
    }

    public static String getString(Enum<?> configOption) {
        if (configOption instanceof MobInteractions) {
            return (String) mobInteractions.get(configOption).get();
        } else if (configOption instanceof WorldGeneration) {
            return (String) worldGeneration.get(configOption).get();
        }

        InfernalExpansion.LOGGER.error("Couldn't find Enum constant {}", configOption);
        return "";
    }

}
