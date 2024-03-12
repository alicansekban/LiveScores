package com.alican.livescores.ui.match_detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alican.livescores.domain.Default
import com.alican.livescores.domain.Empty
import com.alican.livescores.domain.Error
import com.alican.livescores.domain.Loading
import com.alican.livescores.domain.Success
import com.alican.livescores.domain.ui_models.MatchUIModel
import com.alican.livescores.ui.matches.MatchScoreComponent

@Composable
fun MatchDetailScreen(
    viewModel: MatchDetailViewModel = hiltViewModel()
) {

    val matchDetail by viewModel.matchDetail.collectAsStateWithLifecycle()

    when (matchDetail) {
        is Default -> {}
        is Empty -> {}
        is Error -> {}
        is Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is Success -> {
            val matchDetailData = (matchDetail as Success<MatchUIModel>).response
            MatchDetailUIComponent(matchUIModel = matchDetailData)
        }
    }
}

@Composable
fun MatchDetailUIComponent(
    matchUIModel: MatchUIModel
) {

    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        MatchScoreComponent(match = matchUIModel, modifier = Modifier.padding(horizontal = 40.dp))
    }
}