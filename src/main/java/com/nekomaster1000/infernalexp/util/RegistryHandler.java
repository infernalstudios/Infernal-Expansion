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

    //SHROOMLOIN
    static ResourceLocation location01 = new ResourceLocation(InfernalExpansion.MOD_ID, "shroomloinambient");
    public static SoundEvent shroomloin_ambient = new SoundEvent(location01);

    static ResourceLocation location02 = new ResourceLocation(InfernalExpansion.MOD_ID, "shroomloinhurt");
    public static SoundEvent shroomloin_hurt = new SoundEvent(location02);

    static ResourceLocation location03 = new ResourceLocation(InfernalExpansion.MOD_ID, "shroomloindeath");
    public static SoundEvent shroomloin_death = new SoundEvent(location03);

//...

    //WARPBEETLE
    static ResourceLocation location06 = new ResourceLocation(InfernalExpansion.MOD_ID, "warpbeetleambient");
    public static SoundEvent warpbeetle_ambient = new SoundEvent(location06);

    static ResourceLocation location07 = new ResourceLocation(InfernalExpansion.MOD_ID, "warpbeetlehurt");
    public static SoundEvent warpbeetle_hurt = new SoundEvent(location07);

    static ResourceLocation location08 = new ResourceLocation(InfernalExpansion.MOD_ID, "warpbeetledeath");
    public static SoundEvent warpbeetle_death = new SoundEvent(location08);

//...

    //EMBODY
    static ResourceLocation location16 = new ResourceLocation(InfernalExpansion.MOD_ID, "embodyambient");
    public static SoundEvent embody_ambient = new SoundEvent(location16);

    static ResourceLocation location17 = new ResourceLocation(InfernalExpansion.MOD_ID, "embodyhurt");
    public static SoundEvent embody_hurt = new SoundEvent(location17);

    static ResourceLocation location18 = new ResourceLocation(InfernalExpansion.MOD_ID, "embodydeath");
    public static SoundEvent embody_death = new SoundEvent(location18);

//...

    //GIANT
    static ResourceLocation location21 = new ResourceLocation(InfernalExpansion.MOD_ID, "basaltgiantambient");
    public static SoundEvent basalt_giant_ambient = new SoundEvent(location21);

    static ResourceLocation location22 = new ResourceLocation(InfernalExpansion.MOD_ID, "basaltgianthurt");
    public static SoundEvent basalt_giant_hurt = new SoundEvent(location22);

    static ResourceLocation location23 = new ResourceLocation(InfernalExpansion.MOD_ID, "basaltgiantdeath");
    public static SoundEvent basalt_giant_death = new SoundEvent(location23);
}