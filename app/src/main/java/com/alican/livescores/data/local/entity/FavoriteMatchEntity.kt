package com.alican.livescores.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_matches")
data class FavoriteMatchEntity(
    @PrimaryKey
    val id: Int,
    val organization: String,
    val countryFlag: String,
    val homeTeam: String,
    val awayTeam: String,
    val score: String,
)
