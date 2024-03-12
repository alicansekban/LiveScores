package com.alican.livescores.data.remote.dataSource

import com.alican.livescores.data.remote.webservice.WebService
import javax.inject.Inject

class ExampleDataSource @Inject constructor(
    private val webService: WebService
) {
}