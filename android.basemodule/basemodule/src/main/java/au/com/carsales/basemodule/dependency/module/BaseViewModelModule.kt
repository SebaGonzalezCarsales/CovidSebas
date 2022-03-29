package au.com.carsales.basemodule.dependency.module

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.ViewModelProvider
import au.com.carsales.basemodule.util.viewModel.GenericSavedStateViewModelFactory
import au.com.carsales.basemodule.util.viewModel.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class BaseViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}