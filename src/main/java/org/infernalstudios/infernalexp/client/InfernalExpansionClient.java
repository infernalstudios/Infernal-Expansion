/*
 * Copyright 2022 Infernal Studios
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

package org.infernalstudios.infernalexp.client;

import net.minecraft.item.ItemModelsProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import org.infernalstudios.infernalexp.InfernalExpansion;
import org.infernalstudios.infernalexp.client.gui.InfectionHeartOverlay;
import org.infernalstudios.infernalexp.config.gui.screens.ConfigScreen;
import org.infernalstudios.infernalexp.init.IEItems;
import org.infernalstudios.infernalexp.items.WhipItem;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.function.Consumer;

@OnlyIn(Dist.CLIENT)
public class InfernalExpansionClient {

    public static void init(Consumer<Runnable> enqueueWorkConsumer) {
        // Register GUI Factories
        ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.CONFIGGUIFACTORY, () -> (mc, screen) -> new ConfigScreen());

        MinecraftForge.EVENT_BUS.register(new InfectionHeartOverlay());
        MinecraftForge.EVENT_BUS.addListener((LivingUpdateEvent event) -> DynamicLightingHandler.tick(event.getEntityLiving()));

        enqueueWorkConsumer.accept(InfernalExpansionClient::threadSafeInit);
    }

    private static void threadSafeInit() {
        ItemModelsProperties.registerProperty(IEItems.GLOWSILK_BOW.get(), new ResourceLocation("pull"), (itemStack, clientWorld, livingEntity) -> {
            if (livingEntity == null) {
                return 0.0F;
            } else {
                return livingEntity.getActiveItemStack() != itemStack ? 0.0F : (float) (itemStack.getUseDuration() - livingEntity.getItemInUseCount()) / 20.0F;
            }
        });

        ItemModelsProperties.registerProperty(IEItems.GLOWSILK_BOW.get(), new ResourceLocation("pulling"), (itemStack, clientWorld, livingEntity) -> livingEntity != null && livingEntity.isHandActive() && livingEntity.getActiveItemStack() == itemStack ? 1.0F : 0.0F);

        ItemModelsProperties.registerProperty(IEItems.BLINDSIGHT_TONGUE_WHIP.get(), new ResourceLocation("attack_frame"), (itemStack, clientWorld, livingEntity) ->
            livingEntity == null || (livingEntity.getHeldItemMainhand() != itemStack && livingEntity.getHeldItemOffhand() != itemStack) ?
                0 : (int) (((WhipItem) itemStack.getItem()).getTicksSinceAttack(itemStack) / 3.0F)
        );
        ItemModelsProperties.registerProperty(IEItems.BLINDSIGHT_TONGUE_WHIP.get(), new ResourceLocation("attacking"), (itemStack, clientWorld, livingEntity) -> livingEntity != null && (((WhipItem) itemStack.getItem()).getAttacking(itemStack) || ((WhipItem) itemStack.getItem()).getCharging(itemStack)) && (livingEntity.getHeldItemMainhand() == itemStack || livingEntity.getHeldItemOffhand() == itemStack) ? 1.0F : 0.0F);

        ItemModelsProperties.registerProperty(IEItems.KINETIC_TONGUE_WHIP.get(), new ResourceLocation("attack_frame"), (itemStack, clientWorld, livingEntity) ->
            livingEntity == null || (livingEntity.getHeldItemMainhand() != itemStack && livingEntity.getHeldItemOffhand() != itemStack) ?
                0 : (int) (((WhipItem) itemStack.getItem()).getTicksSinceAttack(itemStack) / 3.0F)
        );
        ItemModelsProperties.registerProperty(IEItems.KINETIC_TONGUE_WHIP.get(), new ResourceLocation("attacking"), (itemStack, clientWorld, livingEntity) -> livingEntity != null && (((WhipItem) itemStack.getItem()).getAttacking(itemStack) || ((WhipItem) itemStack.getItem()).getCharging(itemStack)) && (livingEntity.getHeldItemMainhand() == itemStack || livingEntity.getHeldItemOffhand() == itemStack) ? 1.0F : 0.0F);

        InfernalExpansionClient.loadInfernalResources();
    }

    public static void loadInfernalResources() {
        // Creates file location for resource pack
        File dir = new File(".", "resourcepacks");
        File target = new File(dir, "Infernal Resources.zip");

        // If the pack isn't already in the folder, copies the file over from the mod files
        if (!target.exists()) {
            try {
                dir.mkdirs();
                InputStream in = InfernalExpansion.class.getResourceAsStream("/assets/infernalexp/infernal_resources.zip");
                FileOutputStream out = new FileOutputStream(target);

                // The number of bytes here is how many can be read from the resource pack at one time
                // 16kB is the most common disk chunk size, and using this array size
                // reduces latency between reading and actually processing the data.
                // The performance difference is not significant, but it's improved by using a 16kB array.
                byte[] buf = new byte[16384];
                int len = 0;
                while ((len = in.read(buf)) > 0)
                    out.write(buf, 0, len);

                in.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
