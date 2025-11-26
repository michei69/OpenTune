package com.arturo254.opentune.lyrics

import android.content.Context
import com.arturo254.opentune.constants.EnableMusixMatchKey
import com.arturo254.opentune.utils.dataStore
import com.arturo254.opentune.utils.get
import de.michei69.musixmatch.MusixMatch

object MusixMatchLyricsProvider : LyricsProvider {
    override val name = "MusixMatch"

    override fun isEnabled(context: Context): Boolean = context.dataStore[EnableMusixMatchKey] ?: true

    override suspend fun getLyrics(
        id: String,
        title: String,
        artist: String,
        duration: Int
    ): Result<String> = MusixMatch.getLyrics(title, artist, duration)

    override suspend fun getAllLyrics(
        id: String,
        title: String,
        artist: String,
        duration: Int,
        callback: (String) -> Unit
    ) = MusixMatch.getAllLyrics(title, artist, duration, callback)
}