package org.infernalstudios.infernalexp.client.entity.render;

import java.util.Random;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import org.infernalstudios.infernalexp.entities.RockEntity;
import org.jetbrains.annotations.NotNull;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.model.data.EmptyModelData;

@OnlyIn(Dist.CLIENT)
public class RockRenderer extends EntityRenderer<RockEntity> {

    private final Random random = new Random();

    public RockRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.shadowRadius = 0.15F;
        this.shadowStrength = 0.75F;
    }

    @Override
    public void render(RockEntity rock, float p_115037_, float p_115038_, @NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int packedLightIn) {
        BlockState blockState = rock.getBlockState();

        if (blockState.getRenderShape() == RenderShape.MODEL) {
            poseStack.pushPose();

            BlockPos blockPos = new BlockPos(rock.getX(), rock.getBoundingBox().maxY, rock.getZ());
            poseStack.translate(-0.5D, 0.0D, -0.5D);
            BlockRenderDispatcher blockRenderDispatcher = Minecraft.getInstance().getBlockRenderer();

            for (RenderType type: RenderType.chunkBufferLayers()) {
                if (ItemBlockRenderTypes.canRenderInLayer(blockState, type)) {
                    ForgeHooksClient.setRenderType(type);
                    blockRenderDispatcher.getModelRenderer().tesselateBlock(rock.getLevel(), blockRenderDispatcher.getBlockModel(blockState), blockState, blockPos, poseStack, buffer.getBuffer(type), false, random, blockState.getSeed(blockPos), OverlayTexture.NO_OVERLAY, EmptyModelData.INSTANCE);
                }
            }

            ForgeHooksClient.setRenderType(null);

        }

        poseStack.popPose();
        super.render(rock, p_115037_, p_115038_, poseStack, buffer, packedLightIn);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull RockEntity rock) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
