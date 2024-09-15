package jjs.dairyperipherals.block

import net.minecraft.core.BlockPos
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.Level
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
        if(pLevel.isClientSide())
            return InteractionResult.PASS

        // Get block entity if it's picker blockentity. Otherwise (?:) just return PASS
        val be = pLevel.getBlockEntity(pPos) as? PickerBlockEntity
            ?: return InteractionResult.PASS

        // If peripheral is present (Blame Forge)
        be.peripheral.ifPresent { peripheral ->
            // Go through all connected computers
            for(computer in peripheral.computers) {
                // And on each computer queue new event
                computer.queueEvent("use", "Test event!", "You smell!")
            }
        }
        return InteractionResult.SUCCESS
    }
}