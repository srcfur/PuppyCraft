package com.srcfur.puppycraft.datagen;

import com.srcfur.puppycraft.Constants;
import com.srcfur.puppycraft.fluid.PuppyCraftFluids;
import com.srcfur.puppycraft.item.PuppyCraftItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.StonecutterRecipe;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.material.Fluid;

import java.util.List;
import java.util.Set;

public class PuppyCraftCommonRecipes extends RecipeProvider {
    protected PuppyCraftCommonRecipes(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    @Override
    public void buildRecipes() {
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
        ShapedRecipeBuilder.shaped(registries.lookupOrThrow(Registries.ITEM), RecipeCategory.MISC, PuppyCraftItems.PuppyPad.get())
                .pattern("sss").pattern("www").define('s', PuppyCraftItems.CheapAbsorbentPolymer.get())
                        .define('w', PuppyCraftItems.DiaperBackSheet.get()).unlockedBy(getHasName(PuppyCraftItems.CheapAbsorbentPolymer.get()), has(PuppyCraftItems.CheapAbsorbentPolymer.get()))
                        .save(output, Constants.MOD_ID + ":" + "puppy_pad_crafting");

        var DiaperSheetAdvancement = new RecipeUnlockAdvancementBuilder();
        DiaperSheetAdvancement.unlockedBy(getHasName(Items.WHITE_WOOL), has(Items.WHITE_WOOL));
        output.accept(ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Constants.MOD_ID, "diaper_backsheet_from_wool")), new StonecutterRecipe(new Recipe.CommonInfo(true),
                Ingredient.of(registries.lookupOrThrow(BuiltInRegistries.ITEM.key()).getOrThrow(ItemTags.WOOL)),
                new ItemStackTemplate(PuppyCraftItems.DiaperBackSheet.get(), 2)),
                DiaperSheetAdvancement.build(output,
                        ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Constants.MOD_ID, "diaper_backsheet_from_wool")), RecipeCategory.MISC));

        SimpleCookingRecipeBuilder.smoking(Ingredient.of(Items.SUGAR_CANE),
                        RecipeCategory.MISC, PuppyCraftItems.WoodPulp.get(), 0.1f, 20)
                .unlockedBy(getHasName(Items.SUGAR_CANE), has(Items.SUGAR_CANE)).save(output, Constants.MOD_ID + ":pulp_from_sugarcane");
        SimpleCookingRecipeBuilder.smoking(Ingredient.of(registries.lookupOrThrow(BuiltInRegistries.ITEM.key()).getOrThrow(ItemTags.LOGS)),
                        RecipeCategory.MISC, PuppyCraftItems.WoodPulp.get(), 0.1f, 30)
                .unlockedBy(getHasName(Items.SUGAR_CANE), has(Items.SUGAR_CANE)).save(output, Constants.MOD_ID + ":pulp_from_wood");

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(PuppyCraftItems.RawSalt.get()),
                RecipeCategory.MISC, CookingBookCategory.MISC, PuppyCraftItems.Salt.get(), 0.1f, 20)
                        .unlockedBy(getHasName(PuppyCraftItems.RawSalt.get()), has(PuppyCraftItems.RawSalt.get())).save(output, Constants.MOD_ID + ":saltsmelting");

        createDiaperCoreRecipe(output, PuppyCraftItems.Salt.get(), PuppyCraftItems.CheapDiaperCore.get());
        createDiaperCoreRecipe(output, PuppyCraftItems.CheapAbsorbentPolymer.get(), PuppyCraftItems.NormalDiaperCore.get());
        createDiaperCoreRecipe(output, PuppyCraftItems.SuperAbsorbentPolymer.get(), PuppyCraftItems.PremiumDiaperCore.get());

        createDiaperRecipe(output, Items.PAPER, PuppyCraftItems.CheapDiaperCore.get(), PuppyCraftItems.CheapDiaper.get());
        createDiaperRecipe(output, Items.PAPER, PuppyCraftItems.NormalDiaperCore.get(), PuppyCraftItems.PullUpDiaper.get());
        createDiaperRecipe(output, PuppyCraftItems.DiaperBackSheet.get(), PuppyCraftItems.NormalDiaperCore.get(), PuppyCraftItems.MedicalDiaper.get());
        createDiaperRecipe(output, PuppyCraftItems.DiaperBackSheet.get(), PuppyCraftItems.PremiumDiaperCore.get(), PuppyCraftItems.MegaMaxDiaper.get());

        createDyedDiaperRecipe(output, PuppyCraftItems.MegaMaxDiaper.get(), List.of(Items.PINK_DYE, Items.PINK_DYE), PuppyCraftItems.BunnyHoppsDiaper.get());
        createDyedDiaperRecipe(output, PuppyCraftItems.MegaMaxDiaper.get(), List.of(Items.PURPLE_DYE, Items.PINK_DYE), PuppyCraftItems.SubspaceDiaper.get());

        //Milk isn't a liquid :<
        ShapelessRecipeBuilder.shapeless(registries.lookupOrThrow(Registries.ITEM), RecipeCategory.FOOD, PuppyCraftItems.BabyBottleOfMilk.get())
                .requires(PuppyCraftItems.BabyBottle.get()).requires(Items.MILK_BUCKET).unlockedBy(getHasName(Items.MILK_BUCKET), has(Items.MILK_BUCKET))
                .save(output, Constants.MOD_ID + ":" + getItemName(PuppyCraftItems.BabyBottle.get()) + "_fill_bucket");
        createBottleRecipe(output, PuppyCraftFluids.Youth.get(), PuppyCraftItems.BabyBottleOfYouth.get());
    }
    void createDiaperRecipe(RecipeOutput output, ItemLike fabric, ItemLike core, ItemLike diaper){
        var recipe = ShapedRecipeBuilder.shaped(
                this.registries.lookupOrThrow(Registries.ITEM),
                RecipeCategory.MISC,
                diaper).define('F', fabric).define('C', core);
        recipe.pattern("FFF");
        recipe.pattern(" C ");
        recipe.pattern("FFF");
        recipe.unlockedBy(getHasName(core), has(core)).save(output, Constants.MOD_ID + ":" + getItemName(diaper) + "_craftingtable");
    }
    void createDyedDiaperRecipe(RecipeOutput output, ItemLike diaper, List<ItemLike> dyes, ItemLike coloredDiaper){
        var recipe = ShapelessRecipeBuilder.shapeless(this.registries.lookupOrThrow(Registries.ITEM),
                RecipeCategory.MISC,
                coloredDiaper);
        recipe.requires(diaper);
        for(ItemLike dye : dyes){
            recipe.requires(dye);
        }
        recipe.unlockedBy(getHasName(diaper), has(diaper)).save(output, Constants.MOD_ID + ":" + getItemName(coloredDiaper) + "_crafting");
    }
    void createBottleRecipe(RecipeOutput output, Fluid fluid, ItemLike result){
        var recipe = ShapelessRecipeBuilder.shapeless(this.registries.lookupOrThrow(Registries.ITEM),
                RecipeCategory.FOOD,
                result);
        recipe.requires(PuppyCraftItems.BabyBottle.get());
        recipe.requires(fluid.getBucket());
        recipe.unlockedBy(getHasName(fluid.getBucket()), has(fluid.getBucket())).save(output, Constants.MOD_ID + ":" + getItemName(result) + "_fill_bucket");
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
}
