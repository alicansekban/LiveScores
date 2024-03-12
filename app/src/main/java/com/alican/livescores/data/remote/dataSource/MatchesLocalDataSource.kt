package com.alican.livescores.data.remote.dataSource

import com.alican.livescores.data.local.MatchDatabase
import com.alican.livescores.data.local.entity.FavoriteMatchEntity
import com.alican.livescores.data.local.entity.MatchEntity
import com.alican.livescores.utils.ResultWrapper
import javax.inject.Inject

class MatchesLocalDataSource @Inject constructor(
    private val db: MatchDatabase
) {
    suspend fun insertMatches(matches: List<MatchEntity>) {
        db.matchDao().insertMatches(matches)
    }


    fun getMatches(): List<MatchEntity> = db.matchDao().getMatches()


    suspend fun addToFavorite(match: FavoriteMatchEntity): ResultWrapper<Any> {
        return try {
            ResultWrapper.Loading
            ResultWrapper.Success(db.favoriteMatchDao().insertFavoriteMatch(match))
        } catch (e: Exception) {
            ResultWrapper.GenericError(error = e.message)
        }
    }

    fun removeFromFavorite(id: Int): ResultWrapper<Any> {
        return try {
            ResultWrapper.Loading
            ResultWrapper.Success(db.favoriteMatchDao().removeFavoriteMatch(id))
        } catch (e: Exception) {
            ResultWrapper.GenericError(error = e.message)
        }
    }
    fun getFavoriteMatch(id: Int) = db.favoriteMatchDao().getFavoriteMatch(id)
}