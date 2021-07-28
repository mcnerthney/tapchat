package com.softllc.tapcart.domain.usecases

import com.softllc.tapcart.core.Result
import com.softllc.tapcart.core.UseCase
import com.softllc.tapcart.domain.model.Product
import com.softllc.tapcart.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetProductUseCase
@Inject constructor(
    private val productRepository: ProductRepository
)
    : UseCase<Result<Product>, GetProductUseCase.Param>() {

    override fun run(params: Param): Flow<Result<Product>> = productRepository.fetchProduct(params.productId)

    class Param(val productId: String)
}