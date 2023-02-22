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

package org.infernalstudios.infernalexp.data.providers;

import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.ModelProvider;
import net.minecraftforge.client.model.generators.MultiPartBlockStateBuilder;
import org.infernalstudios.infernalexp.InfernalExpansion;
import org.infernalstudios.infernalexp.blocks.VerticalSlabBlock;
import org.infernalstudios.infernalexp.data.DataGenDeferredRegister;
import org.infernalstudios.infernalexp.util.TriFunction;

import java.util.function.Supplier;

public class IEBlockProviders {

    public static final String BLOCK_FOLDER = ModelProvider.BLOCK_FOLDER + "/";

    /**
     * For simple blocks where all six faces have the same texture
     */
    public static BlockProviderConsumer simple() {
        return (provider, block) -> {
            provider.simpleBlock(block.get());
        };
    }

    /**
     * For blocks that have multiple variants that are enumerated 0 - n. E.g: Dimstone
     * @param variants The number of variants. Variants are zero-indexed, so with 25 variants they will be numbered 0 - 24.
     */
    public static BlockProviderConsumer enumerateVariants(int variants) {
        return enumerateVariants(variants, ((models, block, variant) -> configuredModel(
            models.cubeAll(
                BLOCK_FOLDER + name(block) + "/" + variant,
                extend(blockTexture(block), "/" + variant)
            )
        )));
    }

    public static BlockProviderConsumer enumerateVariants(int variants, TriFunction<BlockModelProvider, Block, Integer, ConfiguredModel[]> modelProvider) {
        return (provider, block) -> {
            ConfiguredModel[] models = new ConfiguredModel[variants];

            for (int i = 0; i < models.length; i++) {
                models[i] = modelProvider.apply(provider.models(), block.get(), i)[0];
            }

            provider.simpleBlock(block.get(), models);
        };
    }

    /**
     * For blocks with randomized rotations. E.g: Sand
     */
    public static BlockProviderConsumer randomizeRotations() {
        return (provider, block) -> {
            provider.getVariantBuilder(block.get()).partialState().addModels(ConfiguredModel.allRotations(provider.cubeAll(block.get()), false));
        };
    }

    /**
     * For rotatable pillar blocks that have a top texture and a side texture. E.g: Basalt
     */
    public static BlockProviderConsumer pillar() {
        return (provider, block) -> {
            pillarInternal(provider, block.get(), extend(blockTexture(block.get()), "_side"), extend(blockTexture(block.get()), "_top"));
        };
    }

    /**
     * For rotatable pillar blocks that have only a single texture on all six sides. E.g: Hyphae
     */
    public static BlockProviderConsumer singleTexturePillar() {
        return (provider, block) -> {
            pillarInternal(provider, block.get(), blockTexture(block.get()), blockTexture(block.get()));
        };
    }

    /**
     * For rotatable pillar blocks that have only a single texture on all six sides. E.g: Hyphae
     * @param textureFrom Block to get the texture from
     */
    public static BlockProviderConsumer singleTexturePillar(Supplier<? extends Block> textureFrom) {
        return (provider, block) -> {
            pillarInternal(provider, block.get(), blockTexture(textureFrom.get()), blockTexture(textureFrom.get()));
        };
    }

    private static void pillarInternal(BlockStateProvider provider, Block block, ResourceLocation side, ResourceLocation top) {
        ModelFile vertical = provider.models().cubeColumn(name(block), side, top);
        ModelFile horizontal = provider.models().cubeColumnHorizontal(name(block), side, top);

        provider.getVariantBuilder(block)
            .partialState().with(RotatedPillarBlock.AXIS, Direction.Axis.Y)
            .modelForState().modelFile(vertical).addModel()
            .partialState().with(RotatedPillarBlock.AXIS, Direction.Axis.Z)
            .modelForState().modelFile(horizontal).rotationX(90).addModel()
            .partialState().with(RotatedPillarBlock.AXIS, Direction.Axis.X)
            .modelForState().modelFile(horizontal).rotationX(90).rotationY(90).addModel();
    }


    /**
     * For non-rotatable pillar blocks with bottom, side and top textures. E.g: Crimson Fungus Cap
     */
    public static BlockProviderConsumer staticPillar() {
        return (provider, block) -> {
            provider.simpleBlock(block.get(), provider.models().cubeBottomTop(name(block.get()), extend(blockTexture(block.get()), "_side"), extend(blockTexture(block.get()), "_bottom"), extend(blockTexture(block.get()), "_top")));
        };
    }

