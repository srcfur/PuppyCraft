package com.srcfur.puppycraft.datagen;

import com.srcfur.puppycraft.Constants;
import com.srcfur.puppycraft.block.PuppyCraftBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class PuppyCraftTagProvider extends BlockTagsProvider {

    public PuppyCraftTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, Constants.MOD_ID);
    }
    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(PuppyCraftBlocks.RawSalt.get());
        tag(BlockTags.NEEDS_STONE_TOOL)
                .add(PuppyCraftBlocks.RawSalt.get());
    }
}
