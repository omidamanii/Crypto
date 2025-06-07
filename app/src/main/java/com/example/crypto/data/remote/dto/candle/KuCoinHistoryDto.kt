package com.example.crypto.data.remote.dto.candle

data class KuCoinHistoryDto(
    val code: String?, // e.g., "200000" for success
    val data: List<List<String>>?
    // Each inner list is expected to be: [time, open, close, high, low, volume, turnover]
    // Example: ["1583942400","0.02507","0.02508","0.02508","0.02507","0.5","0.0125369"]
    // time: start time of the candle (Unix timestamp in seconds)
    // open: open price
    // close: close price
    // high: high price
    // low: low price
    // volume: transaction volume
    // turnover: transaction turnover
)
