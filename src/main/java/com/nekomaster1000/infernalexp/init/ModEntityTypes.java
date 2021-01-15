package com.nekomaster1000.infernalexp.init;

import com.nekomaster1000.infernalexp.InfernalExpansion;
import com.nekomaster1000.infernalexp.entities.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEntityTypes {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, InfernalExpansion.MOD_ID);

    // Entity Types

    public static final RegistryObject<EntityType<VolineEntity>> VOLINE = ENTITY_TYPES.register("voline",
            () -> EntityType.Builder.create(VolineEntity::new, EntityClassification.MONSTER)
                    .size(0.6f, 0.8f) // Hitbox Size
                    .build(new ResourceLocation(InfernalExpansion.MOD_ID, "voline").toString()));

    public static final RegistryObject<EntityType<ShroomloinEntity>> SHROOMLOIN = ENTITY_TYPES.register("shroomloin",
            () -> EntityType.Builder.create(ShroomloinEntity::new, EntityClassification.MONSTER)
                    .size(1.0f, 1.0f) // Hitbox Size
                    .build(new ResourceLocation(InfernalExpansion.MOD_ID, "shroomloin").toString()));

    public static final RegistryObject<EntityType<WarpbeetleEntity>> WARPBEETLE = ENTITY_TYPES.register("warpbeetle",
            () -> EntityType.Builder.create(WarpbeetleEntity::new, EntityClassification.MONSTER)
            .size(0.5f, 0.5f) // Hitbox Size
                    .build(new ResourceLocation(InfernalExpansion.MOD_ID, "warpbeetle").toString()));

    public static final RegistryObject<EntityType<CerobeetleEntity>> CEROBEETLE = ENTITY_TYPES.register("cerobeetle",
            () -> EntityType.Builder.create(CerobeetleEntity::new, EntityClassification.MONSTER)
            .size(2.0f, 1.5f) // Hitbox Size
                    .build(new ResourceLocation(InfernalExpansion.MOD_ID, "cerobeetle").toString()));

    public static final RegistryObject<EntityType<EmbodyEntity>> EMBODY = ENTITY_TYPES.register("embody",
            () -> EntityType.Builder.create(EmbodyEntity::new, EntityClassification.MONSTER)
                    .size(0.8f, 0.9f)// Hitbox Size
                    .build(new ResourceLocation(InfernalExpansion.MOD_ID, "embody").toString()));

    public static final RegistryObject<EntityType<BasaltGiantEntity>> BASALT_GIANT = ENTITY_TYPES.register("basalt_giant",
            () -> EntityType.Builder.<BasaltGiantEntity>create(BasaltGiantEntity::new, EntityClassification.MONSTER)
                    .size(1.2f, 4.3f)// Hitbox Size
                    .build(new ResourceLocation(InfernalExpansion.MOD_ID, "basalt_giant").toString()));

    public static final RegistryObject<EntityType<BlackstoneDwarfEntity>> BLACKSTONE_DWARF = ENTITY_TYPES.register("blackstone_dwarf",
            () -> EntityType.Builder.create(BlackstoneDwarfEntity::new, EntityClassification.MONSTER)
                    .size(1.2f, 4.0f)// Hitbox Size
                    .build(new ResourceLocation(InfernalExpansion.MOD_ID, "blackstone_dwarf").toString()));

    public static final RegistryObject<EntityType<GlowsquitoEntity>> GLOWSQUITO = ENTITY_TYPES.register("glowsquito",
            () -> EntityType.Builder.create(GlowsquitoEntity::new, EntityClassification.MONSTER)
                    .size(0.8f, 0.5f)// Hitbox Size
                    .build(new ResourceLocation(InfernalExpansion.MOD_ID, "glowsquito").toString()));

    public static final RegistryObject<EntityType<SkeletalPiglinEntity>> SKELETAL_PIGLIN = ENTITY_TYPES.register("skeletal_piglin",
            () -> EntityType.Builder.create(SkeletalPiglinEntity::new, EntityClassification.MONSTER)
                    .size(0.8f, 2.0f) // Hitbox Size
                    .build(new ResourceLocation(InfernalExpansion.MOD_ID, "skeletal_piglin").toString()));

    public static final RegistryObject<EntityType<PyrnoEntity>> PYRNO = ENTITY_TYPES.register("pyrno",
            () -> EntityType.Builder.create(PyrnoEntity::new, EntityClassification.MONSTER)
                    .size(1.5f, 1.5f) // Hitbox Size
                    .build(new ResourceLocation(InfernalExpansion.MOD_ID, "pyrno").toString()));

    public static final RegistryObject<EntityType<BlindsightEntity>> BLINDSIGHT = ENTITY_TYPES.register("blindsight",
            () -> EntityType.Builder.create(BlindsightEntity::new, EntityClassification.MONSTER)
                    .size(1.0F, 0.8F) //Hitbox Size
                    .build(new ResourceLocation(InfernalExpansion.MOD_ID, "blindsight").toString()));

    private static <T extends Entity> RegistryObject<EntityType<T>> register(String key, EntityType.IFactory<T> factoryIn, EntityClassification classificationIn, float size1, float size2) {
        return ENTITY_TYPES.register(key, () -> EntityType.Builder.create(factoryIn, classificationIn)
                .size(size1, size2)
                .build(new ResourceLocation(InfernalExpansion.MOD_ID, key).toString()));
    }

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
        InfernalExpansion.LOGGER.info("Infernal Expansion: Entity Types Registered!");
    }
}