package com.nekomaster1000.infernalexp.access;

import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

import com.nekomaster1000.infernalexp.InfernalExpansion;

import net.minecraft.client.renderer.model.ModelBakery;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.util.ResourceLocation;

public interface SoulFireAccess {
	public KnownFireTypes getFireType();

	public void setFireType(KnownFireTypes type);

	public static final RenderMaterial LOCATION_SOUL_FIRE_0 = new RenderMaterial(AtlasTexture.LOCATION_BLOCKS_TEXTURE, new ResourceLocation("block/soul_fire_0"));
	public static final RenderMaterial LOCATION_SOUL_FIRE_1 = new RenderMaterial(AtlasTexture.LOCATION_BLOCKS_TEXTURE, new ResourceLocation("block/soul_fire_1"));

	public static final RenderMaterial LOCATION_GLOW_FIRE_0 = new RenderMaterial(AtlasTexture.LOCATION_BLOCKS_TEXTURE, new ResourceLocation(InfernalExpansion.MOD_ID, "block/fire_0_glow"));
	public static final RenderMaterial LOCATION_GLOW_FIRE_1 = new RenderMaterial(AtlasTexture.LOCATION_BLOCKS_TEXTURE, new ResourceLocation(InfernalExpansion.MOD_ID, "block/fire_1_glow"));

	public static final RenderMaterial LOCATION_ENDER_FIRE_0 = new RenderMaterial(AtlasTexture.LOCATION_BLOCKS_TEXTURE, new ResourceLocation("endergetic", "block/ender_fire_0"));
	public static final RenderMaterial LOCATION_ENDER_FIRE_1 = new RenderMaterial(AtlasTexture.LOCATION_BLOCKS_TEXTURE, new ResourceLocation("endergetic", "block/ender_fire_1"));

	public static enum KnownFireTypes {
		FIRE("fire", ModelBakery.LOCATION_FIRE_0, ModelBakery.LOCATION_FIRE_1),
		SOUL_FIRE("soul_fire", LOCATION_SOUL_FIRE_0, LOCATION_SOUL_FIRE_1),
		GLOW_FIRE("glow_fire", LOCATION_GLOW_FIRE_0, LOCATION_GLOW_FIRE_1),
		ENDER_FIRE("ender_fire", LOCATION_ENDER_FIRE_0, LOCATION_ENDER_FIRE_1);

		public static final KnownFireTypes[] VALUES = values();
		public static final Map<String, KnownFireTypes> NAME_LOOKUP = Arrays.stream(VALUES).collect(Collectors.toMap(KnownFireTypes::getName, (fireType) -> {
			return fireType;
		}));
		private final String name;
		private final RenderMaterial associatedSprite0;
		private final RenderMaterial associatedSprite1;

		KnownFireTypes(String name, RenderMaterial associatedSprite0, RenderMaterial associatedSprite1) {
			this.name = name;
			this.associatedSprite0 = associatedSprite0;
			this.associatedSprite1 = associatedSprite1;
		}

		@Override
		public String toString() {
			return getName();
		}

		public String getName() {
			return name;
		}

		public RenderMaterial getAssociatedSprite0() {
			return associatedSprite0;
		}

		public RenderMaterial getAssociatedSprite1() {
			return associatedSprite1;
		}

		@Nullable
		public static KnownFireTypes byName(@Nullable String name) {
			return name == null ? null : NAME_LOOKUP.get(name.toLowerCase(Locale.ROOT));
		}
	}

}
