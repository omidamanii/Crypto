package com.example.crypto.data.remote.dto.candle

data class CandleDto(
    val code: String,
    val data: List<List<String>>
)