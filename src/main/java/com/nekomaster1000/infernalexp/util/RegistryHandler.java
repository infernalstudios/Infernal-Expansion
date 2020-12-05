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

//...

    //VOLINE
    static ResourceLocation location1 = new ResourceLocation(InfernalExpansion.MOD_ID, "volineambient");
    public static SoundEvent voline_ambient = new SoundEvent(location1);

    static ResourceLocation location2 = new ResourceLocation(InfernalExpansion.MOD_ID, "volinehurt");
    public static SoundEvent voline_hurt = new SoundEvent(location2);

//...

    //GIANT
    static ResourceLocation location3 = new ResourceLocation(InfernalExpansion.MOD_ID, "basaltgiantambient");
    public static SoundEvent basalt_giant_ambient = new SoundEvent(location3);

    static ResourceLocation location4 = new ResourceLocation(InfernalExpansion.MOD_ID, "basaltgianthurt");
    public static SoundEvent basalt_giant_hurt = new SoundEvent(location4);

    static ResourceLocation location5 = new ResourceLocation(InfernalExpansion.MOD_ID, "basaltgiantdeath");
    public static SoundEvent basalt_giant_death = new SoundEvent(location5);

//...

    //EMBODY
    static ResourceLocation location7 = new ResourceLocation(InfernalExpansion.MOD_ID, "embodyambient");
    public static SoundEvent embody_ambient = new SoundEvent(location7);

    static ResourceLocation location8 = new ResourceLocation(InfernalExpansion.MOD_ID, "embodyhurt");
    public static SoundEvent embody_hurt = new SoundEvent(location8);

    static ResourceLocation location9 = new ResourceLocation(InfernalExpansion.MOD_ID, "embodydeath");
    public static SoundEvent embody_death = new SoundEvent(location9);


}