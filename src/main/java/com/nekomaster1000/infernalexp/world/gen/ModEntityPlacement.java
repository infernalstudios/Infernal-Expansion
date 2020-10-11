package com.nekomaster1000.infernalexp.world.gen;

import com.nekomaster1000.infernalexp.entities.*;
import com.nekomaster1000.infernalexp.init.ModEntityType;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.world.gen.Heightmap;

public class ModEntityPlacement {

    public static void spawnPlacement() {
        EntitySpawnPlacementRegistry.register(ModEntityType.GLOWSQUITO.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, GlowsquitoEntity::canSpawnOn);
        EntitySpawnPlacementRegistry.register(ModEntityType.VOLINE.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, VolineEntity::canSpawnOn);
        EntitySpawnPlacementRegistry.register(ModEntityType.PYRNO.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, PyrnoEntity::canSpawnOn);
        EntitySpawnPlacementRegistry.register(ModEntityType.WARPBEETLE.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, WarpbeetleEntity::canSpawnOn);
        EntitySpawnPlacementRegistry.register(ModEntityType.CEROBEETLE.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, CerobeetleEntity::canSpawnOn);
        EntitySpawnPlacementRegistry.register(ModEntityType.EMBODY.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, EmbodyEntity::canSpawnOn);
        EntitySpawnPlacementRegistry.register(ModEntityType.BASALT_GIANT.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, BasaltGiantEntity::canSpawnOn);
    }

}
