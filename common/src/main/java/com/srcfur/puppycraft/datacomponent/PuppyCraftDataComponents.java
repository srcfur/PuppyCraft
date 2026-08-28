package com.srcfur.puppycraft.datacomponent;

import com.mojang.serialization.Codec;
import com.srcfur.puppycraft.Constants;
import com.srcfur.puppycraft.item.BabyBottle;
import com.srcfur.puppycraft.utility.DataComponentHelper;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.resources.Identifier;

import java.util.function.Supplier;

public class PuppyCraftDataComponents {
    public static final DataComponentHelper<BabyBottle.BottleData> BabyBottleData = createHelper("baby_bottle",
            ()->new DataComponentType.Builder<BabyBottle.BottleData>()
            .persistent(BabyBottle.BOTTLE_DATA_CODEC)
            .networkSynchronized(BabyBottle.BOTTLE_DATA_STREAM)
            .build());
    private static <T> DataComponentHelper<T> createHelper(String name, Supplier<DataComponentType<T>> supplier){
        return new DataComponentHelper<>(Identifier.fromNamespaceAndPath(Constants.MOD_ID, name), supplier);
    }
}
