package com.bakery.product.presentation.state

import com.bakery.types.product.Product

data class ProductsListState(val products: List<Product> = emptyList())
