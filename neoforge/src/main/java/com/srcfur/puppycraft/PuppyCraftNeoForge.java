package com.srcfur.puppycraft;


import com.srcfur.badhygiene.event.PlayerPeeSelfEvent;
import com.srcfur.badhygiene.event.PlayerSoilSelfEvent;
import com.srcfur.puppycraft.api.PuppyPlayer;
import com.srcfur.puppycraft.block.PuppyCraftBlocks;
import com.srcfur.puppycraft.block.entity.PuppyCraftBlockEntities;
import com.srcfur.puppycraft.fluid.PuppyCraftFluids;
import com.srcfur.puppycraft.item.PuppyCraftItems;
import com.srcfur.puppycraft.utility.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.RegisterEvent;

import java.util.HashSet;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Mod(Constants.MOD_ID)
public class PuppyCraftNeoForge {
    public static FluidType Youth_FluidType;
    public PuppyCraftNeoForge(IEventBus eventBus) {

        // This method is invoked by the NeoForge mod loader when it is ready
        // to load your mod. You can access NeoForge and Common code in this
        // project.

        // Use NeoForge to bootstrap the Common mod.
        Constants.LOG.info("Hello NeoForge world!");
        PuppyCraftCommon.init();
        eventBus.addListener(PuppyCraftNeoForge::registerHelpers);
        NeoForge.EVENT_BUS.addListener(PlayerPeeSelfEvent.class, event -> event.setCanceled(PuppyCraftCommon.API.getPuppyPlayer(event.getEntity()).peeSelf()));
        NeoForge.EVENT_BUS.addListener(PlayerSoilSelfEvent.class, event -> event.setCanceled(PuppyCraftCommon.API.getPuppyPlayer(event.getEntity()).poopSelf()));
    }
    private static BlockEntityProperties<BlockEntity> registerBlockEntity(Identifier id, Supplier<BlockEntityProperties<BlockEntity>> type){
        BlockEntityProperties<BlockEntity> props = type.get();
        props.handle(()->Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE,
                id,
                new BlockEntityType<BlockEntity>(
                    props.supplier::apply,
                    new HashSet<>(props.blocks)
        )));
        return props;
    }
    @SuppressWarnings("unchecked")
    public static void registerHelpers(RegisterEvent event){
        event.register(
                BuiltInRegistries.ITEM.key(),
                _ ->{
                    GenericHelper.registerClass(PuppyCraftItems.class, ItemHelper.class, GenericHelper.simpleRegisterHandler(BuiltInRegistries.ITEM));
                    PuppyCraftCommon.registerTrinketRenderers();
                }
        );
        event.register(
                BuiltInRegistries.BLOCK.key(),
                _ -> {
                    GenericHelper.registerClass(PuppyCraftBlocks.class, BlockHelper.class, GenericHelper.simpleRegisterHandler(BuiltInRegistries.BLOCK));
                }
        );
        event.register(
                BuiltInRegistries.BLOCK_ENTITY_TYPE.key(),
                _ -> {
                    GenericHelper.registerClass(PuppyCraftBlockEntities.class, BlockEntityHelper.class, PuppyCraftNeoForge::registerBlockEntity);
                }
        );
        event.register(
                BuiltInRegistries.FLUID.key(),
                _ -> {
                    GenericHelper.registerClass(PuppyCraftFluids.class, FluidHelper.class, GenericHelper.simpleRegisterHandler(BuiltInRegistries.FLUID));
                }
        );
        event.register(
                NeoForgeRegistries.FLUID_TYPES.key(),
                _ -> {
                    Youth_FluidType = Registry.register(NeoForgeRegistries.FLUID_TYPES,
                            Identifier.fromNamespaceAndPath(Constants.MOD_ID, "youth"),
                            new FluidType(FluidType.Properties.create().canSwim(true).canHydrate(false).canPushEntity(true)));
                }
        );
    }
}