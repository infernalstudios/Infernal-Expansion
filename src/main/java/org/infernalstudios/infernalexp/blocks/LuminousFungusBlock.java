/*
 * Copyright 2022 Infernal Studios
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

package org.infernalstudios.infernalexp.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.levelgen.feature.HugeFungusConfiguration;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.infernalstudios.infernalexp.blockentities.LuminousFungusBlockEntity;
import org.infernalstudios.infernalexp.config.InfernalExpansionConfig;
import org.infernalstudios.infernalexp.init.IEBlockEntityTypes;
import org.infernalstudios.infernalexp.init.IEConfiguredFeatures;
import org.infernalstudios.infernalexp.init.IEEffects;
import org.infernalstudios.infernalexp.init.IETags;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Random;

public class LuminousFungusBlock extends HorizontalBushBlock implements BonemealableBlock, EntityBlock, BlockEntityTicker<LuminousFungusBlockEntity> {
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    protected static final VoxelShape FLOOR_SHAPE = box(5.0D, 0.0D, 5.0D, 11.0D, 10.0D, 11.0D);
    protected static final VoxelShape CEILING_SHAPE = box(5.0D, 6.0D, 5.0D, 11.0D, 16.0D, 11.0D);

    public LuminousFungusBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(FACE, AttachFace.FLOOR).setValue(FACING, Direction.NORTH).setValue(LIT, Boolean.FALSE));
    }

    @Override
    protected boolean isValidGround(BlockState state, BlockGetter worldIn, BlockPos pos) {
        return state.is(IETags.Blocks.LUMINOUS_FUNGUS_BASE_BLOCKS);
    }

    public boolean canAttachToSurface(LevelReader reader, BlockPos pos, Direction direction) {
        BlockPos blockpos = pos.relative(direction);
        return isValidGround(reader.getBlockState(blockpos), reader, blockpos);
    }

    @Override
    public boolean canSurvive(BlockState state, @NotNull LevelReader worldIn, BlockPos pos) {
        return !state.getValue(FACE).equals(AttachFace.WALL) && canAttachToSurface(worldIn, pos, getConnectedDirection(state).getOpposite());
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, @NotNull BlockGetter worldIn, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        Vec3 vector3d = state.getOffset(worldIn, pos);

        if (state.getValue(FACE) == AttachFace.FLOOR) {
            return FLOOR_SHAPE.move(vector3d.x, vector3d.y, vector3d.z);
        } else {
            return CEILING_SHAPE.move(vector3d.x, vector3d.y, vector3d.z);
        }
    }

    @Override
    public void entityInside(@NotNull BlockState state, Level worldIn, @NotNull BlockPos pos, @NotNull Entity entityIn) {
        if (!worldIn.isClientSide()) {
            if (entityIn instanceof LivingEntity livingEntity && entityIn.isAlive() && InfernalExpansionConfig.Miscellaneous.LUMINOUS_FUNGUS_GIVES_EFFECT.getBool()) {
                livingEntity.addEffect(new MobEffectInstance(IEEffects.LUMINOUS.get(), 120, 0, true, true));
            }
        }
    }


    @Override
    public @NotNull OffsetType getOffsetType() {
        return OffsetType.XZ;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builderIn) {
        builderIn.add(FACING, FACE, LIT);
    }

    /**
     * Whether this IGrowable can grow
     */
    @Override
    public boolean isValidBonemealTarget(BlockGetter worldIn, BlockPos pos, @NotNull BlockState state, boolean isClient) {
        Block block = ((HugeFungusConfiguration) IEConfiguredFeatures.DULLTHORN_TREE_PLANTED.value().config()).validBaseState.getBlock();
        Block block1 = worldIn.getBlockState(pos.below()).getBlock();
        return block1 == block;
    }

    @Override
    public boolean isBonemealSuccess(@NotNull Level worldIn, Random rand, @NotNull BlockPos pos, @NotNull BlockState state) {
        return (double) rand.nextFloat() < 0.4D;
    }

    @Override
    public void performBonemeal(@NotNull ServerLevel worldIn, @NotNull Random rand, @NotNull BlockPos pos, @NotNull BlockState state) {
        IEConfiguredFeatures.DULLTHORN_TREE_PLANTED.value().place(worldIn, worldIn.getChunkSource().getGenerator(), rand, pos);
    }

    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new LuminousFungusBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
        if (!world.isClientSide())
            return createTickerHelper(type, IEBlockEntityTypes.LUMINOUS_FUNGUS.get(), LuminousFungusBlockEntity::tick);

        return null;
    }

    private static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTickerHelper(BlockEntityType<A> type, BlockEntityType<E> correctType, BlockEntityTicker<? super E> ticker) {
        return type == correctType ? (BlockEntityTicker<A>) ticker : null;
    }

    @Override
    public void tick(@NotNull Level p_155253_, @NotNull BlockPos p_155254_, @NotNull BlockState p_155255_, @NotNull LuminousFungusBlockEntity p_155256_) {

    }
}
