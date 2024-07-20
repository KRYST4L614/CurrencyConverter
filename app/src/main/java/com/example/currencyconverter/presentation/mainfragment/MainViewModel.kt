package com.example.currencyconverter.presentation.mainfragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.R
import com.example.currencyconverter.domain.usecases.GetCurrenciesUseCase
import com.example.currencyconverter.navigation.FragmentMainRouter
import com.example.currencyconverter.util.ResourceProvider
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val router: FragmentMainRouter,
    private val getCurrenciesUseCase: GetCurrenciesUseCase,
    private val resourceProvider: ResourceProvider
) : ViewModel() {

    private val _state: MutableLiveData<MainState> = MutableLiveData()
    val state: LiveData<MainState> = _state

    private var lastContentState: MainState.Content? = null

    private fun openResult(value: Double, currencyFrom: String, currencyTo: String) =
        router.openResult(value, currencyFrom, currencyTo)

    fun getCurrencies() = viewModelScope.launch {
        _state.value = MainState.Loading
        try {
            lastContentState =
                MainState.Content(
                    getCurrenciesUseCase()
                        .data
                        .keys
                        .toList()
                        .sorted(),
                    0,
                    0
                )
            _state.value = lastContentState!!
        } catch (e: Exception) {
            if (e !is CancellationException) {
                _state.value = MainState.Error(resourceProvider.getString(R.string.network_error))
            }
        }
    }

    fun handleChooseCurrency(fromCurrencyPos: Int, toCurrencyPos: Int) {
        lastContentState?.let {
            if (it.fromCurrencyPos != fromCurrencyPos) {
                lastContentState = it.copy(fromCurrencyPos = fromCurrencyPos)
                _state.value = lastContentState!!
            }
            if (it.toCurrencyPos != toCurrencyPos) {
                lastContentState = lastContentState!!.copy(toCurrencyPos = toCurrencyPos)
                _state.value = lastContentState!!
            }
        }
    }

    fun handleConvert(amount: String?, currencyFrom: String, currencyTo: String) {
        handleAmountChange(amount)
        if (isValidAmount(amount)) {
            openResult(amount?.toDouble()!!, currencyFrom, currencyTo)
        }
    }

    fun handleAmountChange(amount: String?) {
        val newAmountError =
            if (!isValidAmount(amount)) resourceProvider.getString(R.string.amount_error) else null

        lastContentState?.let {
            if (it.amountError != newAmountError) {
                lastContentState = it.copy(amountError = newAmountError)
                _state.value = lastContentState!!
            }
        }
    }

    private fun isValidAmount(amount: String?): Boolean {
        return !amount.isNullOrBlank()
    }
}