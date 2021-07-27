package com.softllc.tapcart.view.productList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.softllc.tapcart.core.Result
import com.softllc.tapcart.domain.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val productsRepository: ProductRepository) :
    ViewModel() {

    private val _productList = MutableLiveData<List<ProductListItem>>()
    val productList : LiveData<List<ProductListItem>> = _productList

    init {
        fetchProductList()
    }

    private fun fetchProductList() {
        viewModelScope.launch {
            productsRepository.fetchProducts().collect {
                it?.let {
                    if ( it.status == Result.Status.SUCCESS) {
                        _productList.value = it.data?.map {
                            ProductListItem(it.productId, it.productName)
                        }
                    }
                }

            }
        }
    }

}