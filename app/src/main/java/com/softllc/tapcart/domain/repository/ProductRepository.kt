package com.softllc.tapcart.domain.repository

import com.softllc.tapcart.core.Failure
import com.softllc.tapcart.core.Result
import com.softllc.tapcart.domain.datasource.ProductsDataSource
import com.softllc.tapcart.domain.db.DataProduct
import com.softllc.tapcart.domain.model.Product
import com.softllc.tapcart.domain.model.ProductVariant
import com.softllc.tapcart.domain.util.ConvertDataToModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val productsDataSource: ProductsDataSource,
) {
    class ProductNotFound: Failure.FeatureFailure()
    class VariantNotFound: Failure.FeatureFailure()

    private var cachedProducts: List<Product>? = null
    fun fetchProducts(): Flow<Result<List<Product>>> = flow {
        if ( cachedProducts != null ) {
            emit(Result.success(cachedProducts))
        }
        else {
            getProductsFromDb().collect {
                emit(it)
                if (it.status == Result.Status.SUCCESS) {
                    cachedProducts = it.data
                }
            }
        }
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
                Result.error(products.error)
            }
        }

    private fun getProductsFromDb() : Flow<Result<List<Product>>> =
        productsDataSource.fetchProducts()
            .map { dbProducts ->
                Result(
                    dbProducts.status,
                    convertToModel(dbProducts.data?.products),
                    dbProducts.error
                )
            }

    private fun convertToModel(dataProduct: List<DataProduct>?): List<Product>? {
        val dbProducts = dataProduct ?: return null
        return dbProducts.map {
            ConvertDataToModel.convertProduct(it)
        }
    }
}




