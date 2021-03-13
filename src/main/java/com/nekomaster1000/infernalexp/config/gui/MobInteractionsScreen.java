package com.nekomaster1000.infernalexp.config.gui;

import com.nekomaster1000.infernalexp.InfernalExpansion;
import com.nekomaster1000.infernalexp.config.InfernalExpansionConfig;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.settings.BooleanOption;
import net.minecraft.util.text.TranslationTextComponent;

public class MobInteractionsScreen extends IESettingsScreen {

	public MobInteractionsScreen(Screen parentScreen) {
		super(parentScreen, new TranslationTextComponent(InfernalExpansion.MOD_ID + ".config.title.mobInteractions"));
	}

	@Override
	public void addSettings() {
		for (InfernalExpansionConfig.MobInteractions mobInteraction : InfernalExpansionConfig.MobInteractions.values()) {
			optionsRowList.addOption(new BooleanOption(InfernalExpansion.MOD_ID + ".config.option." + mobInteraction.getTranslationName(),
					new TranslationTextComponent(InfernalExpansion.MOD_ID + ".config.tooltip." + mobInteraction.getTranslationName()),
					settings -> mobInteraction.get(), (settings, value) -> mobInteraction.set(value)));
		}
	}
}
