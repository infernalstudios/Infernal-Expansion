package com.nekomaster1000.infernalexp;

//Entities are found in src.main.java.world.gen.ModEntitySpawns

import com.nekomaster1000.infernalexp.init.*;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.ref.Reference;
@Mod("infernalexp")
public class InfernalExpansion
{
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "infernalexp";

    public InfernalExpansion()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();


        modEventBus.addListener(this::clientSetup);
        modEventBus.addListener(EventPriority.LOWEST, this::commonSetup);

        //Registering deferred registers to the mod bus
        ModItems.ITEMS.register(modEventBus);
        ModBlocks.BLOCKS.register(modEventBus);
        ModEntityType.ENTITY_TYPES.register(modEventBus);
        ModPaintings.PAINTING_TYPES.register(modEventBus);
        ModSurfaceBuilder.SURFACE_BUILDERS.register(modEventBus);
        //ModBiomes.BIOMES.register(modEventBus);
        ModBiomes.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);

    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        ModBiomes.setupBiomes();
    }

    private void clientSetup(final FMLClientSetupEvent event)
    {
    }

    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event)
    {
    }

    public static final ItemGroup TAB = new ItemGroup("InfernalTab") {

        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModItems.GLOWNUGGETS.get());
        }

    };

}