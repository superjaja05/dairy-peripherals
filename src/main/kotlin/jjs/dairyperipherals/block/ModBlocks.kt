package jjs.dairyperipherals.block

import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import net.minecraftforge.registries.RegistryObject

object ModBlocks {
    val BLOCKS: DeferredRegister<Block> = DeferredRegister.create(ForgeRegistries.BLOCKS, "dairyperipherals")

    // the returned ObjectHolderDelegate can be used as a property delegate
    // this is automatically registered by the deferred registry at the correct times
    val PICKER_PERIPHERAL: RegistryObject<PickerBlock> = BLOCKS.register("picker_block") {
        PickerBlock(BlockBehaviour.Properties.of()
            .strength(3.0f)
            .sound(SoundType.METAL))
    }
}