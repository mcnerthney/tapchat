package com.softllc.tapcart.view.productDetail

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter

class ProductDetailOptionAdapter(private val onItemSelected: (item: ProductDetailOptionItem, value: String) -> Unit) : ListAdapter<ProductDetailOptionItem, ProductDetailOptionHolder> (ProductDetailOptionDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductDetailOptionHolder {
        return ProductDetailOptionHolder.from(parent,onItemSelected)
    }
    override fun onBindViewHolder(holder: ProductDetailOptionHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}
