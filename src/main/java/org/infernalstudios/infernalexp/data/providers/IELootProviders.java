/*
 * Copyright 2023 Infernal Studios
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

package org.infernalstudios.infernalexp.data.providers;

import java.util.function.Supplier;

import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.data.loot.EntityLoot;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.AlternativesEntry;
import net.minecraft.world.level.storage.loot.entries.EntryGroup;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.ApplyExplosionDecay;
import net.minecraft.world.level.storage.loot.functions.LootingEnchantFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.BonusLevelTableCondition;
import net.minecraft.world.level.storage.loot.predicates.ExplosionCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceWithLootingCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.BinomialDistributionGenerator;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import org.infernalstudios.infernalexp.blocks.GlowdustBlock;
import org.infernalstudios.infernalexp.data.DataGenDeferredRegister;
import org.infernalstudios.infernalexp.init.IEBlocks;
import org.infernalstudios.infernalexp.init.IEEntityTypes;
import org.infernalstudios.infernalexp.init.IEItems;

public class IELootProviders {

    public static BlockLootProviderConsumer self() {
        return (provider, block) -> {
            provider.dropSelf(block.get());
        };
    }

    public static BlockLootProviderConsumer other(Supplier<? extends ItemLike> itemLike) {
        return (provider, block) -> {
            provider.dropOther(block.get(), itemLike.get());
        };
    }

    public static BlockLootProviderConsumer silkTouch() {
        return (provider, block) -> {
            provider.dropWhenSilkTouch(block.get());
        };
    }

    public static BlockLootProviderConsumer potted() {
        return (provider, block) -> {
            provider.dropPottedContents(block.get());
        };
    }

    public static BlockLootProviderConsumer gravelLike() {
        return (provider, block) -> {
            provider.add(block.get(), gravelLikeInternal(block.get(), block.get()));
        };
    }

    public static BlockLootProviderConsumer gravelLike(Supplier<? extends ItemLike> fortune) {
        return (provider, block) -> {
            provider.add(block.get(), gravelLikeInternal(block.get(), fortune.get()));
        };
    }

    private static LootTable.Builder gravelLikeInternal(ItemLike itemLike, ItemLike fortune) {
        return LootTable.lootTable().withPool(
            LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(
                LootItem.lootTableItem(itemLike)
                    .when(hasSilkTouch())
                    .otherwise(
                        LootItem.lootTableItem(fortune)
                            .when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.05F, 0.10F, 0.20F, 0.80F))
                            .otherwise(LootItem.lootTableItem(itemLike))
                            .when(ExplosionCondition.survivesExplosion())
                    )
            )
        );
    }

    public static BlockLootProviderConsumer glowdust() {
        return (provider, block) -> {
            provider.add(block.get(), LootTable.lootTable().withPool(
                LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(
                    AlternativesEntry.alternatives(
                        AlternativesEntry.alternatives(
                            LootItem.lootTableItem(block.get()).when(isGlowdustLayer(1)).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))),
                            LootItem.lootTableItem(block.get()).when(isGlowdustLayer(2)).apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F))),
                            LootItem.lootTableItem(block.get()).when(isGlowdustLayer(3)).apply(SetItemCountFunction.setCount(ConstantValue.exactly(3.0F))),
                            LootItem.lootTableItem(block.get()).when(isGlowdustLayer(4)).apply(SetItemCountFunction.setCount(ConstantValue.exactly(4.0F))),
                            LootItem.lootTableItem(block.get()).when(isGlowdustLayer(5)).apply(SetItemCountFunction.setCount(ConstantValue.exactly(5.0F))),
                            LootItem.lootTableItem(block.get()).when(isGlowdustLayer(6)).apply(SetItemCountFunction.setCount(ConstantValue.exactly(6.0F))),
                            LootItem.lootTableItem(block.get()).when(isGlowdustLayer(7)).apply(SetItemCountFunction.setCount(ConstantValue.exactly(7.0F))),
                            LootItem.lootTableItem(IEBlocks.GLOWDUST_SAND.get())
                        ).when(hasSilkTouch()),
                        AlternativesEntry.alternatives(
                            LootItem.lootTableItem(Items.GLOWSTONE_DUST).when(isGlowdustLayer(1)).apply(SetItemCountFunction.setCount(BinomialDistributionGenerator.binomial(1, 0.3F))),
                            LootItem.lootTableItem(Items.GLOWSTONE_DUST).when(isGlowdustLayer(2)).apply(SetItemCountFunction.setCount(BinomialDistributionGenerator.binomial(2, 0.3F))),
                            LootItem.lootTableItem(Items.GLOWSTONE_DUST).when(isGlowdustLayer(3)).apply(SetItemCountFunction.setCount(BinomialDistributionGenerator.binomial(3, 0.3F))),
                            LootItem.lootTableItem(Items.GLOWSTONE_DUST).when(isGlowdustLayer(4)).apply(SetItemCountFunction.setCount(BinomialDistributionGenerator.binomial(4, 0.3F))),
                            LootItem.lootTableItem(Items.GLOWSTONE_DUST).when(isGlowdustLayer(5)).apply(SetItemCountFunction.setCount(BinomialDistributionGenerator.binomial(5, 0.3F))),
                            LootItem.lootTableItem(Items.GLOWSTONE_DUST).when(isGlowdustLayer(6)).apply(SetItemCountFunction.setCount(BinomialDistributionGenerator.binomial(6, 0.3F))),
                            LootItem.lootTableItem(Items.GLOWSTONE_DUST).when(isGlowdustLayer(7)).apply(SetItemCountFunction.setCount(BinomialDistributionGenerator.binomial(7, 0.3F))),
                            LootItem.lootTableItem(Items.GLOWSTONE_DUST).apply(SetItemCountFunction.setCount(BinomialDistributionGenerator.binomial(8, 0.3F)))
                        )
                    )
                ).when(LootItemEntityPropertyCondition.entityPresent(LootContext.EntityTarget.THIS))
            ));
        };
    }

    private static LootItemBlockStatePropertyCondition.Builder isGlowdustLayer(int layer) {
        return LootItemBlockStatePropertyCondition.hasBlockStateProperties(IEBlocks.GLOWDUST.get())
            .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(GlowdustBlock.LAYERS, layer));
    }

    public static BlockLootProviderConsumer glowdustSand() {
        return (provider, block) -> {
            provider.add(block.get(), LootTable.lootTable().withPool(
                LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(
                    LootItem.lootTableItem(block.get())
                        .when(hasSilkTouch())
                        .otherwise(
                            LootItem.lootTableItem(Items.GLOWSTONE_DUST)
                                .when(ExplosionCondition.survivesExplosion())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 5.0F)))
                        )
                )
            ));
        };
    }

    public static BlockLootProviderConsumer dimstone() {
        return (provider, block) -> {
            provider.add(block.get(), LootTable.lootTable().withPool(
                LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(
                    LootItem.lootTableItem(block.get())
                        .when(hasSilkTouch())
                        .otherwise(EntryGroup.list(
                            LootItem.lootTableItem(Items.GLOWSTONE_DUST)
                                .when(ExplosionCondition.survivesExplosion())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                                .apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE, 1)),
                            LootItem.lootTableItem(IEItems.DULLROCKS.get())
                                .when(ExplosionCondition.survivesExplosion())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                                .apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE, 1))
                        ))
                )
            ));
        };
    }

    public static BlockLootProviderConsumer dullstone() {
        return (provider, block) -> {
            provider.add(block.get(), LootTable.lootTable().withPool(
                LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(
                    LootItem.lootTableItem(block.get())
                        .when(hasSilkTouch())
                        .otherwise(
                            LootItem.lootTableItem(IEItems.DULLROCKS.get())
                                .when(ExplosionCondition.survivesExplosion())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                                .apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE, 1))
                        )
                )
            ));
        };
    }

    public static BlockLootProviderConsumer basaltIronOre() {
        return (provider, block) -> {
            provider.add(block.get(), LootTable.lootTable().withPool(
                LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(
                    LootItem.lootTableItem(block.get())
                        .when(hasSilkTouch())
                        .otherwise(
                            LootItem.lootTableItem(Items.IRON_NUGGET)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 6.0F)))
                                .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))
                                .apply(ApplyExplosionDecay.explosionDecay())
                        )
                )
            ));
        };
    }

    public static BlockLootProviderConsumer campfire(Supplier<? extends ItemLike> fuel) {
        return (provider, block) -> {
            provider.add(block.get(), LootTable.lootTable().withPool(
                LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(
                    LootItem.lootTableItem(block.get())
                        .when(hasSilkTouch())
                        .otherwise(
                            LootItem.lootTableItem(fuel.get())
                                .when(ExplosionCondition.survivesExplosion())
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))
                        )
                )
            ));
        };
    }

    public static BlockLootProviderConsumer glowsilkCocoon() {
        return (provider, block) -> {
            provider.add(block.get(), LootTable.lootTable().withPool(
                LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(
                    LootItem.lootTableItem(block.get())
                        .when(hasSilkTouch())
                        .otherwise(
                            LootItem.lootTableItem(IEItems.GLOWSILK.get())
                        )
                        .when(ExplosionCondition.survivesExplosion())
                )
            ));
        };
    }

    public static BlockLootProviderConsumer dullthorns() {
        return (provider, block) -> {
            provider.add(block.get(), LootTable.lootTable().withPool(
                LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(
                    LootItem.lootTableItem(block.get())
                        .when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(Items.SHEARS)).or(hasSilkTouch()))
                        .otherwise(
                            LootItem.lootTableItem(block.get())
                                .when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.15F, 0.33F, 0.55F, 0.77F))
                        )
                )
            ));
        };
    }

    private static LootItemCondition.Builder hasSilkTouch() {
        return MatchTool.toolMatches(ItemPredicate.Builder.item().hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.Ints.atLeast(1))));
    }

    public static BlockLootProviderConsumer noneBlock() {
        return (provider, block) -> {
            provider.add(block.get(), LootTable.lootTable());
        };
    }

    public static EntityLootProviderConsumer basaltGiant() {
        return (provider, entityType) -> {
            provider.add(entityType.get(), LootTable.lootTable().withPool(
                LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(
                    LootItem.lootTableItem(Blocks.MAGMA_BLOCK)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 1.0F)))
                        .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)))
                )
            ));
        };
    }

    public static EntityLootProviderConsumer blackstoneDwarf() {
        return (provider, entityType) -> {
            provider.add(entityType.get(), LootTable.lootTable().withPool(
                LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(
                    LootItem.lootTableItem(Items.GOLDEN_APPLE)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 1.0F)))
                        .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)))
                ).when(LootItemKilledByPlayerCondition.killedByPlayer())
            ));
        };
    }

    public static EntityLootProviderConsumer blindsight() {
        return (provider, entityType) -> {
            provider.add(entityType.get(), LootTable.lootTable().withPool(
                LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(
                    LootItem.lootTableItem(IEItems.BLINDSIGHT_TONGUE.get())
                        .when(LootItemRandomChanceCondition.randomChance(0.3F))
                )
            ));
        };
    }

    public static EntityLootProviderConsumer embody() {
        return (provider, entityType) -> {
            provider.add(entityType.get(), LootTable.lootTable().withPool(
                LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(
                    LootItem.lootTableItem(Blocks.SOUL_SOIL)
                        .setWeight(100)
                        .apply(SetItemCountFunction.setCount(BinomialDistributionGenerator.binomial(1, 0.3F)))
                        .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)))
                ).add(
                    LootItem.lootTableItem(IEItems.MUSIC_DISC_SOUL_SPUNK.get())
                )
            ).withPool(
                LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(
                    LootItem.lootTableItem(IEItems.SOUL_SALT_CLUMP.get())
                        .apply(SetItemCountFunction.setCount(BinomialDistributionGenerator.binomial(1, 0.7F)))
                        .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 3.0F)).setLimit(3))
                )
            ));
        };
    }

    public static EntityLootProviderConsumer glowsilkMoth() {
        return (provider, entityType) -> {
            provider.add(entityType.get(), LootTable.lootTable().withPool(
                LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(
                    LootItem.lootTableItem(IEItems.MOTH_DUST.get())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 1.0F)))
                        .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)))
                )
            ));
        };
    }

    public static EntityLootProviderConsumer glowsquito() {
        return (provider, entityType) -> {
            provider.add(entityType.get(), LootTable.lootTable().withPool(
                LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(
                    LootItem.lootTableItem(IEItems.GLOWCOAL.get())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F)))
                        .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)))
                ).add(
                    LootItem.lootTableItem(IEItems.DULLROCKS.get())
                        .apply(SetItemCountFunction.setCount(BinomialDistributionGenerator.binomial(1, 0.1F)))
                        .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)))
                )
            ).withPool(
                LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(
                    LootItem.lootTableItem(IEItems.MUSIC_DISC_FLUSH.get())
                        .apply(SetItemCountFunction.setCount(BinomialDistributionGenerator.binomial(1, 0.25F)))
                ).when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.KILLER, EntityPredicate.Builder.entity().of(IEEntityTypes.BLACKSTONE_DWARF.get())))
            ));
        };
    }

    public static EntityLootProviderConsumer shroomloin() {
        return (provider, entityType) -> {
            provider.add(entityType.get(), LootTable.lootTable().withPool(
                LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(
                    LootItem.lootTableItem(IEItems.ASCUS_BOMB.get())
                        .when(LootItemRandomChanceCondition.randomChance(0.2F))
                )
            ).withPool(
                LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(
                    LootItem.lootTableItem(IEBlocks.CRIMSON_FUNGUS_CAP.get())
                        .when(LootItemKilledByPlayerCondition.killedByPlayer())
                        .when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.025F, 0.01F))
                )
            ));
        };
    }

    public static EntityLootProviderConsumer voline() {
        return (provider, entityType) -> {
            provider.add(entityType.get(), LootTable.lootTable().withPool(
                LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(
                    LootItem.lootTableItem(Items.GOLD_NUGGET)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))
                        .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)))
                )
            ));
        };
    }

    public static EntityLootProviderConsumer warpbeetle() {
        return (provider, entityType) -> {
            provider.add(entityType.get(), LootTable.lootTable().withPool(
                LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(
                    LootItem.lootTableItem(Items.WARPED_FUNGUS)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))
                        .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)))
                )
            ).withPool(
                LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(
                    LootItem.lootTableItem(IEBlocks.WARPED_FUNGUS_CAP.get())
                        .when(LootItemKilledByPlayerCondition.killedByPlayer())
                        .when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.025F, 0.01F))
                )
            ));
        };
    }

    public static EntityLootProviderConsumer noneEntity() {
        return (provider, entityType) -> {
            provider.add(entityType.get(), LootTable.lootTable());
        };
    }

    public static LootTable.Builder basaltDeltaRuin() {
        return LootTable.lootTable().withPool(
            LootPool.lootPool().setRolls(UniformGenerator.between(4.0F, 8.0F))
                .add(addItemWithWeightAndCount(Items.IRON_INGOT, 7, 4.0F, 22.0F))
                .add(addItemWithWeightAndCount(Items.GOLD_INGOT, 4, 3.0F, 10.0F))
                .add(addItemWithWeightAndCount(IEItems.MOLTEN_GOLD_CLUSTER.get(), 3, 1.0F, 5.0F))
                .add(addItemWithWeightAndCount(Items.CHAIN, 7, 2.0F, 5.0F))
                .add(addItemWithWeight(IEItems.STRIDER_BUCKET.get(), 7))
                .add(addItemWithWeightAndCount(Items.BONE, 4, 1.0F, 6.0F))
                .add(addItemWithWeightAndCount(IEBlocks.SILT.get(), 10, 4.0F, 16.0F))
        );
    }

    public static LootTable.Builder desolateBastionOutpost() {
        return LootTable.lootTable().withPool(
            LootPool.lootPool().setRolls(UniformGenerator.between(4.0F, 8.0F))
                .add(addItemWithWeightAndCount(Items.IRON_NUGGET, 7, 16.0F, 22.0F))
                .add(addItemWithWeightAndCount(IEBlocks.BASALT_IRON_ORE.get(), 5, 4.0F, 13.0F))
                .add(addItemWithWeightAndCount(Items.GOLD_INGOT, 7, 4.0F, 22.0F))
                .add(addItemWithWeightAndCount(IEItems.DULLROCKS.get(), 6, 4.0F, 8.0F))
                .add(addItemWithWeightAndCount(Items.GLOWSTONE_DUST, 7, 4.0F, 22.0F))
                .add(addItemWithWeight(IEItems.BLINDSIGHT_TONGUE_STEW.get(), 3))
                .add(addItemWithWeightAndCount(Blocks.GOLD_BLOCK, 3, 1.0F, 2.0F))
                .add(addItemWithWeightAndCount(Items.LAPIS_LAZULI, 2, 1.0F, 3.0F))
        );
    }

    public static LootTable.Builder glowstoneCanyonRuin() {
        return LootTable.lootTable().withPool(
            LootPool.lootPool().setRolls(UniformGenerator.between(4.0F, 8.0F))
                .add(addItemWithWeightAndCount(IEItems.DULLROCKS.get(), 3, 1.0F, 5.0F))
                .add(addItemWithWeightAndCount(Items.GLOWSTONE_DUST, 4, 1.0F, 5.0F))
                .add(addItemWithWeightAndCount(Items.GOLD_INGOT, 5, 1.0F, 9.0F))
                .add(addItemWithWeight(IEItems.MUSIC_DISC_FLUSH.get(), 1))
        );
    }

    private static LootPoolSingletonContainer.Builder<?> addItemWithWeight(ItemLike item, int weight) {
        return LootItem.lootTableItem(item)
            .setWeight(weight);
    }

    private static LootPoolSingletonContainer.Builder<?> addItemWithWeightAndCount(ItemLike item, int weight, float minCount, float maxCount) {
        return LootItem.lootTableItem(item)
            .setWeight(weight)
            .apply(SetItemCountFunction.setCount(UniformGenerator.between(minCount, maxCount)));
    }

    @FunctionalInterface
    public interface BlockLootProviderConsumer extends DataGenDeferredRegister.LootProviderConsumer<Block, BlockLoot> {}

    @FunctionalInterface
    public interface EntityLootProviderConsumer extends DataGenDeferredRegister.LootProviderConsumer<EntityType<?>, EntityLoot> {}

}
