package com.example.currencyconverter.feature.result.presentation

import android.icu.text.DecimalFormat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.feature.result.R
import com.example.currencyconverter.feature.result.ResultRouter
import com.example.currencyconverter.feature.result.domain.ConvertCurrenciesUseCase
import com.example.currencyconverter.shared.resourceprovider.ResourceProvider
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.currencyconverter.component.resources.R as ComponentR

class ResultViewModel @Inject constructor(
    private val router: ResultRouter,
    private val convertCurrenciesUseCase: ConvertCurrenciesUseCase,
    private val resourceProvider: ResourceProvider
) : ViewModel() {
    private val _state: MutableLiveData<UIState> = MutableLiveData()
    val state: LiveData<UIState> = _state

    private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
        _state.value = UIState.Error(resourceProvider.getString(ComponentR.string.network_error))
    }

    fun close() = router.close()

    fun convertCurrencies(
        currencyFrom: String,
        currencyTo: String,
        value: Double
    ) = viewModelScope.launch(exceptionHandler) {
        _state.value = UIState.Loading

        val scientificFormatter = DecimalFormat("0.000000E00")
        val formatter = DecimalFormat("#.##")
        val converted = convertCurrenciesUseCase(
            currencyFrom,
            currencyTo,
            value
        )

        _state.value =
            UIState.Content(
                resourceProvider.getString(R.string.result).format(
                    if (value > 10e10) scientificFormatter.format(value)
                        .replace("E", "e") else formatter.format(value),
                    currencyFrom,
                    if (converted > 10e10) scientificFormatter.format(converted)
                        .replace("E", "e") else formatter.format(converted),
                    currencyTo
                )
            )
    }
}