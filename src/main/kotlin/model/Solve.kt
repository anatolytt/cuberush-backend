package com.model

import kotlinx.serialization.Serializable

@Serializable
data class Solve(
    val result: Int,
    val scramble: String,
    val penalty: Int,
    val comment: String = "",
    val date: String
)
