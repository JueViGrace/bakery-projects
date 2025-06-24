package com.bakery.models.cart

import com.bakery.FindCartById
import com.bakery.models.order.OrderStatus
import com.bakery.models.product.ProductDto
import io.ktor.server.http.toHttpDateString
import com.bakery.Bakery_cart as BakeryCart
import com.bakery.Bakery_cart_products as BakeryCartWithProducts
import com.bakery.Bakery_order as BakeryOrder
import com.bakery.Bakery_order_products as BakeryOrderWithProducts

fun CartDto.toDatabase(): BakeryCart = BakeryCart(
    id = id,
    total_amount = totalAmount
)

fun CartDto.toDatabaseOrder(): BakeryOrder = BakeryOrder(
    id = 0,
    total_amount = totalAmount,
    payment_method = "cash",
    status = OrderStatus.PLACED.value,
    user_id = id,
    created_at = null,
    updated_at = null,
    deleted_at = null
)

fun CartDto.toOrderProducts(): List<BakeryOrderWithProducts> {
    return items.map { item ->
        BakeryOrderWithProducts(
            order_id = 0,
            product_id = item.productDto.productId,
            price = item.productDto.price,
            quantity = item.quantity
        )
    }
}

fun CartDto.itemsToDatabase(): List<BakeryCartWithProducts> {
    return items.map { item ->
        BakeryCartWithProducts(
            cart_id = id,
            product_id = item.productDto.productId,
            quantity = item.quantity
        )
    }
}

fun List<AddToCartItemsDto>.toDatabase(cartId: Int): List<BakeryCartWithProducts> {
    return this.map { item ->
        BakeryCartWithProducts(
            cart_id = cartId,
            product_id = item.productId,
            quantity = item.quantity,
        )
    }
}

fun List<FindCartById>.toDto(): CartDto {
    val cartItems = this.groupBy { cart ->
        CartDto(
            id = cart.id,
            totalAmount = cart.total_amount,
            items = emptyList()
        )
    }

    var cartDto = CartDto()

    cartItems.forEach { (key, value) ->
        when {
            value.map { it.product_id }.first() == null -> {
                cartDto = cartDto.copy(
                    id = key.id,
                    totalAmount = key.totalAmount,
                    items = key.items
                )
            }

            else -> {
                cartDto = cartDto.copy(
                    id = key.id,
                    totalAmount =
                    value.sumOf { (it.quantity ?: 0) * (it.price ?: 0.0) },
                    items =
                    value.map { product ->
                        CartItemsDto(
                            quantity = product.quantity ?: 0,
                            productDto = ProductDto(
                                productId = product.product_id ?: 0,
                                name = product.name ?: "",
                                description = product.description ?: "",
                                price = product.price ?: 0.0,
                                category = product.category ?: "",
                                stock = product.stock ?: 0,
                                image = product.image ?: "",
                                createdAt = product.created_at?.toHttpDateString(),
                                updatedAt = product.updated_at?.toHttpDateString(),
                                deletedAt = product.deleted_at?.toHttpDateString()
                            )
                        )
                    }
                )
            }
        }
    }

    return cartDto
}
