package com.example.crypto.domain.use_case

import com.example.crypto.domain.model.Coin
import com.example.crypto.domain.repository.CoinRepository
import com.example.crypto.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCoinsUseCase @Inject constructor(
    private val repository: CoinRepository
) {
    operator fun invoke(): Flow<Resource<List<Coin>>> = flow {
        try {
            emit(Resource.Loading())
            val coins = repository.getCoins()
            // Assuming repository.getCoins() itself returns Resource<List<Coin>>
            // If it returns List<Coin> directly and Resource wrapping is done here:
            // emit(Resource.Success(coins))
            // Based on our current CoinRepository interface, it returns Resource directly.
            emit(coins)
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
        // No specific logic for HttpMediaTypeNotAcceptableException or IOException here,
        // assuming the repository handles this and returns an appropriate Resource.Error.
    }
}
