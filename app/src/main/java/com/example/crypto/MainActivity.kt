package com.example.crypto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.crypto.screen.coin.CoinScreen
import com.example.crypto.screen.coin.CoinViewModel
import com.example.crypto.screen.history.HistoryEvent
import com.example.crypto.screen.history.HistoryScreen
import com.example.crypto.screen.history.HistoryViewModel
import com.example.crypto.ui.theme.CryptoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CryptoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = NavScreen.CoinScreen.route
                    ) {

                        composable(NavScreen.CoinScreen.route) {
                            val coinViewModel = hiltViewModel<CoinViewModel>()
                            val coinState by coinViewModel.state.collectAsStateWithLifecycle()

                            CoinScreen(
                                navController = navController,
                                coinState = coinState,
                                onErrorButtonClick = {
                                    coinViewModel.getCoin()
                                }
                            )
                        }

                        composable(NavScreen.HistoryScreen.route) {
                            val historyViewModel = hiltViewModel<HistoryViewModel>()
                            val historyState by historyViewModel.state.collectAsStateWithLifecycle()

                            val coinName = it.arguments?.getString("id") ?: ""

                            HistoryScreen(
                                navController = navController,
                                historyState = historyState,
                                coinName = coinName,
                                onErrorButtonClick = {
                                    historyViewModel.onEvent(
                                        HistoryEvent.ChangeTimeframe(
                                            historyState.selectedTimeframe
                                        )
                                    )
                                },
                                onEvent = historyViewModel::onEvent
                            )
                        }
                    }
                }
            }
        }
    }
}

sealed class NavScreen(val route: String) {

    data object CoinScreen : NavScreen("coinScreen")

    data object HistoryScreen : NavScreen("historyScreen/{id}/{symbol}") {
        fun passParams(
            id: String,
            symbol: String
        ): String {
            return "historyScreen/$id/$symbol"
        }
    }
}