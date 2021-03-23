package com.nekomaster1000.infernalexp.mixin.client;

import java.util.Set;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import com.nekomaster1000.infernalexp.access.SoulFireAccess;

import net.minecraft.client.renderer.model.ModelBakery;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.ModList;

@OnlyIn(Dist.CLIENT)
@Mixin(ModelBakery.class)
public class MixinModelBakery {

	@Shadow
	@Final
	protected static Set<RenderMaterial> LOCATIONS_BUILTIN_TEXTURES;

	static {
		LOCATIONS_BUILTIN_TEXTURES.add(SoulFireAccess.LOCATION_SOUL_FIRE_0);
		LOCATIONS_BUILTIN_TEXTURES.add(SoulFireAccess.LOCATION_SOUL_FIRE_1);
		LOCATIONS_BUILTIN_TEXTURES.add(SoulFireAccess.LOCATION_FIRE_0_GLOW);
		LOCATIONS_BUILTIN_TEXTURES.add(SoulFireAccess.LOCATION_FIRE_1_GLOW);

		if (ModList.get().isLoaded("endergetic")) {
			LOCATIONS_BUILTIN_TEXTURES.add(SoulFireAccess.LOCATION_ENDER_FIRE_0);
			LOCATIONS_BUILTIN_TEXTURES.add(SoulFireAccess.LOCATION_ENDER_FIRE_1);
		}
	}

}
