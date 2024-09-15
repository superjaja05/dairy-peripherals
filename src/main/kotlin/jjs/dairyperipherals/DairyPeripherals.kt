package jjs.dairyperipherals

import dan200.computercraft.api.peripheral.IPeripheral
import jjs.dairyperipherals.block.ModBlockEntities
import jjs.dairyperipherals.block.ModBlocks
import net.minecraft.client.Minecraft
import net.minecraft.world.item.CreativeModeTabs
import net.minecraft.world.item.ItemStack
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import thedarkcolour.kotlinforforge.forge.MOD_BUS
import thedarkcolour.kotlinforforge.forge.runForDist
import jjs.dairyperipherals.item.ModItems
import net.minecraftforge.common.capabilities.CapabilityManager
import net.minecraftforge.common.capabilities.CapabilityToken
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent

/**
 * Main mod class. Should be an `object` declaration annotated with `@Mod`.
 * The modid should be declared in this object and should match the modId entry
 * in mods.toml.
 *
 * An example for blocks is in the `blocks` package of this mod.
 */
@Mod(DairyPeripherals.ID)
object DairyPeripherals {
    const val ID = "dairyperipherals"

    val CAPABILITY_PERIPHERAL = CapabilityManager.get(object: CapabilityToken<IPeripheral>() {})

    // the logger for our mod
    val LOGGER: Logger = LogManager.getLogger(ID)

    init {
        LOGGER.log(Level.INFO, "Hello world!")

        // Register the KDeferredRegister to the mod-specific event bus
        ModBlocks.BLOCKS.register(MOD_BUS)
        ModItems.ITEMS.register(MOD_BUS)
        ModBlockEntities.BLOCK_ENTITIES.register(MOD_BUS)

        val obj = runForDist(
            clientTarget = {
                MOD_BUS.addListener(DairyPeripherals::onClientSetup)
                Minecraft.getInstance()
            },
            serverTarget = {
                MOD_BUS.addListener(DairyPeripherals::onServerSetup)
                "test"
            })

        println(obj)
    }

    /**
     * This is used for initializing client specific
     * things such as renderers and keymaps
     * Fired on the mod specific event bus.
     */
    private fun onClientSetup(event: FMLClientSetupEvent) {
        LOGGER.log(Level.INFO, "Initializing client...")
    }

    /**
     * Fired on the global Forge bus.
     */
    private fun onServerSetup(event: FMLDedicatedServerSetupEvent) {
        LOGGER.log(Level.INFO, "Server starting...")
    }
}

@SubscribeEvent
fun onCreativeModeTabBuildContents(event: BuildCreativeModeTabContentsEvent) {
    if (event.tab == CreativeModeTabs.BUILDING_BLOCKS) {
        event.accept(ItemStack(ModItems.PICKER_PERIPHERAL_ITEM.get()))
    }
}