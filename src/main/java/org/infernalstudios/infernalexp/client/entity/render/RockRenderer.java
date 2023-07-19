package org.infernalstudios.infernalexp.client.entity.render;

import java.util.Random;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.infernalstudios.infernalexp.entities.RockEntity;
import org.jetbrains.annotations.NotNull;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RockRenderer extends EntityRenderer<RockEntity> {
    private final ItemRenderer itemRenderer;
    private final Random random = new Random();

    public RockRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.itemRenderer = context.getItemRenderer();
        this.shadowRadius = 0.15F;
        this.shadowStrength = 0.75F;
    }

    @Override
    public void render(RockEntity rock, float p_115037_, float p_115038_, PoseStack poseStack, @NotNull MultiBufferSource buffer, int packedLightIn) {
        poseStack.pushPose();
        ItemStack itemstack = rock.getItem();
        int i = itemstack.isEmpty() ? 187 : Item.getId(itemstack.getItem()) + itemstack.getDamageValue();
        this.random.setSeed(i);
        BakedModel bakedmodel = this.itemRenderer.getModel(itemstack, rock.level, null, rock.getId());
        boolean flag = bakedmodel.isGui3d();

        poseStack.pushPose();
        this.itemRenderer.render(itemstack, ItemTransforms.TransformType.GROUND, false, poseStack, buffer, packedLightIn, OverlayTexture.NO_OVERLAY, bakedmodel);
        poseStack.popPose();
        if (!flag) {
            poseStack.translate(0.0, 0.0, 0.09375F);
        }

        poseStack.popPose();
        super.render(rock, p_115037_, p_115038_, poseStack, buffer, packedLightIn);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull RockEntity rock) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
