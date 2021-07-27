package com.softllc.tapcart.view.productList

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter

class ProductListAdapter(private val onItemClicked: (item: ProductListItem) -> Unit) : ListAdapter<ProductListItem, ProductListHolder> (ProductListDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductListHolder {
        return ProductListHolder.from(parent,onItemClicked)
    }
    override fun onBindViewHolder(holder: ProductListHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

}
