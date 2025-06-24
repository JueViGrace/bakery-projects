package com.bakery.api.products.repository

import com.bakery.Bakery_product
import com.bakery.database.source.DataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlin.coroutines.CoroutineContext
import com.bakery.Bakery_product as BakeryProduct

interface ProductRepository : DataSource<BakeryProduct> {
    suspend fun findOneByName(name: String): BakeryProduct?
    suspend fun updateStock(id: Int, stock: Int, quantity: Int): BakeryProduct?
}

class ProductRepositoryImpl(
    private val scope: CoroutineScope,
    private val coroutineContext: CoroutineContext
) : ProductRepository {
    override suspend fun findAll(): List<BakeryProduct> {
        return scope.async(coroutineContext) {
            dbHelper.withDatabase { db ->
                db.transactionWithResult {
                    db.bakeryProductQueries
                        .findAll()
                        .executeAsList()
                }
            }
        }.await()
    }

    override suspend fun findOneById(id: Int): BakeryProduct? {
        return scope.async(coroutineContext) {
            dbHelper.withDatabase { db ->
                db.transactionWithResult {
                    db.bakeryProductQueries
                        .findOneById(id)
                        .executeAsOneOrNull()
                }
            }
        }.await()
    }

    override suspend fun findOneByName(name: String): BakeryProduct? {
        return scope.async(coroutineContext) {
            dbHelper.withDatabase { db ->
                db.transactionWithResult {
                    db.bakeryProductQueries
                        .findOneByName(name)
                        .executeAsOneOrNull()
                }
            }
        }.await()
    }

    override suspend fun updateStock(id: Int, stock: Int, quantity: Int): Bakery_product? {
        return scope.async(coroutineContext) {
            dbHelper.withDatabase { db ->
                db.transactionWithResult {
                    db.bakeryProductQueries
                        .updateStock(
                            id = id,
                            stock = stock - quantity
                        )
                        .executeAsOneOrNull()
                }
            }
        }.await()
    }

    override suspend fun insert(e: BakeryProduct): BakeryProduct? {
        return scope.async(coroutineContext) {
            dbHelper.withDatabase { db ->
                db.transactionWithResult {
                    db.bakeryProductQueries
                        .insert(e)
                        .executeAsOneOrNull()
                }
            }
        }.await()
    }

    override suspend fun update(id: Int, e: BakeryProduct): BakeryProduct? {
        return scope.async(coroutineContext) {
            dbHelper.withDatabase { db ->
                db.transactionWithResult {
                    db.bakeryProductQueries
                        .update(
                            id = id,
                            name = e.name,
                            description = e.description,
                            price = e.price,
                            category = e.category,
                            stock = e.stock,
                            image = e.image
                        )
                        .executeAsOneOrNull()
                }
            }
        }.await()
    }

    override suspend fun softDelete(id: Int): BakeryProduct? {
        return scope.async(coroutineContext) {
            dbHelper.withDatabase { db ->
                db.transactionWithResult {
                    db.bakeryProductQueries
                        .softDelete(id)
                        .executeAsOneOrNull()
                }
            }
        }.await()
    }
}
