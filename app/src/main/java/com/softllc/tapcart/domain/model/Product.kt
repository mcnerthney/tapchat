package com.softllc.tapcart.domain.model

import java.math.BigDecimal

data class Product (
    val productId : String,
    val productName : String,
    val price: BigDecimal,
    val options: List<ProductOption>,
    val variants: List<ProductVariant>
)

data class ProductOption (
    val optionId: String,
    val optionName: String,
    val optionValues : List<String>)


data class ProductVariant (
    val variantId : String,
    val variantImage : String,
    val variantOptions : HashMap<String,String>
)


