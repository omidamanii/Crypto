package com.example.crypto.util

sealed class ChartType {
    data object Line : ChartType()
    data object Candle : ChartType()
}