    /**
     * For log blocks. Very similar to {@link IEBlockProviders#pillar()} except the side texture doesn't have the "_side" suffix. E.g: Oak Log
     */
    public static BlockProviderConsumer log() {
        return (provider, block) -> {
            provider.logBlock((RotatedPillarBlock) block.get());
        };
    }

    /**
     * For slabs. E.g: Stone Brick Slab
     * @param fullBlock Full block parent to get texture from
     */
    public static BlockProviderConsumer slab(Supplier<? extends Block> fullBlock) {
        return (provider, block) -> {
            provider.slabBlock((SlabBlock) block.get(), location(fullBlock.get()), blockTexture(fullBlock.get()));
        };
    }

    /**
     * For slabs of pillar blocks. E.g: Basalt Slab
     * @param fullBlock Full block parent to get textures from
     */
    public static BlockProviderConsumer pillarSlab(Supplier<? extends Block> fullBlock) {
        return (provider, block) -> {
            provider.slabBlock((SlabBlock) block.get(), location(fullBlock.get()), extend(blockTexture(fullBlock.get()), "_side"), extend(blockTexture(fullBlock.get()), "_top"), extend(blockTexture(fullBlock.get()), "_top"));
        };
    }

    /**
     * For vertical slabs. E.g: Polished Glowstone Vertical Slab
     * @param fullBlock Full block parent to get texture from
     */
    public static BlockProviderConsumer verticalSlab(Supplier<? extends Block> fullBlock) {
        return (provider, block) -> {
            verticalSlabInternal(provider, block.get(), fullBlock.get(), verticalSlabModel(provider.models(), name(block.get()), blockTexture(fullBlock.get()), blockTexture(fullBlock.get())));
        };
    }

    /**
     * For vertical slabs of pillar blocks. E.g: Polished Basalt Vertical Slab
     * @param fullBlock Full block parent to get textures from
     */
    public static BlockProviderConsumer pillarVerticalSlab(Supplier<? extends Block> fullBlock) {
        return (provider, block) -> {
            verticalSlabInternal(provider, block.get(), fullBlock.get(), verticalSlabModel(provider.models(), name(block.get()), extend(blockTexture(fullBlock.get()), "_side"), extend(blockTexture(fullBlock.get()), "_top")));
        };
    }

    private static void verticalSlabInternal(BlockStateProvider provider, Block block, Block fullBlock, ModelFile model) {
        provider.getVariantBuilder(block)
            .partialState().with(VerticalSlabBlock.TYPE, VerticalSlabBlock.VerticalSlabType.NORTH)
            .modelForState().modelFile(model).rotationY(0).uvLock(true).addModel()
            .partialState().with(VerticalSlabBlock.TYPE, VerticalSlabBlock.VerticalSlabType.EAST)
            .modelForState().modelFile(model).rotationY(90).uvLock(true).addModel()
            .partialState().with(VerticalSlabBlock.TYPE, VerticalSlabBlock.VerticalSlabType.SOUTH)
            .modelForState().modelFile(model).rotationY(180).uvLock(true).addModel()
            .partialState().with(VerticalSlabBlock.TYPE, VerticalSlabBlock.VerticalSlabType.WEST)
            .modelForState().modelFile(model).rotationY(270).uvLock(true).addModel()
            .partialState().with(VerticalSlabBlock.TYPE, VerticalSlabBlock.VerticalSlabType.DOUBLE)
            .modelForState().modelFile(provider.models().getExistingFile(location(fullBlock))).addModel();
    }

    private static ModelFile verticalSlabModel(BlockModelProvider models, String name, ResourceLocation side, ResourceLocation top) {
        return models.withExistingParent(name, new ResourceLocation(InfernalExpansion.MOD_ID, BLOCK_FOLDER + "vertical_slab"))
            .texture("bottom", top)
            .texture("top", top)
            .texture("side", side);
    }

    /**
     * For stairs. E.g: Dullstone Brick Stairs
     * @param fullBlock Full block parent to get texture from
     */
    public static BlockProviderConsumer stairs(Supplier<? extends Block> fullBlock) {
        return (provider, block) -> {
            provider.stairsBlock((StairBlock) block.get(), blockTexture(fullBlock.get()));
        };
    }

    /**
     * For stairs of pillar blocks. E.g: Basalt Stairs
     * @param fullBlock Full block parent to get textures from
     */
    public static BlockProviderConsumer pillarStairs(Supplier<? extends Block> fullBlock) {
        return (provider, block) -> {
            provider.stairsBlock((StairBlock) block.get(), extend(blockTexture(fullBlock.get()), "_side"), extend(blockTexture(fullBlock.get()), "_top"), extend(blockTexture(fullBlock.get()), "_top"));
        };
    }

