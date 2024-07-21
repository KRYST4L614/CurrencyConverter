package com.example.currencyconverter.shared.fragmentdependencies

import androidx.lifecycle.ViewModelProvider

interface FragmentDependencies {
    val viewModelFactory: ViewModelProvider.Factory
}