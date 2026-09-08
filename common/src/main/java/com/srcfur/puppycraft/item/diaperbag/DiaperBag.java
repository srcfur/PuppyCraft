package com.srcfur.puppycraft.item.diaperbag;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.srcfur.puppycraft.block.PuppyCraftBlocks;
import com.srcfur.puppycraft.block.entity.DiaperBagEntity;
import com.srcfur.puppycraft.datacomponent.PuppyCraftDataComponents;
import com.srcfur.puppycraft.item.diaper.DiaperFamilies;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class DiaperBag extends BlockItem {
    public static final Codec<DiaperBagData> CODEC = RecordCodecBuilder.create(instance ->
        instance.group(Codec.INT.fieldOf("diapercount").forGetter(DiaperBagData::diapercount),
                Codec.INT.fieldOf("family").forGetter(DiaperBagData::family)).apply(instance, DiaperBagData::new));
    public static final StreamCodec<ByteBuf, DiaperBagData> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, DiaperBagData::family,
            ByteBufCodecs.INT, DiaperBagData::diapercount,
            DiaperBagData::new
    );

    public DiaperBag(Properties properties) {
        super(PuppyCraftBlocks.DiaperBag.get(), properties);
    }

    public int diaperBagSize() {
        return 10;
    }

    @Override
    public Component getName(ItemStack itemStack) {
        DiaperBagData data = itemStack.get(PuppyCraftDataComponents.DiaperBagData.get());
        if(data == null){
            return super.getName(itemStack);
        }
        if(data.family() > DiaperFamilies.values().length){
            Logger.getAnonymousLogger().warning("Invalid Diaper Family Position!");
            return Component.literal("...");
        }
        return Component.translatable("item.puppycraft.diaperbag." + DiaperFamilies.values()[data.family()].getSerializedName());
    }

    @Override
    protected boolean placeBlock(BlockPlaceContext context, BlockState placementState) {
        if(super.placeBlock(context, placementState)){
            ItemStack itemStack = context.getItemInHand();
            BlockEntity ent = context.getLevel().getBlockEntity(context.getClickedPos());
            DiaperBagEntity diaperbag = (DiaperBagEntity) ent;
            assert diaperbag != null;
            ItemContainerContents storedContents = itemStack.get(DataComponents.CONTAINER);
            if(storedContents == null){ return true; }
            List<ItemStack> stacks = storedContents.allItemsCopyStream().toList();
            for(int i = 0; i < diaperbag.getContainerSize() && i < stacks.size(); i++){
                diaperbag.setItem(i, stacks.get(i));
            }
            return true;
        }
        return false;
    }
}
