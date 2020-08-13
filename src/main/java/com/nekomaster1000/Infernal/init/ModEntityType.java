package com.nekomaster1000.Infernal.init;

import com.nekomaster1000.Infernal.InfernalExpansion;
import com.nekomaster1000.Infernal.entities.*;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEntityType {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, InfernalExpansion.MOD_ID);

    // Entity Types
    public static final RegistryObject<EntityType<GlowsquitoEntity>> GLOWSQUITO = ENTITY_TYPES.register("glowsquito",
            () -> EntityType.Builder.create(GlowsquitoEntity::new, EntityClassification.CREATURE)
                    .size(0.5f, 0.5f)// Hitbox Size
                    .build(new ResourceLocation(InfernalExpansion.MOD_ID, "glowsquito").toString()));

    public static final RegistryObject<EntityType<VolineEntity>> VOLINE = ENTITY_TYPES.register("voline",
            () -> EntityType.Builder.create(VolineEntity::new, EntityClassification.CREATURE)
                    .size(0.5f, 0.5f) // Hitbox Size
                    .build(new ResourceLocation(InfernalExpansion.MOD_ID, "voline").toString()));

    public static final RegistryObject<EntityType<PyrnoEntity>> PYRNO = ENTITY_TYPES.register("pyrno",
            () -> EntityType.Builder.create(PyrnoEntity::new, EntityClassification.CREATURE)
                    .size(1.5f, 1.5f) // Hitbox Size
                    .build(new ResourceLocation(InfernalExpansion.MOD_ID, "pyrno").toString()));

    public static final RegistryObject<EntityType<WarpbeetleEntity>> WARPBEETLE = ENTITY_TYPES.register("warpbeetle",
            () -> EntityType.Builder.create(WarpbeetleEntity::new, EntityClassification.CREATURE)
            .size(0.5f, 0.5f) // Hitbox Size
                    .build(new ResourceLocation(InfernalExpansion.MOD_ID, "warpbeetle").toString()));

    public static final RegistryObject<EntityType<EmbodyEntity>> EMBODY = ENTITY_TYPES.register("embody",
            () -> EntityType.Builder.create(EmbodyEntity::new, EntityClassification.MONSTER)
                    .size(0.5f, 0.5f)// Hitbox Size
                    .build(new ResourceLocation(InfernalExpansion.MOD_ID, "embody").toString()));

}