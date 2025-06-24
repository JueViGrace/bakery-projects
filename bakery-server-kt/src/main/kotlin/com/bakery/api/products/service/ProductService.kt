package com.bakery.api.products.service

import com.bakery.api.products.repository.ProductRepository
import com.bakery.models.product.CreateProductDto
import com.bakery.models.product.ProductDto
import com.bakery.models.product.toDatabase
import com.bakery.models.product.toDto
import com.bakery.models.response.AppResponse
import com.bakery.models.response.DefaultHttpResponse
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class ProductService(
    private val productRepository: ProductRepository,
    private val coroutineContext: CoroutineContext
) {
    suspend fun getAllProducts(): AppResponse<List<ProductDto>> {
        return try {
            withContext(coroutineContext) {
                val products = productRepository.findAll().map { it.toDto() }

                if (products.isNotEmpty()) {
                    DefaultHttpResponse.ok(products)
                } else {
                    DefaultHttpResponse.notFound("No products found")
                }
            }
        } catch (e: Exception) {
            error(e)
        }
    }

    suspend fun getOneProductById(id: Int): AppResponse<ProductDto?> {
        return try {
            withContext(coroutineContext) {
                val product = findProduct(id)

                if (product != null) {
                    DefaultHttpResponse.ok(product)
                } else {
                    DefaultHttpResponse.notFound("Product with id $id was not found")
                }
            }
        } catch (e: Exception) {
            error(e)
        }
    }

    suspend fun getOneProductByName(name: String): AppResponse<ProductDto?> {
        return try {
            withContext(coroutineContext) {
                val product = productRepository.findOneByName(name)?.toDto()

                if (product != null) {
                    DefaultHttpResponse.ok(product)
                } else {
                    DefaultHttpResponse.notFound("Product with name $name was not found")
                }
            }
        } catch (e: Exception) {
            error(e)
        }
    }

    suspend fun createProduct(createProductDto: CreateProductDto): AppResponse<ProductDto?> {
        return try {
            withContext(coroutineContext) {
                val existingProduct = productRepository.findOneByName(createProductDto.name)

                if (existingProduct != null) {
                    return@withContext DefaultHttpResponse.conflict(
                        "A product with name ${createProductDto.name} already exists"
                    )
                }

                val savedProduct = productRepository.insert(createProductDto.toDatabase())?.toDto()

                if (savedProduct != null) {
                    DefaultHttpResponse.ok(savedProduct)
                } else {
                    DefaultHttpResponse.badRequest("Unable to create product")
                }
            }
        } catch (e: Exception) {
            error(e)
        }
    }

    suspend fun updateProduct(id: Int, productDto: CreateProductDto): AppResponse<ProductDto?> {
        return try {
            withContext(coroutineContext) {
                findProduct(id)
                    ?: return@withContext DefaultHttpResponse.notFound("Product with id $id does not exists")

                val updatedProduct = productRepository.update(id, productDto.toDatabase())?.toDto()

                if (updatedProduct != null) {
                    DefaultHttpResponse.ok(updatedProduct)
                } else {
                    DefaultHttpResponse.badRequest("Unable to update the product")
                }
            }
        } catch (e: Exception) {
            error(e)
        }
    }

    suspend fun deleteProduct(id: Int): AppResponse<String> {
        return try {
            withContext(coroutineContext) {
                findProduct(id)
                    ?: return@withContext DefaultHttpResponse.notFound("Product with id $id does not exists")

                val deletedProduct = productRepository.softDelete(id)

                if (deletedProduct != null) {
                    DefaultHttpResponse.noContent(
                        "Product ${deletedProduct.name} (${deletedProduct.id}) was deleted successfully"
                    )
                } else {
                    DefaultHttpResponse.badRequest("Unable to delete the product")
                }
            }
        } catch (e: Exception) {
            error(e)
        }
    }

    private suspend fun findProduct(id: Int): ProductDto? {
        return withContext(coroutineContext) {
            productRepository.findOneById(id)?.toDto()
        }
    }
}
