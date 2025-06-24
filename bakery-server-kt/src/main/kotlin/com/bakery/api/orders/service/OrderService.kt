package com.bakery.api.orders.service

import com.bakery.api.orders.repository.OrderRepository
import com.bakery.models.order.OrderDto
import com.bakery.models.order.OrderStatus
import com.bakery.models.order.UpdateOrderStatus
import com.bakery.models.order.toDto
import com.bakery.models.order.toDtoUser
import com.bakery.models.response.AppResponse
import com.bakery.models.response.DefaultHttpResponse
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class OrderService(
    private val orderRepository: OrderRepository,
    private val coroutineContext: CoroutineContext
) {
    suspend fun getAllOrders(): AppResponse<List<OrderDto>> {
        return try {
            withContext(coroutineContext) {
                val orders = orderRepository.findAllWithRelation()
                    .toDto()
                    .ifEmpty {
                        return@withContext DefaultHttpResponse.notFound(error = "Orders not found")
                    }

                DefaultHttpResponse.ok(orders)
            }
        } catch (e: Exception) {
            error(e)
        }
    }

    suspend fun getUserOrders(id: Int): AppResponse<List<OrderDto>> {
        return try {
            withContext(coroutineContext) {
                val userOrders = orderRepository.findAllByUser(id)
                    .toDtoUser()
                    .ifEmpty {
                        return@withContext DefaultHttpResponse.notFound(error = "Orders not found")
                    }

                DefaultHttpResponse.ok(userOrders)
            }
        } catch (e: Exception) {
            error(e)
        }
    }

    suspend fun getOneOrderById(id: Int): AppResponse<OrderDto> {
        return try {
            withContext(coroutineContext) {
                val order = orderRepository.findOneWithRelation(id).toDto()
                    ?: return@withContext DefaultHttpResponse.badRequest("Unable to retrieve order")

                DefaultHttpResponse.ok(order)
            }
        } catch (e: Exception) {
            error(e)
        }
    }

    suspend fun updateOrderStatus(updateOrderStatus: UpdateOrderStatus): AppResponse<String> {
        return try {
            withContext(coroutineContext) {
                findOrder(updateOrderStatus.orderId)
                    ?: return@withContext DefaultHttpResponse.notFound(
                        error = "Order with id ${updateOrderStatus.orderId} was not found"
                    )

                if (!OrderStatus.entries.map { it.value == updateOrderStatus.status }.contains(true)) {
                    return@withContext DefaultHttpResponse.badRequest(message = "Invalid status")
                }

                orderRepository.updateStatus(
                    id = updateOrderStatus.orderId,
                    status = updateOrderStatus.status
                )
                    ?: return@withContext DefaultHttpResponse.badRequest(message = "Unable to update status")

                DefaultHttpResponse.ok("Order updated!")
            }
        } catch (e: Exception) {
            error(e)
        }
    }

    suspend fun softDeleteOrder(id: Int): AppResponse<String> {
        return try {
            withContext(coroutineContext) {
                findOrder(id)
                    ?: return@withContext DefaultHttpResponse.notFound(error = "Order with id $id not found")

                orderRepository.softDelete(id)

                DefaultHttpResponse.noContent("Order deleted")
            }
        } catch (e: Exception) {
            error(e)
        }
    }

    private suspend fun findOrder(id: Int): OrderDto? {
        return withContext(coroutineContext) {
            orderRepository.findOneById(id)?.toDto()
        }
    }
}
