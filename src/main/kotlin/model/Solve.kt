package com.model

import kotlinx.serialization.Serializable

@Serializable
data class Solve(
    val result: Int,
    val scramble: String,
    val penalty: Penalty,
    val comment: String = "",
    val date: String,
    val event: Events
)
