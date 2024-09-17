package jjs.dairyperipherals.block

import jjs.dairyperipherals.DairyPeripherals.LOGGER
import jjs.dairyperipherals.net.NetworkHandler
import jjs.dairyperipherals.net.PickerPacket
import net.minecraft.core.BlockPos
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.Style
import net.minecraft.network.chat.TextColor
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.BlockHitResult

class PickerBlock(pProperties: Properties) : Block(pProperties), EntityBlock {
    override fun newBlockEntity(p0: BlockPos, p1: BlockState): BlockEntity = PickerBlockEntity(p0, p1)

    @Deprecated("Deprecated in Java", ReplaceWith(
        "super.use(pState, pLevel, pPos, pPlayer, pHand, pHit)",
        "net.minecraft.world.level.block.Block"
    )
    )
    override fun use(
        pState: BlockState,
        pLevel: Level,
        pPos: BlockPos,
        pPlayer: Player,
        pHand: InteractionHand,
        pHit: BlockHitResult
    ): InteractionResult {
        // Don't do anything on client side

        // Get block entity if it's picker blockentity. Otherwise (?:) just return PASS
        val be = pLevel.getBlockEntity(pPos) as? PickerBlockEntity
            ?: return InteractionResult.PASS
        val username = pPlayer.gameProfile.name

        if (pPlayer.isCrouching && pPlayer.getItemInHand(InteractionHand.MAIN_HAND).isEmpty) {
            if(pLevel.isClientSide())
                return InteractionResult.SUCCESS
            pPlayer.swing(InteractionHand.MAIN_HAND)
            val tags = be.saveWithoutMetadata()
            tags.putString("player", username)
            be.load(tags)
            be.setChanged()
            be.peripheral.ifPresent { peripheral ->
                // Go through all connected computers
                for(computer in peripheral.computers) {
                    // And on each computer queue new event
                    computer.queueEvent("picker_linked", username)
                }
            }
            pPlayer.displayClientMessage(Component.literal("Set linked player to: ")
                .append(Component.literal(username)
                    .setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0x44FF44)))), false)

            return InteractionResult.SUCCESS
        }
        /*
        if(pLevel.isClientSide())
            return InteractionResult.SUCCESS
        pPlayer.displayClientMessage(Component.literal("Right Clicked picker!"), false)
        pPlayer.swing(InteractionHand.MAIN_HAND)
        be.peripheral.ifPresent { peripheral ->
            // Go through all connected computers
            for(computer in peripheral.computers) {
                // And on each computer queue new event
                LOGGER.info(computer.toString())
                computer.queueEvent("picker_use", username)
                NetworkHandler.CHANNEL.sendToServer(PickerPacket("PICKED!!!"))
            }
        }
        */
        if(pLevel.isClientSide())
            return InteractionResult.PASS

        return InteractionResult.PASS
    }
}