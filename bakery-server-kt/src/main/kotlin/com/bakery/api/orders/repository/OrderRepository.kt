package com.bakery.api.orders.repository

import com.bakery.Bakery_order as BakeryOrder
import com.bakery.Bakery_order_products as BakeryOrderWithProducts
import com.bakery.FindAllByUser
import com.bakery.FindAllOrders
import com.bakery.FindOneOrder
import com.bakery.database.source.DataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlin.coroutines.CoroutineContext

interface OrderRepository : DataSource<BakeryOrder> {
    suspend fun findAllWithRelation(): List<FindAllOrders>
    suspend fun findAllByUser(id: Int): List<FindAllByUser>
    suspend fun findOneWithRelation(id: Int): List<FindOneOrder>
    suspend fun createOrder(order: BakeryOrder, orderWithProducts: List<BakeryOrderWithProducts>): BakeryOrder?
    suspend fun updateStatus(id: Int, status: String): BakeryOrder?
}

class OrderRepositoryImpl(
    private val scope: CoroutineScope,
    private val coroutineContext: CoroutineContext
) : OrderRepository {
    override suspend fun findAllWithRelation(): List<FindAllOrders> {
        return scope.async(coroutineContext) {
            dbHelper.withDatabase { db ->
                db.transactionWithResult {
                    db.bakeryOrderQueries
                        .findAllOrders()
                        .executeAsList()
                }
            }
        }.await()
    }

    override suspend fun findAllByUser(id: Int): List<FindAllByUser> {
        return scope.async(coroutineContext) {
            dbHelper.withDatabase { db ->
                db.transactionWithResult {
                    db.bakeryOrderQueries
                        .findAllByUser(id)
                        .executeAsList()
                }
            }
        }.await()
    }

    override suspend fun findOneWithRelation(id: Int): List<FindOneOrder> {
        return scope.async(coroutineContext) {
            dbHelper.withDatabase { db ->
                db.transactionWithResult {
                    db.bakeryOrderQueries
                        .findOneOrder(id)
                        .executeAsList()
                }
            }
        }.await()
    }

    override suspend fun findOneById(id: Int): BakeryOrder? {
        return dbHelper.withDatabase { db ->
            db.transactionWithResult {
                db.bakeryOrderQueries
                    .findOneById(id)
                    .executeAsOneOrNull()
            }
        }
    }

    override suspend fun createOrder(
        order: BakeryOrder,
        orderWithProducts: List<BakeryOrderWithProducts>
    ): BakeryOrder? {
        return scope.async(coroutineContext) {
            dbHelper.withDatabase { db ->
                db.transactionWithResult {
                    val savedOrder = db.bakeryOrderQueries
                        .insert(order)
                        .executeAsOneOrNull()

                    if (savedOrder != null) {
                        orderWithProducts.forEach { orderProducts ->
                            db.bakeryOrderWithProductsQueries
                                .insert(orderProducts.copy(order_id = savedOrder.id))
                                .executeAsList()
                        }
                    }

                    savedOrder
                }
            }
        }.await()
    }

    override suspend fun updateStatus(id: Int, status: String): BakeryOrder? {
        return dbHelper.withDatabase { db ->
            db.transactionWithResult {
                db.bakeryOrderQueries
                    .updateStatus(
                        id = id,
                        status = status
                    )
                    .executeAsOneOrNull()
            }
        }
    }

    override suspend fun softDelete(id: Int): BakeryOrder? {
        return dbHelper.withDatabase { db ->
            db.transactionWithResult {
                db.bakeryOrderQueries
                    .softDelete(id)
                    .executeAsOneOrNull()
            }
        }
    }
}
