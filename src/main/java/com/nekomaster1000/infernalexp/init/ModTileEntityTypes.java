package com.nekomaster1000.infernalexp.init;

import com.nekomaster1000.infernalexp.InfernalExpansion;
import com.nekomaster1000.infernalexp.tileentities.GlowCampfireTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModTileEntityTypes {

    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, InfernalExpansion.MOD_ID);

    public static final RegistryObject<TileEntityType<GlowCampfireTileEntity>> GLOW_CAMPFIRE_TILE_ENTITY = TILE_ENTITY_TYPES.register("glow_campfire_tile_entity", () -> TileEntityType.Builder.create(GlowCampfireTileEntity::new, ModBlocks.GLOW_CAMPFIRE.get()).build(null));

}
