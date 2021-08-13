package com.gekaradchenko.coursespbandnbukotlin.nbu

data class NBUCourse(
    val cc: String,
    val exchangedate: String,
    val r030: Int,
    val rate: Double,
    val txt: String,
    var isSelected: Boolean = false,
)