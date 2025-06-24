package com.bakery.models.order

import com.bakery.models.product.ProductDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OrderDto(
    @SerialName("order_id")
    val orderId: Int,
    @SerialName("total_amount")
    val totalAmount: Double,
    @SerialName("payment_method")
    val paymentMethod: String,
    @SerialName("status")
    val status: String,
    @SerialName("user_id")
    val userId: Int,
    @SerialName("created_at")
    val createdAt: String? = null,
    @SerialName("updated_at")
    val updatedAt: String? = null,
    @SerialName("deleted_at")
    val deletedAt: String? = null,
    @SerialName("order_items")
    val orderItems: List<OrderItems> = emptyList()
)

@Serializable
data class OrderItems(
    @SerialName("quantity")
    val quantity: Int,
    @SerialName("products")
    val product: ProductDto
)

enum class OrderStatus(
    val value: String
) {
    PLACED("placed"),
    PAID("paid"),
    PROCESSING("processing"),
    DELIVERED("delivered")
}

@Serializable
data class UpdateOrderStatus(
    @SerialName("order_id")
    val orderId: Int,
    @SerialName("status")
    val status: String
)
