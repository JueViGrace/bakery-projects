package com.bakery.database.source

import com.bakery.database.helper.DbHelper

interface DataSource<T> {
    val dbHelper: DbHelper
        get() = DbHelper

    suspend fun findAll(): List<T> = emptyList()
    suspend fun findOneById(id: Int): T? = null
    suspend fun insert(e: T): T? = null
    suspend fun update(id: Int, e: T): T? = null
    suspend fun softDelete(id: Int): T? = null
    suspend fun deleteById(id: Int): T? = null
    suspend fun delete(): T? = null
}
