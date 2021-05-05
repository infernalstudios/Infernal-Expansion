package com.nekomaster1000.infernalexp.blocks;

import com.nekomaster1000.infernalexp.entities.BlindsightEntity;
import com.nekomaster1000.infernalexp.init.IEBlocks;
import com.nekomaster1000.infernalexp.init.IEEffects;
import com.nekomaster1000.infernalexp.init.IEItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.BushBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathType;
import net.minecraft.potion.EffectInstance;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.IForgeShearable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class DullthornsBlock extends BushBlock implements IForgeShearable {
    public static final IntegerProperty AGE = BlockStateProperties.AGE_0_15;
    protected static final VoxelShape SHAPE = Block.makeCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, 15.0D, 11.0D);

    public DullthornsBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.getDefaultState().with(AGE, Integer.valueOf(0)));
    }

    @Override
    public boolean isShearable(@Nonnull ItemStack item, World world, BlockPos pos) {
        return true;
    }

    @Nonnull
    @Override
    public List<ItemStack> onSheared(@Nullable PlayerEntity player, @Nonnull ItemStack item, World world, BlockPos pos, int fortune) {
        return new ArrayList<>(Collections.singleton(new ItemStack(IEItems.DULLTHORNS.get())));
    }

    @Override
    protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return
			state.matchesBlock(IEBlocks.GLOWDUST_SAND.get()) || state.matchesBlock(Blocks.SAND) || state.matchesBlock(Blocks.RED_SAND) ||

                state.matchesBlock(Blocks.GRASS_BLOCK) || state.matchesBlock(Blocks.DIRT) ||
                state.matchesBlock(Blocks.COARSE_DIRT) || state.matchesBlock(Blocks.FARMLAND) ||
				state.matchesBlock(Blocks.PODZOL) || state.matchesBlock(Blocks.MYCELIUM) ||

				state.matchesBlock(Blocks.CRIMSON_NYLIUM) || state.matchesBlock(Blocks.WARPED_NYLIUM) ||

				state.matchesBlock(Blocks.SOUL_SAND) || state.matchesBlock(Blocks.SOUL_SOIL) ||

				state.matchesBlock(Blocks.GLOWSTONE) || state.matchesBlock(IEBlocks.DIMSTONE.get()) ||
				state.matchesBlock(IEBlocks.DULLSTONE.get()) ||

                state.matchesBlock(IEBlocks.DULLTHORNS.get())
			;
    }

    @Override
    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
        if (!worldIn.isAreaLoaded(pos, 1)) return; // Forge: prevent growing cactus from loading unloaded chunks with block update
        if (!state.isValidPosition(worldIn, pos)) {
            worldIn.destroyBlock(pos, true);
        }

    }

    /**
     * Performs a random tick on a block.
     */
    //I have no idea what overriding does LOL - Neko
    //Shut up neko - Swan
    @Override
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        BlockPos blockpos = pos.up();
        if (worldIn.isAirBlock(blockpos)) {
            int i;
			for (i = 1; worldIn.getBlockState(pos.down(i)).matchesBlock(this); ++i) {
			}

            if (i < 9) {
                int j = state.get(AGE);
                if(net.minecraftforge.common.ForgeHooks.onCropsGrowPre(worldIn, blockpos, state, true)) {
                    if (j == 15) {
                        worldIn.setBlockState(blockpos, this.getDefaultState());
                        BlockState blockstate = state.with(AGE, Integer.valueOf(0));
                        worldIn.setBlockState(pos, blockstate, 4);
                        blockstate.neighborChanged(worldIn, blockpos, this, pos, false);
                    } else {
                        worldIn.setBlockState(pos, state.with(AGE, Integer.valueOf(j + 1)), 4);
                    }
                    net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos, state);
                }
            }
        }
    }

    @Override
    public boolean isLadder(BlockState state, net.minecraft.world.IWorldReader world, BlockPos pos, net.minecraft.entity.LivingEntity entity) {
       return true;
    }

    @Override
    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        if (!worldIn.isRemote()) {
            if (entityIn instanceof LivingEntity && entityIn.isAlive() && !(entityIn instanceof BlindsightEntity) ) {
                LivingEntity livingEntity = (LivingEntity) entityIn;
                livingEntity.addPotionEffect(new EffectInstance(IEEffects.LUMINOUS.get(), 200, 0, true, true));
            }
            entityIn.attackEntityFrom(DamageSource.CACTUS, 1.0F);
        }
    }
    
    @Override
    public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
    	return false;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        Vector3d vector3d = state.getOffset(worldIn, pos);
        return SHAPE.withOffset(vector3d.x, vector3d.y, vector3d.z);
    }

    @Override
    public OffsetType getOffsetType() {
        return OffsetType.XZ;
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }
}