    /**
     * For buttons. E.g: Soul Slate Button
     * @param fullBlock Full block parent to get texture from
     */
    public static BlockProviderConsumer button(Supplier<? extends Block> fullBlock) {
        return (provider, block) -> {
            provider.buttonBlock((ButtonBlock) block.get(), blockTexture(fullBlock.get()));
        };
    }

    /**
     * For buttons of pillar blocks. E.g: Basalt Button
     * @param fullBlock Full block parent to get textures from
     */
    public static BlockProviderConsumer pillarButton(Supplier<? extends Block> fullBlock) {
        return (provider, block) -> {
            provider.buttonBlock((ButtonBlock) block.get(), extend(blockTexture(fullBlock.get()), "_side"));
        };
    }

    /**
     * For pressure plates. E.g: Smooth Glowstone Pressure Plate
     * @param fullBlock Full block parent to get texture from
     */
    public static BlockProviderConsumer pressurePlate(Supplier<? extends Block> fullBlock) {
        return (provider, block) -> {
            provider.pressurePlateBlock((PressurePlateBlock) block.get(), blockTexture(fullBlock.get()));
        };
    }

    /**
     * For pressure plates of pillar blocks. E.g: Polished Basalt Pressure Plate
     * @param fullBlock Full block parent to get textures from
     */
    public static BlockProviderConsumer pillarPressurePlate(Supplier<? extends Block> fullBlock) {
        return (provider, block) -> {
            provider.pressurePlateBlock((PressurePlateBlock) block.get(), extend(blockTexture(fullBlock.get()), "_top"));
        };
    }

    /**
     * For walls. E.g: Soul Stone Wall
     * @param fullBlock Full block parent to get texture from
     */
    public static BlockProviderConsumer wall(Supplier<? extends Block> fullBlock) {
        return (provider, block) -> {
            provider.wallBlock((WallBlock) block.get(), blockTexture(fullBlock.get()));
        };
    }

    /**
     * For walls of pillar blocks. E.g: Basalt Wall
     * @param fullBlock Full block parent to get textures from
     */
    public static BlockProviderConsumer pillarWall(Supplier<? extends Block> fullBlock) {
        return (provider, block) -> {
            provider.wallBlock((WallBlock) block.get(),
                provider.models().wallPost(name(block.get()) + "_post", extend(blockTexture(fullBlock.get()), "_top")),
                provider.models().wallSide(name(block.get()) + "_side", extend(blockTexture(fullBlock.get()), "_side")),
                provider.models().wallSideTall(name(block.get()) + "_side_tall", extend(blockTexture(fullBlock.get()), "_side")));
        };
    }

    /**
     * For layered blocks. E.g: Snow
     * @param fullBlock Full block parent to get texture from
     */
    public static BlockProviderConsumer layer(Supplier<? extends Block> fullBlock) {
        return (provider, block) -> {
            provider.getVariantBuilder(block.get()).forAllStates(state -> {
                int layer = state.getValue(SnowLayerBlock.LAYERS);
                ModelFile model = switch(layer) {
                    case 8 -> provider.models().getExistingFile(location(fullBlock.get()));
                    default -> layerModel(provider.models(), name(block.get()) + "_height" + layer * 2, blockTexture(fullBlock.get()), layer);
                };

                return configuredModel(model);
            });
        };
    }

    private static ModelFile layerModel(BlockModelProvider models, String name, ResourceLocation texture, int layer) {
        return models.withExistingParent(name, new ResourceLocation(BLOCK_FOLDER + "thin_block"))
            .texture("particle", texture)
            .texture("texture", texture)
            .element()
            .from(0, 0, 0)
            .to(16, layer * 2, 16)
            .allFaces((direction, faceBuilder) -> {
                switch (direction) {
                    case UP -> faceBuilder.texture("#texture");
                    default -> faceBuilder.texture("#texture").cullface(direction);
                }
            })
            .end();
    }

    /**
     * For path blocks. E.g: Soul Soil Path
     * @param fullBlock Full block parent to get texture from
     */
    public static BlockProviderConsumer path(Supplier<? extends Block> fullBlock) {
        return (provider, block) -> {
            provider.simpleBlock(block.get(), pathModel(provider, block.get(), fullBlock.get(), fullBlock.get(), false));
        };
    }

