package com.alican.livescores.data.remote.repository

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
                    // burada gelen veriyi önce favori olup olmadığı ile ilgili kontrol edip daha sonra local db'ye ekliyoruz.
                    val response = apiData.value.data.filter { it.sc?.abbr == "Bitti" }
                    val entityList = response.map {
                        val favoriteMatch = localDataSource.getFavoriteMatch(it.i ?: 0)
                        val isFavorite = favoriteMatch != null
                        it.toEntity(isFavorite)
                    }
                    localDataSource.insertMatches(entityList)

                }
            }
            // işlem sonucunda veriyi lokal db'den çekiyoruz.

            emit(ResultWrapper.Success(localDataSource.getMatches()))
        }
    }
}