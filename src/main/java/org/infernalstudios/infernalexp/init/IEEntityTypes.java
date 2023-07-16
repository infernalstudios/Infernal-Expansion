package org.infernalstudios.infernalexp.init;

import java.util.function.Supplier;

import javax.annotation.Nonnull;
import net.minecraft.data.loot.EntityLoot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import org.infernalstudios.infernalexp.InfernalExpansion;
import org.infernalstudios.infernalexp.data.DataGenDeferredRegister;
import org.infernalstudios.infernalexp.data.providers.IELootProviders;
import org.infernalstudios.infernalexp.entities.AscusBombEntity;
import org.infernalstudios.infernalexp.entities.BasaltGiantEntity;
import org.infernalstudios.infernalexp.entities.BlackstoneDwarfEntity;
import org.infernalstudios.infernalexp.entities.BlindsightEntity;
import org.infernalstudios.infernalexp.entities.EmbodyEntity;
import org.infernalstudios.infernalexp.entities.GlowsilkArrowEntity;
import org.infernalstudios.infernalexp.entities.GlowsilkMothEntity;
import org.infernalstudios.infernalexp.entities.GlowsquitoEntity;
import org.infernalstudios.infernalexp.entities.InfernalPaintingEntity;
import org.infernalstudios.infernalexp.entities.ShroomloinEntity;
import org.infernalstudios.infernalexp.entities.ThrowableBrickEntity;
import org.infernalstudios.infernalexp.entities.ThrowableFireChargeEntity;
import org.infernalstudios.infernalexp.entities.ThrowableMagmaCreamEntity;
import org.infernalstudios.infernalexp.entities.ThrowableNetherBrickEntity;
import org.infernalstudios.infernalexp.entities.VolineEntity;
import org.infernalstudios.infernalexp.entities.WarpbeetleEntity;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class IEEntityTypes {

//    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, InfernalExpansion.MOD_ID);
    public static final DataGenDeferredRegister<EntityType<?>, DataGenDeferredRegister.NoneDataProvider, EntityLoot> ENTITY_TYPES = new DataGenDeferredRegister<>(ForgeRegistries.ENTITIES);

    // Entity Types

    public static final RegistryObject<EntityType<VolineEntity>> VOLINE = registerLivingEntityType(
        "voline",
        () -> EntityType.Builder.of(VolineEntity::new, MobCategory.MONSTER)
            .sized(0.6f, 0.8f) // Hitbox Size
            .build(new ResourceLocation(InfernalExpansion.MOD_ID, "voline").toString()),
        IELootProviders.voline()
    );

    public static final RegistryObject<EntityType<ShroomloinEntity>> SHROOMLOIN = registerLivingEntityType(
        "shroomloin",
        () -> EntityType.Builder.of(ShroomloinEntity::new, IEEntityClassifications.IECREATURE)
            .sized(1.0f, 1.0f) // Hitbox Size
            .build(new ResourceLocation(InfernalExpansion.MOD_ID, "shroomloin").toString()),
        IELootProviders.shroomloin()
    );

    public static final RegistryObject<EntityType<WarpbeetleEntity>> WARPBEETLE = registerLivingEntityType(
        "warpbeetle",
        () -> EntityType.Builder.of(WarpbeetleEntity::new, IEEntityClassifications.IECREATURE)
            .sized(0.5f, 0.5f) // Hitbox Size
            .build(new ResourceLocation(InfernalExpansion.MOD_ID, "warpbeetle").toString()),
        IELootProviders.warpbeetle()
    );

    /*
    public static final RegistryObject<EntityType<CerobeetleEntity>> CEROBEETLE = ENTITY_TYPES.register("cerobeetle",
            () -> EntityType.Builder.create(CerobeetleEntity::new, EntityClassification.MONSTER)
            .size(2.0f, 1.5f) // Hitbox Size
                    .build(new ResourceLocation(InfernalExpansion.MOD_ID, "cerobeetle").toString()));
     */

    public static final RegistryObject<EntityType<EmbodyEntity>> EMBODY = registerLivingEntityType(
        "embody",
        () -> EntityType.Builder.of(EmbodyEntity::new, MobCategory.MONSTER)
            .sized(0.8f, 0.9f)// Hitbox Size
            .build(new ResourceLocation(InfernalExpansion.MOD_ID, "embody").toString()),
        IELootProviders.embody()
    );

    public static final RegistryObject<EntityType<BasaltGiantEntity>> BASALT_GIANT = registerLivingEntityType(
        "basalt_giant",
        () -> EntityType.Builder.<BasaltGiantEntity>of(BasaltGiantEntity::new, IEEntityClassifications.IECREATURE)
            .sized(1.2f, 4.3f)// Hitbox Size
            .build(new ResourceLocation(InfernalExpansion.MOD_ID, "basalt_giant").toString()),
        IELootProviders.basaltGiant()
    );

    public static final RegistryObject<EntityType<BlackstoneDwarfEntity>> BLACKSTONE_DWARF = registerLivingEntityType(
        "blackstone_dwarf",
        () -> EntityType.Builder.of(BlackstoneDwarfEntity::new, MobCategory.MONSTER)
            .sized(1.2f, 2.9f)// Hitbox Size
            .build(new ResourceLocation(InfernalExpansion.MOD_ID, "blackstone_dwarf").toString()),
        IELootProviders.blackstoneDwarf()
    );

    public static final RegistryObject<EntityType<GlowsquitoEntity>> GLOWSQUITO = registerLivingEntityType(
        "glowsquito",
        () -> EntityType.Builder.of(GlowsquitoEntity::new, IEEntityClassifications.IECREATURE)
            .sized(0.8f, 0.5f)// Hitbox Size
            .build(new ResourceLocation(InfernalExpansion.MOD_ID, "glowsquito").toString()),
        IELootProviders.glowsquito()
    );

    /*
    public static final RegistryObject<EntityType<PyrnoEntity>> PYRNO = ENTITY_TYPES.register("pyrno",
            () -> EntityType.Builder.create(PyrnoEntity::new, EntityClassification.MONSTER)
                    .size(1.5f, 1.5f) // Hitbox Size
                    .build(new ResourceLocation(InfernalExpansion.MOD_ID, "pyrno").toString()));
     */

    public static final RegistryObject<EntityType<BlindsightEntity>> BLINDSIGHT = registerLivingEntityType(
        "blindsight",
        () -> EntityType.Builder.of(BlindsightEntity::new, MobCategory.MONSTER)
            .sized(1.0F, 0.8F) //Hitbox Size
            .build(new ResourceLocation(InfernalExpansion.MOD_ID, "blindsight").toString()),
        IELootProviders.blindsight()
    );

    public static final RegistryObject<EntityType<GlowsilkMothEntity>> GLOWSILK_MOTH = registerLivingEntityType(
        "glowsilk_moth",
        () -> EntityType.Builder.of(GlowsilkMothEntity::new, IEEntityClassifications.IECREATURE)
            .sized(1.0F, 1.5F) //Hitbox Size
            .build(new ResourceLocation(InfernalExpansion.MOD_ID, "glowsilk_moth").toString()),
        IELootProviders.glowsilkMoth()
    );

    public static final RegistryObject<EntityType<AscusBombEntity>> ASCUS_BOMB = registryNonLivingEntityType(
        "ascus_bomb",
        () -> EntityType.Builder.<AscusBombEntity>of(AscusBombEntity::new, MobCategory.MISC)
            .sized(0.25f, 0.25f).clientTrackingRange(4).updateInterval(10)
            .build(new ResourceLocation(InfernalExpansion.MOD_ID, "ascus_bomb").toString())
    );

    public static final RegistryObject<EntityType<ThrowableMagmaCreamEntity>> THROWABLE_MAGMA_CREAM = registryNonLivingEntityType(
        "throwable_magma_cream",
        () -> EntityType.Builder.<ThrowableMagmaCreamEntity>of(ThrowableMagmaCreamEntity::new, MobCategory.MISC)
            .sized(0.25f, 0.25f).clientTrackingRange(4).updateInterval(10)
            .build(new ResourceLocation(InfernalExpansion.MOD_ID, "throwable_magma_cream").toString())
    );

    public static final RegistryObject<EntityType<ThrowableFireChargeEntity>> THROWABLE_FIRE_CHARGE = registryNonLivingEntityType(
        "throwable_fire_charge",
        () -> EntityType.Builder.<ThrowableFireChargeEntity>of(ThrowableFireChargeEntity::new, MobCategory.MISC)
            .sized(0.25f, 0.25f).clientTrackingRange(4).updateInterval(10)
            .build(new ResourceLocation(InfernalExpansion.MOD_ID, "throwable_fire_charge").toString())
    );

    public static final RegistryObject<EntityType<GlowsilkArrowEntity>> GLOWSILK_ARROW = registryNonLivingEntityType(
        "glowsilk_arrow",
        () -> EntityType.Builder.<GlowsilkArrowEntity>of(GlowsilkArrowEntity::new, MobCategory.MISC)
            .sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20)
            .build(new ResourceLocation(InfernalExpansion.MOD_ID, "glowsilk_arrow").toString())
    );

    public static final RegistryObject<EntityType<InfernalPaintingEntity>> INFERNAL_PAINTING = registryNonLivingEntityType(
        "infernal_painting",
        () -> EntityType.Builder.<InfernalPaintingEntity>of(InfernalPaintingEntity::new, MobCategory.MISC)
            .sized(0.5F, 0.5F).clientTrackingRange(10).updateInterval(Integer.MAX_VALUE)
            .build(new ResourceLocation(InfernalExpansion.MOD_ID, "infernal_painting").toString())
    );

    public static final RegistryObject<EntityType<ThrowableBrickEntity>> THROWABLE_BRICK = registryNonLivingEntityType(
        "throwable_brick",
        () -> EntityType.Builder.<ThrowableBrickEntity>of(ThrowableBrickEntity::new, MobCategory.MISC)
            .sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10)
            .build(new ResourceLocation(InfernalExpansion.MOD_ID, "throwable_brick").toString())
    );

    public static final RegistryObject<EntityType<ThrowableNetherBrickEntity>> THROWABLE_NETHER_BRICK = registryNonLivingEntityType(
        "throwable_nether_brick",
        () -> EntityType.Builder.<ThrowableNetherBrickEntity>of(ThrowableNetherBrickEntity::new, MobCategory.MISC)
            .sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10)
            .build(new ResourceLocation(InfernalExpansion.MOD_ID, "throwable_nether_brick").toString())
    );

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
        InfernalExpansion.LOGGER.info("Infernal Expansion: Entity Types Registered!");
    }

    private static <T extends Entity> RegistryObject<EntityType<T>> registerLivingEntityType(String name, Supplier<EntityType<T>> entityTypeSupplier, @Nonnull IELootProviders.EntityLootProviderConsumer lootProvider) {
        return ENTITY_TYPES.register(name, entityTypeSupplier, null, lootProvider);
    }

    private static <T extends Entity> RegistryObject<EntityType<T>> registryNonLivingEntityType(String name, Supplier<EntityType<T>> entityTypeSupplier) {
        return ENTITY_TYPES.register(name, entityTypeSupplier, null, null);
    }
}
