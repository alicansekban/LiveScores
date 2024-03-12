package com.alican.livescores.data.remote.repository

import com.alican.livescores.data.remote.dataSource.MatchesRemoteDataSource
import javax.inject.Inject

class MatchesRepository @Inject constructor(
    private val matchesRemoteDataSource: MatchesRemoteDataSource
)