package com.example.crypto.screen.history

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crypto.domain.model.Candle
import com.example.crypto.domain.use_case.GetCoinHistoryUseCase
import com.example.crypto.util.Resource
import com.example.crypto.util.Timeframe
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getCoinHistoryUseCase: GetCoinHistoryUseCase, // Injected use case
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(HistoryState())
    val state: StateFlow<HistoryState> = _state.asStateFlow()

    private var currentCoinId: String? = null

    init {
        savedStateHandle.get<String>("id")?.let { coinId ->
            currentCoinId = coinId
            _state.value = _state.value.copy(coinId = coinId) // Store coinId in state
            // Initial fetch with default timeframe or saved timeframe
            fetchCoinHistory(coinId, _state.value.selectedTimeframe)
        }
    }

    fun onEvent(event: HistoryEvent) {
        when (event) {
            is HistoryEvent.ChangeTimeframe -> {
                val newTimeframe = event.timeframe
                _state.value = _state.value.copy(selectedTimeframe = newTimeframe)
                currentCoinId?.let { coinId ->
                    fetchCoinHistory(coinId, newTimeframe)
                } ?: run {
                     _state.value = _state.value.copy(error = "Coin ID missing. Cannot fetch history for new timeframe.")
                }
            }
        }
    }

    private fun fetchCoinHistory(coinId: String, timeframe: Timeframe) {
        getCoinHistoryUseCase(id = coinId, timeframe = timeframe).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        candles = result.data ?: emptyList(),
                        isLoading = false,
                        error = null
                        // coinId is already set or remains as is
                    )
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = result.message ?: "An unexpected error occurred"
                    )
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true, error = null)
                }
            }
        }.launchIn(viewModelScope)
    }
}
