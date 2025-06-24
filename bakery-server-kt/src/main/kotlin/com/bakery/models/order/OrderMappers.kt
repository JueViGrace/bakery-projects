package com.bakery.models.order

import com.bakery.FindAllByUser
import com.bakery.FindAllOrders
import com.bakery.FindOneOrder
import com.bakery.models.product.ProductDto
import io.ktor.server.http.toHttpDateString
import com.bakery.Bakery_order as BakeryOrder

fun BakeryOrder.toDto(): OrderDto = OrderDto(
    orderId = id,
    totalAmount = total_amount,
    paymentMethod = payment_method,
    status = status,
    userId = user_id,
    createdAt = created_at?.toHttpDateString(),
    updatedAt = updated_at?.toHttpDateString(),
    deletedAt = deleted_at?.toHttpDateString(),
)

fun List<FindAllOrders>.toDto(): List<OrderDto> {
    val orderItems = this.groupBy { order ->
        OrderDto(
            orderId = order.id,
            totalAmount = order.total_amount,
            paymentMethod = order.payment_method,
            status = order.status,
            userId = order.user_id,
            createdAt = order.created_at?.toHttpDateString(),
            updatedAt = order.updated_at?.toHttpDateString(),
            deletedAt = order.deleted_at?.toHttpDateString(),
        )
    }

    val orderDto: MutableList<OrderDto> = mutableListOf()

    orderItems.forEach { (key, value) ->
        orderDto.add(
            OrderDto(
                orderId = key.orderId,
                totalAmount = key.totalAmount,
                paymentMethod = key.paymentMethod,
                status = key.status,
                userId = key.userId,
                createdAt = key.createdAt,
                updatedAt = key.updatedAt,
                deletedAt = key.deletedAt,
                orderItems = value.map {
                    OrderItems(
                        quantity = it.quantity ?: 0,
                        product = ProductDto(
                            productId = it.product_id ?: 0,
                            name = it.name ?: "",
                            description = it.description ?: "",
                            price = it.price ?: 0.0,
                            category = it.category ?: "",
                            stock = it.stock ?: 0,
                            image = it.image ?: "",
                            createdAt = it.created_at?.toHttpDateString(),
                            updatedAt = it.updated_at?.toHttpDateString(),
                            deletedAt = it.deleted_at?.toHttpDateString()
                        )
                    )
                }
            )
        )
    }

    return orderDto.toList()
}

fun List<FindAllByUser>.toDtoUser(): List<OrderDto> {
    val orderItems = this.groupBy { order ->
        OrderDto(
            orderId = order.id,
            totalAmount = order.total_amount,
            paymentMethod = order.payment_method,
            status = order.status,
            userId = order.user_id,
            createdAt = order.created_at?.toHttpDateString(),
            updatedAt = order.updated_at?.toHttpDateString(),
            deletedAt = order.deleted_at?.toHttpDateString(),
        )
    }

    val orderDto: MutableList<OrderDto> = mutableListOf()

    orderItems.forEach { (key, value) ->
        orderDto.add(
            OrderDto(
                orderId = key.orderId,
                totalAmount = key.totalAmount,
                paymentMethod = key.paymentMethod,
                status = key.status,
                userId = key.userId,
                createdAt = key.createdAt,
                updatedAt = key.updatedAt,
                deletedAt = key.deletedAt,
                orderItems = value.map {
                    OrderItems(
                        quantity = it.quantity ?: 0,
                        product = ProductDto(
                            productId = it.product_id ?: 0,
                            name = it.name ?: "",
                            description = it.description ?: "",
                            price = it.price ?: 0.0,
                            category = it.category ?: "",
                            stock = it.stock ?: 0,
                            image = it.image ?: "",
                            createdAt = it.created_at?.toHttpDateString(),
                            updatedAt = it.updated_at?.toHttpDateString(),
                            deletedAt = it.deleted_at?.toHttpDateString()
                        )
                    )
                }
            )
        )
    }

    return orderDto.toList()
}

fun List<FindOneOrder>.toDto(): OrderDto? {
    val orderItems = this.groupBy { order ->
        OrderDto(
            orderId = order.id,
            totalAmount = order.total_amount,
            paymentMethod = order.payment_method,
            status = order.status,
            userId = order.user_id,
            createdAt = order.created_at?.toHttpDateString(),
            updatedAt = order.updated_at?.toHttpDateString(),
            deletedAt = order.deleted_at?.toHttpDateString(),
        )
    }

    var orderDto: OrderDto? = null

    orderItems.forEach { (key, value) ->
        orderDto = OrderDto(
            orderId = key.orderId,
            totalAmount = key.totalAmount,
            paymentMethod = key.paymentMethod,
            status = key.status,
            userId = key.userId,
            createdAt = key.createdAt,
            updatedAt = key.updatedAt,
            deletedAt = key.deletedAt,
            orderItems = value.map {
                OrderItems(
                    quantity = it.quantity ?: 0,
                    product = ProductDto(
                        productId = it.product_id ?: 0,
                        name = it.name ?: "",
                        description = it.description ?: "",
                        price = it.price ?: 0.0,
                        category = it.category ?: "",
                        stock = it.stock ?: 0,
                        image = it.image ?: "",
                        createdAt = it.created_at?.toHttpDateString(),
                        updatedAt = it.updated_at?.toHttpDateString(),
                        deletedAt = it.deleted_at?.toHttpDateString()
                    )
                )
            }
        )
    }

    return orderDto
}
