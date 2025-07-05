package com.bakery.database.driver

import app.cash.sqldelight.async.coroutines.synchronous
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import app.cash.sqldelight.driver.native.NativeSqliteDriver

/*
* Driver factory Native implementation.
* */
actual class DriverFactoryImpl : DriverFactory {
    actual override fun createDriver(schema: SqlSchema<QueryResult.Value<Unit>>, name: String): SqlDriver {
        return NativeSqliteDriver(schema, name)
    }

    actual override fun createAsyncDriver(schema: SqlSchema<QueryResult.AsyncValue<Unit>>, name: String): SqlDriver {
        return NativeSqliteDriver(schema.synchronous(), name)
    }
}
