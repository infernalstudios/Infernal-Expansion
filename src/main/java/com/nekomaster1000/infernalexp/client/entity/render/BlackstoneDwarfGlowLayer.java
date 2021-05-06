package com.nekomaster1000.infernalexp.client.entity.render;

import com.nekomaster1000.infernalexp.InfernalExpansion;
import com.nekomaster1000.infernalexp.entities.BasaltGiantEntity;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.AbstractEyesLayer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
class BlackstoneDwarfGlowLayer<T extends BasaltGiantEntity, M extends EntityModel<T>> extends AbstractEyesLayer<T,M> {
    private static final RenderType RENDER_TYPE = RenderType.getEyes(new ResourceLocation(InfernalExpansion.MOD_ID,
            "textures/entity/blackstone_dwarf_glow.png"));

    public BlackstoneDwarfGlowLayer(IEntityRenderer<T, M> rendererIn) {
        super(rendererIn);
    }

    @Override
    public RenderType getRenderType() {
        return RENDER_TYPE;
    }
}
