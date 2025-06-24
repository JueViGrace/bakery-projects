package com.bakery.web.view.components

import com.bakery.models.product.ProductDto
import kotlinx.html.FlowContent
import kotlinx.html.article
import kotlinx.html.classes
import kotlinx.html.div
import kotlinx.html.h1
import kotlinx.html.img

fun FlowContent.products(products: List<ProductDto>) {
    article {
        classes = setOf("flex", "flex-row", "flex-wrap", "justify-center", "items-center", "gap-2", "p-2")
        products.forEach { product ->
            div {
                classes = setOf("p-2")

                img {
                    src = product.image
                    alt = product.name
                }

                h1 {
                    classes=setOf("text-xl", "text-gray-200")
                    +product.name
                }
            }
        }
    }
}
