package com.nekomaster1000.infernalexp.config.gui.widgets;

import net.minecraft.client.AbstractOption;
import net.minecraft.client.GameSettings;
import net.minecraft.client.gui.widget.Widget;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TitleOption extends AbstractOption {

	public TitleOption(String translationKeyIn) {
		super(translationKeyIn);
	}

	@Override
	public Widget createWidget(GameSettings options, int xIn, int yIn, int widthIn) {
		return new TitleWidget(xIn, yIn, widthIn, 20, getBaseMessageTranslation());
	}

}
