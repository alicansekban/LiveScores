package com.alican.livescores.data.remote.dataSource

import com.alican.livescores.data.remote.response.MatchesResponse
import com.alican.livescores.data.remote.webservice.WebService
import com.alican.livescores.utils.ResultWrapper
import com.alican.livescores.utils.safeApiCall
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class MatchesRemoteDataSource @Inject constructor(
    private val webService: WebService
) {
    suspend fun getMatches(): ResultWrapper<MatchesResponse> =
        safeApiCall(Dispatchers.IO) { webService.getMatches() }
}