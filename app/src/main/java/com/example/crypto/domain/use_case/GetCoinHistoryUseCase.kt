package com.example.crypto.domain.use_case

import com.example.crypto.domain.model.Candle
import com.example.crypto.domain.repository.CoinRepository
import com.example.crypto.util.Resource
import com.example.crypto.util.Timeframe
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCoinHistoryUseCase @Inject constructor(
    private val repository: CoinRepository
) {
    operator fun invoke(id: String, timeframe: Timeframe): Flow<Resource<List<Candle>>> = flow {
        try {
            emit(Resource.Loading())
            // Basic validation for id
            if (id.isBlank()) {
                emit(Resource.Error("Coin ID cannot be blank."))
                return@flow
            }
            val history = repository.getCoinHistory(id, timeframe)
            // Assuming repository.getCoinHistory() returns Resource<List<Candle>>
            emit(history)
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }
}
