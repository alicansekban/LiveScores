package com.alican.livescores.ui.matches

import com.alican.livescores.domain.ui_models.MatchUIModel

sealed class MatchesScreenUIEvents {
    data class AddToFavorite(val match: MatchUIModel) : MatchesScreenUIEvents()
}

data class MatchesScreenUIModel(
    val matches: Map<String, List<MatchUIModel>>? = null,
    val isSuccess: Boolean = false,
    val isLoading: Boolean = true,
    val errorMessage: String = "",
    val isError: Boolean = false
)