package com.example.crypto.data.remote.dto.coin

data class CoinDto(
    val data: List<DataDto>?,
    val timestamp: Long?
)

data class DataDto(
    val id: String?,
    val rank: String?,
    val symbol: String?,
    val name: String?,
    val supply: String?,
    val maxSupply: String?,
    val marketCapUsd: String?,
    val volumeUsd24Hr: String?,
    val priceUsd: String?,
    val changePercent24Hr: String?,
    val vwap24Hr: String?,
    val explorer: String?
)