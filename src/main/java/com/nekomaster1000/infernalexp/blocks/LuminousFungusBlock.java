package com.nekomaster1000.infernalexp.blocks;

import com.nekomaster1000.infernalexp.config.InfernalExpansionConfig;
import com.nekomaster1000.infernalexp.entities.BlindsightEntity;
import com.nekomaster1000.infernalexp.init.IEBlocks;
import com.nekomaster1000.infernalexp.init.IEConfiguredFeatures;
import com.nekomaster1000.infernalexp.init.IEEffects;
import com.nekomaster1000.infernalexp.tileentities.LuminousFungusTileEntity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IGrowable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.AttachFace;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.HugeFungusConfig;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.Random;

public class LuminousFungusBlock extends HorizontalBushBlock implements IGrowable {
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    protected static final VoxelShape FLOOR_SHAPE = Block.makeCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, 10.0D, 11.0D);
    protected static final VoxelShape CEILING_SHAPE = Block.makeCuboidShape(5.0D, 6.0D, 5.0D, 11.0D, 16.0D, 11.0D);

    public LuminousFungusBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.getDefaultState().with(FACE, AttachFace.FLOOR).with(HORIZONTAL_FACING, Direction.NORTH).with(LIT, Boolean.valueOf(false)));
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return
			state.matchesBlock(IEBlocks.GLOWDUST_SAND.get()) || state.matchesBlock(Blocks.SAND) || state.matchesBlock(Blocks.RED_SAND)
				|| state.matchesBlock(Blocks.GRASS) || state.matchesBlock(Blocks.GRASS_BLOCK) ||
				state.matchesBlock(Blocks.DIRT) || state.matchesBlock(Blocks.COARSE_DIRT) || state.matchesBlock(Blocks.FARMLAND) ||
				state.matchesBlock(Blocks.PODZOL) || state.matchesBlock(Blocks.MYCELIUM) ||
				state.matchesBlock(Blocks.CRIMSON_NYLIUM) || state.matchesBlock(Blocks.WARPED_NYLIUM) ||
				state.matchesBlock(Blocks.SOUL_SOIL) || state.matchesBlock(Blocks.GLOWSTONE) || state.matchesBlock(IEBlocks.DIMSTONE.get()) ||
				state.matchesBlock(IEBlocks.DULLSTONE.get()) || state.matchesBlock(IEBlocks.DULLTHORNS.get())
			;
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
    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        if (!worldIn.isRemote()) {
            if (entityIn instanceof LivingEntity && entityIn.isAlive() && InfernalExpansionConfig.Miscellaneous.LUMINOUS_FUNGUS_GIVES_EFFECT.getBool()) {
                LivingEntity livingEntity = (LivingEntity) entityIn;
                livingEntity.addPotionEffect(new EffectInstance(IEEffects.LUMINOUS.get(), 120, 0, true, true));
            }
        }
    }


    @Override
    public OffsetType getOffsetType() {
        return OffsetType.XZ;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builderIn) {
        builderIn.add(HORIZONTAL_FACING, FACE, LIT);
    }
    
	/**
	 * Whether this IGrowable can grow
	 */
	@Override
	public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
		Block block = ((HugeFungusConfig) (IEConfiguredFeatures.DULLTHORN_TREE_PLANTED).config).validBaseBlock.getBlock();
		Block block1 = worldIn.getBlockState(pos.down()).getBlock();
		return block1 == block;
	}

	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
		return (double) rand.nextFloat() < 0.4D;
	}

	@Override
	public void grow(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state) {
		IEConfiguredFeatures.DULLTHORN_TREE_PLANTED.generate(worldIn, worldIn.getChunkProvider().getChunkGenerator(), rand, pos);
	}

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new LuminousFungusTileEntity();
    }
}
