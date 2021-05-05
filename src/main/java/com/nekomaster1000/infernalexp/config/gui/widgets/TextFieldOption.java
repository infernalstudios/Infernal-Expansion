package com.nekomaster1000.infernalexp.config.gui.widgets;

import net.minecraft.client.AbstractOption;
import net.minecraft.client.GameSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.text.ITextComponent;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.function.BiConsumer;
import java.util.function.Function;

@OnlyIn(Dist.CLIENT)
public class TextFieldOption extends AbstractOption {

	private final ITextComponent tooltip;
	private final Function<GameSettings, String> getter;
	private final BiConsumer<GameSettings, String> setter;

	public TextFieldOption(String translationKey, @Nullable ITextComponent tooltip, Function<GameSettings, String> getter, BiConsumer<GameSettings, String> setter) {
		super(translationKey);
		this.tooltip = tooltip;
		this.getter = getter;
		this.setter = setter;
	}

	@Override
	public Widget createWidget(GameSettings options, int xIn, int yIn, int widthIn) {
		if (tooltip != null) {
			this.setOptionValues(Minecraft.getInstance().fontRenderer.trimStringToWidth(tooltip, 200));
		}

		return new IETextFieldWidget(options, xIn, yIn, widthIn, getBaseMessageTranslation(), this);
	}

	public void set(GameSettings options, String textIn) {
		setter.accept(options, textIn);
	}

	public String get(GameSettings options) {
		return getter.apply(options);
	}

}
