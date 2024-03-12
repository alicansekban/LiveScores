package com.alican.livescores.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alican.livescores.data.local.entity.FavoriteMatchEntity

@Dao
interface FavoriteMatchesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteMatch(matchEntity: FavoriteMatchEntity)

    @Query("SELECT * FROM favorite_matches WHERE id = :id")
    fun getFavoriteMatch(id: Int): FavoriteMatchEntity?

    @Query("SELECT * FROM favorite_matches")
    fun getFavoriteMatches(): List<FavoriteMatchEntity>

    @Query("DELETE FROM favorite_matches Where id = :id")
    fun removeFavoriteMatch(id: Int)
}