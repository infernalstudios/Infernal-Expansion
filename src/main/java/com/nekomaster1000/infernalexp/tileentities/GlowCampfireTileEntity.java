package com.nekomaster1000.infernalexp.tileentities;

import com.nekomaster1000.infernalexp.init.IETileEntityTypes;

import net.minecraft.tileentity.CampfireTileEntity;
import net.minecraft.tileentity.TileEntityType;

public class GlowCampfireTileEntity extends CampfireTileEntity {
    public GlowCampfireTileEntity() {
        super();
    }
    
    public TileEntityType<?> getType() {
        return IETileEntityTypes.GLOW_CAMPFIRE.get();
    }
}
