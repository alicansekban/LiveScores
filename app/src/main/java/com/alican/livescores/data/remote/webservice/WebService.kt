package com.alican.livescores.data.remote.webservice

import com.alican.livescores.data.remote.response.MatchesResponse
import retrofit2.http.GET


interface WebService {


    @GET("SOCCER/matches")
    suspend fun getMatches() : MatchesResponse
}
