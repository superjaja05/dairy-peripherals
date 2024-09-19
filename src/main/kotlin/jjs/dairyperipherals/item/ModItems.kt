package jjs.dairyperipherals.item

import jjs.dairyperipherals.block.ModBlocks
import net.minecraft.world.food.FoodProperties
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import net.minecraftforge.registries.RegistryObject

object ModItems {
    val ITEMS: DeferredRegister<Item> = DeferredRegister.create(ForgeRegistries.ITEMS, "dairyperipherals")

    val DAIRY_CORE_ITEM: RegistryObject<Item> = ITEMS.register("dairy_core") {
        Item(Item.Properties().food(FoodProperties.Builder().nutrition(6).build()))
    }

    val PICKER_PERIPHERAL_ITEM: RegistryObject<Item> = ITEMS.register("picker_block") {
        BlockItem(ModBlocks.PICKER_PERIPHERAL.get(), Item.Properties())
    }
}