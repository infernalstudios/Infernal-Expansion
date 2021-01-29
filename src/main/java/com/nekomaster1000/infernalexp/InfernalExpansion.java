package com.nekomaster1000.infernalexp;

import com.nekomaster1000.infernalexp.config.ConfigHelper;
import com.nekomaster1000.infernalexp.config.ConfigHolder;
import com.nekomaster1000.infernalexp.init.ModBiomes;
import com.nekomaster1000.infernalexp.init.ModBlocks;
import com.nekomaster1000.infernalexp.init.ModCommands;
import com.nekomaster1000.infernalexp.init.ModEffects;
import com.nekomaster1000.infernalexp.init.ModEntityTypes;
import com.nekomaster1000.infernalexp.init.ModEvents;
import com.nekomaster1000.infernalexp.init.ModItems;
import com.nekomaster1000.infernalexp.init.ModPaintings;
import com.nekomaster1000.infernalexp.init.ModParticleTypes;
import com.nekomaster1000.infernalexp.init.ModTileEntityTypes;
import com.nekomaster1000.infernalexp.world.dimension.ModNetherBiomeCollector;
import com.nekomaster1000.infernalexp.world.dimension.ModNetherBiomeProvider;
import com.nekomaster1000.infernalexp.world.gen.ModEntityPlacement;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
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
        final ModLoadingContext modLoadingContext = ModLoadingContext.get();
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::clientSetup);
        modEventBus.addListener(this::commonSetup);

        //Registering deferred registers to the mod bus
        ModParticleTypes.PARTICLES.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModItems.register(modEventBus);
        ModEffects.register(modEventBus);
        ModEntityTypes.register(modEventBus);
        ModPaintings.register(modEventBus);
        ModTileEntityTypes.register(modEventBus);
        ModBiomes.register(modEventBus);
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new ModEvents());

        //Registering Configs
        modLoadingContext.registerConfig(ModConfig.Type.CLIENT, ConfigHolder.CLIENT_SPEC);
        modLoadingContext.registerConfig(ModConfig.Type.SERVER, ConfigHolder.SERVER_SPEC);

        //Baking Configs
        ConfigHelper.bakeClient(null);
        ConfigHelper.bakeServer(null);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        ModNetherBiomeCollector.netherBiomeCollection();
        Registry.register(Registry.BIOME_PROVIDER_CODEC, new ResourceLocation(MOD_ID, "infernalexp_nether"), ModNetherBiomeProvider.MOD_NETHER_CODEC);

        //Places entity spawn locations on the ground
        ModEntityPlacement.spawnPlacement();

        //Register New Flowers to be Able to Place in Pots
        FlowerPotBlock flowerPot = (FlowerPotBlock) Blocks.FLOWER_POT;
        flowerPot.addPlant(ModBlocks.DULLTHORNS.getId(), ModBlocks.POTTED_DULLTHORNS);
        flowerPot.addPlant(ModBlocks.LUMINOUS_FUNGUS.getId(), ModBlocks.POTTED_LUMINOUS_FUNGUS);
        flowerPot.addPlant(ModBlocks.SHROOMLIGHT_FUNGUS.getId(), ModBlocks.POTTED_SHROOMLIGHT_FUNGUS);
    }

    private void clientSetup(final FMLClientSetupEvent event) {
    }

    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        ModCommands.registerCommands(event.getServer().getCommandManager().getDispatcher());
    }

    public static final ItemGroup TAB = new ItemGroup("InfernalTab") {

        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModItems.DULLROCKS.get());
        }

    };

}