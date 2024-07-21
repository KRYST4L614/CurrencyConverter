package com.example.currencyconverter.di

import android.content.Context
import com.example.currencyconverter.choosecurrency.ui.ChooseCurrencyFragment
import com.example.currencyconverter.feature.result.ui.ResultFragment
import com.example.currencyconverter.shared.fragmentdependencies.FragmentDependencies
import com.example.currencyconverter.ui.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NavigationModule::class, PresentationModule::class, DataModule::class])
interface AppComponent : FragmentDependencies {
    fun inject(activity: MainActivity)
    fun inject(fragment: ChooseCurrencyFragment)
    fun inject(fragment: ResultFragment)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder
        fun build(): AppComponent
    }
}