    /**
     * For nylium path blocks. E.g: Crimson Nylium Path<br>
     * Similar to {@link IEBlockProviders#path(java.util.function.Supplier)} except it randomizes Y rotation, uses netherrack as the bottom block and uses custom side textures
     * @param fullBlock Full block parent to get texture from
     */
    public static BlockProviderConsumer nyliumPath(Supplier<? extends Block> fullBlock) {
        return (provider, block) -> {
            provider.simpleBlock(block.get(), ConfiguredModel.allYRotations(pathModel(provider, block.get(), fullBlock.get(), Blocks.NETHERRACK, true), 0, false));
        };
    }

    private static ModelFile pathModel(BlockStateProvider provider, Block block, Block fullBlock, Block bottomBlock, boolean customSideTexture) {
        return provider.models().withExistingParent(name(block), new ResourceLocation(BLOCK_FOLDER + "block"))
            .texture("particle", blockTexture(fullBlock))
            .texture("top", blockTexture(block))
            .texture("side", customSideTexture ? extend(blockTexture(block), "_side") : blockTexture(fullBlock))
            .texture("bottom", blockTexture(bottomBlock))
            .element().from(0, 0, 0).to(16, 15, 16)
            .allFaces((direction, faceBuilder) -> {
                switch (direction) {
                    case UP -> faceBuilder.texture("#top").end();
                    case DOWN -> faceBuilder.texture("#bottom").cullface(direction).end();
                    default -> faceBuilder.texture("#side").cullface(direction).end();
                }
            }).end();
    }

    /**
     * For carpet blocks. E.g: Warped Nylium Carpet
     * @param fullBlock Full block parent to get texture from
     */
    public static BlockProviderConsumer carpet(Supplier<? extends Block> fullBlock) {
        return (provider, block) -> {
            provider.simpleBlock(block.get(), provider.models().carpet(name(block.get()), blockTexture(fullBlock.get())));
        };
    }

    /**
     * For thin pane blocks. E.g: Quartz Glass Pane
     * @param fullBlock Full block parent to get texture from
     */
    public static BlockProviderConsumer pane(Supplier<? extends Block> fullBlock) {
        return (provider, block) -> {
            provider.paneBlock((IronBarsBlock) block.get(), blockTexture(fullBlock.get()), extend(blockTexture(fullBlock.get()), "_pane_top"));
        };
    }

    /**
     * For torch blocks. E.g: Glow Torch
     */
    public static BlockProviderConsumer torch() {
        return (provider, block) -> {
            provider.simpleBlock(block.get(), provider.models().torch(name(block.get()), blockTexture(block.get())));
        };
    }

    /**
     * For torch blocks. E.g: Glow Torch
     */
    public static BlockProviderConsumer wallTorch() {
        return (provider, block) -> {
            ModelFile model = provider.models().torchWall(name(block.get()), removeSuffix(blockTexture(block.get()), "_wall"));

            provider.getVariantBuilder(block.get())
                .partialState().with(WallTorchBlock.FACING, Direction.EAST)
                .modelForState().modelFile(model).addModel()
                .partialState().with(WallTorchBlock.FACING, Direction.SOUTH)
                .modelForState().modelFile(model).rotationY(90).addModel()
                .partialState().with(WallTorchBlock.FACING, Direction.WEST)
                .modelForState().modelFile(model).rotationY(180).addModel()
                .partialState().with(WallTorchBlock.FACING, Direction.NORTH)
                .modelForState().modelFile(model).rotationY(270).addModel();
        };
    }

    /**
     * For lantern blocks. E.g: Glow Lantern
     */
    public static BlockProviderConsumer lantern() {
        return (provider, block) -> {
            provider.getVariantBuilder(block.get())
                .partialState().with(LanternBlock.HANGING, true)
                .modelForState().modelFile(provider.models().singleTexture(
                    name(block.get()) + "_hanging",
                    new ResourceLocation(BLOCK_FOLDER + "template_hanging_lantern"),
                    "lantern",
                    extend(blockTexture(block.get()), "_block")
                )).addModel()
                .partialState().with(LanternBlock.HANGING, false)
                .modelForState().modelFile(provider.models().singleTexture(
                    name(block.get()),
                    new ResourceLocation(BLOCK_FOLDER + "template_lantern"),
                    "lantern",
                    extend(blockTexture(block.get()), "_block")
                )).addModel();
        };
    }

