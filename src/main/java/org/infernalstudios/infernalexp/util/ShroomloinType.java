package org.infernalstudios.infernalexp.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.CheckForNull;
import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * ShroomloinType is a utility class for registering new shroomloin types.
 * To use this in your own code, make sure to register it using ShroomloinType::registerShroomloinType
 * If you're a modder, feel free to ask for compat or PR it in {@link org.infernalstudios.infernalexp.init.IEShroomloinTypes}
 */
public class ShroomloinType {
    private static final Map<ResourceLocation, ShroomloinType> SHROOMLOIN_TYPES = new LinkedHashMap<>();

    private Supplier<Item> conversionItem;
    private ResourceLocation id;
    private Pair<ResourceLocation, ResourceLocation> textures;

    public ShroomloinType(@Nullable Item conversionItem, ResourceLocation id, ResourceLocation texture, @Nullable ResourceLocation glowTexture) {
        this(() -> conversionItem, id, texture, glowTexture);
    }

    public ShroomloinType(@Nullable Supplier<Item> conversionItem, ResourceLocation id, ResourceLocation texture, @Nullable ResourceLocation glowTexture) {
        this(conversionItem, id, Pair.of(texture, glowTexture));
    }

    public ShroomloinType(@Nullable Supplier<Item> conversionItem, ResourceLocation id, Pair<ResourceLocation, ResourceLocation> textures) {
        this.conversionItem = conversionItem;
        this.id = id;
        this.textures = textures;
    }

    @CheckForNull
    public Item getConversionItem() {
        final Item item = this.conversionItem.get();
        if (item == null || item.equals(Items.AIR)) {
            return null;
        } else {
            return item;
        }
    }

    public void setConversionItem(@Nullable Item conversionItem) {
        this.conversionItem = () -> conversionItem;
    }

    public ResourceLocation getId() {
        return id;
    }

    public void setId(ResourceLocation id) {
        this.id = id;
    }

    public ResourceLocation getTextureLocation() {
        return this.textures.getLeft();
    }

    public void setTextureLocation(ResourceLocation textureLocation) {
        this.textures = Pair.of(textureLocation, this.textures.getRight());
    }

    public ResourceLocation getGlowTextureLocation() {
        return this.textures.getRight();
    }

    public void setGlowTextureLocation(ResourceLocation textureLocation) {
        this.textures = Pair.of(this.textures.getLeft(), textureLocation);
    }

    public static ShroomloinType registerShroomloinType(ShroomloinType shroomloinType) {
        ResourceLocation id = shroomloinType.getId();
        if (SHROOMLOIN_TYPES.containsKey(id)) {
            throw new IllegalStateException(String.format("%s already exists in the ShroomloinType registry.", id.toString()));
        }
        SHROOMLOIN_TYPES.put(id, shroomloinType);
        return shroomloinType;
    }

    @Nullable
    public static ShroomloinType getById(@Nullable String id) {
        if (id == null) {
            return null;
        } else {
            return getById(ResourceLocation.tryParse(id));
        }
    }

    @Nullable
    public static ShroomloinType getById(@Nullable ResourceLocation id) {
        return SHROOMLOIN_TYPES.get(id);
    }

    @Nullable
    public static ShroomloinType getByItem(@Nullable Item item) {
        if (item == null) {
            return null;
        }
        for (Map.Entry<ResourceLocation, ShroomloinType> entry : SHROOMLOIN_TYPES.entrySet()) {
            final ShroomloinType shroomloinType = entry.getValue();
            final Item conversionItem = shroomloinType.getConversionItem();
            if (conversionItem == null) {
                continue;
            }
            if (conversionItem.equals(item)) {
                return shroomloinType;
            }
        }
        return null;
    }

    public boolean equals(Object obj) {
        if (obj instanceof ShroomloinType) {
            final ShroomloinType type = (ShroomloinType) obj;
            return type.getId().equals(this.getId()) &&
                type.getConversionItem().equals(this.getConversionItem()) &&
                type.getTextureLocation().equals(this.getTextureLocation()) &&
                type.getGlowTextureLocation().equals(this.getGlowTextureLocation());
        } else {
            return false;
        }
    }
}
