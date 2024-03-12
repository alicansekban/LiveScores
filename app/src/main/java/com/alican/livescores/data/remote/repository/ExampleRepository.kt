package com.alican.livescores.data.remote.repository

import com.alican.livescores.data.remote.dataSource.ExampleDataSource
import javax.inject.Inject

class ExampleRepository @Inject constructor(
    private val exampleDataSource: ExampleDataSource
) {
}