package com.alican.livescores.data.remote.dataSource

import com.alican.livescores.data.local.MatchDatabase
import com.alican.livescores.data.local.entity.FavoriteMatchEntity
import com.alican.livescores.data.local.entity.MatchEntity
import javax.inject.Inject

class MatchesLocalDataSource @Inject constructor(
    private val db: MatchDatabase
) {
    suspend fun insertMatches(matches: List<MatchEntity>) {
        db.matchDao().insertMatches(matches)
    }

    fun deleteMatches() = db.matchDao().deleteMatches()

    fun getMatches(): List<MatchEntity> = db.matchDao().getMatches()


    suspend fun addToFavorite(match: FavoriteMatchEntity) {
        db.favoriteMatchDao().insertFavoriteMatch(match)
    }

    fun removeFromFavorite(id: Int) = db.favoriteMatchDao().removeFavoriteMatch(id)

    fun getFavoriteMatch(id: Int) = db.favoriteMatchDao().getFavoriteMatch(id)
}