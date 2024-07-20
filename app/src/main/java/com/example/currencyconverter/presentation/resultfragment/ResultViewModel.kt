package com.example.currencyconverter.presentation.resultfragment

import android.icu.text.DecimalFormat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.R
import com.example.currencyconverter.domain.usecases.ConvertCurrenciesUseCase
import com.example.currencyconverter.navigation.FragmentResultRouter
import com.example.currencyconverter.util.ResourceProvider
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import javax.inject.Inject

class ResultViewModel @Inject constructor(
    private val router: FragmentResultRouter,
    private val convertCurrenciesUseCase: ConvertCurrenciesUseCase,
    private val resourceProvider: ResourceProvider
) : ViewModel() {
    private val _state: MutableLiveData<ResultState> = MutableLiveData()
    val state: LiveData<ResultState> = _state

    fun close() = router.close()

    fun convertCurrencies(
        value: Double,
        currencyFrom: String,
        currencyTo: String
    ) = viewModelScope.launch {
        _state.value = ResultState.Loading
        try {
            val scientificFormatter = DecimalFormat("0.000000E00")
            val formatter = DecimalFormat("#.##")
            val converted = convertCurrenciesUseCase(
                currencyFrom,
                currencyTo,
                value
            )
            _state.value =
                ResultState.Content(
                    resourceProvider.getString(R.string.result).format(
                        if (value > 10e10) scientificFormatter.format(value)
                            .replace("E", "e") else formatter.format(value),
                        currencyFrom,
                        if (converted > 10e10) scientificFormatter.format(converted)
                            .replace("E", "e") else formatter.format(converted),
                        currencyTo
                    )
                )
        } catch (e: Exception) {
            if (e !is CancellationException) {
                _state.value = ResultState.Error(resourceProvider.getString(R.string.network_error))
            }
        }
    }
}