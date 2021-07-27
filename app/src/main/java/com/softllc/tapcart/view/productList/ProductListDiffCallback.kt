package com.softllc.tapcart.view.productList

import androidx.recyclerview.widget.DiffUtil

class ProductListDiffCallback : DiffUtil.ItemCallback<ProductListItem>() {

    override fun areItemsTheSame(oldItem: ProductListItem, newItem: ProductListItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ProductListItem, newItem: ProductListItem): Boolean {
        return oldItem == newItem
    }


}