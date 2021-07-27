package com.softllc.tapcart.view.productDetail

import androidx.recyclerview.widget.DiffUtil

class ProductDetailOptionDiffCallback : DiffUtil.ItemCallback<ProductDetailOptionItem>() {

    override fun areItemsTheSame(oldItem: ProductDetailOptionItem, newItem: ProductDetailOptionItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ProductDetailOptionItem, newItem: ProductDetailOptionItem): Boolean {
        return oldItem == newItem
    }


}