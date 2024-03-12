package com.alican.livescores.domain.use_case

import com.alican.livescores.data.remote.repository.MatchesRepository
import com.alican.livescores.domain.BaseUIModel
import com.alican.livescores.domain.Empty
import com.alican.livescores.domain.Error
import com.alican.livescores.domain.Loading
import com.alican.livescores.domain.Success
import com.alican.livescores.domain.mapper.toUIModel
import com.alican.livescores.domain.ui_models.MatchUIModel
import com.alican.livescores.utils.ResultWrapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMatchesUseCase @Inject constructor(
    private val repository: MatchesRepository
) {

    operator fun invoke(
    ): Flow<BaseUIModel<List<MatchUIModel>>> {
        return flow {
            emit(Loading())
            // repositoryden çektiğimiz result'u uimodel'imize çevirip gerekli dataları mapper yapıyoruz.
            repository.getMatches().collect { data ->
                when (data) {
                    is ResultWrapper.GenericError -> {
                        emit(Error(data.error ?: "something went wrong"))
                    }

                    ResultWrapper.Loading -> {
                        emit(Loading())
                    }

                    is ResultWrapper.NetworkError -> {
                        emit(Error("Network Error"))
                    }

                    is ResultWrapper.Success -> {
                        // gelen sonucun dolu ya da boş olduğunu kontrol edip ona göre ui'a sonuç aktarıyoruz.
                        if (data.value.isEmpty()) {
                            emit(Empty())
                        } else {
                            emit(Success(data.value.map { match ->
                                match.toUIModel()
                            }))
                        }
                    }
                }
            }
        }
    }
}