package com.example.currencyconverter.navigation

import com.example.currencyconverter.choosecurrency.ChooseCurrencyRouter
import com.example.currencyconverter.feature.result.getResultScreen
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

class ChooseCurrencyRouterImpl @Inject constructor(private val router: Router) :
    ChooseCurrencyRouter {
    override fun openResult(
        value: Double,
        currencyFrom: String,
        currencyTo: String
    ) = router.navigateTo(getResultScreen(value, currencyFrom, currencyTo))
}