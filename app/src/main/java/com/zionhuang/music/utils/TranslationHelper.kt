package com.zionhuang.music.utils

import com.zionhuang.music.db.entities.LyricsEntity

/**
 * FOSS build: on-device lyrics translation (ML Kit) is intentionally not bundled,
 * so translation is a no-op that returns the original lyrics unchanged.
 */
object TranslationHelper {
    suspend fun translate(lyrics: LyricsEntity): LyricsEntity = lyrics
}
