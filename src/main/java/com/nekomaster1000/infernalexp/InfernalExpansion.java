package com.nekomaster1000.infernalexp;

import com.nekomaster1000.infernalexp.brewing.IEBrewingRecipe;
import com.nekomaster1000.infernalexp.client.InfernalExpansionClient;
import com.nekomaster1000.infernalexp.config.ConfigHelper;
import com.nekomaster1000.infernalexp.config.ConfigHolder;
import com.nekomaster1000.infernalexp.data.SpawnrateManager;
import com.nekomaster1000.infernalexp.events.MiscEvents;
import com.nekomaster1000.infernalexp.events.MobEvents;
import com.nekomaster1000.infernalexp.events.WorldEvents;
import com.nekomaster1000.infernalexp.init.IEBiomes;
import com.nekomaster1000.infernalexp.init.IEBlocks;
import com.nekomaster1000.infernalexp.init.IECommands;
import com.nekomaster1000.infernalexp.init.IECompostables;
import com.nekomaster1000.infernalexp.init.IEEffects;
import com.nekomaster1000.infernalexp.init.IEEntityClassifications;
import com.nekomaster1000.infernalexp.init.IEEntityTypes;
import com.nekomaster1000.infernalexp.init.IEItems;
import com.nekomaster1000.infernalexp.init.IELootModifiers;
import com.nekomaster1000.infernalexp.init.IEPaintings;
import com.nekomaster1000.infernalexp.init.IEParticleTypes;
import com.nekomaster1000.infernalexp.init.IEPotions;
import com.nekomaster1000.infernalexp.init.IEProcessors;
import com.nekomaster1000.infernalexp.init.IESoundEvents;
import com.nekomaster1000.infernalexp.init.IEStructures;
import com.nekomaster1000.infernalexp.init.IETileEntityTypes;
import com.nekomaster1000.infernalexp.mixin.common.WorldCarverAccessor;
import com.nekomaster1000.infernalexp.network.IENetworkHandler;
import com.nekomaster1000.infernalexp.util.CompatibilityQuark;
import com.nekomaster1000.infernalexp.util.ModSpawnEggItem;
import com.nekomaster1000.infernalexp.world.dimension.ModNetherBiomeProvider;
import com.nekomaster1000.infernalexp.world.gen.ModEntityPlacement;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Mod(InfernalExpansion.MOD_ID)
public class InfernalExpansion {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "infernalexp";

    public InfernalExpansion() {
        final ModLoadingContext modLoadingContext = ModLoadingContext.get();
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::clientSetup);
        modEventBus.addListener(this::commonSetup);

        //Registering deferred registers to the mod bus
        IEParticleTypes.PARTICLES.register(modEventBus);
        IESoundEvents.register(modEventBus);
        IEBlocks.register(modEventBus);
        IEItems.register(modEventBus);
        IEEffects.register(modEventBus);
        IEPotions.register(modEventBus);
        IEEntityTypes.register(modEventBus);
        IEPaintings.register(modEventBus);
        IETileEntityTypes.register(modEventBus);
        IEBiomes.register(modEventBus);
        IELootModifiers.register(modEventBus);

