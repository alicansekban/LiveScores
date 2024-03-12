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
                    val response = apiData.value.data
                    val entityList = response.map {
                        val favoriteMatch = localDataSource.getFavoriteMatch(it.i ?: 0)
                        val isFavorite = favoriteMatch != null
                        it.toEntity(isFavorite)
                    }
                    localDataSource.insertMatches(entityList)

                }
            }
            // offline first mantığı ile veriyi sadece db'den çekip domain katmanına gönderiyoruz.

            emit(ResultWrapper.Success(localDataSource.getMatches()))
        }
    }
}