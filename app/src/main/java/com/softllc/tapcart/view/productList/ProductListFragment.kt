package com.softllc.tapcart.view.productList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.softllc.tapcart.databinding.FragmentProductListBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class ProductListFragment : Fragment() {

    private lateinit var binding: FragmentProductListBinding

    private val productsViewModel: ProductListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentProductListBinding.inflate(inflater, container, false)

        initView()

        return binding.root
    }

    private fun initView() {

        val adapter = ProductListAdapter {
            onProductsItemClick(it)
        }
        binding.productsList.layoutManager = LinearLayoutManager(context)
        binding.productsList.adapter = adapter
        binding.productsList.setViewCacheExtension(CustomBlockCacheExtension())
        //binding.productsList.recycledViewPool.setMaxRecycledViews(0, 500);
        //binding.productsList.setItemViewCacheSize(300)
        productsViewModel.productList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    private fun onProductsItemClick(item: ProductListItem) {
        Timber.d("clicked $item")
        findNavController().navigate(ProductListFragmentDirections.actionToProductDetail(item.name,item.id))
    }
}


class CustomBlockCacheExtension : RecyclerView.ViewCacheExtension() {
    override fun getViewForPositionAndType(
        recycler: RecyclerView.Recycler,
        position: Int,
        type: Int
    ): View? {
        Timber.d("getViewForPositionAndType $position ${cache[position].hashCode()}")

        return cache[position]
    }

    companion object {
        val cache = HashMap<Int,View>()
    }

}