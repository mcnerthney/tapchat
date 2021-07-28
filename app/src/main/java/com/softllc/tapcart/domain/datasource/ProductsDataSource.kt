package com.softllc.tapcart.domain.datasource

import android.content.Context
import com.google.gson.Gson
import com.softllc.tapcart.core.Failure
import com.softllc.tapcart.core.Result
import com.softllc.tapcart.domain.db.DataProducts
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import java.io.InputStreamReader


class ProductsDataSource(val context: Context) {

    class FetchProductsFailure(val throwable: Throwable): Failure.FeatureFailure()

    fun fetchProducts(): Flow<Result<DataProducts>> = flow {
        try {
            emit(Result.loading())
            Timber.d("fetch from ProductsDataSource json")
            val inputStream = context.assets.open("products.json")
            val products = Gson().fromJson(InputStreamReader(inputStream), DataProducts::class.java)
            emit(Result.success(products))
        } catch (e: Throwable) {
            emit(
                Result(Result.Status.ERROR, null, FetchProductsFailure(e))
            )
        }
    }
}