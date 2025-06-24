package com.bakery.api.users.repository

import com.bakery.database.source.DataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlin.coroutines.CoroutineContext
import com.bakery.Bakery_user as BakeryUser

interface UserRepository : DataSource<BakeryUser> {
    suspend fun findOneByEmail(email: String): BakeryUser? = null
}

class UserRepositoryImpl(
    private val scope: CoroutineScope,
    private val coroutineContext: CoroutineContext
) : UserRepository {
    override suspend fun findAll(): List<BakeryUser> {
        return scope.async(coroutineContext) {
            dbHelper.withDatabase { db ->
                db.transactionWithResult {
                    db.bakeryUserQueries
                        .findAll()
                        .executeAsList()
                }
            }
        }.await()
    }

    override suspend fun findOneById(id: Int): BakeryUser? {
        return scope.async(coroutineContext) {
            dbHelper.withDatabase { db ->
                db.transactionWithResult {
                    db.bakeryUserQueries
                        .findOneById(id)
                        .executeAsOneOrNull()
                }
            }
        }.await()
    }

    override suspend fun findOneByEmail(email: String): BakeryUser? {
        return scope.async {
            dbHelper.withDatabase { db ->
                db.transactionWithResult {
                    db.bakeryUserQueries
                        .findOneByEmail(email)
                        .executeAsOneOrNull()
                }
            }
        }.await()
    }

    override suspend fun insert(e: BakeryUser): BakeryUser? {
        return scope.async {
            dbHelper.withDatabase { db ->
                db.transactionWithResult {
                    db.bakeryUserQueries
                        .insert(e)
                        .executeAsOneOrNull()
                }
            }
        }.await()
    }

    override suspend fun update(id: Int, e: BakeryUser): BakeryUser? {
        return scope.async {
            dbHelper.withDatabase { db ->
                db.transactionWithResult {
                    db.bakeryUserQueries
                        .update(
                            id = id,
                            name = e.name,
                            lastname = e.lastname,
                            birth_date = e.birth_date,
                            phone = e.phone,
                        )
                        .executeAsOneOrNull()
                }
            }
        }.await()
    }

    override suspend fun softDelete(id: Int): BakeryUser? {
        return scope.async {
            dbHelper.withDatabase { db ->
                db.transactionWithResult {
                    db.bakeryUserQueries
                        .softDelete(id = id)
                        .executeAsOneOrNull()
                }
            }
        }.await()
    }
}
