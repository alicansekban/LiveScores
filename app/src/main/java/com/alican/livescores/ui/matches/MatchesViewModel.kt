package com.alican.livescores.ui.matches

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alican.livescores.domain.BaseUIModel
import com.alican.livescores.domain.Default
import com.alican.livescores.domain.Empty
import com.alican.livescores.domain.Error
import com.alican.livescores.domain.Loading
import com.alican.livescores.domain.Success
import com.alican.livescores.domain.ui_models.MatchUIModel
import com.alican.livescores.domain.use_case.AddToFavoriteUseCase
import com.alican.livescores.domain.use_case.GetMatchesUseCase
import com.alican.livescores.domain.use_case.RemoveFromFavoriteUseCase
import com.alican.livescores.utils.FavoriteActionType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MatchesViewModel @Inject constructor(
    private val matchesUseCase: GetMatchesUseCase,
    private val addToFavoriteUseCase: AddToFavoriteUseCase,
    private val removeFromFavoriteUseCase: RemoveFromFavoriteUseCase
) : ViewModel() {

    private val _uiStates = MutableStateFlow(MatchesScreenUIModel())
    val uiStates: StateFlow<MatchesScreenUIModel>
        get() = _uiStates.stateIn(
            viewModelScope,
            SharingStarted.Eagerly, MatchesScreenUIModel()
        )
    private val _favoriteState = MutableStateFlow<BaseUIModel<FavoriteActionType>>(Default())
    val favoriteState: StateFlow<BaseUIModel<FavoriteActionType>>
        get() = _favoriteState.stateIn(
            viewModelScope,
            SharingStarted.Eagerly, Default()
        )

    init {
        getMatches()
    }

    fun updateUIEvents(event: MatchesScreenUIEvents) {
        when (event) {
            is MatchesScreenUIEvents.AddToFavorite -> {
                handleFavoriteClick(event.isFavorite, event.match)
            }

            MatchesScreenUIEvents.ResetFavoriteState -> {
                _favoriteState.value = Default()
            }
        }
    }

    private fun handleFavoriteClick(isFavorite: Boolean, match: MatchUIModel) {
        viewModelScope.launch(Dispatchers.IO) {
            if (isFavorite) {
                removeFromFavoriteUseCase.invoke(match.id).collect {
                    _favoriteState.emit(it)
                }
            } else {
                addToFavoriteUseCase.invoke(match).collect {
                    _favoriteState.emit(it)
                }
            }
        }
    }

    private fun getMatches() {
        viewModelScope.launch(Dispatchers.IO) {
            matchesUseCase().collect { state ->
                when (state) {
                    is Default -> {}
                    is Empty -> {
                        _uiStates.value = _uiStates.value.copy(
                            isLoading = false,
                            isSuccess = true,
                        )
                    }

                    is Error -> {
                        _uiStates.value = _uiStates.value.copy(
                            isLoading = false,
                            isError = true,
                            errorMessage = state.errorMessage
                        )
                    }

                    is Loading -> {
                        _uiStates.value = _uiStates.value.copy(isLoading = true)
                    }

                    is Success -> {
                        _uiStates.value = _uiStates.value.copy(
                            isLoading = false,
                            isSuccess = true,
                            groupedMatches = state.response.groupBy { it.organization }
                        )
                    }
                }
            }
        }
    }
}