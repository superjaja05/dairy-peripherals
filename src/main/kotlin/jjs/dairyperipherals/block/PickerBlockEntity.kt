package jjs.dairyperipherals.block

import com.sun.jdi.connect.spi.TransportService.Capabilities
import jjs.dairyperipherals.DairyPeripherals.CAPABILITY_PERIPHERAL
import jjs.dairyperipherals.peripherals.PickerPeripheral
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.util.LazyOptional

class PickerBlockEntity( pPos: BlockPos, pBlockState: BlockState)
    :BlockEntity(ModBlockEntities.PICKER_BLOCK_ENTITY.get(), pPos, pBlockState) {
    val peripheral = LazyOptional.of { PickerPeripheral(this) }

    override fun <T : Any?> getCapability(cap: Capability<T>): LazyOptional<T> {
        if (cap == CAPABILITY_PERIPHERAL)
            return peripheral.cast()
        return super.getCapability(cap)
    }

    override fun invalidateCaps() {
        peripheral.invalidate()
        super.invalidateCaps()
    }
}