package com.bakery.database.driver

import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema

/**
* Interface to create sqldelight drivers.
* */
interface DriverFactory {
    /**
     * Create a sql diver by the given schema and name synchronously.
     * @param schema SqlSchema to create the driver
     * @param name Name of the database or url where the resulting db will be created
     * */
    fun createDriver(schema: SqlSchema<QueryResult.Value<Unit>>, name: String): SqlDriver

    /**
     * Create a sql diver by the given schema and name synchronously.
     * @param schema SqlSchema to create the driver
     * @param name Name of the database or url where the resulting db will be created
     * */
    fun createAsyncDriver(schema: SqlSchema<QueryResult.AsyncValue<Unit>>, name: String): SqlDriver
}
