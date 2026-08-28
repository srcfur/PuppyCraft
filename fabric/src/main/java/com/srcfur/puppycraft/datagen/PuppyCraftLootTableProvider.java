package com.srcfur.puppycraft.datagen;

import com.srcfur.puppycraft.Constants;
import com.srcfur.puppycraft.item.PuppyCraftItems;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableSubProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.context.ContextKeySet;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class PuppyCraftLootTableProvider extends SimpleFabricLootTableSubProvider {
    private static ResourceKey<LootTable> loottable = ResourceKey.create(
            Registries.LOOT_TABLE,
            Identifier.fromNamespaceAndPath(Constants.MOD_ID, "barrels/nursery")
    );
    public PuppyCraftLootTableProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookupFuture) {
        super(output, registryLookupFuture, LootContextParamSets.CHEST);
    }

    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> biConsumer) {
        biConsumer.accept(
                loottable,
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

    @Override
    public String getName() {
        return "loottable";
    }
}