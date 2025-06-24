package com.bakery.api.cart.repository

import com.bakery.Bakery_cart
import com.bakery.FindCartById
import com.bakery.database.source.DataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlin.coroutines.CoroutineContext
import com.bakery.Bakery_cart as BakeryCart
import com.bakery.Bakery_cart_products as BakeryCartWithProducts
import com.bakery.Bakery_product as BakeryProduct

interface CartRepository : DataSource<BakeryCart> {
    suspend fun findCartById(id: Int): List<FindCartById>
    suspend fun addItemsToCart(id: Int, e: List<BakeryCartWithProducts>): BakeryCart?
    suspend fun removeItem(id: Int, e: List<BakeryCartWithProducts>): BakeryCart?
    suspend fun removeAll(id: Int): BakeryCart?
}

class CartRepositoryImpl(
    private val scope: CoroutineScope,
    private val coroutineContext: CoroutineContext
) : CartRepository {
    override suspend fun findCartById(id: Int): List<FindCartById> {
        return scope.async(coroutineContext) {
            dbHelper.withDatabase { db ->
                db.transactionWithResult {
                    db.bakeryCartQueries
                        .findCartById(id)
                        .executeAsList()
                }
            }
        }.await()
    }

    override suspend fun addItemsToCart(id: Int, e: List<BakeryCartWithProducts>): BakeryCart? {
        return scope.async {
            dbHelper.withDatabase { db ->
                db.transactionWithResult {
                    val totalAmount = calculateTotalAmount(e) { productId ->
                        db.bakeryProductQueries
                            .findOneById(productId)
                            .executeAsOneOrNull()
                    }

                    e.forEach {
                        db.bakeryCartWithProductsQueries
                            .insert(it)
                            .executeAsOneOrNull()
                    }

                    db.bakeryCartQueries
                        .update(
                            id = id,
                            totalAmount = totalAmount
                        )
                        .executeAsOneOrNull()
                }
            }
        }.await()
    }

    override suspend fun removeItem(id: Int, e: List<BakeryCartWithProducts>): BakeryCart? {
        return scope.async {
            dbHelper.withDatabase { db ->
                db.transactionWithResult {
                    e.map {
                        db.bakeryCartWithProductsQueries
                            .deleteProduct(it.cart_id, it.product_id)
                    }

                    val productsInCart = db.bakeryCartWithProductsQueries.findAllById(id).executeAsList()

                    val totalAmount = calculateTotalAmount(productsInCart) { productId ->
                        db.bakeryProductQueries
                            .findOneById(productId)
                            .executeAsOneOrNull()
                    }

                    db.bakeryCartQueries
                        .update(
                            id = id,
                            totalAmount = totalAmount
                        )
                        .executeAsOneOrNull()
                }
            }
        }.await()
    }

    override suspend fun removeAll(id: Int): Bakery_cart? {
        return scope.async {
            dbHelper.withDatabase { db ->
                db.transactionWithResult {
                    db.bakeryCartWithProductsQueries.deleteProducts(id)

                    db.bakeryCartQueries
                        .update(
                            id = id,
                            totalAmount = 0.0
                        )
                        .executeAsOneOrNull()
                }
            }
        }.await()
    }

    private fun calculateTotalAmount(e: List<BakeryCartWithProducts>, products: (Int) -> BakeryProduct?): Double {
        return e.sumOf { bp ->
            val product = products(bp.product_id)

            if (product != null) {
                bp.quantity * product.price
            } else {
                0.0
            }
        }
    }

    override suspend fun insert(e: BakeryCart): BakeryCart? {
        return scope.async(coroutineContext) {
            dbHelper.withDatabase { db ->
                db.transactionWithResult {
                    db.bakeryCartQueries
                        .insert(e)
                        .executeAsOneOrNull()
                }
            }
        }.await()
    }

    override suspend fun update(id: Int, e: BakeryCart): BakeryCart? {
        return scope.async(coroutineContext) {
            dbHelper.withDatabase { db ->
                db.transactionWithResult {
                    db.bakeryCartQueries
                        .update(
                            id = id,
                            totalAmount = e.total_amount
                        )
                        .executeAsOneOrNull()
                }
            }
        }.await()
    }

    override suspend fun deleteById(id: Int): BakeryCart? {
        return scope.async(coroutineContext) {
            dbHelper.withDatabase { db ->
                db.transactionWithResult {
                    db.bakeryCartQueries
                        .delete(id = id)
                        .executeAsOneOrNull()
                }
            }
        }.await()
    }
}
