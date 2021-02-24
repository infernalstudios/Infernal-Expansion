package com.nekomaster1000.infernalexp.blocks;

import com.nekomaster1000.infernalexp.init.IEEntityTypes;
import com.nekomaster1000.infernalexp.init.IEItems;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SweetBerryBushBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class SoulBerryBushBlock extends SweetBerryBushBlock {
    private static final VoxelShape BUSHLING_SHAPE = Block.makeCuboidShape(3.0D, 0.0D, 3.0D, 13.0D, 13.0D, 13.0D);
    
    public SoulBerryBushBlock(Properties properties) {
        super(properties);
    }
    
    @Override
    public ItemStack getItem(IBlockReader worldIn, BlockPos pos, BlockState state) {
        return new ItemStack(IEItems.SOUL_BERRIES.get());
    }
    
    @Override
    public boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return state.isIn(Blocks.SOUL_SAND) || state.isIn(Blocks.SOUL_SOIL);
     }
    
    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        if (state.get(AGE) == 0) {
           return BUSHLING_SHAPE;
        } else {
           return super.getShape(state, worldIn, pos, context);
        }
     }
    
    @Override
    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        if (entityIn instanceof LivingEntity && entityIn.getType() != EntityType.FOX && entityIn.getType() != IEEntityTypes.EMBODY.get()) {
           entityIn.setMotionMultiplier(state, new Vector3d((double)0.8F, 0.75D, (double)0.8F));
           if (!worldIn.isRemote && state.get(AGE) > 0 && (entityIn.lastTickPosX != entityIn.getPosX() || entityIn.lastTickPosZ != entityIn.getPosZ())) {
              double d0 = Math.abs(entityIn.getPosX() - entityIn.lastTickPosX);
              double d1 = Math.abs(entityIn.getPosZ() - entityIn.lastTickPosZ);
              if (d0 >= (double)0.003F || d1 >= (double)0.003F) {
                 entityIn.attackEntityFrom(DamageSource.SWEET_BERRY_BUSH, 1.0F);
              }
           }
        }
     }
    
    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        int i = state.get(AGE);
        boolean flag = i == 3;
        if (!flag && player.getHeldItem(handIn).getItem() == Items.BONE_MEAL) {
           return ActionResultType.PASS;
        } else if (i > 1) {
           int j = 1 + worldIn.rand.nextInt(2);
           spawnAsEntity(worldIn, pos, new ItemStack(IEItems.SOUL_BERRIES.get(), j + (flag ? 1 : 0)));
           worldIn.playSound((PlayerEntity)null, pos, SoundEvents.ITEM_SWEET_BERRIES_PICK_FROM_BUSH, SoundCategory.BLOCKS, 1.0F, 0.8F + worldIn.rand.nextFloat() * 0.4F);
           worldIn.setBlockState(pos, state.with(AGE, Integer.valueOf(1)), 2);
           return ActionResultType.func_233537_a_(worldIn.isRemote);
        } else {
           return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
        }
     }
    
}
