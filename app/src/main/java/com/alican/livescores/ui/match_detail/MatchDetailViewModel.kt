package com.alican.livescores.ui.match_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alican.livescores.domain.BaseUIModel
import com.alican.livescores.domain.Loading
import com.alican.livescores.domain.ui_models.MatchUIModel
import com.alican.livescores.domain.use_case.GetMatchDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MatchDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getMatchDetailUseCase: GetMatchDetailUseCase
) : ViewModel() {

    private val id = checkNotNull(savedStateHandle.get<String>("id"))

    private val _matchDetail = MutableStateFlow<BaseUIModel<MatchUIModel>>(Loading())
    val matchDetail: StateFlow<BaseUIModel<MatchUIModel>>
        get() = _matchDetail.stateIn(
            viewModelScope,
            SharingStarted.Eagerly, Loading()
        )

    init {
        getMatchDetail(id.toInt())
    }

    private fun getMatchDetail(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            getMatchDetailUseCase.invoke(id).collect {
                _matchDetail.emit(it)
            }
        }
    }
}