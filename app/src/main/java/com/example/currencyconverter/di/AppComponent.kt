package com.example.currencyconverter.di

import android.content.Context
import com.example.currencyconverter.ui.MainActivity
import com.example.currencyconverter.ui.MainFragment
import com.example.currencyconverter.ui.ResultFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NavigationModule::class, PresentationModule::class, DataModule::class])
interface AppComponent {
    fun inject(activity: MainActivity)
    fun inject(fragment: MainFragment)
    fun inject(fragment: ResultFragment)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context): Builder
        fun build(): AppComponent

    }
}