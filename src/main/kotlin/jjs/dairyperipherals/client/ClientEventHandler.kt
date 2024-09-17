package jjs.dairyperipherals.client

import jjs.dairyperipherals.DairyPeripherals.LOGGER
import jjs.dairyperipherals.net.NetworkHandler
import jjs.dairyperipherals.net.PickerPacket
import net.minecraft.client.Minecraft
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.BlockHitResult
import net.minecraft.world.phys.HitResult
import net.minecraftforge.client.event.InputEvent.InteractionKeyMappingTriggered
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.registries.ForgeRegistries


class ClientEventHandler {
    fun registerHandlers() {
        LOGGER.debug("Registering Client Event Handlers")
        val eventBus = MinecraftForge.EVENT_BUS
        eventBus.addListener<InteractionKeyMappingTriggered> { event ->
            this.handleBlockPick(event)
        }
    }

    fun handleBlockPick(event: InteractionKeyMappingTriggered) {
        val mc = Minecraft.getInstance()
        val player = mc.player
        if (player == null || !event.isPickBlock || mc.hitResult == null || mc.hitResult!!.type != HitResult.Type.BLOCK) {
            return
        }
        val target = mc.hitResult
        val level: Level = player.level()
        val pos = (target as BlockHitResult).blockPos
        val state: BlockState = level.getBlockState(pos)

        if (state.isAir) {
            return
        }

        val result = state.getCloneItemStack(target, level, pos, player)

        if (result.isEmpty) {
            return
        }

        val itemId = ForgeRegistries.ITEMS.getKey(result.item)

        var successPickblock = false
        if (player.inventory.findSlotMatchingItem(result) > -1 || player.isCreative)
            successPickblock = true

        NetworkHandler.CHANNEL.sendToServer(PickerPacket(player.gameProfile.name, successPickblock, itemId.toString(), result.tag))
    }
}