package com.bakery.models.cart

import com.bakery.models.product.ProductDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CartDto(
    @SerialName("cart_id")
    val id: Int = 0,
    @SerialName("total_amount")
    val totalAmount: Double = 0.0,
    @SerialName("items")
    val items: List<CartItemsDto> = emptyList()
)

@Serializable
data class CartItemsDto(
    @SerialName("quantity")
    val quantity: Int,
    @SerialName("product")
    val productDto: ProductDto
)

@Serializable
data class AddToCartDto(
    @SerialName("cart_id")
    val cartId: Int,
    @SerialName("items")
    val items: List<AddToCartItemsDto>
)

@Serializable
data class AddToCartItemsDto(
    @SerialName("quantity")
    val quantity: Int,
    @SerialName("product_id")
    val productId: Int,
)
