package com.srcfur.puppycraft.datagen;

import com.srcfur.puppycraft.Constants;
import com.srcfur.puppycraft.item.PuppyCraftItems;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.datagen.v1.recipe.FabricRecipeOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class PuppyCraftRecipeProvider extends FabricRecipeProvider {
    public PuppyCraftRecipeProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected @NotNull RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        return new RecipeProvider(registries, output) {
            @Override
            public void buildRecipes() {
                HolderLookup.RegistryLookup<Item> itemLookup = registries.lookupOrThrow(BuiltInRegistries.ITEM.key());
                oreSmelting(List.of(PuppyCraftItems.RawSalt.get()),
                        RecipeCategory.MISC,
                        CookingBookCategory.MISC,
                        PuppyCraftItems.Salt.get(),
                        0.25f,
                        60,
                        "raw_salt_to_salt_smelting");
                shapeless(RecipeCategory.MISC, PuppyCraftItems.CheapAbsorbentPolymer.get())
                        .requires(PuppyCraftItems.Salt.get(), 4)
                        .unlockedBy(getHasName(PuppyCraftItems.Salt.get()), has(PuppyCraftItems.Salt.get()))
                        .save(output);
                shapeless(RecipeCategory.MISC, PuppyCraftItems.SuperAbsorbentPolymer.get())
                        .requires(PuppyCraftItems.CheapAbsorbentPolymer.get(), 2)
                        .requires(PuppyCraftItems.WoodPulp.get(), 2)
                        .unlockedBy(getHasName(PuppyCraftItems.CheapAbsorbentPolymer.get()), has(PuppyCraftItems.CheapAbsorbentPolymer.get()))
                        .save(output);
                SimpleCookingRecipeBuilder.smoking(Ingredient.of(Items.SUGAR_CANE),
                        RecipeCategory.MISC,
                        PuppyCraftItems.WoodPulp.get(),
                        0.1f,
                        20)
                        .unlockedBy(getHasName(Items.SUGAR_CANE), has(Items.SUGAR_CANE))
                        .save(output, "sugar_cane_to_wood_pulp");
                SimpleCookingRecipeBuilder.smoking(Ingredient.of(registries.lookupOrThrow(BuiltInRegistries.ITEM.key()).getOrThrow(ItemTags.LOGS)),
                                RecipeCategory.MISC, PuppyCraftItems.WoodPulp.get(), 0.1f, 30)
                        .unlockedBy(getHasName(Items.SUGAR_CANE), has(Items.SUGAR_CANE)).save(output, Constants.MOD_ID + ":pulp_from_wood");

                createDiaperCoreRecipe(output, PuppyCraftItems.Salt.get(), PuppyCraftItems.CheapDiaperCore.get());
                createDiaperCoreRecipe(output, PuppyCraftItems.CheapAbsorbentPolymer.get(), PuppyCraftItems.NormalDiaperCore.get());
                createDiaperCoreRecipe(output, PuppyCraftItems.SuperAbsorbentPolymer.get(), PuppyCraftItems.PremiumDiaperCore.get());
            }
            void createDiaperCoreRecipe(RecipeOutput output, ItemLike filling, ItemLike result){
                var recipe = ShapedRecipeBuilder.shaped(
                        this.registries.lookupOrThrow(Registries.ITEM),
                        RecipeCategory.MISC,
                        result).define('P', Items.PAPER).define('C', filling);
                for(int i = 0; i < 3; i++){
                    recipe.pattern("PCP");
                }
                recipe.unlockedBy(getHasName(filling), has(filling)).save(output, Constants.MOD_ID + ":crafting_table_core_" + getItemName(result));
            }
        };
    }



    @Override
    public String getName() {
        return "recipes";
    }
}
