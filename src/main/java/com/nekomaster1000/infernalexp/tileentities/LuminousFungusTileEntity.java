package com.nekomaster1000.infernalexp.tileentities;

import com.nekomaster1000.infernalexp.blocks.LuminousFungusBlock;
import com.nekomaster1000.infernalexp.config.InfernalExpansionConfig;
import com.nekomaster1000.infernalexp.init.IETileEntityTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;

import java.util.List;

public class LuminousFungusTileEntity extends TileEntity implements ITickableTileEntity {

    private int lightTime = 0;
    
    public LuminousFungusTileEntity() {
        super(IETileEntityTypes.LUMINOUS_FUNGUS_TILE_ENTITY.get());
    }

    @Override
    public void tick() {
        List<LivingEntity> nearbyEntities = this.getWorld().getEntitiesWithinAABB(LivingEntity.class,
                new AxisAlignedBB(this.getPos()).grow(InfernalExpansionConfig.luminousFungusActivateDistance));
        boolean shouldLight = false;
        for (LivingEntity entity : nearbyEntities) {
            double motion = entity.getMotion().length();
            if (motion >= 0.08D) {
                shouldLight = true;                                    
                break;
            }
        }
        if (lightTime == 0) {
            this.world.setBlockState(this.pos, this.getBlockState().with(LuminousFungusBlock.LIT, shouldLight));
        }
        if (lightTime != 0) {
            this.lightTime--;
        }
        if (shouldLight) this.lightTime = 60;
    }
}
