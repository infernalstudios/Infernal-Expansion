package com.nekomaster1000.Infernal.util;
import com.nekomaster1000.Infernal.InfernalExpansion;
import com.nekomaster1000.Infernal.blocks.*;
import com.nekomaster1000.Infernal.init.ModBlocks;
import com.nekomaster1000.Infernal.init.ModEntityType;
import com.nekomaster1000.Infernal.init.ModItems;
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
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraft.world.biome.Biome;

public class RegistryHandler {

    public static DeferredRegister<PaintingType> PAINTING_TYPES= DeferredRegister.create(ForgeRegistries.PAINTING_TYPES, InfernalExpansion.MOD_ID);


    public static void init() {
        ModItems.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModBlocks.BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModEntityType.ENTITY_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
        PAINTING_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());

    }

    //SOUNDS
    static ResourceLocation location1 = new ResourceLocation(InfernalExpansion.MOD_ID, "volinehurt");
    public static SoundEvent voline_hurt = new SoundEvent(location1);
    static ResourceLocation location2 = new ResourceLocation(InfernalExpansion.MOD_ID, "volineambient");
    public static SoundEvent voline_ambient = new SoundEvent(location2);

    //PAITNINGS
    public static RegistryObject<PaintingType> PIGMAN_PAINTING = PAINTING_TYPES.register("pigman_painting",()-> new PaintingType(48, 64));


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