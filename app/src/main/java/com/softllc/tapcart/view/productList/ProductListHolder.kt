package com.softllc.tapcart.view.productList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.softllc.tapcart.databinding.ItemProductsBinding

class ProductListHolder(private val binding: ItemProductsBinding, private val onItemClicked: (item: ProductListItem) -> Unit) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: ProductListItem) {
        binding.product = item
        binding.itemProducts.setOnClickListener {
            onItemClicked.invoke(item)
        }
    }

    companion object {
        fun from(parent: ViewGroup, onItemClicked: (item: ProductListItem) -> Unit): ProductListHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemProductsBinding.inflate(layoutInflater, parent, false)
            return ProductListHolder(binding, onItemClicked)
        }
    }
}