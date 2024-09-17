package jjs.dairyperipherals.net

import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceLocation
import net.minecraftforge.network.NetworkEvent
import net.minecraftforge.network.NetworkRegistry
import net.minecraftforge.network.simple.SimpleChannel
import java.util.function.BiConsumer
import java.util.function.Supplier

object NetworkHandler {
    private const val PROTOCOL_VERSION = "1"
    private var packetId = 0

    // Create a SimpleChannel for communication
    val CHANNEL: SimpleChannel = NetworkRegistry.newSimpleChannel(
        ResourceLocation("yourmodid", "main_channel"),
        { PROTOCOL_VERSION },  // Check for compatible protocol version
        { PROTOCOL_VERSION == it },  // Client side version check
        { PROTOCOL_VERSION == it }   // Server side version check
    )

    // Register packets
    fun registerPackets() {
        registerPacket(
            PickerPacket::class.java,
            PickerPacket::encode,
            { byteBuf: FriendlyByteBuf -> PickerPacket(byteBuf) },  // Decoder (constructor)
            PickerPacket::handle
        )
    }

    // Helper method to register a packet
    private fun <M> registerPacket(
        messageType: Class<M>,
        encoder: BiConsumer<M, FriendlyByteBuf>,
        decoder: java.util.function.Function<FriendlyByteBuf, M>,
        messageConsumer: BiConsumer<M, Supplier<NetworkEvent.Context>>
    ) {
        CHANNEL.registerMessage(packetId++, messageType, encoder, decoder, messageConsumer)
    }
}