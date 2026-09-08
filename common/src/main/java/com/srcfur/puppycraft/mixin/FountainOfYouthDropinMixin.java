package com.srcfur.puppycraft.mixin;

import com.srcfur.puppycraft.block.PuppyCraftBlocks;
import com.srcfur.puppycraft.block.PuppyPadBlock;
import com.srcfur.puppycraft.datacomponent.PuppyCraftDataComponents;
import com.srcfur.puppycraft.item.PuppyCraftItems;
import com.srcfur.puppycraft.item.diaper.DiaperItem;
import com.srcfur.puppycraft.item.diaperbag.DiaperBagData;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.material.FlowingFluid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Mixin(ItemEntity.class)
public abstract class FountainOfYouthDropinMixin extends Entity {
    public FountainOfYouthDropinMixin(EntityType<?> type, Level level) {
        super(type, level);
    }

    @Shadow
    abstract ItemStack getItem();
    @Shadow
    abstract void setItem(ItemStack stack);
    @Inject(at = @At("HEAD"), method = "tick")
    void pc$tick(CallbackInfo ci) {
        ItemStack stack = getItem();
        if(level().getBlockState(blockPosition()).getBlock() != PuppyCraftBlocks.Youth.get() || level().isClientSide())
            return;
        if(level().getBlockState(blockPosition()).getValue(LiquidBlock.LEVEL) > 0)
            return;
        if(stack.getItem() == Items.EMERALD)
            setItem(new ItemStack(PuppyCraftItems.GEM_OF_MATURITY.get(), stack.getCount()));
        if(stack.getItem() == Items.DIAMOND) {
            //Our lil randomizer :3
            List<Item> validItems = List.of(PuppyCraftItems.DiaperBag.get());
            Item dropItem = validItems.get(level().getRandom().nextInt(validItems.size()));
            ItemStack dropStack = new ItemStack(dropItem);
            if(dropItem == PuppyCraftItems.DiaperBag.get()){
                List<DiaperItem> diaper = List.of(PuppyCraftItems.MedicalDiaper.get(), PuppyCraftItems.MegaMaxDiaper.get(), PuppyCraftItems.BunnyHoppsDiaper.get(), PuppyCraftItems.SubspaceDiaper.get());
                DiaperItem selectedDiaper = diaper.get(level().getRandom().nextInt(diaper.size()));

                ArrayList<ItemStack> inventory = new ArrayList<>();
                int diaperCount = Math.min(10, (level().getRandom().nextInt(4) + 2) * stack.getCount());
                for(int i = 0; i < 10; i++){ inventory.add(i < diaperCount ? new ItemStack(selectedDiaper) : ItemStack.EMPTY); }

                dropStack.set(DataComponents.CONTAINER, ItemContainerContents.fromItems(inventory));
                dropStack.set(PuppyCraftDataComponents.DiaperBagData.get(), new DiaperBagData(selectedDiaper.Family.ordinal(), diaperCount));
                level().setBlockAndUpdate(blockPosition(), Blocks.AIR.defaultBlockState());
            }
            setItem(dropStack);
        }
        if(stack.getItem() == PuppyCraftItems.Salt.get())
            setItem(new ItemStack(PuppyCraftItems.CheapAbsorbentPolymer.get(), stack.getCount()));
    }
}
