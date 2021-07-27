package com.softllc.tapcart.view.productDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.softllc.tapcart.databinding.FragmentProductDetailBinding
import com.softllc.tapcart.view.productList.ProductListAdapter
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class ProductDetailFragment : Fragment() {

    private lateinit var binding: FragmentProductDetailBinding

    private val productDetailViewModel: ProductDetailViewModel by viewModels()
    private val args: ProductDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = productDetailViewModel
        initView()

        return binding.root
    }

    private fun initView() {

        productDetailViewModel.loadProduct(args.product.productId)

        val adapter = ProductDetailOptionAdapter { item, value ->
            productDetailViewModel.selectVariant(item.name, value)
        }

        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.productDetailOptionsList.layoutManager = layoutManager
        binding.productDetailOptionsList.adapter = adapter

        productDetailViewModel.productOptions.observe(viewLifecycleOwner) {
            val values = it.map {
                ProductDetailOptionItem(it.optionId, it.optionName, it.optionValues )
            }
            adapter.submitList(values)
        }

        productDetailViewModel.selectedVariant.observe(viewLifecycleOwner) {
            Glide.with(this).load(it?.variantImage ).into(binding.productDetailImageView)
        }

        binding.productDetailAddToCart.setOnClickListener {
            Timber.d("add product variant to cart ${args.product.productId} ${productDetailViewModel.selectedVariant.value}" )
        }
    }

}