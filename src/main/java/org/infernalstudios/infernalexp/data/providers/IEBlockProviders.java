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
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.ModelProvider;
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
        return enumerateVariants(variants, ((provider, block, variant) -> {
            return new ConfiguredModel(provider.models().cubeAll(
                BLOCK_FOLDER + name(block) + "/" + variant,
                extend(blockTexture(block), "/" + variant)
            ));
        }));
    }

    public static BlockProviderConsumer enumerateVariants(int variants, TriFunction<BlockStateProvider, Block, Integer, ConfiguredModel> modelProvider) {
        return (provider, block) -> {
            ConfiguredModel[] models = new ConfiguredModel[variants];

            for (int i = 0; i < models.length; i++) {
                models[i] = modelProvider.apply(provider, block.get(), i);
            }

            provider.simpleBlock(block.get(), models);
        };
    }

    /**
     * For blocks with randomized rotations. E.g: Sand
     */
    public static BlockProviderConsumer randomizeRotations() {
        return (provider, block) -> {
            ConfiguredModel[] models = new ConfiguredModel[16];

            for (int x = 0; x < 4; x++) {
                for (int y = 0; y < 4; y++) {
                    models[(x * 4) + y] = ConfiguredModel.builder()
                        .modelFile(provider.cubeAll(block.get()))
                        .rotationX(x * 90)
                        .rotationY(y * 90)
                        .buildLast();
                }
            }

            provider.getVariantBuilder(block.get())
                .partialState().addModels(models);
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
            provider.getVariantBuilder(block.get())
                .forAllStates(state -> {
                    int layer = state.getValue(SnowLayerBlock.LAYERS);
                    ModelFile model = switch(layer) {
                        case 8 -> provider.models().getExistingFile(location(fullBlock.get()));
                        default -> layerModel(provider.models(), name(block.get()) + "_height" + layer * 2, blockTexture(fullBlock.get()), layer);
                    };

                    return ConfiguredModel.builder().modelFile(model).build();
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

    @FunctionalInterface
    public interface BlockProviderConsumer extends DataGenDeferredRegister.ProviderConsumer<Block, BlockStateProvider> {}

}
