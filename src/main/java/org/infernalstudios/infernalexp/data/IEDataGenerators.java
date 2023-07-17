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

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.data.loot.EntityLoot;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import org.infernalstudios.infernalexp.InfernalExpansion;
import org.infernalstudios.infernalexp.data.providers.IEChestLoot;
import org.infernalstudios.infernalexp.data.providers.lang.DeDeLanguageProvider;
import org.infernalstudios.infernalexp.data.providers.lang.EsArLanguageProvider;
import org.infernalstudios.infernalexp.data.providers.lang.EsEsLanguageProvider;
import org.infernalstudios.infernalexp.data.providers.lang.FrCaLanguageProvider;
import org.infernalstudios.infernalexp.data.providers.lang.FrFrLanguageProvider;
import org.infernalstudios.infernalexp.data.providers.lang.HeIlLanguageProvider;
import org.infernalstudios.infernalexp.data.providers.lang.IELanguageProvider;
import org.infernalstudios.infernalexp.data.providers.lang.IdIdLanguageProvider;
import org.infernalstudios.infernalexp.data.providers.lang.JaJpLanguageProvider;
import org.infernalstudios.infernalexp.data.providers.lang.LolUsLanguageProvider;
import org.infernalstudios.infernalexp.data.providers.lang.MsMyLanguageProvider;
import org.infernalstudios.infernalexp.data.providers.lang.NlNlLanguageProvider;
import org.infernalstudios.infernalexp.data.providers.lang.PlPlLanguageProvider;
import org.infernalstudios.infernalexp.data.providers.lang.PtBrLanguageProvider;
import org.infernalstudios.infernalexp.data.providers.lang.RoRoLanguageProvider;
import org.infernalstudios.infernalexp.data.providers.lang.RuRuLanguageProvider;
import org.infernalstudios.infernalexp.data.providers.lang.ZhTwLanguageProvider;
import org.infernalstudios.infernalexp.data.providers.recipes.IERecipeProvider;
import org.infernalstudios.infernalexp.init.IEBiomeTags;
import org.infernalstudios.infernalexp.init.IEBlockTags;
import org.infernalstudios.infernalexp.init.IEBlocks;
import org.infernalstudios.infernalexp.init.IEEntityTypes;
import org.infernalstudios.infernalexp.init.IEItemTags;
import org.infernalstudios.infernalexp.init.IEItems;
import org.infernalstudios.infernalexp.init.IEStructureTags;
import org.jetbrains.annotations.NotNull;

import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = InfernalExpansion.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class IEDataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper fileHelper = event.getExistingFileHelper();

        generator.addProvider(new BlockStateProvider(generator, InfernalExpansion.MOD_ID, fileHelper) {
            @Override
            protected void registerStatesAndModels() {
                IEBlocks.BLOCKS.getDataProviders().forEach(pair -> {
                    pair.getSecond().accept(this, pair.getFirst());
                });
            }
        });

        generator.addProvider(new ItemModelProvider(generator, InfernalExpansion.MOD_ID, fileHelper) {
            @Override
            protected void registerModels() {
                IEItems.ITEMS.getDataProviders().forEach(pair -> {
                    pair.getSecond().accept(this, pair.getFirst());
                });
            }
        });

        generator.addProvider(new LootTableProvider(generator) {
            @Override
            protected @NotNull List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> getTables() {
                return List.of(
                    Pair.of(
                        () -> new BlockLoot() {
                            @Override
                            protected void addTables() {
                                IEBlocks.BLOCKS.getLootProviders().forEach(pair -> {
                                    pair.getSecond().accept(this, pair.getFirst());
                                });
                            }

                            @Override
                            protected @NotNull Iterable<Block> getKnownBlocks() {
                                return IEBlocks.BLOCKS.getRegister().getEntries().stream().map(RegistryObject::get).collect(Collectors.toList());
                            }
                        },
                        LootContextParamSets.BLOCK
                    ),
                    Pair.of(
                        () -> new EntityLoot() {
                            @Override
                            protected void addTables() {
                                IEEntityTypes.ENTITY_TYPES.getLootProviders().forEach(pair -> {
                                    pair.getSecond().accept(this, pair.getFirst());
                                });
                            }

                            @Override
                            protected @NotNull Iterable<EntityType<?>> getKnownEntities() {
                                return IEEntityTypes.ENTITY_TYPES.getRegister().getEntries().stream().map(RegistryObject::get).collect(Collectors.toList());
                            }
                        },
                        LootContextParamSets.ENTITY
                    ),
                    Pair.of(IEChestLoot::new, LootContextParamSets.CHEST)
                );
            }

            @Override
            protected void validate(@NotNull Map<ResourceLocation, LootTable> map, @NotNull ValidationContext validationTracker) {}
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
            public @NotNull String getName() {
                return "IE Structure Tags";
            }

        });

        generator.addProvider(new IERecipeProvider(generator));

        generator.addProvider(new IELanguageProvider(generator) {
            @Override
            protected void addTranslations() {
                addBaseTranslations();
            }
        });
        generator.addProvider(new DeDeLanguageProvider(generator));
        generator.addProvider(new EsArLanguageProvider(generator));
        generator.addProvider(new EsEsLanguageProvider(generator));
        generator.addProvider(new FrCaLanguageProvider(generator));
        generator.addProvider(new FrFrLanguageProvider(generator));
        generator.addProvider(new HeIlLanguageProvider(generator));
        generator.addProvider(new IdIdLanguageProvider(generator));
        generator.addProvider(new JaJpLanguageProvider(generator));
        generator.addProvider(new LolUsLanguageProvider(generator));
        generator.addProvider(new MsMyLanguageProvider(generator));
        generator.addProvider(new NlNlLanguageProvider(generator));
        generator.addProvider(new PlPlLanguageProvider(generator));
        generator.addProvider(new PtBrLanguageProvider(generator));
        generator.addProvider(new RoRoLanguageProvider(generator));
        generator.addProvider(new RuRuLanguageProvider(generator));
        generator.addProvider(new ZhTwLanguageProvider(generator));
    }

}
