package com.softllc.tapcart.di

import android.content.Context
import com.softllc.tapcart.domain.datasource.ProductsDataSource
import com.softllc.tapcart.domain.repository.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideProductsRepository(productsDataSource: ProductsDataSource): ProductRepository {
        return ProductRepository(productsDataSource)
    }

    @Provides
    fun provideProductsDataSource(@ApplicationContext context: Context): ProductsDataSource {
        return ProductsDataSource(context)
    }

}


