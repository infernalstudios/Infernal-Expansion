/*
 * Copyright 2022 Infernal Studios
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.infernalstudios.infernalexp;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.levelgen.carver.WorldCarver;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.infernalstudios.infernalexp.client.InfernalExpansionClient;
import org.infernalstudios.infernalexp.config.ConfigHelper;
import org.infernalstudios.infernalexp.config.ConfigHolder;
import org.infernalstudios.infernalexp.data.SpawnrateManager;
import org.infernalstudios.infernalexp.events.MiscEvents;
import org.infernalstudios.infernalexp.events.MobEvents;
import org.infernalstudios.infernalexp.events.WorldEvents;
import org.infernalstudios.infernalexp.init.IEBiomes;
import org.infernalstudios.infernalexp.init.IEBlockEntityTypes;
import org.infernalstudios.infernalexp.init.IEBlocks;
import org.infernalstudios.infernalexp.init.IEBrewingRecipes;
import org.infernalstudios.infernalexp.init.IECapabilities;
import org.infernalstudios.infernalexp.init.IECommands;
import org.infernalstudios.infernalexp.init.IECompostables;
import org.infernalstudios.infernalexp.init.IEConfiguredStructures;
import org.infernalstudios.infernalexp.init.IEEffects;
import org.infernalstudios.infernalexp.init.IEEntityClassifications;
import org.infernalstudios.infernalexp.init.IEEntityTypes;
import org.infernalstudios.infernalexp.init.IEItems;
import org.infernalstudios.infernalexp.init.IELootModifiers;
import org.infernalstudios.infernalexp.init.IENoiseData;
import org.infernalstudios.infernalexp.init.IEPaintings;
import org.infernalstudios.infernalexp.init.IEParticleTypes;
import org.infernalstudios.infernalexp.init.IEPotions;
import org.infernalstudios.infernalexp.init.IEProcessors;
import org.infernalstudios.infernalexp.init.IEShroomloinTypes;
import org.infernalstudios.infernalexp.init.IESoundEvents;
import org.infernalstudios.infernalexp.init.IEStructureSets;
import org.infernalstudios.infernalexp.items.IESpawnEggItem;
import org.infernalstudios.infernalexp.mixin.common.WorldCarverAccessor;
import org.infernalstudios.infernalexp.network.IENetworkHandler;
import org.infernalstudios.infernalexp.util.CompatibilityQuark;
import org.infernalstudios.infernalexp.world.gen.ModEntityPlacement;

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

        // Registering deferred registers to the mod bus
        IEParticleTypes.PARTICLES.register(modEventBus);
        IESoundEvents.register(modEventBus);
        IEBlocks.register(modEventBus);
        IEItems.register(modEventBus);
        IEEffects.register(modEventBus);
        IEPotions.register(modEventBus);
        IEEntityTypes.register(modEventBus);
        IEPaintings.register(modEventBus);
        IEBlockEntityTypes.register(modEventBus);
        IEBiomes.register(modEventBus);
        IELootModifiers.register(modEventBus);

        IEShroomloinTypes.registerAll();
        IEEntityClassifications.register();

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new MiscEvents());
        MinecraftForge.EVENT_BUS.register(new MobEvents());
        MinecraftForge.EVENT_BUS.register(new WorldEvents());
        MinecraftForge.EVENT_BUS.register(new IECapabilities());

        // Registering Configs
        modLoadingContext.registerConfig(ModConfig.Type.CLIENT, ConfigHolder.CLIENT_SPEC);
        modLoadingContext.registerConfig(ModConfig.Type.COMMON, ConfigHolder.COMMON_SPEC);

        // Baking Configs
        ConfigHelper.bakeClient(null);
        ConfigHelper.bakeCommon(null);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Setup and register structures, processors, packets, capabilities and brewing recipes
        event.enqueueWork(IEProcessors::bootstrap); // If we fully switched over to using just code for structures, this line wouldn't be necessary
        event.enqueueWork(IEConfiguredStructures::register);
        event.enqueueWork(IEStructureSets::register);
        event.enqueueWork(IENoiseData::register);
        event.enqueueWork(IENetworkHandler::register);
        event.enqueueWork(IEBrewingRecipes::register);

        // Create mob spawnrate config files, they get created on game load instead of world load
        // just in case someone only launches the games once then goes and looks at the config files.
        event.enqueueWork(SpawnrateManager::createResources);

        // Add custom blocks to nether cave carver
        event.enqueueWork(() -> {
            Set<Block> newCarvableBlocks = Stream.of(IEBlocks.DULLSTONE.get(), IEBlocks.DIMSTONE.get(), Blocks.GLOWSTONE, IEBlocks.GLOWDUST_SAND.get(), IEBlocks.GLOWDUST.get()).collect(Collectors.toCollection(HashSet::new));

            newCarvableBlocks.addAll(((WorldCarverAccessor) WorldCarver.NETHER_CAVE).getReplaceableBlocks());
            ((WorldCarverAccessor) WorldCarver.NETHER_CAVE).setReplaceableBlocks(newCarvableBlocks);
        });

        // Register for Quark Compatibility in recipe
        CraftingHelper.register(new CompatibilityQuark.Serializer());

        // Places entity spawn locations on the ground
        ModEntityPlacement.spawnPlacement();

        // Register New Flowers to be Able to Place in Pots
        FlowerPotBlock flowerPot = (FlowerPotBlock) Blocks.FLOWER_POT;
        flowerPot.addPlant(IEBlocks.DULLTHORNS.getId(), IEBlocks.POTTED_DULLTHORNS);
        flowerPot.addPlant(IEBlocks.LUMINOUS_FUNGUS.getId(), IEBlocks.POTTED_LUMINOUS_FUNGUS);
        flowerPot.addPlant(IEBlocks.SHROOMLIGHT_FUNGUS.getId(), IEBlocks.POTTED_SHROOMLIGHT_FUNGUS);

        // Custom Dispenser Behavior
        DispenserBlock.registerBehavior(Items.GLOWSTONE_DUST, new DefaultDispenseItemBehavior() {
            @Override
            protected ItemStack execute(BlockSource source, ItemStack stack) {
                Level world = source.getLevel();
                BlockPos blockpos = source.getPos().relative(source.getBlockState().getValue(DispenserBlock.FACING));
                ItemStack itemstack = stack.split(1);
                if (world.getBlockState(blockpos).getBlock() == IEBlocks.DIMSTONE.get()) {
                    world.setBlockAndUpdate(blockpos, Blocks.GLOWSTONE.defaultBlockState());
                } else if (world.getBlockState(blockpos).getBlock() == IEBlocks.DULLSTONE.get()) {
                    world.setBlockAndUpdate(blockpos, IEBlocks.DIMSTONE.get().defaultBlockState());
                } else {
                    spawnItem(world, itemstack, 6, source.getBlockState().getValue(DispenserBlock.FACING), DispenserBlock.getDispensePosition(source));
                }

                return stack;
            }
        });

        IECompostables.registerCompostables();
        IESpawnEggItem.initUnaddedEggs();
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> InfernalExpansionClient::init);
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        IECommands.registerCommands(event.getServer().getCommands().getDispatcher());
    }

    public static final CreativeModeTab TAB = new CreativeModeTab("InfernalTab") {

        @Override
        public ItemStack makeIcon() {
            return new ItemStack(IEItems.TAB_ITEM.get());
        }

    };

}
