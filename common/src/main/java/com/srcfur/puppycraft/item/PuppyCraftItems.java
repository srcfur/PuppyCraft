package com.srcfur.puppycraft.item;

import com.srcfur.puppycraft.Constants;
import com.srcfur.puppycraft.block.PuppyCraftBlocks;
import com.srcfur.puppycraft.datacomponent.PuppyCraftDataComponents;
import com.srcfur.puppycraft.fluid.PuppyCraftFluids;
import com.srcfur.puppycraft.item.diaper.DiaperFamilies;
import com.srcfur.puppycraft.item.diaper.DiaperItem;
import com.srcfur.puppycraft.utility.BlockHelper;
import com.srcfur.puppycraft.utility.ItemHelper;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public class PuppyCraftItems {
    //Generics
    public static ItemHelper<Item> BabyBottle = simple("baby_bottle");
    public static ItemHelper<Item> BabyBottleOfMilk = createHelper("milk_baby_bottle",
            ()->new BabyBottle(ezKey("milk_baby_bottle").food(new FoodProperties(8, 4, true))
                    .component(PuppyCraftDataComponents.BabyBottleData.get(), new BabyBottle.BottleData(10, -30))));
    public static ItemHelper<Item> BabyBottleOfYouth = createHelper("youth_baby_bottle",
            ()->new BabyBottle(ezKey("youth_baby_bottle").food(new FoodProperties(0, 0, true))
                    .component(PuppyCraftDataComponents.BabyBottleData.get(), new BabyBottle.BottleData(0, -3000))));
    public static ItemHelper<BucketItem> BucketOfYouth = createHelper("youth_bucket", ()->new BucketItem(PuppyCraftFluids.Youth.get(), ezKey("youth_bucket")));
    public static ItemHelper<Item> CheapAbsorbentPolymer = simple("cheapdiapersap");
    public static ItemHelper<Item> CheapDiaperCore = simple("cheapdiapercore");
    public static ItemHelper<Item> DiaperBackSheet = simple("clothbacksheet");
    public static ItemHelper<BlockItem> DiaperBag = block("diaper_bag", PuppyCraftBlocks.DiaperBag);
    public static ItemHelper<Item> DiaperTrash = simple("balled_diaper");
    public static ItemHelper<LaxativeCookie> LaxativeCookie = createHelper("laxative_cookie", ()->new LaxativeCookie(ezKey("laxative_cookie").food(new FoodProperties(10, 10, true))));
    public static ItemHelper<Item> NormalDiaperCore = simple("diapercore");
    public static ItemHelper<Item> PremiumDiaperCore = simple("threediapercore");
    public static ItemHelper<BlockItem> PuppyPad = block("puppy_pad", PuppyCraftBlocks.PuppyPad);
    public static ItemHelper<Item> RawSalt = simple("raw_salt");
    public static ItemHelper<Item> Salt = simple("salt");
    public static ItemHelper<BlockItem> SeaSalt = block("seasalt", PuppyCraftBlocks.RawSalt);
    public static ItemHelper<Item> SuperAbsorbentPolymer = simple("diapersap");
    public static ItemHelper<Item> WoodPulp = simple("woodpulp");

    //Diapers
    public static ItemHelper<DiaperItem> MedicalDiaper = diaper("medicaldiaper", "medical", DiaperFamilies.MEDICAL, 100);
    public static ItemHelper<DiaperItem> BunnyHoppsDiaper = diaper("bunnyhoppsdiaper", "bunnyhopps", DiaperFamilies.BUNNYHOPPS, 300);

    private static ItemHelper<Item> simple(String name){
        return createHelper(name, ()->new Item(ezKey(name)));
    }
    private static ItemHelper<DiaperItem> diaper(String name, String texture, DiaperFamilies family, int health) { return createHelper(name,
            ()-> new DiaperItem(texture, family, ezKey(name).component(DataComponents.MAX_DAMAGE, health).stacksTo(1))); }
    private static ItemHelper<BlockItem> block(String name, BlockHelper<? extends Block> blockHelper){
        return createHelper(name, ()->new BlockItem(blockHelper.get(), ezKey(name)));
    }
    private static Item.Properties ezKey(String name){
        return new Item.Properties().setId(ResourceKey.create(BuiltInRegistries.ITEM.key(), Identifier.fromNamespaceAndPath(Constants.MOD_ID, name)));
    }
    private static <T extends Item>ItemHelper<T> createHelper(String name, Supplier<T> supplier){
        return new ItemHelper<T>(Identifier.fromNamespaceAndPath(Constants.MOD_ID, name), supplier);
    }
}
