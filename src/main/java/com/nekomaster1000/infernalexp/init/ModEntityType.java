package com.nekomaster1000.infernalexp.init;

import com.nekomaster1000.infernalexp.InfernalExpansion;
import com.nekomaster1000.infernalexp.entities.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
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
                    .size(0.8f, 0.5f)// Hitbox Size
                    .build(new ResourceLocation(InfernalExpansion.MOD_ID, "glowsquito").toString()));

    public static final RegistryObject<EntityType<VolineEntity>> VOLINE = ENTITY_TYPES.register("voline",
            () -> EntityType.Builder.create(VolineEntity::new, EntityClassification.CREATURE)
                    .size(0.8f, 0.8f) // Hitbox Size
                    .build(new ResourceLocation(InfernalExpansion.MOD_ID, "voline").toString()));

    public static final RegistryObject<EntityType<PyrnoEntity>> PYRNO = ENTITY_TYPES.register("pyrno",
            () -> EntityType.Builder.create(PyrnoEntity::new, EntityClassification.CREATURE)
                    .size(1.5f, 1.5f) // Hitbox Size
                    .build(new ResourceLocation(InfernalExpansion.MOD_ID, "pyrno").toString()));

    public static final RegistryObject<EntityType<WarpbeetleEntity>> WARPBEETLE = ENTITY_TYPES.register("warpbeetle",
            () -> EntityType.Builder.create(WarpbeetleEntity::new, EntityClassification.CREATURE)
            .size(0.5f, 0.5f) // Hitbox Size
                    .build(new ResourceLocation(InfernalExpansion.MOD_ID, "warpbeetle").toString()));

    public static final RegistryObject<EntityType<CerobeetleEntity>> CEROBEETLE = ENTITY_TYPES.register("cerobeetle",
            () -> EntityType.Builder.create(CerobeetleEntity::new, EntityClassification.CREATURE)
            .size(2.0f, 2.0f) // Hitbox Size
                    .build(new ResourceLocation(InfernalExpansion.MOD_ID, "cerobeetle").toString()));

    public static final RegistryObject<EntityType<EmbodyEntity>> EMBODY = ENTITY_TYPES.register("embody",
            () -> EntityType.Builder.create(EmbodyEntity::new, EntityClassification.MONSTER)
                    .size(0.8f, 0.9f)// Hitbox Size
                    .build(new ResourceLocation(InfernalExpansion.MOD_ID, "embody").toString()));

    public static final RegistryObject<EntityType<BasaltGiantEntity>> BASALT_GIANT = ENTITY_TYPES.register("basalt_giant",
            () -> EntityType.Builder.create(BasaltGiantEntity::new, EntityClassification.MONSTER)
                    .size(2.6f, 4.3f)// Hitbox Size
                    .build(new ResourceLocation(InfernalExpansion.MOD_ID, "basalt_giant").toString()));

    public static final RegistryObject<EntityType<BasaltTitanEntity>> BASALT_TITAN = ENTITY_TYPES.register("basalt_titan",
            () -> EntityType.Builder.create(BasaltTitanEntity::new, EntityClassification.MONSTER)
                    .size(5.0f, 8.5f)// Hitbox Size
                    .build(new ResourceLocation(InfernalExpansion.MOD_ID, "basalt_titan").toString()));

    //public static final RegistryObject<EntityType<BasaltGiantEntity>> BASALT_GIANT =
    //        register("basalt_giant", BasaltGiantEntity::new, EntityClassification.CREATURE, 3.0f, 2.0f);

    private static <T extends Entity> RegistryObject<EntityType<T>> register(
            String key, EntityType.IFactory<T> factoryIn, EntityClassification classificationIn, float size1, float size2) {
        return ENTITY_TYPES.register(key, () -> EntityType.Builder.create(factoryIn, classificationIn)
                .size(size1, size2)
                .build(new ResourceLocation(InfernalExpansion.MOD_ID, key).toString()));
    }

}