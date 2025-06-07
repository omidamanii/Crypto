package com.example.crypto.screen.coin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crypto.domain.model.Coin
import com.example.crypto.domain.use_case.GetCoinsUseCase
import com.example.crypto.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CoinViewModel @Inject constructor(
    private val getCoinsUseCase: GetCoinsUseCase // Injected use case
) : ViewModel() {

    private val _state = MutableStateFlow(CoinState())
    val state: StateFlow<CoinState> = _state.asStateFlow()

    init {
        getCoins() // Renamed from getCoin to getCoins
    }

    // Renamed to getCoins as it fetches a list and we take the first for the state.
    // If CoinState were to hold List<Coin>, this would be more direct.
    fun getCoins() {
        getCoinsUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = CoinState(coin = result.data?.firstOrNull(), isLoading = false, error = null)
                }
                is Resource.Error -> {
                    _state.value = CoinState(
                        isLoading = false,
                        error = result.message ?: "An unexpected error occurred"
                    )
                }
                is Resource.Loading -> {
                    _state.value = CoinState(isLoading = true, error = null)
                }
            }
        }.launchIn(viewModelScope)
    }
}
