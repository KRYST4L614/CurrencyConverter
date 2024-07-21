package com.example.currencyconverter.choosecurrency

import com.example.currencyconverter.choosecurrency.ui.ChooseCurrencyFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

fun getChooseCurrencyScreen() = FragmentScreen { ChooseCurrencyFragment.newInstance() }