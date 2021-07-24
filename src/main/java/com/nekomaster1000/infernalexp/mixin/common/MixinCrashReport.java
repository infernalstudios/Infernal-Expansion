package com.nekomaster1000.infernalexp.mixin.common;

import com.nekomaster1000.infernalexp.world.dimension.ModNetherBiomeCollector;
import net.minecraft.crash.CrashReport;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;

@Mixin(CrashReport.class)
public class MixinCrashReport {

    @Shadow
    private StackTraceElement[] stacktrace;

    @Shadow
    @Final
    private Throwable cause;

    @Inject(method = "getSectionsInStringBuilder", at = @At(value = "INVOKE", target = "Ljava/lang/StringBuilder;append(Ljava/lang/String;)Ljava/lang/StringBuilder;", ordinal = 0))
    private void IE_checkForBiomeConfigCrash(StringBuilder builder, CallbackInfo ci) {
       if (
            this.cause.getMessage().equals("/ by zero") &&
            Arrays.stream(this.stacktrace).anyMatch(element -> element.getClassName().equals(ModNetherBiomeCollector.class.getName()) &&
            element.getMethodName().equals("getRandomNetherBiomes"))
        ) {
            builder.append("Comment added by Infernal Expansion:\n");
            builder.append("This issue is usually caused by blacklisting all biomes in 'config/infernalexp-common.toml',\nor using a whitelist that only has invalid biome names.\n\n");
        }
    }

}
