package com.example.currencyconverter.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.currencyconverter.choosecurrency.presentation.ChooseCurrencyViewModel
import com.example.currencyconverter.feature.result.presentation.ResultViewModel
import com.example.currencyconverter.presentation.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface PresentationModule {

    @Binds
    @IntoMap
    @ViewModelKey(ChooseCurrencyViewModel::class)
    fun bindsMainViewModel(viewModel: ChooseCurrencyViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ResultViewModel::class)
    fun bindsResultViewModel(viewModel: ResultViewModel): ViewModel

    @Binds
    fun bindsViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}