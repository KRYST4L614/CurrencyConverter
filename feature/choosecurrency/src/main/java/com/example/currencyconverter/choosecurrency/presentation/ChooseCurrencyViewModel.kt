package com.example.currencyconverter.choosecurrency.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.choosecurrency.ChooseCurrencyRouter
import com.example.currencyconverter.choosecurrency.domain.GetCurrenciesUseCase
import com.example.currencyconverter.feature.choosecurrency.R
import com.example.currencyconverter.shared.resourceprovider.ResourceProvider
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.currencyconverter.component.resources.R as ComponentR

class ChooseCurrencyViewModel @Inject constructor(
    private val router: ChooseCurrencyRouter,
    private val getCurrenciesUseCase: GetCurrenciesUseCase,
    private val resourceProvider: ResourceProvider
) : ViewModel() {

    private val _state: MutableLiveData<UIState> = MutableLiveData()
    val state: LiveData<UIState> = _state

    private var lastContentState: UIState.Content? = null

    private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
        _state.value = UIState.Error(resourceProvider.getString(ComponentR.string.network_error))
    }

    fun getCurrencies() = viewModelScope.launch(exceptionHandler) {
        _state.value = UIState.Loading
        lastContentState =
            UIState.Content(
                getCurrenciesUseCase()
                    .data
                    .keys
                    .toList()
                    .sorted()
            )
        _state.value = lastContentState!!
    }

    fun handleConvert(amount: String?, currencyFrom: String, currencyTo: String) {
        handleAmountChange(amount)
        if (isValidAmount(amount)) {
            openResult(amount?.toDouble()!!, currencyFrom, currencyTo)
        }
    }

    private fun openResult(value: Double, currencyFrom: String, currencyTo: String) =
        router.openResult(value, currencyFrom, currencyTo)

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