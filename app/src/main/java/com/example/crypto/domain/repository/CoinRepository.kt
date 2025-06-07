package com.example.crypto.domain.repository

import com.example.crypto.domain.model.Candle
import com.example.crypto.domain.model.Coin
import com.example.crypto.util.Resource
import com.example.crypto.util.Timeframe

interface CoinRepository {
    suspend fun getCoins(): Resource<List<Coin>>
    suspend fun getCoinHistory(id: String, timeframe: Timeframe): Resource<List<Candle>>
}