    /**
     * For campfire blocks. E.g: Glow Campfire
     */
    public static BlockProviderConsumer campfire() {
        return (provider, block) -> {
            provider.getVariantBuilder(block.get()).forAllStates(state -> {
                ModelFile model;
                if (state.getValue(CampfireBlock.LIT)) {
                    model = provider.models()
                        .withExistingParent(name(block.get()), new ResourceLocation(BLOCK_FOLDER + "template_campfire"))
                        .texture("fire", extend(blockTexture(block.get()), "_fire"))
                        .texture("lit_log", extend(blockTexture(block.get()), "_log_lit"));
                } else {
                    model = provider.models().getExistingFile(new ResourceLocation(BLOCK_FOLDER + "campfire_off"));
                }
                
                int rotationY = switch (state.getValue(CampfireBlock.FACING)) {
                    case WEST -> 90;
                    case NORTH -> 180;
                    case EAST -> 270;
                    default -> 0;
                };

                return configuredModel(model, 0, rotationY);
            });
        };
    }

    /**
     * For fire blocks. E.g: Glow Fire
     */
    public static BlockProviderConsumer fire() {
        return (provider, block) -> {
            ModelFile floor0 = provider.models().singleTexture(name(block.get()) + "_floor0", new ResourceLocation(BLOCK_FOLDER + "template_fire_floor"), "fire", extend(blockTexture(block.get()), "_0"));
            ModelFile floor1 = provider.models().singleTexture(name(block.get()) + "_floor1", new ResourceLocation(BLOCK_FOLDER + "template_fire_floor"), "fire", extend(blockTexture(block.get()), "_1"));
            ModelFile side0 = provider.models().singleTexture(name(block.get()) + "_side0", new ResourceLocation(BLOCK_FOLDER + "template_fire_side"), "fire", extend(blockTexture(block.get()), "_0"));
            ModelFile side1 = provider.models().singleTexture(name(block.get()) + "_side1", new ResourceLocation(BLOCK_FOLDER + "template_fire_side"), "fire", extend(blockTexture(block.get()), "_1"));
            ModelFile sideAlt0 = provider.models().singleTexture(name(block.get()) + "_side_alt0", new ResourceLocation(BLOCK_FOLDER + "template_fire_side_alt"), "fire", extend(blockTexture(block.get()), "_0"));
            ModelFile sideAlt1 = provider.models().singleTexture(name(block.get()) + "_side_alt1", new ResourceLocation(BLOCK_FOLDER + "template_fire_side_alt"), "fire", extend(blockTexture(block.get()), "_1"));

            MultiPartBlockStateBuilder builder = provider.getMultipartBuilder(block.get());
            builder.part().modelFile(floor0).nextModel().modelFile(floor1).addModel().end();

            for (int i = 0; i < 4; i++) {
                builder.part().modelFile(side0).rotationY(i * 90).nextModel()
                    .modelFile(side1).rotationY(i * 90).nextModel()
                    .modelFile(sideAlt0).rotationY(i * 90).nextModel()
                    .modelFile(sideAlt1).rotationY(i * 90).addModel().end();
            }
        };
    }

    /**
     * For potted plants. E.g: Potted Shroomlight Fungus
     * @param fullPlant Full plant parent to get texture from
     */
    public static BlockProviderConsumer pottedPlant(Supplier<? extends Block> fullPlant) {
        return (provider, block) -> {
            provider.simpleBlock(block.get(), provider.models().singleTexture(
                name(block.get()),
                new ResourceLocation(BLOCK_FOLDER + "flower_pot_cross"),
                "plant",
                blockTexture(fullPlant.get())
            ));
        };
    }

    public static String name(Block block) {
        return block.getRegistryName().getPath();
    }

    public static ResourceLocation location(Block block) {
        return block.getRegistryName();
    }

    public static ResourceLocation blockTexture(Block block) {
        return prepend(location(block), BLOCK_FOLDER);
    }

    public static ResourceLocation prepend(ResourceLocation location, String prefix) {
        return new ResourceLocation(location.getNamespace(), prefix + location.getPath());
    }

    public static ResourceLocation extend(ResourceLocation location, String suffix) {
        return new ResourceLocation(location.getNamespace(), location.getPath() + suffix);
    }

    public static ResourceLocation removeSuffix(ResourceLocation location, String suffix) {
        if (location.getPath().endsWith(suffix))
            return new ResourceLocation(
                location.getNamespace(),
                location.getPath().substring(0, location.getPath().length() - suffix.length())
            );
        else
            return location;
    }

    public static ConfiguredModel[] configuredModel(ModelFile model) {
        return configuredModel(model, 0, 0);
    }

    public static ConfiguredModel[] configuredModel(ModelFile model, int rotationX, int rotationY) {
        return new ConfiguredModel[]{new ConfiguredModel(model, rotationX, rotationY, false)};
    }

    @FunctionalInterface
    public interface BlockProviderConsumer extends DataGenDeferredRegister.ProviderConsumer<Block, BlockStateProvider> {}

}
