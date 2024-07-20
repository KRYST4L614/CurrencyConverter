package com.example.currencyconverter.data.entity

import com.google.gson.annotations.SerializedName

data class CurrenciesNetworkEntity(
    @SerializedName("data")
    val data: Map<String, CurrencyNetworkEntity>
)