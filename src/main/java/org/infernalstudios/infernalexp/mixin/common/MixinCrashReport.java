/*
 * Copyright 2021 Infernal Studios
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.infernalstudios.infernalexp.mixin.common;

import org.infernalstudios.infernalexp.world.dimension.ModNetherBiomeCollector;
import net.minecraft.CrashReport;
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
    private StackTraceElement[] uncategorizedStackTrace;

    @Shadow
    @Final
    private Throwable exception;

    @Inject(method = "getDetails(Ljava/lang/StringBuilder;)V", at = @At(value = "INVOKE", target = "Ljava/lang/StringBuilder;append(Ljava/lang/String;)Ljava/lang/StringBuilder;", ordinal = 0), remap = false)
    private void IE_checkForBiomeConfigCrash(StringBuilder builder, CallbackInfo ci) {
        try {
            if (
                this.exception.getMessage().equals("/ by zero") &&
                    Arrays.stream(this.uncategorizedStackTrace).anyMatch(element -> element.getClassName().equals(ModNetherBiomeCollector.class.getName()) &&
                        element.getMethodName().equals("getRandomNetherBiomes"))
            ) {
                builder.append("Comment added by Infernal Expansion:\n");
                builder.append("This issue is usually caused by blacklisting all biomes in 'config/infernalexp-common.toml',\nor using a whitelist that only has invalid biome names.\n\n");
            }
        } catch (NullPointerException e) {}
    }

}
