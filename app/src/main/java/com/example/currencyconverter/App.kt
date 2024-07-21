package com.example.currencyconverter

import android.app.Application
import com.example.currencyconverter.di.DaggerAppComponent
import com.example.currencyconverter.shared.fragmentdependencies.FragmentDependenciesStore

class App : Application() {
    val appComponent by lazy {
        DaggerAppComponent.builder().context(this).build()
    }

    override fun onCreate() {
        super.onCreate()
        FragmentDependenciesStore.dependencies =
            appComponent
    }
}