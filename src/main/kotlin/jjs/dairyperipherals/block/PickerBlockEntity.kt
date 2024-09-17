package jjs.dairyperipherals.block

import jjs.dairyperipherals.DairyPeripherals.CAPABILITY_PERIPHERAL
import jjs.dairyperipherals.DairyPeripherals.LOGGER
import jjs.dairyperipherals.peripherals.PickerPeripheral
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.util.LazyOptional

object PickerBlockEntities {
    val list = mutableSetOf<PickerBlockEntity>()
}


class PickerBlockEntity( pPos: BlockPos, pBlockState: BlockState)
    :BlockEntity(ModBlockEntities.PICKER_BLOCK_ENTITY.get(), pPos, pBlockState) {
    val peripheral = LazyOptional.of { PickerPeripheral(this) }

    var linkedPlayerName: String = ""

    override fun <T : Any?> getCapability(cap: Capability<T>, side: Direction?): LazyOptional<T> {
        if (cap == CAPABILITY_PERIPHERAL) {
            return peripheral.cast()
        }
        return super.getCapability(cap, side)
    }

    override fun invalidateCaps() {
        peripheral.invalidate()
        super.invalidateCaps()
    }

    override fun saveAdditional(pTag: CompoundTag) {
        pTag.putString("player", linkedPlayerName)
        super.saveAdditional(pTag)
    }

    override fun load(pTag: CompoundTag) {
        linkedPlayerName = pTag.getString("player")
        super.load(pTag)
    }

    override fun onLoad() {
        LOGGER.debug("Added Picker at " + this.blockPos.toString() + " (Loaded)")
        PickerBlockEntities.list.add(this)
        super.onLoad()
    }

    override fun onChunkUnloaded() {
        PickerBlockEntities.list.remove(this)
        LOGGER.debug("Removed Picker at " + this.blockPos.toString() + " (Unloaded)")
        super.onChunkUnloaded()
    }
}