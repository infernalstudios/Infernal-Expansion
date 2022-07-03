package org.infernalstudios.infernalexp.client.entity.render;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.infernalstudios.infernalexp.entities.RockEntity;

@OnlyIn(Dist.CLIENT)
public class RockRenderer extends EntityRenderer<RockEntity> {

    public RockRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(RockEntity entity) {
        return null;
    }
}
