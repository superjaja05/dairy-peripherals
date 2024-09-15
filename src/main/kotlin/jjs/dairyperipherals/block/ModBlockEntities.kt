package jjs.dairyperipherals.block

import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import net.minecraftforge.registries.RegistryObject

object ModBlockEntities {
    val BLOCK_ENTITIES: DeferredRegister<BlockEntityType<*>> = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, "dairyperipherals")

    val PICKER_BLOCK_ENTITY: RegistryObject<BlockEntityType<*>> = BLOCK_ENTITIES.register("picker_block") {
        BlockEntityType.Builder.of(::PickerBlockEntity).build(null)
    }
}