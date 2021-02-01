package com.nekomaster1000.infernalexp.blocks;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class FungusCapBlock extends Block {

    public FungusCapBlock(Properties properties) {
        super(properties);
    }
    
    public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {
       if (entityIn.isSuppressingBounce()) {
           entityIn.onLivingFall(fallDistance, 0.75F);
       } else {
          entityIn.onLivingFall(fallDistance, 0.1F);
       }
    }
    
    public void onLanded(IBlockReader worldIn, Entity entityIn) {
       if (entityIn.isSuppressingBounce()) {
          super.onLanded(worldIn, entityIn);
       } else {
          bounceEntity(entityIn);
       }
    }

    private void bounceEntity(Entity entity) {
       Vector3d vector3d = entity.getMotion();
       if (vector3d.y < 0.0D) {
          double d0 = entity instanceof LivingEntity ? 0.5D : 0.3D;
          entity.setMotion(vector3d.x, -vector3d.y * d0, vector3d.z);
       }
    }

}
