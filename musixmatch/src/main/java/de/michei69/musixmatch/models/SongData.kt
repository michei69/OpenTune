package de.michei69.musixmatch.models

import kotlinx.serialization.Serializable

@Serializable
data class SongData(
    val title: String,
    val artists: List<String>,
    val lrc: String,
    val lrcLanguage: String,
    val lyrics: String,
    val allLrc: List<String>
)