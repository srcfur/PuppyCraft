package com.srcfur.puppycraft.block;

import com.srcfur.puppycraft.Constants;
import com.srcfur.puppycraft.utility.BlockHelper;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Supplier;

public class PuppyCraftBlocks {
    public static final BlockHelper<Block> RawSalt = createHelper("seasalt", ()->new Block(ezKey("seasalt").destroyTime(0.75f)));
    public static final BlockHelper<DiaperBagBlock> DiaperBag = createHelper("diaper_bag", ()->new DiaperBagBlock(ezKey("diaper_bag")));

    private static BlockBehaviour.Properties ezKey(String name){
        return BlockBehaviour.Properties.of().setId(ResourceKey.create(BuiltInRegistries.BLOCK.key(), Identifier.fromNamespaceAndPath(Constants.MOD_ID, name)));
    }
    private static <T extends Block> BlockHelper<T> createHelper(String name, Supplier<T> supplier){
        return new BlockHelper<T>(Identifier.fromNamespaceAndPath(Constants.MOD_ID, name), supplier);
    }
}
