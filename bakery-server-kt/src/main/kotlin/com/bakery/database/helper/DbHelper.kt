package com.bakery.database.helper

import app.cash.sqldelight.db.SqlDriver
import com.bakery.BakeryDb
import com.bakery.database.driver.DriverFactory
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

object DbHelper {
    private val driver: SqlDriver = DriverFactory.initDatabase()

    private var db: BakeryDb? = null

    private val mutex = Mutex()

    suspend fun <Result : Any?> withDatabase(block: (BakeryDb) -> Result): Result = mutex.withLock {
        if (db == null) {
            db = createDb()
        }

        return@withLock block(db!!)
    }

    private fun createDb(): BakeryDb {
        return BakeryDb(driver = driver)
    }
}
