package com.nekomaster1000.infernalexp.world.gen;

import com.nekomaster1000.infernalexp.entities.*;
import com.nekomaster1000.infernalexp.init.ModEntityTypes;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.world.gen.Heightmap;

public class ModEntityPlacement {

    public static void spawnPlacement() {
        EntitySpawnPlacementRegistry.register(ModEntityTypes.VOLINE.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, VolineEntity::canSpawnOn);
        EntitySpawnPlacementRegistry.register(ModEntityTypes.SHROOMLOIN.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, VolineEntity::canSpawnOn);
        EntitySpawnPlacementRegistry.register(ModEntityTypes.WARPBEETLE.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, WarpbeetleEntity::canSpawnOn);
        EntitySpawnPlacementRegistry.register(ModEntityTypes.CEROBEETLE.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, CerobeetleEntity::canSpawnOn);
        EntitySpawnPlacementRegistry.register(ModEntityTypes.EMBODY.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, EmbodyEntity::canSpawnOn);
        EntitySpawnPlacementRegistry.register(ModEntityTypes.BASALT_GIANT.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, BasaltGiantEntity::canSpawnOn);
        EntitySpawnPlacementRegistry.register(ModEntityTypes.BLACKSTONE_DWARF.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, BlackstoneDwarfEntity::canSpawnOn);
        EntitySpawnPlacementRegistry.register(ModEntityTypes.GLOWSQUITO.get(), EntitySpawnPlacementRegistry.PlacementType.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, GlowsquitoEntity::canSpawnOn);
        EntitySpawnPlacementRegistry.register(ModEntityTypes.SKELETAL_PIGLIN.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, SkeletalPiglinEntity::canSpawnOn);
        EntitySpawnPlacementRegistry.register(ModEntityTypes.PYRNO.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, PyrnoEntity::canSpawnOn);
        EntitySpawnPlacementRegistry.register(ModEntityTypes.BLINDSIGHT.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, BlindsightEntity::canSpawnOn);
    }

}
