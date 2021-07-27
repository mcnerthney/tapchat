package com.softllc.tapcart.domain.repository

import android.content.Context
import com.google.gson.Gson
import com.softllc.tapcart.core.Result
import com.softllc.tapcart.domain.db.DataProducts
import java.io.InputStreamReader


class ProductsDataSource(val context: Context) {
    fun fetchProducts(): Result<DataProducts> {
        return try {
            val inputStream = context.assets.open("products.json")
            val products = Gson().fromJson(InputStreamReader(inputStream), DataProducts::class.java)
            Result.success(products)
        } catch (e: Throwable) {
            Result.error("Error ${e.localizedMessage}", null)
        }
    }
}