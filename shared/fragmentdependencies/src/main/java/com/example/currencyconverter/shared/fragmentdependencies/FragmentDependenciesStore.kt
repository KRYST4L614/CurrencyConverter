package com.example.currencyconverter.shared.fragmentdependencies

import kotlin.properties.Delegates

object FragmentDependenciesStore : FragmentDependenciesProvider {

    override var dependencies: FragmentDependencies by Delegates.notNull()
}