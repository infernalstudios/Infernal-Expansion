package com.nekomaster1000.infernalexp.mixin.client;

import com.nekomaster1000.infernalexp.InfernalExpansion;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Minecraft.class)
public class MixinMinecraft {

    @ModifyVariable(
            method = "loadWorld(Ljava/lang/String;Lnet/minecraft/util/registry/DynamicRegistries$Impl;Ljava/util/function/Function;Lcom/mojang/datafixers/util/Function4;ZLnet/minecraft/client/Minecraft$WorldSelectionType;)V",
            at = @At(value = "FIELD", target = "Lnet/minecraft/client/Minecraft$WorldSelectionType;NONE:Lnet/minecraft/client/Minecraft$WorldSelectionType;", ordinal = 0),
            ordinal = 2,
            index = 11,
            name = "flag2",
            require = 1
    )
    private boolean replaceFlag2(boolean flag2) {
        InfernalExpansion.LOGGER.info("Suppressed Experimental Settings Warning");
        return false;
    }
}
