package com.alican.livescores.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.alican.livescores.data.local.entity.MatchEntity

@Dao
interface MatchDao {
    @Insert(onConflict = REPLACE)
    suspend fun insertMatches(matchEntity: List<MatchEntity>)

    @Query("UPDATE matches SET isFavorite = :isFavorite WHERE id = :matchId")
    fun updateFavorite(matchId: String, isFavorite: Boolean)

    @Query("SELECT * FROM matches WHERE id = :matchId")
    fun getMatch(matchId: String): MatchEntity

    @Query("SELECT * FROM matches")
    fun getMatches(): List<MatchEntity>

    @Query("DELETE FROM matches")
    fun deleteMatches()
}
