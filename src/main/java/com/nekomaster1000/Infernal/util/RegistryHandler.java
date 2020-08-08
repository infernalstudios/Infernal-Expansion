package com.nekomaster1000.Infernal.util;
import com.nekomaster1000.Infernal.InfernalExpansion;
import com.nekomaster1000.Infernal.blocks.*;
import com.nekomaster1000.Infernal.init.*;
import com.nekomaster1000.Infernal.items.ItemBase;
//import com.nekomaster1000.Infernal.world.biome.GlowstoneCanyonBiome;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.PaintingType;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraft.world.biome.Biome;

public class RegistryHandler {
    // Eventually we'll want to port these to InfernalExpansion constructor
    // And move the sounds to a new class
    private static void init() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.ITEMS.register(modEventBus);
        ModBlocks.BLOCKS.register(modEventBus);
        ModEntityType.ENTITY_TYPES.register(modEventBus);
        ModPaintings.PAINTING_TYPES.register(modEventBus);
        ModBiomes.BIOMES.register(modEventBus);
        ModBiomes.registerBiomes();
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