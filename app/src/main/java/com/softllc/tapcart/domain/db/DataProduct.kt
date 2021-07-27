package com.softllc.tapcart.domain.db

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class DataProducts (
    val products : List<DataProduct>
    )

data class DataProduct (
    @SerializedName("id")  val productId : String,
    @SerializedName("title") val productName : String,
    val price: BigDecimal,
    val options: List<DataProductOption>,
    val variants: List<DataProductVariant>
)

data class DataProductOption (
    @SerializedName("id")  val optionId: String,
    @SerializedName("name")  val optionName: String,
    @SerializedName("values")  val optionValues : List<String>)


data class DataProductVariant (
    @SerializedName("id")  val variantId : String,
    @SerializedName("image") val variantImage : String,
    val selectedOptions : List<DataProductSelectedOption>
)
data class DataProductSelectedOption(
    @SerializedName("name") val SelectedOptionName: String,
    @SerializedName("value") val SelectedOptionValue: String
)

