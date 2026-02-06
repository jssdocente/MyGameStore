package com.pmdm.mygamestore.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.pmdm.mygamestore.data.local.dao.*
import com.pmdm.mygamestore.data.local.entities.*

@Database(
    entities = [
        UserEntity::class,
        LibraryEntity::class,
        SearchHistoryEntity::class,
        RecentGameEntity::class,
        GameNoteEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(RoomConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun libraryDao(): LibraryDao
    abstract fun searchHistoryDao(): SearchHistoryDao
    abstract fun recentGameDao(): RecentGameDao
    abstract fun gameNoteDao(): GameNoteDao
}
