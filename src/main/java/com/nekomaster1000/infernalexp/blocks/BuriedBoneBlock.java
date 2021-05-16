package com.nekomaster1000.infernalexp.blocks;

import com.nekomaster1000.infernalexp.init.IEBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.NetherRootsBlock;
import net.minecraft.block.NetherSproutsBlock;
import net.minecraft.block.TallGrassBlock;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.AttachFace;
import net.minecraft.util.Direction;
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import javax.annotation.CheckForNull;

public class BuriedBoneBlock extends HorizontalBushBlock {
    protected static final VoxelShape FLOOR_SHAPE = Block.makeCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, 10.0D, 11.0D);
    protected static final VoxelShape CEILING_SHAPE = Block.makeCuboidShape(5.0D, 6.0D, 5.0D, 11.0D, 16.0D, 11.0D);

    public BuriedBoneBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.getDefaultState().with(FACE, AttachFace.FLOOR));
    }

    @CheckForNull
    public BlockState getPlaceableState(World world, BlockPos pos, Direction placeSide) {
        if (world.getBlockState(pos).getMaterial().isReplaceable() && world.getBlockState(pos).getBlock() != IEBlocks.BURIED_BONE.get()) {
            if (placeSide.getAxis() != Axis.Y) {
                placeSide = Direction.UP;
            }
            Direction attachdirection;
            if (this.isValidGround(world.getBlockState(pos.offset(placeSide.getOpposite())), world, pos)) {
                attachdirection = placeSide.getOpposite();
            } else if (this.isValidGround(world.getBlockState(pos.offset(placeSide)), world, pos)) {
                attachdirection = placeSide;
            } else {
                return null;
            }
            AttachFace attachface;
            if (attachdirection == Direction.UP) {
                attachface = AttachFace.CEILING;
            } else {
                attachface = AttachFace.FLOOR;
            }
            return this.getDefaultState().with(FACE, attachface);
        }
        return null;
    }
    
    @Override
    protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return
			state.matchesBlock(IEBlocks.GLOWDUST_SAND.get()) || state.matchesBlock(Blocks.SAND) || state.matchesBlock(Blocks.RED_SAND) ||

				state.matchesBlock(Blocks.GRASS_BLOCK) || state.matchesBlock(Blocks.DIRT) || state.matchesBlock(Blocks.COARSE_DIRT) ||
				state.matchesBlock(Blocks.PODZOL) || state.matchesBlock(Blocks.MYCELIUM) ||

                state.matchesBlock(Blocks.NETHERRACK) ||
				state.matchesBlock(Blocks.CRIMSON_NYLIUM) || state.matchesBlock(Blocks.NETHER_WART_BLOCK) ||

                state.matchesBlock(Blocks.WARPED_NYLIUM) || state.matchesBlock(Blocks.WARPED_WART_BLOCK) ||

                state.matchesBlock(IEBlocks.CRIMSON_FUNGUS_CAP.get()) || state.matchesBlock(IEBlocks.WARPED_FUNGUS_CAP.get()) ||
                state.matchesBlock(IEBlocks.LUMINOUS_FUNGUS_CAP.get()) ||

				state.matchesBlock(Blocks.SOUL_SAND) || state.matchesBlock(Blocks.SOUL_SOIL) ||

                state.matchesBlock(IEBlocks.SILT.get()) || state.matchesBlock(IEBlocks.RUBBLE.get());
    }

    public boolean canAttach(IWorldReader reader, BlockPos pos, Direction direction) {
        BlockPos blockpos = pos.offset(direction);
        return isValidGround(reader.getBlockState(blockpos), reader, blockpos);
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        return !state.get(FACE).equals(AttachFace.WALL) && canAttach(worldIn, pos, getFacing(state).getOpposite());
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        Vector3d vector3d = state.getOffset(worldIn, pos);

        switch(state.get(FACE)){
            case FLOOR:
                return FLOOR_SHAPE.withOffset(vector3d.x, vector3d.y, vector3d.z);
            case CEILING:
            default:
                return CEILING_SHAPE.withOffset(vector3d.x, vector3d.y, vector3d.z);
        }
    }

    @Override
    public OffsetType getOffsetType() {
        return OffsetType.XZ;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builderIn) {
        builderIn.add(HORIZONTAL_FACING, FACE);
    }

    @Override
    public Item asItem() {
        return Items.BONE;
    }
}
