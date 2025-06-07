package com.example.crypto.screen.history

import com.example.crypto.domain.model.Candle // Updated import
import com.example.crypto.util.Timeframe

data class HistoryState(
    val candles: List<Candle> = emptyList(), // Updated to domain model
    val isLoading: Boolean = false,
    val error: String? = null,
    val selectedTimeframe: Timeframe = Timeframe.D1,
    val coinId: String = "" // Added to hold current coinId if needed
)
