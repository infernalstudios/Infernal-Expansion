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

    //CEROBEETLE
    static ResourceLocation location11 = new ResourceLocation(InfernalExpansion.MOD_ID, "cerobeetleambient");
    public static SoundEvent cerobeetle_ambient = new SoundEvent(location11);

    static ResourceLocation location12 = new ResourceLocation(InfernalExpansion.MOD_ID, "cerobeetlehurt");
    public static SoundEvent cerobeetle_hurt = new SoundEvent(location12);

    static ResourceLocation location13 = new ResourceLocation(InfernalExpansion.MOD_ID, "cerobeetledeath");
    public static SoundEvent cerobeetle_death = new SoundEvent(location13);

    static ResourceLocation location14 = new ResourceLocation(InfernalExpansion.MOD_ID, "cerobeetledeath");
    public static SoundEvent cerobeetle_roar = new SoundEvent(location14);

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

//...

    //SKELETAL PIGLIN
    static ResourceLocation location26 = new ResourceLocation(InfernalExpansion.MOD_ID, "skeletalpiglinambient");
    public static SoundEvent skeletal_piglin_ambient = new SoundEvent(location26);

    static ResourceLocation location27 = new ResourceLocation(InfernalExpansion.MOD_ID, "skeletalpiglinhurt");
    public static SoundEvent skeletal_piglin_hurt = new SoundEvent(location27);

    static ResourceLocation location28 = new ResourceLocation(InfernalExpansion.MOD_ID, "skeletalpiglindeath");
    public static SoundEvent skeletal_piglin_death = new SoundEvent(location28);

//...

    //GLOWSQUITO
    static ResourceLocation location31 = new ResourceLocation(InfernalExpansion.MOD_ID, "glowsquitoambient");
    public static SoundEvent glowsquito_ambient = new SoundEvent(location31);

    static ResourceLocation location32 = new ResourceLocation(InfernalExpansion.MOD_ID, "glowsquitohurt");
    public static SoundEvent glowsquito_hurt = new SoundEvent(location32);

    static ResourceLocation location33 = new ResourceLocation(InfernalExpansion.MOD_ID, "glowsquitodeath");
    public static SoundEvent glowsquito_death = new SoundEvent(location33);

//...

    //BLINDSIGHT
    static ResourceLocation location36 = new ResourceLocation(InfernalExpansion.MOD_ID, "blindsightambient");
    public static SoundEvent blindsight_ambient = new SoundEvent(location36);

    static ResourceLocation location37 = new ResourceLocation(InfernalExpansion.MOD_ID, "blindsighthurt");
    public static SoundEvent blindsight_hurt = new SoundEvent(location37);

    static ResourceLocation location38 = new ResourceLocation(InfernalExpansion.MOD_ID, "blindsightdeath");
    public static SoundEvent blindsight_death = new SoundEvent(location38);

//...

    //BLACKSTONE_DWARF
    static ResourceLocation location86 = new ResourceLocation(InfernalExpansion.MOD_ID, "dwarfambient");
    public static SoundEvent blackstone_dwarf_ambient = new SoundEvent(location36);

    static ResourceLocation location87 = new ResourceLocation(InfernalExpansion.MOD_ID, "dwarfhurt");
    public static SoundEvent blackstone_dwarf_hurt = new SoundEvent(location37);

    static ResourceLocation location88 = new ResourceLocation(InfernalExpansion.MOD_ID, "dwarfdeath");
    public static SoundEvent blackstone_dwarf_death = new SoundEvent(location38);

//...

    //GLAW
    static ResourceLocation location41 = new ResourceLocation(InfernalExpansion.MOD_ID, "glawambient");
    public static SoundEvent glaw_ambient = new SoundEvent(location41);

    static ResourceLocation location42 = new ResourceLocation(InfernalExpansion.MOD_ID, "glawhurt");
    public static SoundEvent glaw_hurt = new SoundEvent(location42);

    static ResourceLocation location43 = new ResourceLocation(InfernalExpansion.MOD_ID, "glawdeath");
    public static SoundEvent glaw_death = new SoundEvent(location43);

//...

    //SMOLT
    static ResourceLocation location46 = new ResourceLocation(InfernalExpansion.MOD_ID, "smoltambient");
    public static SoundEvent smolt_ambient = new SoundEvent(location46);

    static ResourceLocation location47 = new ResourceLocation(InfernalExpansion.MOD_ID, "smolthurt");
    public static SoundEvent smolt_hurt = new SoundEvent(location47);

    static ResourceLocation location48 = new ResourceLocation(InfernalExpansion.MOD_ID, "smoltdeath");
    public static SoundEvent smolt_death = new SoundEvent(location48);

//...

    //PYRNO
    static ResourceLocation location51 = new ResourceLocation(InfernalExpansion.MOD_ID, "pyrnoambient");
    public static SoundEvent pyrno_ambient = new SoundEvent(location51);

    static ResourceLocation location52 = new ResourceLocation(InfernalExpansion.MOD_ID, "pyrnohurt");
    public static SoundEvent pyrno_hurt = new SoundEvent(location52);

    static ResourceLocation location53 = new ResourceLocation(InfernalExpansion.MOD_ID, "pyrnodeath");
    public static SoundEvent pyrno_death = new SoundEvent(location53);

}