package com.nekomaster1000.infernalexp.init;

import com.nekomaster1000.infernalexp.InfernalExpansion;
import com.nekomaster1000.infernalexp.tileentities.GlowCampfireTileEntity;

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class IETileEntityTypes {

    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, InfernalExpansion.MOD_ID);

    public static final RegistryObject<TileEntityType<GlowCampfireTileEntity>> GLOW_CAMPFIRE = TILE_ENTITY_TYPES.register("glow_campfire", () -> TileEntityType.Builder.create(GlowCampfireTileEntity::new, IEBlocks.GLOW_CAMPFIRE.get()).build(null));

    public static void register(IEventBus eventBus) {
        TILE_ENTITY_TYPES.register(eventBus);
        InfernalExpansion.LOGGER.info("Infernal Expansion: Tile Entity Types Registered!");
    }

}
