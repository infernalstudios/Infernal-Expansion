package com.nekomaster1000.infernalexp.config.gui;

import com.nekomaster1000.infernalexp.InfernalExpansion;
import com.nekomaster1000.infernalexp.config.InfernalExpansionConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.settings.BooleanOption;
import net.minecraft.client.settings.SliderPercentageOption;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class FloraBehaviourScreen extends IESettingsScreen {

	protected FloraBehaviourScreen(Screen parentScreen) {
		super(parentScreen, new TranslationTextComponent(InfernalExpansion.MOD_ID + ".config.title.floraBehaviour"));
	}

	@Override
	public void addSettings() {
		for (InfernalExpansionConfig.FloraBehaviour floraBehaviour : InfernalExpansionConfig.FloraBehaviour.values()) {
			if (floraBehaviour.isSlider()) {
				optionsRowList.addOption(new SliderPercentageOption(InfernalExpansion.MOD_ID + ".config.option." + floraBehaviour.getTranslationName(), floraBehaviour.getMinValue(), floraBehaviour.getMaxValue(), floraBehaviour.getStepSize(),
						settings -> floraBehaviour.getDouble(), (settings, value) -> floraBehaviour.set(value),
						(settings, option) -> {
							option.setOptionValues(Minecraft.getInstance().fontRenderer.trimStringToWidth(
									new TranslationTextComponent(InfernalExpansion.MOD_ID + ".config.tooltip." + floraBehaviour.getTranslationName()), 200));

							return new TranslationTextComponent("options.generic_value", option.getBaseMessageTranslation(),
									new StringTextComponent(Double.toString((double) Math.round(option.get(settings) * 100) / 100)));
						}));
			} else {
				optionsRowList.addOption(new BooleanOption(InfernalExpansion.MOD_ID + ".config.option." + floraBehaviour.getTranslationName(),
						new TranslationTextComponent(InfernalExpansion.MOD_ID + ".config.tooltip." + floraBehaviour.getTranslationName()),
						settings -> floraBehaviour.getBool(), (settings, value) -> floraBehaviour.set(value)));
			}
		}
	}
}
