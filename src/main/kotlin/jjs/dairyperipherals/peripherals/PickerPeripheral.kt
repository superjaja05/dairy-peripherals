package jjs.dairyperipherals.peripherals

import dan200.computercraft.api.peripheral.IComputerAccess
import dan200.computercraft.api.peripheral.IPeripheral
import jjs.dairyperipherals.block.PickerBlockEntity

class PickerPeripheral(val be: PickerBlockEntity): IPeripheral {
    val computers = mutableSetOf<IComputerAccess>()

    override fun equals(other: IPeripheral?): Boolean =
        (other as? PickerPeripheral)?.be == be

    override fun getType(): String {
        return "picker"
    }

    override fun attach(computer: IComputerAccess) {
        computers.add(computer)
    }
    override fun detach(computer: IComputerAccess) {
        computers.remove(computer)
    }
}