package com.nekomaster1000.infernalexp.util;
import com.nekomaster1000.infernalexp.InfernalExpansion;
import com.nekomaster1000.infernalexp.init.*;
//import com.nekomaster1000.Infernal.world.biome.GlowstoneCanyonBiome;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class RegistryHandler {
    // Eventually we'll want to port these to InfernalExpansion constructor
    // And move the sounds to a new class
    private static void init() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
    }

    //SOUNDS
    static ResourceLocation location1 = new ResourceLocation(InfernalExpansion.MOD_ID, "volinehurt");
    public static SoundEvent voline_hurt = new SoundEvent(location1);
    static ResourceLocation location2 = new ResourceLocation(InfernalExpansion.MOD_ID, "volineambient");
    public static SoundEvent voline_ambient = new SoundEvent(location2);

    //Biome List
    /*private static GlowstoneCanyonBiome glowstone_canyon;

    @SubscribeEvent
    public static void registerBiomes(final RegistryEvent.Register<Biome> event)
    {
        event.getRegistry().registerAll
                (RegistryHandler.glowstone_canyon = new GlowstoneCanyonBiome()
                );
        ;
    }*/
}