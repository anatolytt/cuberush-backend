package com.model

enum class Penalty {
    DNF,
    NONE,
    PLUS2;

    fun getIntFromPenalty() : Int {
        return when(this) {
            DNF -> 0
            PLUS2 -> 1
            NONE -> 2
        }
    }
}