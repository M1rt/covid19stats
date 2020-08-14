package su.mirt.covid19stats.api

import kotlinx.serialization.Decoder
import kotlinx.serialization.Encoder
import kotlinx.serialization.KSerializer
import kotlinx.serialization.PrimitiveDescriptor
import kotlinx.serialization.PrimitiveKind
import kotlinx.serialization.SerialDescriptor
import kotlinx.serialization.Serializer
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Serializer(forClass = Calendar::class)
object CalendarSerializer : KSerializer<Calendar> {
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:SS'Z'", Locale.US)

    override val descriptor: SerialDescriptor =
        PrimitiveDescriptor("WithCustomDefault", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): Calendar {
        val calendar = Calendar.getInstance()
        calendar.time = dateFormat.parse(decoder.decodeString())!!
        return calendar
    }

    override fun serialize(encoder: Encoder, value: Calendar) {
        encoder.encodeString(dateFormat.format(value.time))
    }
}
