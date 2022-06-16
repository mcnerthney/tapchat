package com.softllc.tapcart.view.productList

import android.view.LayoutInflater
import android.view.ViewGroup
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.softllc.tapcart.databinding.ItemProductsBinding
import timber.log.Timber


class ProductListHolder(
    private val binding: ItemProductsBinding,
    private val onItemClicked: (item: ProductListItem) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: ProductListItem, position: Int) {
        Timber.d("bind $position")
        Timber.d("ViewHolder ${this.toString()}")
        if (CustomBlockCacheExtension.cache[position] == null) {
            binding.customBlockWebView.loadUrl("https://mcnerthney.github.io/1?pos=$position")
            binding.itemProducts.setOnClickListener {
                onItemClicked.invoke(item)
            }
            //CustomBlockCacheExtension.cache[position] = binding.root
        }
    }


    companion object {
        fun from(
            parent: ViewGroup,
            onItemClicked: (item: ProductListItem) -> Unit
        ): ProductListHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemProductsBinding.inflate(layoutInflater, parent, false)
            return ProductListHolder(binding, onItemClicked)
        }

    }
}

