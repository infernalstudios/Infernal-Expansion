package com.nekomaster1000.infernalexp.config.gui.screens;

import com.mojang.blaze3d.matrix.MatrixStack;

import com.nekomaster1000.infernalexp.config.ConfigHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.SettingsScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.list.OptionsRowList;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public abstract class IESettingsScreen extends Screen {

	private final Screen parentScreen;
	protected OptionsRowList optionsRowList;

	protected IESettingsScreen(Screen parentScreen, ITextComponent titleIn) {
		super(titleIn);
		this.parentScreen = parentScreen;
	}

	public abstract void addSettings();

	@Override
	protected void init() {
		optionsRowList = new OptionsRowList(minecraft, width, height, 24, height - 32, 25);

		addSettings();

		children.add(optionsRowList);

		addButton(new Button((width - 200) / 2, height - 26, 200, 20, new TranslationTextComponent("gui.done"), button -> closeScreen()));
	}

	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		renderBackground(matrixStack);

		optionsRowList.render(matrixStack, mouseX, mouseY, partialTicks);

		List<IReorderingProcessor> list = SettingsScreen.func_243293_a(optionsRowList, mouseX, mouseY);
		if (list != null) {
			this.renderTooltip(matrixStack, list, mouseX, mouseY);
		}

		// The parameter names for this function are wrong. The three integers at the end should be x, y, color
		drawCenteredString(matrixStack, font, title, width / 2, 8, 0xFFFFFF);

		super.render(matrixStack, mouseX, mouseY, partialTicks);
	}

	@Override
	public void closeScreen() {
		ConfigHelper.saveToClient();
		ConfigHelper.saveToCommon();
		Minecraft.getInstance().displayGuiScreen(parentScreen);
	}
}
