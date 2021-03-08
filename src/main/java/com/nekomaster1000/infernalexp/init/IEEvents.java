package com.nekomaster1000.infernalexp.init;

import com.nekomaster1000.infernalexp.InfernalExpansion;
import com.nekomaster1000.infernalexp.blocks.HorizontalBushBlock;
import com.nekomaster1000.infernalexp.config.ConfigHelper;
import com.nekomaster1000.infernalexp.config.ConfigHolder;
import com.nekomaster1000.infernalexp.config.InfernalExpansionConfig;
import com.nekomaster1000.infernalexp.entities.*;
import com.nekomaster1000.infernalexp.entities.ai.AvoidBlockGoal;
import com.nekomaster1000.infernalexp.util.RegistryHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.monster.piglin.PiglinBruteEntity;
import net.minecraft.entity.monster.piglin.PiglinEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.EffectInstance;
import net.minecraft.state.properties.AttachFace;
import net.minecraft.stats.Stats;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraftforge.common.util.BlockSnapshot;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.PotionColorCalculationEvent;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.NoteBlockEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Mod.EventBusSubscriber(modid = InfernalExpansion.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class IEEvents {

    //Called When Config is Changed
    @SubscribeEvent
    public static void onModConfigEvent(final ModConfig.ModConfigEvent event){
        final ModConfig config = event.getConfig();
        //Recalculates what the configs should be when changed
        if (config.getSpec() == ConfigHolder.CLIENT_SPEC) {
            ConfigHelper.bakeClient(config);
        } else if (config.getSpec() == ConfigHolder.COMMON_SPEC) {
            ConfigHelper.bakeCommon(config);
        }
    }

    @SubscribeEvent
    public void onEntityJoin(EntityJoinWorldEvent event) {

        //
        //RUN AWAY!!
        //

        //Piglins fear Warpbeetles and Embodies
        if (event.getEntity() instanceof PiglinEntity){
            if(InfernalExpansionConfig.piglinFearWarpbeetle) {
                ((CreatureEntity) event.getEntity()).goalSelector.addGoal(4,
                        new AvoidEntityGoal<>((CreatureEntity) event.getEntity(),
                                WarpbeetleEntity.class, 16.0F, 1.2D, 1.2D));
            }
            if(InfernalExpansionConfig.piglinFearEmbody) {
                ((CreatureEntity) event.getEntity()).goalSelector.addGoal(4,
                        new AvoidEntityGoal<>((CreatureEntity) event.getEntity(),
                                EmbodyEntity.class, 16.0F, 1.2D, 1.2D));
            }
        }

        if(event.getEntity() instanceof HoglinEntity){
            if(InfernalExpansionConfig.hoglinFearWarpbeetle) {
                ((CreatureEntity) event.getEntity()).goalSelector.addGoal(4,
                        new AvoidEntityGoal<>((CreatureEntity) event.getEntity(),
                                WarpbeetleEntity.class, 16.0F, 1.2D, 1.2D));
            }
            if(InfernalExpansionConfig.hoglinFearEmbody) {
                ((CreatureEntity) event.getEntity()).goalSelector.addGoal(4,
                        new AvoidEntityGoal<>((CreatureEntity) event.getEntity(),
                                EmbodyEntity.class, 16.0F, 1.2D, 1.2D));
            }
        }


        //
        //ATTACK!!
        //

        //Spiders attack Warp beetles
        if (event.getEntity() instanceof SpiderEntity && InfernalExpansionConfig.spiderAttackWarpbeetle) {
            ((CreatureEntity) event.getEntity()).goalSelector.addGoal(4,
                    new NearestAttackableTargetGoal<>((CreatureEntity) event.getEntity(),
                            WarpbeetleEntity.class, true, false));

           }


        //Skeletons attacks Piglins, Brutes, Embodies & Basalt Giants
        if (event.getEntity() instanceof SkeletonEntity) {

            if(InfernalExpansionConfig.skeletonAttackPiglin) {
                ((CreatureEntity) event.getEntity()).goalSelector.addGoal(2,
                        new NearestAttackableTargetGoal<>((CreatureEntity) event.getEntity(),
                                PiglinEntity.class, true, false));
            }
            if(InfernalExpansionConfig.skeletonAttackBrute) {
                ((CreatureEntity) event.getEntity()).goalSelector.addGoal(2,
                        new NearestAttackableTargetGoal<>((CreatureEntity) event.getEntity(),
                                PiglinBruteEntity.class, true, false));
            }
            if(InfernalExpansionConfig.skeletonAttackEmbody) {
                ((CreatureEntity) event.getEntity()).goalSelector.addGoal(3,
                        new NearestAttackableTargetGoal<>((CreatureEntity) event.getEntity(),
                                EmbodyEntity.class, true, false));
            }
            if(InfernalExpansionConfig.skeletonAttackGiant) {
                ((CreatureEntity) event.getEntity()).goalSelector.addGoal(2,
                        new NearestAttackableTargetGoal<>((CreatureEntity) event.getEntity(),
                                BasaltGiantEntity.class, true, false));
            }
        }


        //Piglins attack Skeletons & Voline
        if (event.getEntity() instanceof PiglinEntity) {
            if(InfernalExpansionConfig.piglinAttackSkeleton) {
                ((CreatureEntity) event.getEntity()).goalSelector.addGoal(2,
                        new NearestAttackableTargetGoal<>((CreatureEntity) event.getEntity(),
                                SkeletonEntity.class, true, false));
            }
            if(InfernalExpansionConfig.piglinAttackVoline) {
                ((CreatureEntity) event.getEntity()).goalSelector.addGoal(2,
                        new NearestAttackableTargetGoal<>((CreatureEntity) event.getEntity(),
                                VolineEntity.class, true, false));
            }
        }

        if(event.getEntity() instanceof PiglinBruteEntity){
            if(InfernalExpansionConfig.bruteAttackSkeleton) {
                ((CreatureEntity) event.getEntity()).goalSelector.addGoal(2,
                        new NearestAttackableTargetGoal<>((CreatureEntity) event.getEntity(),
                                SkeletonEntity.class, true, false));
            }
            if(InfernalExpansionConfig.bruteAttackVoline) {
                ((CreatureEntity) event.getEntity()).goalSelector.addGoal(2,
                        new NearestAttackableTargetGoal<>((CreatureEntity) event.getEntity(),
                                VolineEntity.class, true, false));
            }
        }


        //Ghasts attack Voline, Embodies, Skeletons
        if (event.getEntity() instanceof GhastEntity) {
            
            ((FlyingEntity) event.getEntity()).targetSelector.addGoal(4,
                    new NearestAttackableTargetGoal<>((GhastEntity) event.getEntity(),
                            GlowsquitoEntity.class, true, false));

            if(InfernalExpansionConfig.ghastAttackVoline) {
                ((FlyingEntity) event.getEntity()).targetSelector.addGoal(2,
                        new NearestAttackableTargetGoal<>((GhastEntity) event.getEntity(),
                                VolineEntity.class, true, false));
            }
            if(InfernalExpansionConfig.ghastAttackEmbody) {
                ((FlyingEntity) event.getEntity()).targetSelector.addGoal(3,
                        new NearestAttackableTargetGoal<>((GhastEntity) event.getEntity(),
                                EmbodyEntity.class, true, false));
            }
            if(InfernalExpansionConfig.ghastAttackSkeleton) {
                ((FlyingEntity) event.getEntity()).targetSelector.addGoal(3,
                        new NearestAttackableTargetGoal<>((GhastEntity) event.getEntity(),
                                SkeletonEntity.class, true, false));
            }
        }

        if (event.getEntity() instanceof MagmaCubeEntity) {

            ((SlimeEntity) event.getEntity()).goalSelector.addGoal(0,
                    new AvoidBlockGoal((SlimeEntity) event.getEntity(), IEBlocks.GLOW_TORCH.get(),
                            16.0F));

            ((SlimeEntity) event.getEntity()).goalSelector.addGoal(0,
                    new AvoidBlockGoal((SlimeEntity) event.getEntity(), IEBlocks.GLOW_WALL_TORCH.get(),
                            16.0F));

            ((SlimeEntity) event.getEntity()).goalSelector.addGoal(0,
                    new AvoidBlockGoal((SlimeEntity) event.getEntity(), IEBlocks.GLOW_LANTERN.get(),
                            16.0F));

            ((SlimeEntity) event.getEntity()).goalSelector.addGoal(0,
                    new AvoidBlockGoal((SlimeEntity) event.getEntity(), IEBlocks.GLOW_CAMPFIRE.get(),
                            16.0F));

            ((SlimeEntity) event.getEntity()).goalSelector.addGoal(0,
                    new AvoidBlockGoal((SlimeEntity) event.getEntity(), IEBlocks.GLOW_FIRE.get(),
                            16.0F));
        }

    }

    //Mob Spawning in pre-existing biomes
    @SubscribeEvent
    public void onBiomeLoad(BiomeLoadingEvent event) {

        if (event.getName().toString().equals("minecraft:nether_wastes")) {

            if(InfernalExpansionConfig.volineInWastes) {
                event.getSpawns().withSpawner(EntityClassification.MONSTER,
                        new MobSpawnInfo.Spawners(IEEntityTypes.VOLINE.get(),
                                InfernalExpansionConfig.volineWastesRate, 1, 3));
            }


        } else if (event.getName().toString().equals("minecraft:crimson_forest")) {

            if(InfernalExpansionConfig.shroomloinInCrimson) {
                event.getSpawns().withSpawner(EntityClassification.MONSTER,
                        new MobSpawnInfo.Spawners(IEEntityTypes.SHROOMLOIN.get(),
                                InfernalExpansionConfig.shroomloinCrimsonRate, 1, 3));
            }

            if(InfernalExpansionConfig.volineInCrimson) {
                event.getSpawns().withSpawner(EntityClassification.MONSTER,
                        new MobSpawnInfo.Spawners(IEEntityTypes.VOLINE.get(),
                                InfernalExpansionConfig.volineCrimsonRate, 1, 5));
            }

        } else if (event.getName().toString().equals("minecraft:warped_forest")) {

        //    event.getSpawns().withSpawner(EntityClassification.MONSTER,
        //            new MobSpawnInfo.Spawners(ModEntityType.CEROBEETLE.get(), 1, 1, 1));

            if(InfernalExpansionConfig.warpbeetleInWarped) {
                event.getSpawns().withSpawner(EntityClassification.MONSTER,
                        new MobSpawnInfo.Spawners(IEEntityTypes.WARPBEETLE.get(),
                                InfernalExpansionConfig.warpbeetleWarpedRate, 1, 1));
            }

        } else if (event.getName().toString().equals("minecraft:basalt_deltas")) {

            if(InfernalExpansionConfig.giantInDeltas) {
                event.getSpawns().withSpawner(EntityClassification.MONSTER,
                        new MobSpawnInfo.Spawners(IEEntityTypes.BASALT_GIANT.get(),
                                InfernalExpansionConfig.giantDeltasRate, 1, 1));
            }

            //event.getSpawns().withSpawner(EntityClassification.MONSTER,
            //        new MobSpawnInfo.Spawners(ModEntityType.GLOWSQUITO.get(), 1, 5, 10));


        } else if (event.getName().toString().equals("minecraft:soul_sand_valley")) {

            if(InfernalExpansionConfig.embodyInSSV) {
                event.getSpawns().withSpawner(EntityClassification.MONSTER,
                        new MobSpawnInfo.Spawners(IEEntityTypes.EMBODY.get(),
                                InfernalExpansionConfig.embodySSVRate, 1, 5));
            }

            event.getSpawns().withSpawner(EntityClassification.MONSTER,
                    new MobSpawnInfo.Spawners(IEEntityTypes.SKELETAL_PIGLIN.get(), 10, 1, 1));


            //Mob Spawning in new biomes

        } else if (event.getName().toString().equals("infernalexp:glowstone_canyon")) {
            event.getSpawns().withSpawner(EntityClassification.MONSTER,
                    new MobSpawnInfo.Spawners(IEEntityTypes.GLOWSQUITO.get(), 80, 1, 10));

            event.getSpawns().withSpawner(EntityClassification.MONSTER,
                    new MobSpawnInfo.Spawners(IEEntityTypes.BLINDSIGHT.get(), 10, 1, 1));

            event.getSpawns().withSpawner(EntityClassification.MONSTER,
                    new MobSpawnInfo.Spawners(IEEntityTypes.SKELETAL_PIGLIN.get(), 10, 1, 1));

            event.getSpawns().withSpawner(EntityClassification.MONSTER,
                    new MobSpawnInfo.Spawners(IEEntityTypes.BLACKSTONE_DWARF.get(), 1, 1, 1));

            //event.getSpawns().withSpawner(EntityClassification.MONSTER,
            //        new MobSpawnInfo.Spawners(EntityType.GHAST, 20, 1, 1));
            // Not spawning for some reason?

        } else if (event.getName().toString().equals("infernalexp:delta_shores")) {

            event.getSpawns().withSpawner(EntityClassification.MONSTER,
                    new MobSpawnInfo.Spawners(IEEntityTypes.BASALT_GIANT.get(), 4, 1, 1));

            event.getSpawns().withSpawner(EntityClassification.MONSTER,
                    new MobSpawnInfo.Spawners(IEEntityTypes.SKELETAL_PIGLIN.get(), 6, 1, 1));

        }
    }

    //Blocks being broken
    @SubscribeEvent
    public void onBlockBreak(BlockEvent.BreakEvent event){
        if(event.getState().equals(Blocks.CRIMSON_FUNGUS.getDefaultState())
                || event.getState().equals(Blocks.CRIMSON_ROOTS.getDefaultState())
                || event.getState().equals(Blocks.CRIMSON_STEM.getDefaultState())
                || event.getState().equals(Blocks.STRIPPED_CRIMSON_STEM.getDefaultState())
                || event.getState().equals(Blocks.WEEPING_VINES.getDefaultState())
                || event.getState().equals(Blocks.WEEPING_VINES_PLANT.getDefaultState())
                || event.getState().equals(Blocks.NETHER_WART_BLOCK.getDefaultState())) {
            List<?> list = event.getPlayer().world.getEntitiesWithinAABB(ShroomloinEntity.class,
                    event.getPlayer().getBoundingBox().grow(32.0D));
            for(int j = 0; j < list.size(); j++)
            {
                Entity entity = (Entity)list.get(j);
                if(entity instanceof ShroomloinEntity)
                {
                    ShroomloinEntity shroomloinEntity = (ShroomloinEntity) entity;
                    shroomloinEntity.becomeAngryAt(event.getPlayer());
                }
            }
        }
    }
    
    // Custom note block sounds
    @SubscribeEvent
    public void noteBlockPlayed(NoteBlockEvent.Play event) {
        World world = (World) event.getWorld();
        BlockPos noteBlockPos = event.getPos();
        SoundEvent sound = null;
        Block blockUnder = world.getBlockState(noteBlockPos.down()).getBlock();
        if (blockUnder == Blocks.GILDED_BLACKSTONE) {
            sound = RegistryHandler.cymbal;
        } else if (blockUnder == Blocks.ANCIENT_DEBRIS) {
            sound = RegistryHandler.electric_guitar;
        } else if (blockUnder == Blocks.SOUL_SOIL) {
            sound = RegistryHandler.choir;
        } else if (blockUnder == IEBlocks.DIMSTONE.get()) {
            sound = RegistryHandler.saxophone;
        } else if (blockUnder == Blocks.CRYING_OBSIDIAN) {
            sound = RegistryHandler.violin;
        }
        
        if (sound != null) {
            float pitch = (float) Math.pow(2.0, (event.getVanillaNoteId() - 12) / 12.0); // Math to get correct pitch
            world.playSound(null, noteBlockPos, sound, SoundCategory.RECORDS, 1F, pitch);
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        ItemStack heldItemStack = event.getItemStack();
        World world = event.getWorld();
        BlockPos pos = event.getPos();
        Direction face = event.getFace();
        PlayerEntity player = event.getPlayer();
        if (heldItemStack.getItem() == Items.BONE) {
            pos = pos.offset(face);
            BlockState blockstate = IEBlocks.BURIED_BONE.get().getPlaceableState(world, pos, face);
            if (blockstate != null) {
                player.swingArm(event.getHand());
                if (!world.isAirBlock(pos) && !world.isRemote() && world.getBlockState(pos).getFluidState().isEmpty()) {
                    world.destroyBlock(pos, true);
                }
                world.setBlockState(pos, blockstate, 3);
                world.playSound(player, pos, blockstate.getSoundType().getPlaceSound(), SoundCategory.BLOCKS, 1.0F, 1.0F);
                if (!player.isCreative()) {
                    heldItemStack.shrink(1);
                }
                ForgeEventFactory.onBlockPlace(player, BlockSnapshot.create(world.getDimensionKey(), world, pos), face);
            }
        }
    }

    @SubscribeEvent
    public void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
        World world = event.getWorld();
        PlayerEntity player = event.getPlayer();
        ItemStack heldItemStack = player.getHeldItem(event.getHand());

        if (heldItemStack.getItem() == Items.MAGMA_CREAM) {
            player.swingArm(event.getHand());

            if (!world.isRemote) {
                ThrowableMagmaCreamEntity throwableMagmaCreamEntity = new ThrowableMagmaCreamEntity(world, player);
                throwableMagmaCreamEntity.setItem(heldItemStack);
                throwableMagmaCreamEntity.func_234612_a_(player, player.rotationPitch, player.rotationYaw, -20, 0.5f, 1);
                world.addEntity(throwableMagmaCreamEntity);
            }

            player.addStat(Stats.ITEM_USED.get(heldItemStack.getItem()));

            if (!player.abilities.isCreativeMode) {
                heldItemStack.shrink(1);
            }
        }
    }

    @SubscribeEvent
    public void onApplyBonemeal(BonemealEvent event) {
        Block block = event.getBlock().getBlock();
        World world = event.getWorld();
        BlockPos pos = event.getPos();
        if (block == Blocks.SHROOMLIGHT && InfernalExpansionConfig.isShroomlightGrowable) {
            pos = pos.down();
            if (world.isAirBlock(pos)) {
                event.setResult(Event.Result.ALLOW);
                if (world.getRandom().nextDouble() < InfernalExpansionConfig.shroomlightGrowChance && !world.isRemote()) {
                    world.setBlockState(pos, IEBlocks.SHROOMLIGHT_FUNGUS.get().getDefaultState().with(HorizontalBushBlock.FACE, AttachFace.CEILING), 3);
                }
            }
        }
    }

    // Register features and surface builders
    @SubscribeEvent
    public static void registerFeatures(RegistryEvent.Register<Feature<?>> event) {
        IEFeatures.features.forEach(feature -> event.getRegistry().register(feature));
    }

    @SubscribeEvent
    public static void registerSurfaceBuilders(RegistryEvent.Register<SurfaceBuilder<?>> event) {
        IESurfaceBuilders.surfaceBuilders.forEach(surfaceBuilder -> event.getRegistry().register(surfaceBuilder));
    }

    @SubscribeEvent
    public static void registerWorldCarvers(RegistryEvent.Register<WorldCarver<?>> event) {
        IECarvers.carvers.forEach(carver -> event.getRegistry().register(carver));
    }

    @SubscribeEvent
    public void onPotionColorCalculate(PotionColorCalculationEvent event) {
        List<EffectInstance> effects = new ArrayList<>(event.getEffects());

        for (EffectInstance effectInstance : effects) {
            if (effectInstance.getPotion() == IEEffects.INFECTION.get()) {
                if (effects.size() == 1) {
                    event.shouldHideParticles(true);
                }

//                effects.remove(effectInstance);
                break;
            }
        }


    }

    @SubscribeEvent
    public void onLivingEntityUpdate(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();

        if (entity.isPotionActive(IEEffects.INFECTION.get())) {
            if ((Objects.requireNonNull(entity.getActivePotionEffect(IEEffects.INFECTION.get())).getDuration() & 10) == 0 && Objects.requireNonNull(entity.getActivePotionEffect(IEEffects.INFECTION.get())).doesShowParticles()) {
                for (int i = 0; i < 1; i++) {
                    entity.world.addParticle(IEParticleTypes.INFECTION.get(), entity.getPosXRandom(entity.getBoundingBox().getXSize()), entity.getPosYRandom(), entity.getPosZRandom(entity.getBoundingBox().getZSize()), 0, 0, 0);
                }
            }
        }
    }
}
