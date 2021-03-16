package com.nekomaster1000.infernalexp.config.gui.widgets;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.text.ITextComponent;

public class TitleWidget extends Widget {

	public TitleWidget(int x, int y, int width, int height, ITextComponent title) {
		super(x, y, width, height, title);
	}

	@Override
	public void renderButton(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		drawCenteredString(matrixStack, Minecraft.getInstance().fontRenderer, getMessage(), this.x + this.width / 2, this.y + (this.height - 8) / 2, 0xFFFFFF);
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		return false;
	}
}
