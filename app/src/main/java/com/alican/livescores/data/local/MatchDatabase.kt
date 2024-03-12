package com.alican.livescores.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alican.livescores.data.local.model.MatchEntity

@Database(entities = [MatchEntity::class], version = 1)
abstract class MatchDatabase : RoomDatabase() {
    abstract fun matchDao(): MatchDao
}
