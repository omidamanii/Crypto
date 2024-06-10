package com.example.crypto.api

import com.example.crypto.model.coin.Coin
import retrofit2.http.GET

interface CoinCapApi {

    @GET("assets")
    suspend fun getCoins(): Coin
}