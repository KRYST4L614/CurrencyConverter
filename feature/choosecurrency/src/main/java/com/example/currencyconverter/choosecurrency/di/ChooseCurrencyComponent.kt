package com.example.currencyconverter.choosecurrency.di

import com.example.currencyconverter.choosecurrency.ui.ChooseCurrencyFragment
import com.example.currencyconverter.shared.fragmentdependencies.FragmentDependencies
import dagger.Component

@Component(dependencies = [FragmentDependencies::class])
interface ChooseCurrencyComponent {
    fun inject(fragment: ChooseCurrencyFragment)

    @Component.Builder
    interface Builder {

        fun dependencies(dependencies: FragmentDependencies): Builder

        fun build(): ChooseCurrencyComponent
    }
}