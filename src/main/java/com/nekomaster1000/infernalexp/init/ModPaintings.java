package com.nekomaster1000.infernalexp.init;

import com.nekomaster1000.infernalexp.InfernalExpansion;
import net.minecraft.entity.item.PaintingType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModPaintings {
    public static DeferredRegister<PaintingType> PAINTING_TYPES= DeferredRegister.create(ForgeRegistries.PAINTING_TYPES, InfernalExpansion.MOD_ID);

    public static RegistryObject<PaintingType> THE_FALLEN_ONES = PAINTING_TYPES.register("the_fallen_ones",()-> new PaintingType(48, 64));
    public static RegistryObject<PaintingType> CHILLING_ISLES = PAINTING_TYPES.register("chilling_isles",()-> new PaintingType(48, 48));
    public static RegistryObject<PaintingType> VOLINE = PAINTING_TYPES.register("voline",()-> new PaintingType(16, 16));


}
