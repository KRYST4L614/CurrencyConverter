package com.example.currencyconverter

import android.app.Application
import com.example.currencyconverter.di.DaggerAppComponent

class App : Application() {
    val appComponent by lazy {
        DaggerAppComponent.builder().context(this).build()
    }
}