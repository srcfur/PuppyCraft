package com.srcfur.puppycraft.datagen;

import com.srcfur.puppycraft.datagen.worldgen.ModConfiguredFeatures;
import com.srcfur.puppycraft.datagen.worldgen.ModPlacedFeatures;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;

public class PuppyCraftDatagen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(PuppyCraftWorldGenProvider::new);
        pack.addProvider(PuppyCraftRecipeProvider::new);
        pack.addProvider(PuppyCraftBlockTagProvider::new);
        pack.addProvider(PuppyCraftBlockLootTableProvider::new);
        pack.addProvider(PuppyCraftLootTableProvider::new);
    }

    @Override
    public void buildRegistry(RegistrySetBuilder registryBuilder) {
        registryBuilder.add(Registries.CONFIGURED_FEATURE, ModConfiguredFeatures::bootstrap);
        registryBuilder.add(Registries.PLACED_FEATURE, ModPlacedFeatures::bootstrap);
        DataGeneratorEntrypoint.super.buildRegistry(registryBuilder);
    }
}
