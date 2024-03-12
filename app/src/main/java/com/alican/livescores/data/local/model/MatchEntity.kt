package com.alican.livescores.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "matches")
data class MatchEntity(
    @PrimaryKey
    val id: String,
    val organization: String,
    val countryFlag: String,
    val homeTeam: String,
    val awayTeam: String,
    val score: String,
    val isFavorite: Boolean = false
)
