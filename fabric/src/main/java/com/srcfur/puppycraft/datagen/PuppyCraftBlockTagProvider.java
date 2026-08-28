package com.srcfur.puppycraft.datagen;

import com.srcfur.puppycraft.block.PuppyCraftBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;

import java.util.concurrent.CompletableFuture;

public class PuppyCraftBlockTagProvider extends FabricTagsProvider.BlockTagsProvider {
    public PuppyCraftBlockTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookupFuture) {
        super(output, registryLookupFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
        builder(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(PuppyCraftBlocks.RawSalt.get().properties().blockId())
                .setReplace(false);
        builder(BlockTags.NEEDS_STONE_TOOL)
                .add(PuppyCraftBlocks.RawSalt.get().properties().blockId())
                .setReplace(false);
    }
    @Override
    public String getName() {
        return "blocktags";
    }
}
