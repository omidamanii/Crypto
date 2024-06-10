package com.example.crypto.repository

import com.example.crypto.api.CoinCapApi
import com.example.crypto.api.KuCoinApi
import com.example.crypto.model.candle.Candle
import com.example.crypto.model.coin.Coin
import com.example.crypto.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CoinRepository @Inject constructor(
    private val coinCap: CoinCapApi,
    private val kuCoinApi: KuCoinApi
) {

    suspend fun getCoins(): Flow<Resource<Coin>> {
        return flow {
            try {
                emit(Resource.Loading(true))

                val coins = coinCap.getCoins()
                emit(Resource.Success(coins))

            } catch (e: HttpException) {
                emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
            } catch (e: IOException) {
                emit(Resource.Error("Couldn't reach server. Check your internet connection."))
            }
        }
    }

    suspend fun getCandle(symbol: String, timeframe: String): Flow<Resource<Candle>> {
        return flow {
            try {
                emit(Resource.Loading(true))

                val candle = kuCoinApi.getCandle(symbol, timeframe)
                emit(Resource.Success(candle))
            } catch (e: HttpException) {
                emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
            } catch (e: IOException) {
                emit(Resource.Error("Couldn't reach server. Check your internet connection."))
            }
        }
    }
}