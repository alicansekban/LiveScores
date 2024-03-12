package com.alican.livescores.domain.ui_models


data class MatchUIModel(
    val id: Int,
    val organization: String,
    val countryFlag: String,
    val homeTeam: String,
    val awayTeam: String,
    val score: String,
    val isFavorite: Boolean = false
)
