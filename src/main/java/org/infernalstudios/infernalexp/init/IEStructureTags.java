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

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.StructureFeatures;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import org.infernalstudios.infernalexp.InfernalExpansion;
import org.infernalstudios.infernalexp.data.providers.IETagProviders;

public class IEStructureTags {

    public static final List<Pair<Supplier<TagKey<ConfiguredStructureFeature<?, ?>>>, IETagProviders.TagProviderConsumer<ConfiguredStructureFeature<?, ?>>>> TAGS = new ArrayList<>();

    public static final TagKey<ConfiguredStructureFeature<?, ?>> NO_INTERSECTING_FEATURES = tag("no_intersecting_features", IETagProviders.simple(
        IEConfiguredStructures.GLOWSTONE_CANYON_RUIN.value(), IEConfiguredStructures.SOUL_SAND_VALLEY_RUIN.value(), IEConfiguredStructures.BASTION_OUTPOST.value(),
        IEConfiguredStructures.STRIDER_ALTAR.value(), StructureFeatures.FORTRESS.value(), StructureFeatures.BASTION_REMNANT.value()
    ));

    private static TagKey<ConfiguredStructureFeature<?, ?>> tag(String name, IETagProviders.TagProviderConsumer<ConfiguredStructureFeature<?, ?>> tagProvider) {
        TagKey<ConfiguredStructureFeature<?, ?>> tag = TagKey.create(Registry.CONFIGURED_STRUCTURE_FEATURE_REGISTRY, new ResourceLocation(InfernalExpansion.MOD_ID, name));
        TAGS.add(Pair.of(() -> tag, tagProvider));
        return tag;
    }

}
