package com.srcfur.puppycraft.block.entity;

import com.srcfur.puppycraft.block.DiaperBagBlock;
import com.srcfur.puppycraft.item.diaper.DiaperFamilies;
import com.srcfur.puppycraft.item.diaper.DiaperItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.ContainerEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class DiaperBagEntity extends BlockEntity implements Container {
    public DiaperBagEntity(BlockEntityType<?> type, BlockPos worldPosition, BlockState blockState) {
        super(type, worldPosition, blockState);
    }
    public DiaperBagEntity(BlockPos pos, BlockState state){
        this(PuppyCraftBlockEntities.DiaperBag.getType(), pos, state);
    }
    private NonNullList<ItemStack> inventory = NonNullList.withSize(10, ItemStack.EMPTY);

    @Override
    public int getContainerSize() {
        return 10;
    }

    @Override
    public boolean isEmpty() {
        for(ItemStack stack : inventory){
            if(!stack.isEmpty())
                return false;
        }
        return true;
    }

    public int getNextAvailableSlot(){
        for(int i = 0; i < getContainerSize(); i++){
            if(getItem(i).isEmpty()){
                return i;
            }
        }
        return -1;
    }
    public ItemStack pullFirst(){
        for(int i = 0; i < getContainerSize(); i++){
            if(getItem(i).isEmpty()) continue;
            return removeItemNoUpdate(i);
        }
        return ItemStack.EMPTY;
    }

    public DiaperFamilies getFamily(){
        for(ItemStack stack : inventory){
            if(stack.isEmpty())continue;
            if(!(stack.getItem() instanceof DiaperItem)) continue;
            return ((DiaperItem)stack.getItem()).Family;
        }
        return DiaperFamilies.Generic;
    }

    @Override
    public ItemStack getItem(int i) {
        return inventory.get(i);
    }

    @Override
    public ItemStack removeItem(int i, int i1) {
        ItemStack stack = getItem(i).copy();
        stack.setCount(i1);
        getItem(i).setCount(getItem(i).getCount() - i1);
        return stack;
    }

    @Override
    public ItemStack removeItemNoUpdate(int i) {
        ItemStack stack = getItem(i).copy();
        setItem(i, ItemStack.EMPTY);
        return stack;
    }

    @Override
    public void setItem(int i, ItemStack itemStack) {
        inventory.set(i, itemStack);
        updateBlockState();
    }

    @Override
    public boolean stillValid(Player player) {
        return false;
    }

    @Override
    public void clearContent() {
        for(int i = 0; i < getContainerSize(); i++){
            setItem(i, ItemStack.EMPTY);
        }
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        ContainerHelper.loadAllItems(input, inventory);
        updateBlockState();
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        ContainerHelper.saveAllItems(output, inventory);
    }

    private void updateBlockState(){
        if(getLevel() == null) return; //Weird shit, just wanting IntelliJ to shut up <3
        getLevel().setBlockAndUpdate(getBlockPos(), getBlockState().setValue(DiaperBagBlock.FAMILY, getFamily()));
    }
}
