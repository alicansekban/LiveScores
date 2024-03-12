package com.alican.livescores.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alican.livescores.data.local.dao.FavoriteMatchesDao
import com.alican.livescores.data.local.dao.MatchDao
import com.alican.livescores.data.local.entity.FavoriteMatchEntity
import com.alican.livescores.data.local.entity.MatchEntity

@Database(entities = [MatchEntity::class, FavoriteMatchEntity::class], version = 1)
abstract class MatchDatabase : RoomDatabase() {
    abstract fun matchDao(): MatchDao
    abstract fun favoriteMatchDao(): FavoriteMatchesDao
}
