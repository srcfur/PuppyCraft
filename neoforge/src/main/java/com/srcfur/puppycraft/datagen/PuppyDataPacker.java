package com.srcfur.puppycraft.datagen;

import com.srcfur.puppycraft.Constants;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = Constants.MOD_ID)
public class PuppyDataPacker {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent.Client event){

        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        event.createProvider(PuppyCraftRecipeProvider.Runner::new);
        generator.addProvider(true, new ModDatapackProvider(output, lookupProvider));
        generator.addProvider(true, new LootTableProvider(output, Collections.emptySet(),
                List.of(new LootTableProvider.SubProviderEntry(PuppyCraftBlockLootTableProvider::new, LootContextParamSets.BLOCK),
                        new LootTableProvider.SubProviderEntry(PuppyCraftLootTableProvider::new, LootContextParamSets.ALL_PARAMS)), lookupProvider));
        generator.addProvider(true, new PuppyCraftTagProvider(output, lookupProvider));
    }
}
