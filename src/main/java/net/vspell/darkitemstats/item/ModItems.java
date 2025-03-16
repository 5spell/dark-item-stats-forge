package net.vspell.darkitemstats.item;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.vspell.darkitemstats.DarkItemStats;

public class ModItems {

    public static final DeferredRegister<Item> ITEM_REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, DarkItemStats.MOD_ID);


    public static final RegistryObject<Item> RUBY = ITEM_REGISTER.register("ruby",() -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SAPPHIRE = ITEM_REGISTER.register("sapphire",() -> new Item(new Item.Properties()));


    public static void registerItems(IEventBus eventBus){
        ITEM_REGISTER.register(eventBus);
    }

}
