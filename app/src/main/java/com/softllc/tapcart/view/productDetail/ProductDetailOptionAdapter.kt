package com.softllc.tapcart.view.productDetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.softllc.tapcart.databinding.ItemProductDetailOptionBinding

class ProductDetailOptionAdapter(private val onItemSelected: (item: ProductDetailOptionItem, value: String) -> Unit) : ListAdapter<ProductDetailOptionItem, ProductDetailOptionHolder> (ProductDetailOptionDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductDetailOptionHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemProductDetailOptionBinding.inflate(layoutInflater, parent, false)
        return ProductDetailOptionHolder(binding, onItemSelected)
    }
    override fun onBindViewHolder(holder: ProductDetailOptionHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}
