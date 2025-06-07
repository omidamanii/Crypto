package com.example.crypto.data.repository

import com.example.crypto.data.mapper.toDomainCandle
import com.example.crypto.data.mapper.toDomainCoinList
import com.example.crypto.data.remote.dto.candle.CandleDto
import com.example.crypto.data.remote.service.CoinCapApi
import com.example.crypto.data.remote.service.KuCoinApi
import com.example.crypto.domain.model.Candle
import com.example.crypto.domain.model.Coin
import com.example.crypto.domain.repository.CoinRepository
import com.example.crypto.util.Resource
import com.example.crypto.util.Timeframe
import java.io.IOException
import javax.inject.Inject
import retrofit2.Response // Added for Response type check

class CoinRepositoryImpl @Inject constructor(
    private val coinCapApi: CoinCapApi,
    private val kuCoinApi: KuCoinApi
) : CoinRepository {

    override suspend fun getCoins(): Resource<List<Coin>> {
        return try {
            // Assuming coinCapApi.getCoins() returns Response<CoinDto>
            val response: Response<com.example.crypto.data.remote.dto.coin.CoinDto> = coinCapApi.getCoins()
            if (response.isSuccessful) {
                response.body()?.let { coinDto ->
                    Resource.Success(coinDto.toDomainCoinList())
                } ?: Resource.Error("Empty response body")
            } else {
                Resource.Error("Failed to fetch coins: ${response.code()} ${response.message()}")
            }
        } catch (e: IOException) {
            Resource.Error("Network error: ${e.localizedMessage ?: "Couldn't reach server"}")
        } catch (e: Exception) {
            Resource.Error("Unexpected error: ${e.localizedMessage ?: "An unknown error occurred"}")
        }
    }

    override suspend fun getCoinHistory(id: String, timeframe: Timeframe): Resource<List<Candle>> {
        return try {
            val startTime = System.currentTimeMillis() / 1000 - timeframe.seconds
            val endTime = System.currentTimeMillis() / 1000
            val granularity = timeframe.granularity

            // Assuming kuCoinApi.getHistory returns Response<List<List<String>>>
            val response: Response<com.example.crypto.data.remote.dto.candle.KuCoinHistoryDto> = kuCoinApi.getHistory(
                symbol = id,
                startAt = startTime.toString(),
                endAt = endTime.toString(),
                type = granularity
            )

            if (response.isSuccessful) {
                response.body()?.data?.let { data -> // Accessing the 'data' field from KuCoinHistoryDto
                    // Data from KuCoin is List<List<String>>
                    // Format: [timestamp, open, close, high, low, volume, turnover]
                    // We need to map this to List<CandleDto> first, then to List<Candle>
                    val candleDtoList = data.mapNotNull { item ->
                        if (item.size >= 7) {
                            CandleDto(
                                period = item[0].toLongOrNull(), // KuCoin gives seconds timestamp
                                open = item[1],
                                close = item[2], // corrected index from original script comment
                                high = item[3], // corrected index from original script comment
                                low = item[4],  // corrected index from original script comment
                                volume = item[5]
                                // turnover = item[6] // Not in CandleDto/Candle model
                            )
                        } else {
                            null
                        }
                    }
                    Resource.Success(candleDtoList.map { it.toDomainCandle() })
                } ?: Resource.Error("Empty response body for history or data field is null")
            } else {
                Resource.Error("Failed to fetch coin history: ${response.code()} ${response.message()}")
            }
        } catch (e: IOException) {
            Resource.Error("Network error fetching history: ${e.localizedMessage ?: "Couldn't reach server"}")
        } catch (e: Exception) {
            Resource.Error("Unexpected error fetching history: ${e.localizedMessage ?: "An unknown error occurred"}")
        }
    }
}
