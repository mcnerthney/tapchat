package com.softllc.tapcart.view.productDetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import com.softllc.tapcart.databinding.ItemProductDetailOptionBinding

class ProductDetailOptionHolder(private val binding: ItemProductDetailOptionBinding,
                                private val onItemSelected: (item: ProductDetailOptionItem, value: String) -> Unit)
    : RecyclerView.ViewHolder(binding.root)  {
    fun bind(item: ProductDetailOptionItem) {
        val valueAdapter = ArrayAdapter(
            binding.root.context, android.R.layout.simple_spinner_item,
            item.values
        )

        valueAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.productOption.adapter = valueAdapter


        val selectListener = (object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                onItemSelected(item, item.values[position])
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        })

        binding.productOption.onItemSelectedListener = selectListener
        val index = item.values.indexOf(item.selected)
        if ( index > -1 ) binding.productOption.setSelection(index)

    }

    companion object {
        fun from(parent: ViewGroup, onItemSelected: (item: ProductDetailOptionItem, value: String) -> Unit): ProductDetailOptionHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemProductDetailOptionBinding.inflate(layoutInflater, parent, false)
            return ProductDetailOptionHolder(binding, onItemSelected)
        }
    }
}