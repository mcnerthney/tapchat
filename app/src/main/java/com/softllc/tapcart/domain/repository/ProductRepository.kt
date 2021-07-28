package com.softllc.tapcart.domain.repository

import com.softllc.tapcart.core.Failure
import com.softllc.tapcart.core.Result
import com.softllc.tapcart.domain.datasource.ProductsDataSource
import com.softllc.tapcart.domain.db.DataProduct
import com.softllc.tapcart.domain.model.Product
import com.softllc.tapcart.domain.util.ConvertDBtoModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val productsDataSource: ProductsDataSource,
) {
    private var cachedData: Result<List<Product>>? = null

    class ProductNotFound: Failure.FeatureFailure()

    fun fetchProducts(): Flow<Result<List<Product>>> {
        cachedData?.let {
            return flow { emit(it) }
        }
        return getDbProducts()
    }

    fun fetchProduct(id: String): Flow<Result<Product>> =
        fetchProducts().map { products ->
            if (products.status == Result.Status.SUCCESS) {
                val foundProduct = products.data?.find { it.productId == id }
                if (foundProduct != null) {
                    Result.success(foundProduct)
                } else {
                    Result.error(ProductNotFound())
                }
            } else {
                Result(
                    products.status,
                    null,
                    products.error
                )
            }
        }


    private fun getDbProducts() : Flow<Result<List<Product>>> =
        productsDataSource.fetchProducts()
            .map { dbProducts ->
                Result(
                    dbProducts.status,
                    convertProductList(dbProducts.data?.products),
                    dbProducts.error
                )
            }
            .onEach {
                if (it.status == Result.Status.SUCCESS) {
                    cachedData = it
                }
            }

    private fun convertProductList(dataProduct: List<DataProduct>?): List<Product>? {
        val dbProducts = dataProduct ?: return null
        return dbProducts.map {
            ConvertDBtoModel.convertProduct(it)
        }

    }
}




