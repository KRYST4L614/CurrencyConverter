package com.example.currencyconverter.feature.result

import com.example.currencyconverter.feature.result.ui.ResultFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

fun getResultScreen(
    value: Double,
    currencyFrom: String,
    currencyTo: String
) = FragmentScreen { ResultFragment.newInstance(value, currencyFrom, currencyTo) }