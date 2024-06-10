package com.example.crypto.api

import com.example.crypto.model.candle.Candle
import retrofit2.http.GET
import retrofit2.http.Query

interface KuCoinApi {

    @GET("market/candles")
    suspend fun getCandle(
        @Query("symbol") symbol: String,
        @Query("type") timeframe: String
    ): Candle
}