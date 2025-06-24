package com.bakery.api.auth.repository

import com.bakery.Bakery_token as BakeryToken
import com.bakery.database.source.DataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlin.coroutines.CoroutineContext

interface RefreshTokenRepository : DataSource<BakeryToken> {
    suspend fun findEmailByToken(token: String): BakeryToken?
    suspend fun findTokenByEmail(email: String): BakeryToken?
    suspend fun updateToken(e: BakeryToken): BakeryToken?
}

class RefreshTokenRepositoryImpl(
    private val scope: CoroutineScope,
    private val coroutineContext: CoroutineContext
) : RefreshTokenRepository {
    override suspend fun findEmailByToken(token: String): BakeryToken? {
        return scope.async {
            dbHelper.withDatabase { db ->
                db.bakeryTokensQueries
                    .findEmailByToken(token)
                    .executeAsOneOrNull()
            }
        }.await()
    }

    override suspend fun findTokenByEmail(email: String): BakeryToken? {
        return scope.async {
            dbHelper.withDatabase { db ->
                db.bakeryTokensQueries
                    .findTokenByEmail(email)
                    .executeAsOneOrNull()
            }
        }.await()
    }

    override suspend fun insert(e: BakeryToken): BakeryToken? {
        return scope.async(coroutineContext) {
            dbHelper.withDatabase { db ->
                db.transactionWithResult {
                    db.bakeryTokensQueries
                        .insert(e)
                        .executeAsOneOrNull()
                }
            }
        }.await()
    }

    override suspend fun updateToken(e: BakeryToken): BakeryToken? {
        return scope.async {
            dbHelper.withDatabase { db ->
                db.transactionWithResult {
                    db.bakeryTokensQueries
                        .update(
                            token = e.token,
                            email = e.user_email
                        )
                        .executeAsOneOrNull()
                }
            }
        }.await()
    }
}
