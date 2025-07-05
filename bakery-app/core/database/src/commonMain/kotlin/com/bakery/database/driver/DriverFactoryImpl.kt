package com.bakery.database.driver

import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema

/*
* Driver factory expected implementation.
* */
expect class DriverFactoryImpl : DriverFactory {
    override fun createDriver(
        schema: SqlSchema<QueryResult.Value<Unit>>,
        name: String
    ): SqlDriver

    override fun createAsyncDriver(
        schema: SqlSchema<QueryResult.AsyncValue<Unit>>,
        name: String
    ): SqlDriver
}
