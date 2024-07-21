package com.example.currencyconverter.shared.fragmentdependencies

interface FragmentDependenciesProvider {
    val dependencies: FragmentDependencies

    companion object : FragmentDependenciesProvider by FragmentDependenciesStore
}
