package jjs.dairyperipherals.tabs

import jjs.dairyperipherals.DairyPeripherals
import jjs.dairyperipherals.item.ModItems
import net.minecraft.core.HolderLookup.RegistryLookup
import net.minecraft.core.registries.Registries
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.CreativeModeTabs
import net.minecraft.world.item.ItemStack
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent
import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.RegistryObject
import java.util.function.Supplier


object TabInit {
    val CREATIVE_MODE_TABS: DeferredRegister<CreativeModeTab> =
        DeferredRegister.create<CreativeModeTab>(Registries.CREATIVE_MODE_TAB, DairyPeripherals.ID)

    var DAIRYPERIPHERALS_ITEMS: RegistryObject<CreativeModeTab> = CREATIVE_MODE_TABS.register<CreativeModeTab>(
        "dairyperipherals_items"
    ) {
        CreativeModeTab.builder().icon(Supplier<ItemStack> { ItemStack(ModItems.PICKER_PERIPHERAL_ITEM.get()) })
            .title(Component.literal("Dairy Peripherals")).build()
    }

    @SubscribeEvent
    fun addCreative(event: BuildCreativeModeTabContentsEvent) {
        if (event.tab === DAIRYPERIPHERALS_ITEMS.get()) {
            event.accept(ModItems.DAIRY_CORE_ITEM.get())
            event.accept(ModItems.PICKER_PERIPHERAL_ITEM.get())
        }
    }

    fun register(eventBus: IEventBus?) {
        CREATIVE_MODE_TABS.register(eventBus)
    }
}