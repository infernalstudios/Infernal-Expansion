package com.nekomaster1000.infernalexp.config.gui.widgets;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.GameSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.IBidiTooltip;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.text.ITextComponent;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;
import java.util.Optional;

@OnlyIn(Dist.CLIENT)
public class IETextFieldWidget extends TextFieldWidget implements IBidiTooltip {

	private final GameSettings settings;
	private final TextFieldOption option;

	public IETextFieldWidget(GameSettings settings, int x, int y, int width, ITextComponent title, TextFieldOption option) {
        super(Minecraft.getInstance().fontRenderer, x + 2 + 100, y, width - 4 - 100, 20, title);
        this.option = option;
        this.settings = settings;

        setMaxStringLength(1892);
        setText(option.get(settings));
    }

	@Override
	public void renderWidget(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		super.renderWidget(matrixStack, mouseX, mouseY, partialTicks);
		// The parameter names for this function are wrong. The three integers at the end should be x, y, color
		drawString(matrixStack, Minecraft.getInstance().fontRenderer, this.getMessage(), this.x - 100, (this.y + (this.height - 8) / 2), -6250336);
	}

	@Override
	public void onTextChanged(String newText) {
		super.onTextChanged(newText);
		option.set(settings, newText);
	}

	@Override
	public Optional<List<IReorderingProcessor>> func_241867_d() {
		return option.getOptionValues();
	}
}
