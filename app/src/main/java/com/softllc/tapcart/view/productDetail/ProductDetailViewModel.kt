package com.softllc.tapcart.view.productDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.softllc.tapcart.core.Result
import com.softllc.tapcart.domain.model.Product
import com.softllc.tapcart.domain.model.ProductOption
import com.softllc.tapcart.domain.model.ProductVariant
import com.softllc.tapcart.domain.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val productsRepository: ProductRepository
) :
    ViewModel() {

    private val _productName = MutableLiveData<String>()
    val productName: LiveData<String> = _productName

    private val _productOptions = MutableLiveData<List<ProductOption>>()
    val productOptions: LiveData<List<ProductOption>> = _productOptions

    private val _selectedVariant = MutableLiveData<ProductVariant?>()
    val selectedVariant: LiveData<ProductVariant?> = _selectedVariant

    private var product : Product? = null
    private val selectOptions = HashMap<String,String>()

    fun loadProduct(productId: String) {
        viewModelScope.launch {
            productsRepository.fetchProduct(productId).collect {
                it?.let {
                    if (it.status == Result.Status.SUCCESS)
                        it.data?.let {
                            product = it
                            _productName.value = it.productName
                            _productOptions.value = it.options
                            setSelectedImage()
                        }
                }
            }
        }
    }

    fun selectVariant( name : String, value : String ) {
        selectOptions.set(name, value)
        setSelectedImage()
    }

    private fun setSelectedImage() {
        product?.let { product ->
            product.variants.forEach { variant ->
                if ( selectOptions == variant.variantOptions ) {
                    _selectedVariant.value = variant
                    return
                }
            }
            _selectedVariant.value = null
        }

    }
}



