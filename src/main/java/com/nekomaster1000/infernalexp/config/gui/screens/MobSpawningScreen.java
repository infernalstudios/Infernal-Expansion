package com.nekomaster1000.infernalexp.config.gui.screens;

import com.nekomaster1000.infernalexp.InfernalExpansion;
import com.nekomaster1000.infernalexp.config.InfernalExpansionConfig;
import net.minecraft.client.AbstractOption;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.settings.BooleanOption;
import net.minecraft.client.settings.SliderPercentageOption;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.ArrayList;
import java.util.List;

public class MobSpawningScreen extends IESettingsScreen {

	public MobSpawningScreen(Screen parentScreen) {
		super(parentScreen, new TranslationTextComponent(InfernalExpansion.MOD_ID + ".config.title.mobSpawning"));
	}

	@Override
	public void addSettings() {
		List<AbstractOption> options = new ArrayList<>();

		for (InfernalExpansionConfig.MobSpawning mobSpawn : InfernalExpansionConfig.MobSpawning.values()) {
			options.add(new BooleanOption(InfernalExpansion.MOD_ID + ".config.option." + mobSpawn.getTranslationName() + ".enable",
					new TranslationTextComponent(InfernalExpansion.MOD_ID + ".config.tooltip." + mobSpawn.getTranslationName() + ".enable"),
					settings -> mobSpawn.isEnabled(), (settings, value) -> mobSpawn.setEnabled(value)));

			options.add(new SliderPercentageOption(InfernalExpansion.MOD_ID + ".config.option.spawnrate", 1, 200, 1,
					settings -> (double) mobSpawn.getSpawnrate(), (settings, value) -> mobSpawn.setSpawnrate(value.intValue()),
					(settings, option) -> {
						option.setOptionValues(Minecraft.getInstance().fontRenderer.trimStringToWidth(
								new TranslationTextComponent(InfernalExpansion.MOD_ID + ".config.tooltip." + mobSpawn.getTranslationName() + ".spawnrate"), 200));

						return option.getMessageWithValue((int) option.get(settings));
					}));
		}

		optionsRowList.addOptions(options.toArray(new AbstractOption[0]));
	}
}
