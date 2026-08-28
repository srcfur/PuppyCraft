package com.srcfur.puppycraft.datagen.worldgen;

import com.srcfur.puppycraft.Constants;
import com.srcfur.puppycraft.block.PuppyCraftBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.DiskConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class ModConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> PUPPYCRAFT_OVERWORLD_SALT_ORE_KEY = registerKey("seasalt");
    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context){
        register(context, PUPPYCRAFT_OVERWORLD_SALT_ORE_KEY, Feature.DISK,
                new DiskConfiguration(BlockStateProvider.simple(PuppyCraftBlocks.RawSalt.get()),
                        BlockPredicate.matchesBlocks(Blocks.DIRT, Blocks.SAND),
                        UniformInt.of(2,3),
                        1));
    }
    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name){
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, Identifier.fromNamespaceAndPath(Constants.MOD_ID, name));
    }
    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstrapContext<ConfiguredFeature<?, ?>> context,
                                                                                          ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration){
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
