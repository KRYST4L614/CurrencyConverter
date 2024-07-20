package com.example.currencyconverter.navigation

import com.github.terrakok.cicerone.Router
import javax.inject.Inject

class FragmentResultRouter @Inject constructor(private val router: Router) {
    fun close() = router.exit()
}