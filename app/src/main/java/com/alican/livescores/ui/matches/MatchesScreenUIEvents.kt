package com.alican.livescores.ui.matches

import com.alican.livescores.domain.ui_models.MatchUIModel

sealed class MatchesScreenUIEvents {
    data class AddToFavorite(val isFavorite: Boolean, val match: MatchUIModel) :
        MatchesScreenUIEvents()
    data object ResetFavoriteState : MatchesScreenUIEvents()
    data object SendRequestAgain : MatchesScreenUIEvents()
}

data class MatchesScreenUIModel(
    val groupedMatches: Map<String, List<MatchUIModel>>? = null,
    val isSuccess: Boolean = false,
    val isLoading: Boolean = true,
    val errorMessage: String = "",
    val isError: Boolean = false
)