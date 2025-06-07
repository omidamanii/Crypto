package com.example.crypto.data.mapper

import com.example.crypto.data.remote.dto.coin.CoinDto
import com.example.crypto.data.remote.dto.coin.DataDto
import com.example.crypto.domain.model.Coin

fun DataDto.toDomainCoin(): Coin {
    return Coin(
        id = id ?: "",
        rank = rank ?: "",
        symbol = symbol ?: "",
        name = name ?: "",
        supply = supply,
        maxSupply = maxSupply,
        marketCapUsd = marketCapUsd,
        volumeUsd24Hr = volumeUsd24Hr,
        priceUsd = priceUsd,
        changePercent24Hr = changePercent24Hr,
        vwap24Hr = vwap24Hr,
        explorer = explorer
    )
}

fun CoinDto.toDomainCoinList(): List<Coin> {
    return data?.mapNotNull { it.toDomainCoin() } ?: emptyList()
}

// It's also good practice to have a mapper from Domain to DTO if you ever need to send data back
// For now, we are only reading data.
