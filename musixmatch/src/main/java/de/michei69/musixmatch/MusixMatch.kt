package de.michei69.musixmatch

import de.michei69.musixmatch.models.MacroCallSchema
import de.michei69.musixmatch.models.RichSubtitleSchema
import de.michei69.musixmatch.models.SongData
import kotlinx.coroutines.delay

object MusixMatch {
    suspend fun search(title: String, artist: String): SongData? {
        if (!MusixMatchAPI.initialized) {
            while (!MusixMatchAPI.initialized) {
                MusixMatchAPI.init()
                delay(1000)
            }
        }
        val req = MusixMatchAPI.query<MacroCallSchema<RichSubtitleSchema>>("macro.subtitles.get", mapOf(
            "q_track" to title,
            "q_artist" to artist,
            "namespace" to "lyrics_richsynched",
            "subtitle_format" to "lrc"
        ))
        if (req == null) {
            MusixMatchAPI.reinit()
            return search(title, artist)
        }
        val data = req.macroCalls

        val track = data.matcherTrackGet?.message?.body?.track
        val lyrics = data.trackLyricsGet?.message?.body?.lyrics?.lyricsBody
        val allSubtitles = data.trackSubtitlesGet?.message?.body?.subtitleList
        val subtitles = allSubtitles?.firstOrNull()
        if (track == null || track.trackId == 115264642) return null

        return SongData(
            track.trackName,
            listOf(track.artistName),
            subtitles?.subtitle?.subtitleBody ?: "",
            subtitles?.subtitle?.subtitleLanguage ?: "",
            lyrics ?: "",
            allSubtitles?.map { it.subtitle.subtitleBody } ?: emptyList()
        )
    }

    suspend fun getLyrics(title: String, artist: String, duration: Int) = runCatching {
        val data = search(title, artist)
        if (data != null) {
            if (!data.lrc.isEmpty()) return@runCatching data.lrc
            if (!data.lyrics.isEmpty()) return@runCatching data.lyrics
        }
        throw IllegalStateException("Lyrics unavailable")
    }
    suspend fun getAllLyrics(title: String, artist: String, duration: Int, callback: (String) -> Unit) {
        val data = search(title, artist)
        if (data != null) {
            data.allLrc.forEach { it.let(callback) }
            data.lyrics.let(callback)
        }
    }
}