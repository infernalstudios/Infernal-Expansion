package com.nekomaster1000.infernalexp;

//Entities are found in src.main.java.world.gen.ModEntitySpawns

import com.nekomaster1000.infernalexp.init.*;
import com.nekomaster1000.infernalexp.world.dimension.ModNetherBiomeCatch;
import com.nekomaster1000.infernalexp.world.dimension.ModNetherBiomeProvider;
import com.nekomaster1000.infernalexp.world.gen.ModEntityPlacement;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("infernalexp")
public class InfernalExpansion
{
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "infernalexp";

    public InfernalExpansion()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::clientSetup);
        modEventBus.addListener(this::commonSetup);

        //Registering deferred registers to the mod bus
        ModBlocks.register(modEventBus);
        ModItems.register(modEventBus);
        ModEntityType.register(modEventBus);
        ModPaintings.register(modEventBus);
        ModTileEntityTypes.register(modEventBus);
        ModBiomes.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new ModEvents());

    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        ModNetherBiomeCatch.netherBiomeCollection();
        Registry.register(Registry.BIOME_PROVIDER_CODEC, new ResourceLocation(MOD_ID, "infernalexp_nether"), ModNetherBiomeProvider.MOD_NETHER_CODEC);

        //Places entity spawn locations on the ground
        ModEntityPlacement.spawnPlacement();
    }

    private void clientSetup(final FMLClientSetupEvent event)
    {
        RenderTypeLookup.setRenderLayer(ModBlocks.GLOW_TORCH.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.GLOW_WALL_TORCH.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.GLOW_CAMPFIRE.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.GLOW_LANTERN.get(), RenderType.getCutout());
    }

    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) { }

    public static final ItemGroup TAB = new ItemGroup("InfernalTab") {

        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModItems.DIMROCKS.get());
        }

    };

}