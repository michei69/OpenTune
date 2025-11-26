package de.michei69.musixmatch.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MessageSchema<T>(
    val message: MessageContentSchema<T>
) {
    @Serializable
    data class MessageContentSchema<T>(
        val header: HeaderSchema,
        val body: T? = null
    ) {
        @Serializable
        data class HeaderSchema(
            @SerialName("status_code")
            val statusCode: Int
        )
    }
}