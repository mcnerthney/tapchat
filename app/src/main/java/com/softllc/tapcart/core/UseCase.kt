package com.softllc.tapcart.core

import kotlinx.coroutines.flow.Flow

abstract class UseCase<out Type, in Params> where Type : Any {

    abstract fun run(params: Params): Flow<Type>

    operator fun invoke(params: Params, onResult: (Flow<Type>) -> Unit = {}) {
        onResult (run(params))
    }

    class Param
}