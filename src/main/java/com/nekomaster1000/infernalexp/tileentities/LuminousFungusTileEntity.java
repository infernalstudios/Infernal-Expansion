package com.nekomaster1000.infernalexp.tileentities;

import com.nekomaster1000.infernalexp.blocks.LuminousFungusBlock;
import com.nekomaster1000.infernalexp.init.IETileEntityTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;

import java.util.List;

public class LuminousFungusTileEntity extends TileEntity implements ITickableTileEntity {

    public LuminousFungusTileEntity() {
        super(IETileEntityTypes.LUMINOUS_FUNGUS_TILE_ENTITY.get());
    }

    @Override
    public void tick() {
        List<LivingEntity> nearbyEntities = this.getWorld().getEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(this.getPos()).grow(16.0d));
        if (!nearbyEntities.isEmpty()) {
            this.world.setBlockState(this.pos, this.getBlockState().with(LuminousFungusBlock.LIT, Boolean.valueOf(true)));
        } else {
            this.world.setBlockState(this.pos, this.getBlockState().with(LuminousFungusBlock.LIT, Boolean.valueOf(false)));
        }
    }
}
