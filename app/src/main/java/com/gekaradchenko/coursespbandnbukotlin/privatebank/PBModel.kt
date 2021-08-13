package com.gekaradchenko.coursespbandnbukotlin.privatebank

data class PB(
    val bank: String,
    val baseCurrency: Int,
    val baseCurrencyLit: String,
    val date: String,
    val exchangeRate: List<PBCourse>,
)

data class PBCourse(
    val baseCurrency: String?,
    val currency: String?,
    val purchaseRate: Double?,
    val purchaseRateNB: Double?,
    val saleRate: Double?,
    val saleRateNB: Double?,
)