package com.alican.livescores.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import com.alican.livescores.data.local.model.MatchEntity

@Dao
interface MatchDao {
    @Insert(onConflict = REPLACE)
    suspend fun insertData(matchEntity: MatchEntity)
}
