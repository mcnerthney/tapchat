package com.softllc.tapcart.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class SelectedProduct (
    val productId : String,
    val variantId: String? = null
    ): Parcelable



