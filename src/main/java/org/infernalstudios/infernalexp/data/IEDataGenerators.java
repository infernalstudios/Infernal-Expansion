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

package org.infernalstudios.infernalexp.data;

import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.TagsProvider;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import org.infernalstudios.infernalexp.InfernalExpansion;
import org.infernalstudios.infernalexp.init.IEBiomeTags;
import org.infernalstudios.infernalexp.init.IEBlockTags;
import org.infernalstudios.infernalexp.init.IEBlocks;
import org.infernalstudios.infernalexp.init.IEItemTags;
import org.infernalstudios.infernalexp.init.IEItems;
import org.infernalstudios.infernalexp.init.IEStructureTags;
import org.jetbrains.annotations.NotNull;

@Mod.EventBusSubscriber(modid = InfernalExpansion.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class IEDataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper fileHelper = event.getExistingFileHelper();

        generator.addProvider(new BlockStateProvider(generator, InfernalExpansion.MOD_ID, fileHelper) {
            @Override
            protected void registerStatesAndModels() {
                IEBlocks.BLOCKS.getDataProviders().forEach((dataProvider, block) -> {
                    dataProvider.accept(this, block);
                });
            }
        });

        generator.addProvider(new ItemModelProvider(generator, InfernalExpansion.MOD_ID, fileHelper) {
            @Override
            protected void registerModels() {
                IEItems.ITEMS.getDataProviders().forEach((dataProvider, item) -> {
                    dataProvider.accept(this, item);
                });
            }
        });

        generator.addProvider(new TagsProvider<>(generator, Registry.BLOCK, InfernalExpansion.MOD_ID, fileHelper) {
            @Override
            protected void addTags() {
                IEBlockTags.TAGS.forEach((dataProvider, tag) -> {
                    dataProvider.accept(this, tag);
                });
            }

            @Override
            public @NotNull String getName() {
                return "IE Block Tags";
            }
        });

        generator.addProvider(new TagsProvider<>(generator, Registry.ITEM, InfernalExpansion.MOD_ID, fileHelper) {
            @Override
            protected void addTags() {
                IEItemTags.TAGS.forEach((dataProvider, tag) -> {
                    dataProvider.accept(this, tag);
                });
            }

            @Override
            public @NotNull String getName() {
                return "IE Item Tags";
            }

        });

        generator.addProvider(new TagsProvider<>(generator, BuiltinRegistries.BIOME, InfernalExpansion.MOD_ID, fileHelper) {
            @Override
            protected void addTags() {
                IEBiomeTags.TAGS.forEach((dataProvider, tag) -> {
                    dataProvider.accept(this, tag);
                });
            }

            @Override
            public @NotNull String getName() {
                return "IE Biome Tags";
            }
        });

        generator.addProvider(new TagsProvider<>(generator, BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE, InfernalExpansion.MOD_ID, fileHelper) {
            @Override
            protected void addTags() {
                IEStructureTags.TAGS.forEach((dataProvider, tag) -> {
                    dataProvider.accept(this, tag);
                });
            }

            @Override
            public String getName() {
                return "IE Structure Tags";
            }

        });
    }

}
