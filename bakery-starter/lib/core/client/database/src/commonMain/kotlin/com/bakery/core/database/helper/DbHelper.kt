package com.bakery.core.database.helper

import app.cash.sqldelight.Query
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneOrNull
import com.bakery.core.database.BakeryCliDb
import com.bakery.core.database.driver.DriverFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.coroutines.CoroutineContext

class DbHelper(
    private val driver: DriverFactory,
    private val coroutineContext: CoroutineContext,
) {
    private var db: BakeryCliDb? = null
    private val mutex = Mutex()

    suspend fun <Result> withDatabase(block: suspend DbHelper.(BakeryCliDb) -> Result): Result =
        mutex.withLock {
            if (db == null) {
                db = createDb()
            }

            return@withLock block(db!!)
        }

    private suspend fun createDb(): BakeryCliDb = BakeryCliDb(driver.createDriver())

    fun <T : Any> executeOne(query: Query<T>): T? = query.executeAsOneOrNull()

    fun <T : Any> executeOneAsFlow(query: Query<T>): Flow<T?> = query.asFlow().mapToOneOrNull(coroutineContext)

    fun <T : Any> executeList(query: Query<T>): List<T> = query.executeAsList()

    fun <T : Any> executeListAsFlow(query: Query<T>): Flow<List<T>> = query.asFlow().mapToList(coroutineContext)
}
