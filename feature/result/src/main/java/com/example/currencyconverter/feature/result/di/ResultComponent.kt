package com.example.currencyconverter.feature.result.di

import com.example.currencyconverter.feature.result.ui.ResultFragment
import com.example.currencyconverter.shared.fragmentdependencies.FragmentDependencies
import dagger.Component

@Component(dependencies = [FragmentDependencies::class])
interface ResultComponent {
    fun inject(fragment: ResultFragment)

    @Component.Builder
    interface Builder {

        fun dependencies(dependencies: FragmentDependencies): Builder

        fun build(): ResultComponent
    }
}