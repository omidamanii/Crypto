package com.example.crypto.screen.coin

import com.example.crypto.model.coin.Coin

data class CoinState(
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val coin: Coin? = null
)
