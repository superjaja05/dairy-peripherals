package jjs.dairyperipherals.peripherals

import dan200.computercraft.api.lua.LuaFunction
import dan200.computercraft.api.peripheral.IComputerAccess
import dan200.computercraft.api.peripheral.IPeripheral
import jjs.dairyperipherals.DairyPeripherals.LOGGER
import jjs.dairyperipherals.block.PickerBlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType

class PickerPeripheral(val be: PickerBlockEntity): IPeripheral {
    val computers = mutableSetOf<IComputerAccess>()

    override fun equals(other: IPeripheral?): Boolean =
        (other as? PickerPeripheral)?.be == be

    override fun getType(): String {
        return "picker"
    }

    override fun attach(computer: IComputerAccess) {
        LOGGER.info("Picker Attached")
        computers.add(computer)
        super.attach(computer)
    }
    override fun detach(computer: IComputerAccess) {
        LOGGER.info("Picker Detached")
        computers.remove(computer)
        super.detach(computer)
    }

    @LuaFunction
    fun getLinkedPlayer(): String? {
        val tags = be.saveWithoutMetadata()
        val username = tags.getString("player")
        return username.ifEmpty {
            null
        }
    }
}