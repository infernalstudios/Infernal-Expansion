package org.infernalstudios.infernalexp.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import org.infernalstudios.infernalexp.InfernalExpansion;
import org.infernalstudios.infernalexp.init.IEEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Random;

@OnlyIn(Dist.CLIENT)
public class InfectionHeartOverlay {
    protected int heartOffset;
    private static final ResourceLocation INFECTION_HEART_TEXTURE = new ResourceLocation(InfernalExpansion.MOD_ID, "textures/gui/infection_gui.png");

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onPreRender(RenderGameOverlayEvent.Pre event) {
        if (event.getType() != RenderGameOverlayEvent.ElementType.HEALTH || event.isCanceled()) {
            return;
        }

        heartOffset = ForgeIngameGui.left_height;
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onPostRender(RenderGameOverlayEvent.Post event) {
        if (event.getType() != RenderGameOverlayEvent.ElementType.HEALTH || event.isCanceled()) {
            return;
        }

        Minecraft mc = Minecraft.getInstance();
        PlayerEntity player = mc.player;

        int left = mc.getMainWindow().getScaledWidth() / 2 - 91;
        int top = mc.getMainWindow().getScaledHeight() - heartOffset;

        if (player.getActivePotionEffect(IEEffects.INFECTION.get()) != null) {
            drawInfectionOverlay(mc, event.getMatrixStack(), left, top);
        }
    }

    public static void drawInfectionOverlay(Minecraft mc, MatrixStack matrixStack, int left, int top) {
        matrixStack.push();
        matrixStack.translate(0, 0, 0.01);

        PlayerEntity player = mc.player;
        float currentHealth = MathHelper.ceil(player.getHealth());
        int ticks = mc.ingameGUI.getTicks();
        Random rand = new Random();
        rand.setSeed(ticks * 312871L);

        float absorb = MathHelper.ceil(player.getAbsorptionAmount());
        boolean highlight = mc.ingameGUI.healthUpdateCounter > (long) ticks && (mc.ingameGUI.healthUpdateCounter - (long) ticks) / 3L % 2L == 1L;


        int healthRows = MathHelper.ceil((player.getMaxHealth() + absorb) / 2.0F / 10.0F);
        int rowHeight = Math.max(10 - (healthRows - 2), 3);

        int regen = -1;
        if (player.isPotionActive(Effects.REGENERATION)) {
            regen = ticks % 25;
        }

        final int TOP = 9 * (mc.world.getWorldInfo().isHardcore() ? 1 : 0);
        final int BACKGROUND = (highlight ? 9 : 0);

        float absorbRemaining = absorb;


        mc.getTextureManager().bindTexture(INFECTION_HEART_TEXTURE);
        RenderSystem.enableBlend();


        for (int i = MathHelper.ceil((player.getMaxHealth() + absorb) / 2.0F) - 1; i >= 0; --i) {
            int row = MathHelper.ceil((float) (i + 1) / 10.0F) - 1;
            int x = left + i % 10 * 8;
            int y = top - row * rowHeight;

            if (currentHealth <= 4) y += rand.nextInt(2);
            if (i == regen) y -= 2;

            mc.ingameGUI.blit(matrixStack, x, y, BACKGROUND, TOP, 9, 9);

            if (highlight) {
                if (i * 2 + 1 < mc.ingameGUI.lastPlayerHealth) {
                    mc.ingameGUI.blit(matrixStack, x, y, 54, TOP, 9, 9);
                } else if (i * 2 + 1 == mc.ingameGUI.lastPlayerHealth) {
                    mc.ingameGUI.blit(matrixStack, x, y, 63, TOP, 9, 9);
                }
            }

            if (absorbRemaining > 0.0F) {
                if (absorbRemaining == absorb && absorb % 2.0F == 1.0F) {
                    mc.ingameGUI.blit(matrixStack, x, y, 63, TOP, 9, 9);
                    absorbRemaining -= 1.0F;
                } else {
                    mc.ingameGUI.blit(matrixStack, x, y, 54, TOP, 9, 9);
                    absorbRemaining -= 2.0F;
                }
            } else {
                if (i * 2 + 1 < currentHealth) {
                    mc.ingameGUI.blit(matrixStack, x, y, 36, TOP, 9, 9);
                } else if (i * 2 + 1 == currentHealth) {
                    mc.ingameGUI.blit(matrixStack, x, y, 45, TOP, 9, 9);
                }
            }
        }

        RenderSystem.disableBlend();
        matrixStack.pop();
        mc.getTextureManager().bindTexture(AbstractGui.GUI_ICONS_LOCATION);
    }
}
