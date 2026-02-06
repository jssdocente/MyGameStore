package com.pmdm.mygamestore.data.local

import androidx.room.TypeConverter
import com.pmdm.mygamestore.domain.model.LibraryStatus
import com.pmdm.mygamestore.domain.model.GameProgress

class RoomConverters {
    @TypeConverter
    fun fromLibraryStatus(status: LibraryStatus): String = status.name

    @TypeConverter
    fun toLibraryStatus(value: String): LibraryStatus = try {
        LibraryStatus.valueOf(value)
    } catch (e: Exception) {
        LibraryStatus.NONE
    }

    @TypeConverter
    fun fromGameProgress(progress: GameProgress): String = progress.name

    @TypeConverter
    fun toGameProgress(value: String): GameProgress = try {
        GameProgress.valueOf(value)
    } catch (e: Exception) {
        GameProgress.PENDING
    }
}
