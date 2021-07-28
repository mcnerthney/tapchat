package com.softllc.tapcart.domain.repository

import com.softllc.tapcart.core.Result
import com.softllc.tapcart.domain.datasource.ProductsDataSource
import com.softllc.tapcart.domain.model.Product
import com.softllc.tapcart.domain.util.ConvertDBtoModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val productsDataSource: ProductsDataSource,
) {
    private var cachedData: Result<List<Product>>? = null

    fun fetchProducts(): Flow<Result<List<Product>>> {
        cachedData?.let {
            return flow { emit(it) }
        }
        return allProducts
    }

    fun fetchProduct(id: String): Flow<Result<Product>> =
        fetchProducts().map { products ->
            if (products.status == Result.Status.SUCCESS) {
                val fetchProducts = products.data
                val foundProduct = fetchProducts?.find { it.productId == id }
                if (foundProduct != null) {
                    Result.success(foundProduct)
                } else {
                    Result(
                        Result.Status.ERROR,
                        null,
                        null,
                        "not_found"
                    )
                }
            } else {
                Result(
                    Result.Status.ERROR,
                    null,
                    null,
                    "error"
                )
            }
        }


    private val allProducts: Flow<Result<List<Product>>> =
        productsDataSource.fetchProducts()
            .map { dbProducts ->
                if (dbProducts.status == Result.Status.SUCCESS) {
                    val convertedProducts = mutableListOf<Product>()
                    val fetchProducts = dbProducts.data
                    fetchProducts?.let {
                        it.products.forEach {
                            convertedProducts.add(ConvertDBtoModel.convertProduct(it))
                        }
                    }
                    Result.success(convertedProducts)
                } else {
                    Result(
                        Result.Status.ERROR,
                        null,
                        null,
                        "error"
                    )
                }
            }
            .onEach {
                if (it.status == Result.Status.SUCCESS) {
                    cachedData = it
                }
            }
}




