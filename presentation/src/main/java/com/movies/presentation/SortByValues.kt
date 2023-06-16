package com.movies.presentation

enum class SortByValues(val text: String) {
    BY_NAME_FROM_A_TO_Z("Name(A - Z)"),
    BY_NAME_FROM_Z_TO_A("Name(Z - A)"),
    BY_PRICE_FROM_CHEAP_TO_EXPENSIVE("Price(from cheap to expensive)"),
    BY_PRICE_FROM_EXPENSIVE_TO_CHEAP("Price(from expensive to cheap)")
}