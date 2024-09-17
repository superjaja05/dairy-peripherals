package jjs.dairyperipherals.net

import dan200.computercraft.shared.util.NBTUtil
import jjs.dairyperipherals.DairyPeripherals.LOGGER
import jjs.dairyperipherals.block.PickerBlockEntities
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerPlayer
import net.minecraftforge.network.NetworkEvent
import java.util.function.Supplier

class PickerPacket(val player: String,val success: Boolean, val blockId: String, val blockNbt: CompoundTag?) {
    // Constructor for decoding data from the packet
    constructor(buf: FriendlyByteBuf) : this(buf.readUtf(), buf.readBoolean(), buf.readUtf(), buf.readNbt())

    // Encode method to write data into the packet
    fun encode(buf: FriendlyByteBuf) {
        buf.writeUtf(player)
        buf.writeBoolean(success)
        buf.writeUtf(blockId)
        buf.writeNbt(blockNbt)
    }

    // Handle method: what happens when the packet is received
    fun handle(context: Supplier<NetworkEvent.Context>) {
        val ctx = context.get()
        ctx.enqueueWork {
            PickerBlockEntities.list.forEach{ be ->
                val tags = be.saveWithoutMetadata()
                if (be.isRemoved) {
                    PickerBlockEntities.list.remove(be)
                    LOGGER.debug("Removed Picker at " + be.blockPos.toString() + " (Removed)")
                } else if (tags.getString("player") == player) {
                    be.peripheral.ifPresent { peripheral ->
                        // Go through all connected computers
                        for (computer in peripheral.computers) {
                            // And on each computer queue new event
                            computer.queueEvent("picker_pickblock", success, blockId, NBTUtil.toLua(blockNbt))
                        }
                    }
                }
            }
            LOGGER.debug("Picker Packet: $player | $success | $blockId | NBT:\n$blockNbt")
        }
        ctx.packetHandled = true
    }
}