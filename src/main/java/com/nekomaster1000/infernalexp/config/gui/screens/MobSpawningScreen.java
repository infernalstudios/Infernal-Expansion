package com.nekomaster1000.infernalexp.config.gui.screens;

import com.nekomaster1000.infernalexp.InfernalExpansion;
import com.nekomaster1000.infernalexp.config.InfernalExpansionConfig;
import com.nekomaster1000.infernalexp.config.gui.widgets.TextFieldOption;
import com.nekomaster1000.infernalexp.config.gui.widgets.TitleOption;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.TranslationTextComponent;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MobSpawningScreen extends IESettingsScreen {

	public MobSpawningScreen(Screen parentScreen) {
        super(parentScreen, new TranslationTextComponent(InfernalExpansion.MOD_ID + ".config.title.mob_spawning"));
    }

	@Override
	public void addSettings() {
        optionsRowList.addOption(new TitleOption(InfernalExpansion.MOD_ID + ".config.subtitle.spawnable_biomes"));

        for (InfernalExpansionConfig.MobSpawning mobSpawn : InfernalExpansionConfig.MobSpawning.values()) {
            optionsRowList.addOption(new TextFieldOption("entity." + InfernalExpansion.MOD_ID + "." + mobSpawn.getTranslationName(),
                new TranslationTextComponent(InfernalExpansion.MOD_ID + ".config.tooltip." + mobSpawn.getTranslationName()),
                settings -> mobSpawn.getSpawnableBiomes(), (settings, value) -> mobSpawn.setSpawnableBiomes(value)));
        }

//		List<AbstractOption> options = new ArrayList<>();
//
//		for (InfernalExpansionConfig.MobSpawning mobSpawn : InfernalExpansionConfig.MobSpawning.values()) {
//			options.add(new BooleanOption(InfernalExpansion.MOD_ID + ".config.option." + mobSpawn.getTranslationName() + ".enable",
//				new TranslationTextComponent(InfernalExpansion.MOD_ID + ".config.tooltip." + mobSpawn.getTranslationName() + ".enable"),
//				settings -> mobSpawn.isEnabled(), (settings, value) -> mobSpawn.setEnabled(value)));
//
//			options.add(new SliderPercentageOption(InfernalExpansion.MOD_ID + ".config.option.spawnrate", 1, 200, 1,
//				settings -> (double) mobSpawn.getSpawnrate(), (settings, value) -> mobSpawn.setSpawnrate(value.intValue()),
//				(settings, option) -> {
//					option.setOptionValues(Minecraft.getInstance().fontRenderer.trimStringToWidth(
//						new TranslationTextComponent(InfernalExpansion.MOD_ID + ".config.tooltip." + mobSpawn.getTranslationName() + ".spawnrate"), 200));
//
//					return option.getMessageWithValue((int) option.get(settings));
//				}));
//		}
//
//		optionsRowList.addOptions(options.toArray(new AbstractOption[0]));
    }
}
