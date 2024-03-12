package com.alican.livescores.ui.matches

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alican.livescores.domain.Default
import com.alican.livescores.domain.Empty
import com.alican.livescores.domain.Error
import com.alican.livescores.domain.Loading
import com.alican.livescores.domain.Success
import com.alican.livescores.domain.use_case.GetMatchesUseCase
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
    private val matchesUseCase: GetMatchesUseCase
) : ViewModel() {

    private val _uiStates = MutableStateFlow<MatchesScreenUIModel>(MatchesScreenUIModel())
    val uiStates: StateFlow<MatchesScreenUIModel>
        get() = _uiStates.stateIn(
            viewModelScope,
            SharingStarted.Eagerly, MatchesScreenUIModel()
        )


    init {
        getMatches()
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
                            matches = state.response.groupBy { it.organization }
                        )
                    }
                }
            }
        }
    }
}