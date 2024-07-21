package com.example.currencyconverter.navigation

import com.example.currencyconverter.feature.result.ResultRouter
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

class ResultRouterImpl @Inject constructor(private val router: Router): ResultRouter {
    override fun close() = router.exit()
}