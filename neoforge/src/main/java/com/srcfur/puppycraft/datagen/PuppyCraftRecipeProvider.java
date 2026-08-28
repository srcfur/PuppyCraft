package com.srcfur.puppycraft.datagen;


import com.srcfur.puppycraft.Constants;
import com.srcfur.puppycraft.item.PuppyCraftItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class PuppyCraftRecipeProvider extends RecipeProvider {

    protected PuppyCraftRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    @Override
    protected void buildRecipes() {
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(PuppyCraftItems.RawSalt.get()),
                RecipeCategory.MISC, CookingBookCategory.MISC, PuppyCraftItems.Salt.get(), 0.25f, 60);

        ShapelessRecipeBuilder.shapeless(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.MISC,
                PuppyCraftItems.CheapAbsorbentPolymer.get(),
                1).requires(PuppyCraftItems.Salt.get(), 4)
                .unlockedBy(getHasName(PuppyCraftItems.Salt.get()), has(PuppyCraftItems.Salt.get())).save(output, Constants.MOD_ID + ":cheap_sap");
        ShapelessRecipeBuilder.shapeless(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.MISC,
                PuppyCraftItems.SuperAbsorbentPolymer.get(),
                1).requires(PuppyCraftItems.CheapAbsorbentPolymer.get(), 2).requires(PuppyCraftItems.WoodPulp.get(), 2)
                .unlockedBy(getHasName(PuppyCraftItems.CheapAbsorbentPolymer.get()), has(PuppyCraftItems.CheapAbsorbentPolymer.get())).save(output, Constants.MOD_ID + ":sap");

        SimpleCookingRecipeBuilder.smoking(Ingredient.of(Items.SUGAR_CANE),
                        RecipeCategory.MISC, PuppyCraftItems.WoodPulp.get(), 0.1f, 20)
                .unlockedBy(getHasName(Items.SUGAR_CANE), has(Items.SUGAR_CANE)).save(output, Constants.MOD_ID + ":pulp_from_sugarcane");
        SimpleCookingRecipeBuilder.smoking(Ingredient.of(registries.lookupOrThrow(BuiltInRegistries.ITEM.key()).getOrThrow(ItemTags.LOGS)),
                        RecipeCategory.MISC, new ItemStackTemplate(PuppyCraftItems.WoodPulp.get(), 2), 0.1f, 30)
                .unlockedBy(getHasName(Items.SUGAR_CANE), has(Items.SUGAR_CANE)).save(output, Constants.MOD_ID + ":pulp_from_wood");

        createDiaperCoreRecipe(output, PuppyCraftItems.Salt.get(), PuppyCraftItems.CheapDiaperCore.get());
        createDiaperCoreRecipe(output, PuppyCraftItems.CheapAbsorbentPolymer.get(), PuppyCraftItems.NormalDiaperCore.get());
        createDiaperCoreRecipe(output, PuppyCraftItems.SuperAbsorbentPolymer.get(), PuppyCraftItems.PremiumDiaperCore.get());
    }

    protected void createDiaperCoreRecipe(RecipeOutput output, ItemLike filling, ItemLike result){
        var recipe = ShapedRecipeBuilder.shaped(
                this.registries.lookupOrThrow(Registries.ITEM),
                RecipeCategory.MISC,
                result).define('P', Items.PAPER).define('C', filling);
        for(int i = 0; i < 3; i++){
            recipe.pattern("PCP");
        }
        recipe.unlockedBy(getHasName(filling), has(filling)).save(output, Constants.MOD_ID + ":crafting_table_core_" + getItemName(result));
    }
    public static class Runner extends RecipeProvider.Runner {
        // Get the parameters from the `GatherDataEvent`s.
        public Runner(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
            super(output, lookupProvider);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider provider, RecipeOutput output) {
            return new PuppyCraftRecipeProvider(provider, output);
        }

        @Override
        public String getName() {
            return Constants.MOD_ID;
        }
    }
}
