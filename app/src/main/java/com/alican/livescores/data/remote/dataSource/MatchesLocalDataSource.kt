package com.alican.livescores.data.remote.dataSource

import com.alican.livescores.data.local.MatchDatabase
import javax.inject.Inject

class MatchesLocalDataSource @Inject constructor(
    private val db: MatchDatabase
)