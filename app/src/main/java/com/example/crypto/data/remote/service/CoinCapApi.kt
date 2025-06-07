package com.example.crypto.data.remote.service

import com.example.crypto.data.remote.dto.coin.CoinDto
import retrofit2.http.GET

interface CoinCapApi {

    @GET("assets")
    suspend fun getCoins(): CoinDto
}