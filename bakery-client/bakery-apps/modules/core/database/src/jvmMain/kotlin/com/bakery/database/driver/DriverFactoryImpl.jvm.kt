package com.bakery.database.driver

import app.cash.sqldelight.async.coroutines.synchronous
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import java.util.Properties

/*
* Driver factory JVM implementation.
* */
actual class DriverFactoryImpl : DriverFactory {
    actual override fun createDriver(schema: SqlSchema<QueryResult.Value<Unit>>, name: String): SqlDriver {
        return JdbcSqliteDriver(
            url = "jdbc:sqlite:$name",
            properties = Properties().apply { put("foreign_keys", "true") },
            schema = schema
        )
    }

    actual override fun createAsyncDriver(schema: SqlSchema<QueryResult.AsyncValue<Unit>>, name: String): SqlDriver {
        return JdbcSqliteDriver(
            url = "jdbc:sqlite:$name",
            properties = Properties().apply { put("foreign_keys", "true") },
            schema = schema.synchronous()
        )
    }
}
