package com.example.crypto.screen.history.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.crypto.screen.history.HistoryEvent
import com.example.crypto.util.Timeframe

@Composable
fun TimeframeSelector(
    selectedTimeframe: Timeframe,
    onEvent: (HistoryEvent) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        for (timeframe in Timeframe.getTimeframes()) {
            FilterChip(
                selected = timeframe == selectedTimeframe,
                onClick = {
                    onEvent(HistoryEvent.ChangeTimeframe(timeframe))
                },
                label = {
                    Text(text = timeframe.timeframe)
                }
            )
        }
    }
}