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

package org.infernalstudios.infernalexp.init;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import org.infernalstudios.infernalexp.InfernalExpansion;
import org.infernalstudios.infernalexp.data.DataProviderCollection;
import org.infernalstudios.infernalexp.data.providers.IETagsProviders;

public class IEBiomeTags {

    public static final DataProviderCollection<TagKey<Biome>, IETagsProviders<Biome>> TAGS = new DataProviderCollection<>();

    public static final TagKey<Biome> HAS_GLOWSTONE_CANYON_RUIN = tag("has_structure/glowstone_canyon_ruin", IETagsProviders.simple(IEBiomes.GLOWSTONE_CANYON));
    public static final TagKey<Biome> HAS_SOUL_SAND_VALLEY_RUIN = tag("has_structure/soul_sand_valley_ruin", IETagsProviders.simple(Biomes.SOUL_SAND_VALLEY));
    public static final TagKey<Biome> HAS_BASTION_OUTPOST = tag("has_structure/bastion_outpost", IETagsProviders.simple(IEBiomes.GLOWSTONE_CANYON));
    public static final TagKey<Biome> HAS_STRIDER_ALTAR = tag("has_structure/strider_altar", IETagsProviders.simple(Biomes.BASALT_DELTAS));

    private static TagKey<Biome> tag(String name, IETagsProviders.TagProviderConsumer<Biome> tagProvider) {
        TagKey<Biome> tag = TagKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(InfernalExpansion.MOD_ID, name));
        TAGS.addProvider(() -> tag, tagProvider);
        return tag;
    }

}
