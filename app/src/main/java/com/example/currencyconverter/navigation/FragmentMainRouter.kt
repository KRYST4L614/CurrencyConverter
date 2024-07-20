package com.example.currencyconverter.navigation

import com.example.currencyconverter.ui.ResultFragment
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.FragmentScreen
import javax.inject.Inject

class FragmentMainRouter @Inject constructor(private val router: Router) {
    fun openResult(
        value: Double,
        currencyFrom: String,
        currencyTo: String
    ) = router.navigateTo(FragmentScreen {
        ResultFragment.newInstance(
            value,
            currencyFrom,
            currencyTo
        )
    })
}