        IEEntityClassifications.register();

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new MiscEvents());
        MinecraftForge.EVENT_BUS.register(new MobEvents());
        MinecraftForge.EVENT_BUS.register(new WorldEvents());

        //Registering Configs
        modLoadingContext.registerConfig(ModConfig.Type.CLIENT, ConfigHolder.CLIENT_SPEC);
        modLoadingContext.registerConfig(ModConfig.Type.COMMON, ConfigHolder.COMMON_SPEC);

        //Baking Configs
        ConfigHelper.bakeClient(null);
        ConfigHelper.bakeCommon(null);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        //Search for all biomes to add to nether and register nether biome provider
        event.enqueueWork(ModNetherBiomeProvider::registerBiomeProvider);

        //Setup and register structures and processors and packets
        event.enqueueWork(IEProcessors::registerProcessors);
        event.enqueueWork(IEStructures::setupStructures);
        event.enqueueWork(IENetworkHandler::register);

        // Create mob spawnrate config files, they get created on game load instead of world load
        // just in case someone only launches the games once then goes and looks at the config files.
        event.enqueueWork(SpawnrateManager::createResources);

        // Add custom blocks to nether cave carver
        event.enqueueWork(() -> {
            Set<Block> newCarvableBlocks = Stream.of(IEBlocks.DULLSTONE.get(), IEBlocks.DIMSTONE.get(), Blocks.GLOWSTONE, IEBlocks.GLOWDUST_SAND.get(), IEBlocks.GLOWDUST.get()).collect(Collectors.toCollection(HashSet::new));

            newCarvableBlocks.addAll(((WorldCarverAccessor) WorldCarver.NETHER_CAVE).getCarvableBlocks());
            ((WorldCarverAccessor) WorldCarver.NETHER_CAVE).setCarvableBlocks(newCarvableBlocks);
        });

        //Register for Quark Compatibility in recipe
        CraftingHelper.register(new CompatibilityQuark.Serializer());

        //Places entity spawn locations on the ground
        ModEntityPlacement.spawnPlacement();

        //Register New Flowers to be Able to Place in Pots
        FlowerPotBlock flowerPot = (FlowerPotBlock) Blocks.FLOWER_POT;
        flowerPot.addPlant(IEBlocks.DULLTHORNS.getId(), IEBlocks.POTTED_DULLTHORNS);
        flowerPot.addPlant(IEBlocks.LUMINOUS_FUNGUS.getId(), IEBlocks.POTTED_LUMINOUS_FUNGUS);
        flowerPot.addPlant(IEBlocks.SHROOMLIGHT_FUNGUS.getId(), IEBlocks.POTTED_SHROOMLIGHT_FUNGUS);

        //Register Brewing Recipes for Potions
        BrewingRecipeRegistry.addRecipe(new IEBrewingRecipe(
            PotionUtils.addPotionToItemStack(Items.POTION.getDefaultInstance(), Potions.AWKWARD),
            IEItems.MOTH_DUST.get().getDefaultInstance(),
            PotionUtils.addPotionToItemStack(Items.POTION.getDefaultInstance(), IEPotions.LUMINOUS.get())));
        BrewingRecipeRegistry.addRecipe(new IEBrewingRecipe(
                PotionUtils.addPotionToItemStack(Items.POTION.getDefaultInstance(), IEPotions.LUMINOUS.get()),
                Items.GUNPOWDER.getDefaultInstance(),
                PotionUtils.addPotionToItemStack(Items.SPLASH_POTION.getDefaultInstance(), IEPotions.LUMINOUS.get())));
        BrewingRecipeRegistry.addRecipe(new IEBrewingRecipe(
                PotionUtils.addPotionToItemStack(Items.SPLASH_POTION.getDefaultInstance(), IEPotions.LUMINOUS.get()),
                Items.DRAGON_BREATH.getDefaultInstance(),
                PotionUtils.addPotionToItemStack(Items.LINGERING_POTION.getDefaultInstance(), IEPotions.LUMINOUS.get())));
        BrewingRecipeRegistry.addRecipe(new IEBrewingRecipe(
                PotionUtils.addPotionToItemStack(Items.POTION.getDefaultInstance(), IEPotions.LUMINOUS.get()),
                Items.REDSTONE.getDefaultInstance(),
                PotionUtils.addPotionToItemStack(Items.POTION.getDefaultInstance(), IEPotions.LONG_LUMINOUS.get())));
        BrewingRecipeRegistry.addRecipe(new IEBrewingRecipe(
                PotionUtils.addPotionToItemStack(Items.POTION.getDefaultInstance(), IEPotions.LUMINOUS.get()),
                Items.GLOWSTONE_DUST.getDefaultInstance(),
                PotionUtils.addPotionToItemStack(Items.POTION.getDefaultInstance(), IEPotions.STRONG_LUMINOUS.get())));

		BrewingRecipeRegistry.addRecipe(new IEBrewingRecipe(
			PotionUtils.addPotionToItemStack(Items.POTION.getDefaultInstance(), Potions.AWKWARD),
			IEItems.ASCUS_BOMB.get().getDefaultInstance(),
			PotionUtils.addPotionToItemStack(Items.POTION.getDefaultInstance(), IEPotions.INFECTION.get())));
		BrewingRecipeRegistry.addRecipe(new IEBrewingRecipe(
			PotionUtils.addPotionToItemStack(Items.POTION.getDefaultInstance(), IEPotions.INFECTION.get()),
			Items.GUNPOWDER.getDefaultInstance(),
			PotionUtils.addPotionToItemStack(Items.SPLASH_POTION.getDefaultInstance(), IEPotions.INFECTION.get())));
		BrewingRecipeRegistry.addRecipe(new IEBrewingRecipe(
			PotionUtils.addPotionToItemStack(Items.SPLASH_POTION.getDefaultInstance(), IEPotions.INFECTION.get()),
			Items.DRAGON_BREATH.getDefaultInstance(),
			PotionUtils.addPotionToItemStack(Items.LINGERING_POTION.getDefaultInstance(), IEPotions.INFECTION.get())));
		BrewingRecipeRegistry.addRecipe(new IEBrewingRecipe(
			PotionUtils.addPotionToItemStack(Items.POTION.getDefaultInstance(), IEPotions.INFECTION.get()),
			Items.REDSTONE.getDefaultInstance(),
			PotionUtils.addPotionToItemStack(Items.POTION.getDefaultInstance(), IEPotions.LONG_INFECTION.get())));
		BrewingRecipeRegistry.addRecipe(new IEBrewingRecipe(
			PotionUtils.addPotionToItemStack(Items.POTION.getDefaultInstance(), IEPotions.INFECTION.get()),
			Items.GLOWSTONE_DUST.getDefaultInstance(),
			PotionUtils.addPotionToItemStack(Items.POTION.getDefaultInstance(), IEPotions.STRONG_INFECTION.get())));

		//Custom Dispenser Behavior
        DispenserBlock.registerDispenseBehavior(Items.GLOWSTONE_DUST, new DefaultDispenseItemBehavior() {
            @Override
            protected ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
                World world = source.getWorld();
                BlockPos blockpos = source.getBlockPos().offset(source.getBlockState().get(DispenserBlock.FACING));
                ItemStack itemstack = stack.split(1);
                if (world.getBlockState(blockpos).getBlock() == IEBlocks.DIMSTONE.get()) {
                    world.setBlockState(blockpos, Blocks.GLOWSTONE.getDefaultState());
                } else if (world.getBlockState(blockpos).getBlock() == IEBlocks.DULLSTONE.get()) {
                    world.setBlockState(blockpos, IEBlocks.DIMSTONE.get().getDefaultState());
                } else {
                    doDispense(world, itemstack, 6, source.getBlockState().get(DispenserBlock.FACING), DispenserBlock.getDispensePosition(source));
                }

                return stack;
            }
        });

        IECompostables.registerCompostables();
        ModSpawnEggItem.initUnaddedEggs();
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> InfernalExpansionClient::init);
    }

    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        IECommands.registerCommands(event.getServer().getCommandManager().getDispatcher());
    }

    public static final ItemGroup TAB = new ItemGroup("InfernalTab") {

        @Override
        public ItemStack createIcon() {
            return new ItemStack(IEItems.TAB_ITEM.get());
        }

    };

}
