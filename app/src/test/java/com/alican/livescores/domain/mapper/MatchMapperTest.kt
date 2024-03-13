package com.alican.livescores.domain.mapper

import com.alican.livescores.data.local.entity.MatchEntity
import com.alican.livescores.data.remote.response.At
import com.alican.livescores.data.remote.response.Ht
import com.alican.livescores.data.remote.response.MatchResponseModel
import com.alican.livescores.data.remote.response.Sc
import com.alican.livescores.data.remote.response.To
import com.alican.livescores.domain.ui_models.MatchUIModel
import org.junit.Test

class MatchMapperTest {
    @Test
    fun `toEntity should map correctly with favorite true`() {
        // Arrange
        val matchResponseModel = MatchResponseModel(
            i = 123,
            to = To(n = "Example Organization", flag = "🇺🇸"),
            ht = Ht(n = "Home Team"),
            at = At(n = "Away Team"),
            sc = Sc(
                ht = Ht(r = 2),
                at = At(r = 1)
            )
        )
        val isFavorite = true

        // Act
        val matchEntity = matchResponseModel.toEntity(isFavorite)

        // Assert
        assert(matchEntity.organization == "Example Organization")
        assert(matchEntity.countryFlag == "🇺🇸")
        assert(matchEntity.homeTeam == "Home Team")
        assert(matchEntity.awayTeam == "Away Team")
        assert(matchEntity.score == "2 - 1")
        assert(matchEntity.isFavorite)
    }

    @Test
    fun `toEntity should map correctly with favorite false`() {
        // Arrange
        val matchResponseModel = MatchResponseModel(
            i = 123,
            to = To(n = "Example Organization", flag = "🇺🇸"),
            ht = Ht(n = "Home Team"),
            at = At(n = "Away Team"),
            sc = Sc(
                ht = Ht(r = 2),
                at = At(r = 1)
            )
        )
        val isFavorite = false

        // Act
        val matchEntity = matchResponseModel.toEntity(isFavorite)

        // Assert
        assert(matchEntity.id == 123)
        assert(matchEntity.organization == "Example Organization")
        assert(matchEntity.countryFlag == "🇺🇸")
        assert(matchEntity.homeTeam == "Home Team")
        assert(matchEntity.awayTeam == "Away Team")
        assert(matchEntity.score == "2 - 1")
        assert(!matchEntity.isFavorite)
    }

    @Test
    fun `toUIModel should map correctly`() {
        // Arrange
        val matchEntity = MatchEntity(
            id = 123,
            organization = "Example Organization",
            countryFlag = "🇺🇸",
            homeTeam = "Home Team",
            awayTeam = "Away Team",
            score = "2 - 1",
            isFavorite = true
        )

        // Act
        val matchUIModel = matchEntity.toUIModel()

        // Assert
        assert(matchUIModel.id == 123)
        assert(matchUIModel.organization == "Example Organization")
        assert(matchUIModel.countryFlag == "🇺🇸")
        assert(matchUIModel.homeTeam == "Home Team")
        assert(matchUIModel.awayTeam == "Away Team")
        assert(matchUIModel.score == "2 - 1")
        assert(matchUIModel.isFavorite)
    }

    @Test
    fun `toFavoriteEntity should map correctly`() {
        // Arrange
        val matchUIModel = MatchUIModel(
            id = 123,
            organization = "Example Organization",
            countryFlag = "🇺🇸",
            homeTeam = "Home Team",
            awayTeam = "Away Team",
            score = "2 - 1",
            isFavorite = true
        )

        // Act
        val favoriteMatchEntity = matchUIModel.toFavoriteEntity()

        // Assert
        assert(favoriteMatchEntity.id == 123)
        assert(favoriteMatchEntity.organization == "Example Organization")
        assert(favoriteMatchEntity.countryFlag == "🇺🇸")
        assert(favoriteMatchEntity.homeTeam == "Home Team")
        assert(favoriteMatchEntity.awayTeam == "Away Team")
        assert(favoriteMatchEntity.score == "2 - 1")
    }
}