package com.alican.livescores.domain.use_case

import com.alican.livescores.data.remote.repository.MatchesRepository
import com.alican.livescores.domain.BaseUIModel
import com.alican.livescores.domain.Error
import com.alican.livescores.domain.Loading
import com.alican.livescores.domain.Success
import com.alican.livescores.utils.FavoriteActionType
import com.alican.livescores.utils.ResultWrapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RemoveFromFavoriteUseCase @Inject constructor(
    private val repository: MatchesRepository
) {

    operator fun invoke(id: Int): Flow<BaseUIModel<FavoriteActionType>> {
        return flow {
            emit(Loading())
            emit(
                when (val result = repository.removeFavoriteMatch(id)) {
                    is ResultWrapper.GenericError -> Error(result.error ?: "Error Occurred")
                    ResultWrapper.Loading -> Loading()
                    ResultWrapper.NetworkError -> Error("")
                    is ResultWrapper.Success -> Success(FavoriteActionType.Removed)
                }
            )
        }
    }
}