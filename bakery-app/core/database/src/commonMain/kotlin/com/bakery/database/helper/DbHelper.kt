package com.bakery.database.helper

import app.cash.sqldelight.Query
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneOrNull
import com.bakery.util.Logs
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

/*
* Generic helper interface to make and manage database operations.
* */
interface DbHelper<T> {
    val db: T

    val coroutineContext: CoroutineContext
        get() = Dispatchers.IO
    val scope: CoroutineScope
        get() = CoroutineScope(coroutineContext + SupervisorJob())
    val mutex: Mutex
        get() = Mutex()

    fun <R : Any> executeOne(query: Query<R>): R? {
        return query.executeAsOneOrNull()
    }

    fun <R : Any> executeOneAsFlow(query: Query<R>): Flow<R?> {
        return query.asFlow().mapToOneOrNull(coroutineContext)
    }

    fun <R : Any> executeList(query: Query<R>): List<R> {
        return query.executeAsList()
    }

    fun <R : Any> executeListAsFlow(query: Query<R>): Flow<List<R>> {
        return query.asFlow().mapToList(coroutineContext)
    }

    suspend fun <R : Any> withDatabase(
        block: suspend DbHelper<T>.() -> R?,
    ): R? {
        return withContext(coroutineContext) {
            try {
                mutex.withLock {
                    block()
                }
            } catch (e: Exception) {
                coroutineContext.ensureActive()
                Logs.error(tag = this::class.simpleName ?: "DbHelper", msg = "Error: ${e.message}")
                null
            }
        }
    }
}
