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

import java.util.List;
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
        return Component.translatable("item.puppycraft.diaperbag." + DiaperFamilies.values()[data.family()].getSerializedName());
    }

    @Override
    public InteractionResult place(BlockPlaceContext placeContext) {
        BlockPlaceContext updatedPlaceContext = this.updatePlacementContext(placeContext);
        assert updatedPlaceContext != null;
        ItemStack itemStack = updatedPlaceContext.getItemInHand();
        InteractionResult og = super.place(placeContext);
        if(og == InteractionResult.SUCCESS){
            BlockEntity ent = placeContext.getLevel().getBlockEntity(placeContext.getClickedPos());
            DiaperBagEntity diaperbag = (DiaperBagEntity) ent;
            assert diaperbag != null;
            ItemContainerContents storedContents = itemStack.get(DataComponents.CONTAINER);
            if(storedContents == null){ return og; }
            List<ItemStack> stacks = storedContents.allItemsCopyStream().toList();
            for(int i = 0; i < diaperbag.getContainerSize() && i < stacks.size(); i++){
                diaperbag.setItem(i, stacks.get(i));
            }
        }
        return og;
    }
}
