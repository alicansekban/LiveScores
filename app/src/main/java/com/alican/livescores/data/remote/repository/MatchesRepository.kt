package com.alican.livescores.data.remote.repository

import com.alican.livescores.data.local.entity.FavoriteMatchEntity
import com.alican.livescores.data.local.entity.MatchEntity
import com.alican.livescores.data.remote.dataSource.MatchesLocalDataSource
import com.alican.livescores.data.remote.dataSource.MatchesRemoteDataSource
import com.alican.livescores.domain.mapper.toEntity
import com.alican.livescores.utils.ResultWrapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MatchesRepository @Inject constructor(
    private val remoteDataSource: MatchesRemoteDataSource,
    private val localDataSource: MatchesLocalDataSource
) {
    suspend fun getMatches(): Flow<ResultWrapper<List<MatchEntity>>> {
        return flow {

            emit(ResultWrapper.Loading)
            when (val apiData = remoteDataSource.getMatches()) {
                is ResultWrapper.GenericError -> {
                    emit(ResultWrapper.GenericError())
                }

                ResultWrapper.Loading -> {}
                is ResultWrapper.NetworkError -> {
                    emit(ResultWrapper.NetworkError)
                }

                is ResultWrapper.Success -> {
                    // burada gelen veriyi filtreleyip sadece göstermek istediğimiz, yani sonuçlanmış maçları gösteriyoruz.
                    val response = apiData.value.data.filter { it.sc?.abbr == "Bitti" }
                        .sortedByDescending { it.d }
                    val entityList = response.map {
                        // map işlemi yapılacak elemanın favori olup olmadığı bilgisini burada kontrol ediyoruz ve ona göre mapper'a paslıyoruz.
                        val favoriteMatch = localDataSource.getFavoriteMatch(it.i ?: 0)
                        val isFavorite = favoriteMatch != null
                        it.toEntity(isFavorite)
                    }
                    localDataSource.insertMatches(entityList)
                    // işlem sonucunda veriyi lokal db'den çekiyoruz. bunun sebebi detay ekranına giderken veriyi lokal db üzerinden çekeceğiz. çünkü compose'da navigation yaparken data class paslamak şuan official destekli değil.
                    emit(ResultWrapper.Success(localDataSource.getMatches()))

                }
            }

        }
    }

    fun getMatchDetail(id: Int): ResultWrapper<MatchEntity> {
        return localDataSource.getMatch(id)
    }

    suspend fun insertFavoriteMatch(match: FavoriteMatchEntity): ResultWrapper<Any> {
        return localDataSource.addToFavorite(match)
    }

    fun removeFavoriteMatch(id: Int): ResultWrapper<Any> {
        return localDataSource.removeFromFavorite(id)
    }
}