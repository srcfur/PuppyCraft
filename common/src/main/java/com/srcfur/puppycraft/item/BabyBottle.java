package com.srcfur.puppycraft.item;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.srcfur.badhygiene.api.HygieneAPI;
import com.srcfur.puppycraft.PuppyCraftCommon;
import com.srcfur.puppycraft.api.PuppyPlayer;
import com.srcfur.puppycraft.datacomponent.PuppyCraftDataComponents;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class BabyBottle extends Item {
    public record BottleData(int urine, int maturity){

    }
    public static final Codec<BottleData> BOTTLE_DATA_CODEC = RecordCodecBuilder.create(instance ->
            instance.group(Codec.INT.fieldOf("urine").forGetter(BottleData::urine),
                    Codec.INT.fieldOf("maturity").forGetter(BottleData::maturity)).apply(instance, BottleData::new));
    public static final StreamCodec<ByteBuf, BottleData> BOTTLE_DATA_STREAM = StreamCodec.composite(
            ByteBufCodecs.INT, BottleData::urine,
            ByteBufCodecs.INT, BottleData::maturity,
            BottleData::new
    );


    public BabyBottle(Properties properties) {
        super(properties);
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 100;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        if(!(livingEntity instanceof Player)) return super.finishUsingItem(stack, level, livingEntity);
        if(level.isClientSide()) return super.finishUsingItem(stack, level, livingEntity);
        PuppyPlayer puppy = PuppyCraftCommon.API.getPuppyPlayer((Player) livingEntity);
        BottleData data = stack.getOrDefault(PuppyCraftDataComponents.BabyBottleData.get(), new BottleData(0,0));
        puppy.impactMaturity(data.maturity());
        puppy.getHygiene().setBladder(puppy.getHygiene().getBladder() + data.urine());
        return super.finishUsingItem(stack, level, livingEntity);
    }
}
