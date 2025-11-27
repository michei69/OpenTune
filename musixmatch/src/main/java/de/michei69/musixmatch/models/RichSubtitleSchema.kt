package de.michei69.musixmatch.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject


@Serializable
data class MacroCallSchema<T> (
    @SerialName("macro_calls")
    val macroCalls: T
)

@Serializable
data class RichSubtitleSchema (
    @SerialName("track.lyrics.get")
    private val _trackLyricsGet: JsonElement,
    @SerialName("track.subtitles.get")
    private val _trackSubtitlesGet: JsonElement,
    @SerialName("matcher.track.get")
    private val _matcherTrackGet: JsonElement,
) {

    val trackLyricsGet: MessageSchema<LyricsSchema>?
        get() = try {
            val json = Json {
                ignoreUnknownKeys = true
                isLenient = true
                coerceInputValues = true
            }
            when (_trackSubtitlesGet) {
                is JsonObject -> json.decodeFromJsonElement(MessageSchema.serializer(LyricsSchema.serializer()), _trackLyricsGet)
                else -> null
            }
        } catch (e: Exception) {
            null
        }

    val trackSubtitlesGet: MessageSchema<SubtitleListSchema>?
        get() = try {
            val json = Json {
                ignoreUnknownKeys = true
                isLenient = true
                coerceInputValues = true
            }
            when (_trackSubtitlesGet) {
                is JsonObject -> json.decodeFromJsonElement(MessageSchema.serializer(SubtitleListSchema.serializer()), _trackSubtitlesGet)
                else -> null
            }
        } catch (e: Exception) {
            null
        }

    val matcherTrackGet: MessageSchema<MatcherTrackSchema>?
        get() = try {
            val json = Json {
                ignoreUnknownKeys = true
                isLenient = true
                coerceInputValues = true
            }
            when (_trackSubtitlesGet) {
                is JsonObject -> json.decodeFromJsonElement(MessageSchema.serializer(
                    MatcherTrackSchema.serializer()), _matcherTrackGet)
                else -> null
            }
        } catch (e: Exception) {
            null
        }

    @Serializable
    data class LyricsSchema(
        val lyrics: LyricsContentSchema
    ) {
        @Serializable
        data class LyricsContentSchema(
            val instrumental: Int,
            @SerialName("lyrics_body")
            val lyricsBody: String,
            @SerialName("lyrics_language")
            val lyricsLanguage: String,
            @SerialName("lyrics_language_description")
            val lyricsLanguageDescription: String
        )
    }

    @Serializable
    data class SubtitleListSchema(
        @SerialName("subtitle_list")
        val subtitleList: List<SubtitleSchema>
    ) {
        @Serializable
        data class SubtitleSchema(
            val subtitle: SubtitleContentSchema
        ) {
            @Serializable
            data class SubtitleContentSchema(
                @SerialName("subtitle_body")
                val subtitleBody: String,
                @SerialName("subtitle_length")
                val subtitleLength: Int,
                @SerialName("subtitle_language")
                val subtitleLanguage: String,
            )
        }
    }

    @Serializable
    data class MatcherTrackSchema(
        val track: TrackSchema
    ) {
        @Serializable
        data class TrackSchema(
            @SerialName("track_id")
            val trackId: Int,
            @SerialName("track_name")
            val trackName: String,
            @SerialName("artist_name")
            val artistName: String
        )
    }
}