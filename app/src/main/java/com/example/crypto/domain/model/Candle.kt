package com.example.crypto.domain.model

data class Candle(
    val open: String,
    val high: String,
    val low: String,
    val close: String,
    val volume: String,
    val period: Long
)
