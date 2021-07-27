package com.softllc.tapcart.domain.util

import com.softllc.tapcart.domain.db.DataProduct
import com.softllc.tapcart.domain.db.DataProductOption
import com.softllc.tapcart.domain.db.DataProductVariant
import com.softllc.tapcart.domain.model.Product
import com.softllc.tapcart.domain.model.ProductOption
import com.softllc.tapcart.domain.model.ProductVariant

object ConvertDBtoModel {

    fun convertProduct(dataProduct: DataProduct) : Product {
        val options = dataProduct.options.map {
            convertProductOption(it)
        }
        val variants = dataProduct.variants.map {
            convertProductVariant(it)
        }
        return Product(
            dataProduct.productId,
            dataProduct.productName,
            dataProduct.price,
            options,
            variants
        )
    }
    private fun convertProductOption(dataProductOption: DataProductOption) : ProductOption {
        return ProductOption(
            dataProductOption.optionId,
            dataProductOption.optionName,
            dataProductOption.optionValues
        )
    }
    private fun convertProductVariant(dataProductVariant: DataProductVariant) : ProductVariant {
        val variantOptions = HashMap<String,String>()
        dataProductVariant.selectedOptions.forEach {
            variantOptions[it.SelectedOptionName] = it.SelectedOptionValue
        }
        return ProductVariant(
            dataProductVariant.variantId,
            dataProductVariant.variantImage,
            variantOptions
        )
    }


}