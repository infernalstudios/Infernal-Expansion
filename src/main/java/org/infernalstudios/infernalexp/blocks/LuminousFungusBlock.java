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

import javax.annotation.Nullable;
import java.util.Random;

public class LuminousFungusBlock extends HorizontalBushBlock implements BonemealableBlock, EntityBlock, BlockEntityTicker<LuminousFungusBlockEntity> {
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    protected static final VoxelShape FLOOR_SHAPE = box(5.0D, 0.0D, 5.0D, 11.0D, 10.0D, 11.0D);
    protected static final VoxelShape CEILING_SHAPE = box(5.0D, 6.0D, 5.0D, 11.0D, 16.0D, 11.0D);

    public LuminousFungusBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(FACE, AttachFace.FLOOR).setValue(FACING, Direction.NORTH).setValue(LIT, Boolean.valueOf(false)));
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
    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        return !state.getValue(FACE).equals(AttachFace.WALL) && canAttachToSurface(worldIn, pos, getConnectedDirection(state).getOpposite());
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        Vec3 vector3d = state.getOffset(worldIn, pos);

        switch (state.getValue(FACE)) {
            case FLOOR:
                return FLOOR_SHAPE.move(vector3d.x, vector3d.y, vector3d.z);
            case CEILING:
            default:
                return CEILING_SHAPE.move(vector3d.x, vector3d.y, vector3d.z);
        }
    }

    @Override
    public void entityInside(BlockState state, Level worldIn, BlockPos pos, Entity entityIn) {
        if (!worldIn.isClientSide()) {
            if (entityIn instanceof LivingEntity livingEntity && entityIn.isAlive() && InfernalExpansionConfig.Miscellaneous.LUMINOUS_FUNGUS_GIVES_EFFECT.getBool()) {
                livingEntity.addEffect(new MobEffectInstance(IEEffects.LUMINOUS.get(), 120, 0, true, true));
            }
        }
    }


    @Override
    public OffsetType getOffsetType() {
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
    public boolean isValidBonemealTarget(BlockGetter worldIn, BlockPos pos, BlockState state, boolean isClient) {
        Block block = ((HugeFungusConfiguration) IEConfiguredFeatures.DULLTHORN_TREE_PLANTED.value().config()).validBaseState.getBlock();
        Block block1 = worldIn.getBlockState(pos.below()).getBlock();
        return block1 == block;
    }

    @Override
    public boolean isBonemealSuccess(Level worldIn, Random rand, BlockPos pos, BlockState state) {
        return (double) rand.nextFloat() < 0.4D;
    }

    @Override
    public void performBonemeal(ServerLevel worldIn, Random rand, BlockPos pos, BlockState state) {
        IEConfiguredFeatures.DULLTHORN_TREE_PLANTED.value().place(worldIn, worldIn.getChunkSource().getGenerator(), rand, pos);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new LuminousFungusBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
        if (!world.isClientSide())
            return createTickerHelper(type, IEBlockEntityTypes.LUMINOUS_FUNGUS.get(), LuminousFungusBlockEntity::tick);

        return null;
    }

    private static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTickerHelper(BlockEntityType<A> type, BlockEntityType<E> correctType, BlockEntityTicker<? super E> ticker) {
        return type == correctType ? (BlockEntityTicker<A>) ticker : null;
    }

    @Override
    public void tick(Level p_155253_, BlockPos p_155254_, BlockState p_155255_, LuminousFungusBlockEntity p_155256_) {

    }
}
