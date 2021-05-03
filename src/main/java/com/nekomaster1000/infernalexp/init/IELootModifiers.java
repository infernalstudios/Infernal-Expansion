package com.nekomaster1000.infernalexp.init;

import com.google.gson.JsonObject;
import com.nekomaster1000.infernalexp.InfernalExpansion;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import java.util.List;

public class IELootModifiers {

    public static final DeferredRegister<GlobalLootModifierSerializer<?>> LOOT_MODIFIERS = DeferredRegister.create(ForgeRegistries.LOOT_MODIFIER_SERIALIZERS, InfernalExpansion.MOD_ID);

    public static final RegistryObject<GlobalLootModifierSerializer<HoglinLootModifier>> HOGLIN_LOOT_MODIFIER = LOOT_MODIFIERS.register("hoglin_loot_modifier", HoglinLootSerializer::new);

    private static class HoglinLootModifier extends LootModifier {

        /**
         * Constructs a LootModifier.
         *
         * @param conditionsIn the ILootConditions that need to be matched before the loot is modified.
         */
        protected HoglinLootModifier(ILootCondition[] conditionsIn) {
            super(conditionsIn);
        }

        @Nonnull
        @Override
        protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {

            int numChops = 0;
            int numCookedChops = 0;

            for (ItemStack item : generatedLoot) {
                if (item.isItemEqual(Items.PORKCHOP.getDefaultInstance())) {
                    numChops += item.getCount();
                } else if (item.isItemEqual(Items.COOKED_PORKCHOP.getDefaultInstance())) {
                    numCookedChops += item.getCount();
                }
            }

            generatedLoot.removeIf(x -> x.isItemEqual(Items.PORKCHOP.getDefaultInstance()));
            generatedLoot.removeIf(x -> x.isItemEqual(Items.COOKED_PORKCHOP.getDefaultInstance()));
            generatedLoot.add(new ItemStack(IEItems.COOKED_HOGCHOP.get(), numCookedChops));
            generatedLoot.add(new ItemStack(IEItems.RAW_HOGCHOP.get(), numChops));

            return generatedLoot;
        }
    }

    private static class HoglinLootSerializer extends GlobalLootModifierSerializer<HoglinLootModifier> {

        @Override
        public HoglinLootModifier read(ResourceLocation location, JsonObject object, ILootCondition[] conditionsIn) {
            return new HoglinLootModifier(conditionsIn);
        }

        @Override
        public JsonObject write(HoglinLootModifier instance) {
            return null;
        }
    }

    public static void register(IEventBus eventBus) {
        LOOT_MODIFIERS.register(eventBus);
        InfernalExpansion.LOGGER.info("Infernal Expansion: Loot Modifiers Registered!");
    }
}
