package com.bakery.models.product

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductDto(
    @SerialName("product_id")
    val productId: Int,
    @SerialName("name")
    val name: String,
    @SerialName("description")
    val description: String,
    @SerialName("price")
    val price: Double,
    @SerialName("category")
    val category: String,
    @SerialName("stock")
    val stock: Int,
    @SerialName("image")
    val image: String,
    @SerialName("created_at")
    val createdAt: String? = null,
    @SerialName("updated_at")
    val updatedAt: String? = null,
    @SerialName("deleted_at")
    val deletedAt: String? = null,
)

@Serializable
data class CreateProductDto(
    @SerialName("name")
    val name: String,
    @SerialName("description")
    val description: String,
    @SerialName("price")
    val price: Double,
    @SerialName("category")
    val category: String,
    @SerialName("stock")
    val stock: Int,
    @SerialName("image")
    val image: String,
)
