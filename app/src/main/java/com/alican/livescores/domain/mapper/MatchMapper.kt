package com.alican.livescores.domain.mapper

import com.alican.livescores.data.local.entity.FavoriteMatchEntity
import com.alican.livescores.data.local.entity.MatchEntity
import com.alican.livescores.data.remote.response.MatchResponseModel
import com.alican.livescores.domain.ui_models.MatchUIModel


fun MatchResponseModel.toEntity(isFavorite: Boolean): MatchEntity {
    return MatchEntity(
        id = this.i ?: 0,
        organization = this.to?.n ?: "",
        countryFlag = this.to?.flag ?: "",
        homeTeam = this.ht?.n ?: "",
        awayTeam = this.at?.n ?: "",
        score = this.getMatchScore(),
        isFavorite = isFavorite
    )
}

fun MatchEntity.toUIModel(): MatchUIModel {
    return MatchUIModel(
        id = this.id,
        organization = this.organization,
        countryFlag = this.countryFlag,
        homeTeam = this.homeTeam,
        awayTeam = this.awayTeam,
        score = this.score,
        isFavorite = this.isFavorite
    )
}

fun MatchUIModel.toFavoriteEntity(): FavoriteMatchEntity {
    return FavoriteMatchEntity(
        id = this.id,
        organization = this.organization,
        countryFlag = this.countryFlag,
        homeTeam = this.homeTeam,
        awayTeam = this.awayTeam,
        score = this.score,
    )
}