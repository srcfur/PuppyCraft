package com.srcfur.puppycraft.datagen;

import com.srcfur.puppycraft.block.PuppyCraftBlocks;
import com.srcfur.puppycraft.item.PuppyCraftItems;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootSubProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.concurrent.CompletableFuture;

public class PuppyCraftBlockLootTableProvider extends FabricBlockLootSubProvider {
    protected PuppyCraftBlockLootTableProvider(FabricPackOutput packOutput, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(packOutput, registriesFuture);
    }

    @Override
    public void generate() {
        add(PuppyCraftBlocks.RawSalt.get(),
                LootTable.lootTable().withPool(applyExplosionCondition(PuppyCraftItems.RawSalt.get(), LootPool.lootPool()
                        .setRolls(new UniformGenerator(new ConstantValue(2), new ConstantValue(5)))
                        .add(LootItem.lootTableItem(PuppyCraftItems.RawSalt.get())))));
    }
    @Override
    public String getName() {
        return "blockloot";
    }
}
