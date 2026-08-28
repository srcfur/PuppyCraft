package com.srcfur.puppycraft.datagen;

import com.srcfur.puppycraft.Constants;
import com.srcfur.puppycraft.item.PuppyCraftItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.function.BiConsumer;

public class PuppyCraftLootTableProvider implements LootTableSubProvider {
    public PuppyCraftLootTableProvider(HolderLookup.Provider lookupProvider) {

    }
    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> biConsumer) {
        biConsumer.accept(
                ResourceKey.create(
                        Registries.LOOT_TABLE,
                        Identifier.fromNamespaceAndPath(Constants.MOD_ID, "barrels/nursery")
                ),
                LootTable.lootTable()
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(UniformGenerator.between(3,9))
                                        .add(LootItem.lootTableItem(PuppyCraftItems.BabyBottleOfMilk.get()).setWeight(3))
                                        .add(LootItem.lootTableItem(PuppyCraftItems.BabyBottleOfYouth.get()).setWeight(1))
                                        .add(LootItem.lootTableItem(Items.CARROT).setWeight(5))
                                        .add(LootItem.lootTableItem(Items.APPLE).setWeight(5))
                                        .add(LootItem.lootTableItem(Items.COOKIE).setWeight(5))
                        ));
    }
}
