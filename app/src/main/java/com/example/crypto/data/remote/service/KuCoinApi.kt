package com.example.crypto.data.remote.service

import com.example.crypto.data.remote.dto.candle.CandleDto
import retrofit2.http.GET
import com.example.crypto.data.remote.dto.candle.KuCoinHistoryDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface KuCoinApi {

    @GET("market/candles")
    suspend fun getCandle(
        @Query("symbol") symbol: String,
        @Query("type") timeframe: String
    ): Candle
}