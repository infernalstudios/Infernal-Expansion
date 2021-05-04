package com.nekomaster1000.infernalexp.config.gui.screens;

import com.nekomaster1000.infernalexp.InfernalExpansion;
import com.nekomaster1000.infernalexp.config.InfernalExpansionConfig;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.settings.BooleanOption;
import net.minecraft.client.settings.SliderPercentageOption;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ClientConfigScreen extends IESettingsScreen {

	public ClientConfigScreen(Screen parentScreen) {
		super(parentScreen, new TranslationTextComponent(InfernalExpansion.MOD_ID + ".config.title.clientConfig"));
	}

	@Override
	public void addSettings() {
		for (InfernalExpansionConfig.ClientConfig clientConfig : InfernalExpansionConfig.ClientConfig.values()) {
			if (clientConfig.isSlider()) {
				optionsRowList.addOption(new SliderPercentageOption(InfernalExpansion.MOD_ID + ".config.option." + clientConfig.getTranslationName(), clientConfig.getMinValue(), clientConfig.getMaxValue(), clientConfig.getStepSize(),
					settings -> clientConfig.getDouble(), (settings, value) -> clientConfig.set(value),
					(settings, option) -> {
						option.setOptionValues(Minecraft.getInstance().fontRenderer.trimStringToWidth(
							new TranslationTextComponent(InfernalExpansion.MOD_ID + ".config.tooltip." + clientConfig.getTranslationName()), 200));

						return new TranslationTextComponent("options.generic_value", option.getBaseMessageTranslation(),
							new StringTextComponent(Integer.toString((int) option.get(settings))));
					}));
			} else {
				optionsRowList.addOption(new BooleanOption(InfernalExpansion.MOD_ID + ".config.option." + clientConfig.getTranslationName(),
					new TranslationTextComponent(InfernalExpansion.MOD_ID + ".config.tooltip." + clientConfig.getTranslationName()),
					settings -> clientConfig.getBool(), (settings, value) -> clientConfig.set(value)));
			}
		}
	}

}
