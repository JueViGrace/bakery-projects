package com.bakery.database.driver

import android.content.Context
import androidx.sqlite.db.SupportSQLiteDatabase
import app.cash.sqldelight.async.coroutines.synchronous
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import app.cash.sqldelight.driver.android.AndroidSqliteDriver

/*
* Driver factory Android implementation.
* */
actual class DriverFactoryImpl(private val context: Context) : DriverFactory {
    actual override fun createDriver(schema: SqlSchema<QueryResult.Value<Unit>>, name: String): SqlDriver = AndroidSqliteDriver(
        schema = schema,
        context = context,
        name = name,
        callback = object : AndroidSqliteDriver.Callback(schema) {
            override fun onOpen(db: SupportSQLiteDatabase) {
                db.setForeignKeyConstraintsEnabled(true)
                super.onOpen(db)
            }
        },
    )

    actual override fun createAsyncDriver(schema: SqlSchema<QueryResult.AsyncValue<Unit>>, name: String): SqlDriver = AndroidSqliteDriver(
        schema = schema.synchronous(),
        context = context,
        name = name,
        callback = object : AndroidSqliteDriver.Callback(schema.synchronous()) {
            override fun onOpen(db: SupportSQLiteDatabase) {
                db.setForeignKeyConstraintsEnabled(true)
                super.onOpen(db)
            }
        },
    )
}
