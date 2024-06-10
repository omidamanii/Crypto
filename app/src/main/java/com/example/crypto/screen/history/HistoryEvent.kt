package com.example.crypto.screen.history

import com.example.crypto.util.Timeframe

sealed interface HistoryEvent {
    data class ChangeTimeframe(val timeframe: Timeframe) : HistoryEvent
    data object ChangeChartType : HistoryEvent
}