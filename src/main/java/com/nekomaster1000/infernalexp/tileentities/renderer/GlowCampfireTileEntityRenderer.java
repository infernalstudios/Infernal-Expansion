package com.nekomaster1000.infernalexp.tileentities.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.nekomaster1000.infernalexp.blocks.GlowCampfireBlock;
import com.nekomaster1000.infernalexp.tileentities.GlowCampfireTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.vector.Vector3f;

public class GlowCampfireTileEntityRenderer extends TileEntityRenderer<GlowCampfireTileEntity> {
    public GlowCampfireTileEntityRenderer(TileEntityRendererDispatcher p_i226007_1_) {
        super(p_i226007_1_);
    }

    public void render(GlowCampfireTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        Direction direction = tileEntityIn.getBlockState().get(GlowCampfireBlock.FACING);
        NonNullList<ItemStack> nonnulllist = tileEntityIn.getInventory();

        for (int i = 0; i < nonnulllist.size(); ++i) {
            ItemStack itemstack = nonnulllist.get(i);
            if (itemstack != ItemStack.EMPTY) {
                matrixStackIn.push();
                matrixStackIn.translate(0.5D, 0.44921875D, 0.5D);
                Direction direction1 = Direction.byHorizontalIndex((i + direction.getHorizontalIndex()) % 4);
                float f = -direction1.getHorizontalAngle();
                matrixStackIn.rotate(Vector3f.YP.rotationDegrees(f));
                matrixStackIn.rotate(Vector3f.XP.rotationDegrees(90.0F));
                matrixStackIn.translate(-0.3125D, -0.3125D, 0.0D);
                matrixStackIn.scale(0.375F, 0.375F, 0.375F);
                Minecraft.getInstance().getItemRenderer().renderItem(itemstack, ItemCameraTransforms.TransformType.FIXED, combinedLightIn, combinedOverlayIn, matrixStackIn, bufferIn);
                matrixStackIn.pop();
            }
        }
    }
}
