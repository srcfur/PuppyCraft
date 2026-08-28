package com.srcfur.puppycraft;


import com.srcfur.badhygiene.event.PlayerPeeSelfEvent;
import com.srcfur.badhygiene.event.PlayerSoilSelfEvent;
import com.srcfur.puppycraft.api.PuppyPlayer;
import com.srcfur.puppycraft.block.PuppyCraftBlocks;
import com.srcfur.puppycraft.block.entity.PuppyCraftBlockEntities;
import com.srcfur.puppycraft.datacomponent.PuppyCraftDataComponents;
import com.srcfur.puppycraft.fluid.PuppyCraftFluids;
import com.srcfur.puppycraft.item.PuppyCraftItems;
import com.srcfur.puppycraft.utility.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
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
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
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
        eventBus.addListener(PuppyCraftNeoForge::buildContentsCreative);
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
    public static void buildContentsCreative(BuildCreativeModeTabContentsEvent event) {
        // Is this the tab we want to add to?
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(PuppyCraftItems.RawSalt.get());
            event.accept(PuppyCraftItems.Salt.get());
            event.accept(PuppyCraftItems.CheapAbsorbentPolymer.get());
            event.accept(PuppyCraftItems.SuperAbsorbentPolymer.get());
            event.accept(PuppyCraftItems.DiaperBackSheet.get());
        }
        if(event.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS){
            event.accept(PuppyCraftItems.BabyBottle.get());
            event.accept(PuppyCraftItems.BabyBottleOfMilk.get());
            event.accept(PuppyCraftItems.BabyBottleOfYouth.get());
        }
        if(event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS){
            event.accept(PuppyCraftItems.DiaperBag.get());
            event.accept(PuppyCraftItems.PuppyPad.get());
        }
        if(event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS){
            event.accept(PuppyCraftItems.SeaSalt.get());
        }
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
                            new FluidType(FluidType.Properties.create().canSwim(true).canHydrate(false).canPushEntity(true).density(0)
                                    .isWaterLike(true)));
                }
        );
        event.register(
                BuiltInRegistries.DATA_COMPONENT_TYPE.key(),
                _->{
                    GenericHelper.registerClass(PuppyCraftDataComponents.class, DataComponentHelper.class, GenericHelper.simpleRegisterHandler(BuiltInRegistries.DATA_COMPONENT_TYPE));
                }
        );
        event.register(
                BuiltInRegistries.CREATIVE_MODE_TAB.key(),
                registry ->{
                    registry.register(Identifier.fromNamespaceAndPath(Constants.MOD_ID, "diapers"), CreativeModeTab.builder()
                                    .title(Component.literal("Diapers"))
                                    .icon(()->new ItemStack(PuppyCraftItems.MedicalDiaper.get()))
                                    .displayItems((params, out) -> {
                                        out.accept(PuppyCraftItems.CheapDiaper.get());
                                        out.accept(PuppyCraftItems.MedicalDiaper.get());
                                        out.accept(PuppyCraftItems.PullUpDiaper.get());
                                        out.accept(PuppyCraftItems.MegaMaxDiaper.get());
                                        out.accept(PuppyCraftItems.SubspaceDiaper.get());
                                        out.accept(PuppyCraftItems.BunnyHoppsDiaper.get());
                                    })
                            .build());
                }
        );
    }
}