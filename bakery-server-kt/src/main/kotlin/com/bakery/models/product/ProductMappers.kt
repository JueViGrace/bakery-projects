package com.bakery.models.product

import io.ktor.server.http.toHttpDateString
import com.bakery.Bakery_product as BakeryProduct

fun BakeryProduct.toDto(): ProductDto = ProductDto(
    productId = id,
    name = name,
    description = description,
    price = price,
    category = category,
    stock = stock,
    image = image,
    createdAt = created_at?.toHttpDateString(),
    updatedAt = updated_at?.toHttpDateString(),
    deletedAt = deleted_at?.toHttpDateString()
)

fun ProductDto.toDatabase(): BakeryProduct = BakeryProduct(
    id = productId,
    name = name,
    description = description,
    price = price,
    category = category,
    stock = stock,
    image = image,
    created_at = null,
    updated_at = null,
    deleted_at = null
)

fun CreateProductDto.toDatabase(): BakeryProduct = BakeryProduct(
    id = 0,
    name = name,
    description = description,
    price = price,
    category = category,
    stock = stock,
    image = image,
    created_at = null,
    updated_at = null,
    deleted_at = null
)
