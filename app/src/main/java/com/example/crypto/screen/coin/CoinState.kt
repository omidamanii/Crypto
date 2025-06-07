package com.example.crypto.screen.coin

import com.example.crypto.domain.model.Coin // Updated import

data class CoinState(
    // Assuming the screen shows details of one coin, or the first from a list.
    // If the screen is meant to show a list, this should be coins: List<Coin> = emptyList()
    val coin: Coin? = null, // Updated to domain model
    val isLoading: Boolean = false,
    val error: String? = null
)
