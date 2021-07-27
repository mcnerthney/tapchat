package com.softllc.tapcart.domain.repository

import com.softllc.tapcart.core.Result
import com.softllc.tapcart.domain.model.Product
import com.softllc.tapcart.domain.util.ConvertDBtoModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val productsDataSource: ProductsDataSource,
) {
    private var fetchedData: Result<List<Product>>? = null

    suspend fun fetchProducts(): Flow<Result<List<Product>>?> {
        return flow {
            val nullProduct : List<Product>? = null
            if (fetchedData == null) {
                emit(Result.loading())
                val products = fetchAndConvertProducts()
                if ( products != null ) {
                    fetchedData = Result.success(products)
                    emit(fetchedData)
                }
                else {
                    emit(
                        Result(
                            Result.Status.ERROR,
                            nullProduct,
                            null,
                            "error"
                        )
                    )
                }
            }
            emit(fetchedData)
        }.flowOn(Dispatchers.IO) // Use the IO thread for this Flow
    }


    suspend fun fetchProduct(id: String): Flow<Result<Product>?> {
        return flow {
            var product: Product? = null
            if ( fetchedData == null ) {
                emit(Result.loading())
                val products = fetchAndConvertProducts()
                if (products != null) {
                    fetchedData = Result.success(products)
                }
            }
            if (fetchedData?.status == Result.Status.SUCCESS) {
                val foundProduct = fetchedData?.data?.find {
                    it.productId == id
                }
                if (foundProduct == null) {
                    emit(Result(Result.Status.ERROR, product, null, "not_found"))
                } else {
                    product = foundProduct
                    emit(Result.success(product))
                }
            } else {
                emit(
                    Result(
                        Result.Status.ERROR,
                        product,
                        null,
                        "error"
                    )
                )
            }
        }.flowOn(Dispatchers.IO)
    }

    private fun fetchAndConvertProducts() : List<Product>? {
        val dbProducts = productsDataSource.fetchProducts()

        if (dbProducts.status == Result.Status.SUCCESS) {
            val convertedProducts = mutableListOf<Product>()
            val fetchProducts = dbProducts.data
            fetchProducts?.let {
                it.products.forEach {
                    convertedProducts.add(ConvertDBtoModel.convertProduct(it))
                }
            }
            return convertedProducts
        }

        return null
    }
}


