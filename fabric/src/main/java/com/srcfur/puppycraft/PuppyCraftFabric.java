package com.srcfur.puppycraft;

import com.srcfur.badhygiene.event.PlayerPeeSelf;
import com.srcfur.badhygiene.event.PlayerPoopSelf;
import com.srcfur.puppycraft.attachment.PuppyCraftAttachments;
import com.srcfur.puppycraft.block.PuppyCraftBlocks;
import com.srcfur.puppycraft.block.entity.PuppyCraftBlockEntities;
import com.srcfur.puppycraft.datacomponent.PuppyCraftDataComponents;
import com.srcfur.puppycraft.datagen.worldgen.ModPlacedFeatures;
import com.srcfur.puppycraft.fluid.PuppyCraftFluids;
import com.srcfur.puppycraft.item.PuppyCraftItems;
import com.srcfur.puppycraft.utility.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModification;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.levelgen.GenerationStep;

import java.lang.reflect.Array;
import java.util.HashSet;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class PuppyCraftFabric implements ModInitializer {
    
    @Override
    public void onInitialize() {
        
        // This method is invoked by the Fabric mod loader when it is ready
        // to load your mod. You can access Fabric and Common code in this
        // project.

        // Use Fabric to bootstrap the Common mod.
        Constants.LOG.info("Hello Fabric world!");
        PuppyCraftCommon.init();

        PuppyCraftAttachments.initialize();
        GenericHelper.registerClass(PuppyCraftDataComponents.class, DataComponentHelper.class, GenericHelper.simpleRegisterHandler(BuiltInRegistries.DATA_COMPONENT_TYPE));
        GenericHelper.registerClass(PuppyCraftFluids.class, FluidHelper.class, GenericHelper.simpleRegisterHandler(BuiltInRegistries.FLUID));
        GenericHelper.registerClass(PuppyCraftBlocks.class, BlockHelper.class, GenericHelper.simpleRegisterHandler(BuiltInRegistries.BLOCK));
        GenericHelper.registerClass(PuppyCraftBlockEntities.class, BlockEntityHelper.class, PuppyCraftFabric::registerBlockEntity);
        GenericHelper.registerClass(PuppyCraftItems.class, ItemHelper.class, GenericHelper.simpleRegisterHandler(BuiltInRegistries.ITEM));

        BiomeModifications.addFeature(
                BiomeSelectors.tag(BiomeTags.IS_OCEAN),
                GenerationStep.Decoration.UNDERGROUND_ORES,
                ModPlacedFeatures.SEA_SALT_PLACED
        );

        PlayerPeeSelf.EVENT.register(event-> PuppyCraftCommon.API.getPuppyPlayer(event).peeSelf() ? InteractionResult.CONSUME : InteractionResult.PASS);
        PlayerPoopSelf.EVENT.register(event-> PuppyCraftCommon.API.getPuppyPlayer(event).poopSelf() ? InteractionResult.CONSUME : InteractionResult.PASS);
    }
    private static BlockEntityProperties<BlockEntity> registerBlockEntity(Identifier id, Supplier<BlockEntityProperties<BlockEntity>> type){
        BlockEntityProperties<BlockEntity> props = type.get();
        props.handle(()-> Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE,
                id,
                FabricBlockEntityTypeBuilder.create(props.supplier::apply, props.blocks.toArray(new Block[0])).build()
        ));
        return props;
    }
}
