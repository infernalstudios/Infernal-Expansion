package com.nekomaster1000.infernalexp.util;
import com.nekomaster1000.infernalexp.InfernalExpansion;
import com.nekomaster1000.infernalexp.init.*;
//import com.nekomaster1000.Infernal.world.biome.GlowstoneCanyonBiome;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class RegistryHandler {
    //SOUNDS
    static ResourceLocation location1 = new ResourceLocation(InfernalExpansion.MOD_ID, "volinehurt");
    public static SoundEvent voline_hurt = new SoundEvent(location1);
    static ResourceLocation location2 = new ResourceLocation(InfernalExpansion.MOD_ID, "volineambient");
    public static SoundEvent voline_ambient = new SoundEvent(location2);
}