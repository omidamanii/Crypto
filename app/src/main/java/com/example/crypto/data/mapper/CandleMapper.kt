package com.example.crypto.data.mapper

import com.example.crypto.data.remote.dto.candle.CandleDto
import com.example.crypto.domain.model.Candle

fun CandleDto.toDomainCandle(): Candle {
    return Candle(
        open = open ?: "0",
        high = high ?: "0",
        low = low ?: "0",
        close = close ?: "0",
        volume = volume ?: "0",
        period = period ?: 0L
    )
}
