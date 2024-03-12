package com.alican.livescores.ui.matches

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.alican.livescores.domain.Default
import com.alican.livescores.domain.Empty
import com.alican.livescores.domain.Error
import com.alican.livescores.domain.Loading
import com.alican.livescores.domain.Success
import com.alican.livescores.domain.ui_models.MatchUIModel
import com.alican.livescores.utils.FavoriteActionType
import com.alican.livescores.utils.ScreenRoutes

@Composable
fun MatchesScreen(
    viewModel: MatchesViewModel = hiltViewModel(),
    openMatchDetail: (String) -> Unit
) {

    val context = LocalContext.current
    val uiStates by viewModel.uiStates.collectAsStateWithLifecycle()
    val favoriteState by viewModel.favoriteState.collectAsStateWithLifecycle()

    when (favoriteState) {
        is Default -> {}
        is Empty -> {}
        is Error -> {}
        is Loading -> {}
        is Success -> {
            val type = (favoriteState as Success<FavoriteActionType>).response
            val message =
                if (type == FavoriteActionType.Added) "Favorilere eklendi" else "Favorilerden kaldırıldı"
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            viewModel.updateUIEvents(MatchesScreenUIEvents.ResetFavoriteState)
        }
    }


    if (uiStates.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
    if (uiStates.isError) {
        // olası hata durumunda hata mesajını basıp tekrar istek atılabilir bir akış yapıyoruz.
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                text = "Tekrar Dene",
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.clickable {
                    viewModel.updateUIEvents(MatchesScreenUIEvents.SendRequestAgain)
                })
        }
        Toast.makeText(context, uiStates.errorMessage, Toast.LENGTH_SHORT).show()
    }
    if (uiStates.isSuccess) {
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(MaterialTheme.colorScheme.onBackground),
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            uiStates.groupedMatches?.let {
                it.map { (organization, matches) ->
                    matches.groupBy { matchList -> matchList.countryFlag }
                        .map { (countryFlag, matches) ->
                            MatchCategoryComponent(
                                matches = matches,
                                organization = organization,
                                countryFlag = countryFlag,
                                openMatchDetail = openMatchDetail,
                                favoriteClicked = { isFavorite, match ->
                                    viewModel.updateUIEvents(
                                        MatchesScreenUIEvents.AddToFavorite(
                                            isFavorite,
                                            match
                                        )
                                    )
                                }
                            )
                        }

                }
            }
        }


    }

}

@Composable
fun MatchCategoryComponent(
    matches: List<MatchUIModel>,
    organization: String,
    countryFlag: String,
    openMatchDetail: (String) -> Unit,
    favoriteClicked: (isFavorite: Boolean, match: MatchUIModel) -> Unit
) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp), verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            AsyncImage(
                model = countryFlag, contentDescription = "", modifier = Modifier
                    .size(24.dp)
                    .clip(
                        CircleShape
                    )
            )
            Text(
                text = organization,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
        MatchesComponent(matches = matches, openMatchDetail, favoriteClicked)
    }
}

@Composable
fun MatchesComponent(
    matches: List<MatchUIModel>,
    openMatchDetail: (String) -> Unit,
    favoriteClicked: (isFavorite: Boolean, match: MatchUIModel) -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(5.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.onPrimary)
    ) {
        matches.forEach { match ->
            MatchRowComponent(match, openMatchDetail, favoriteClicked)
        }

    }
}

@Composable
fun MatchRowComponent(
    match: MatchUIModel,
    openMatchDetail: (String) -> Unit,
    favoriteClicked: (isFavorite: Boolean, match: MatchUIModel) -> Unit
) {

    var isFavorite by rememberSaveable { mutableStateOf(match.isFavorite) }
    Row(
        Modifier
            .fillMaxWidth()
            .clickable {
                openMatchDetail(
                    ScreenRoutes.MATCH_DETAIL_SCREEN.replace(
                        oldValue = "{id}",
                        newValue = match.id.toString()
                    )
                )
            }
            .padding(horizontal = 8.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "MS",
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.weight(1f)
        )
        MatchScoreComponent(
            match,
            modifier = Modifier.weight(6f)
        )
        Icon(
            imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
            contentDescription = "favorite",
            modifier = Modifier
                .weight(1f)
                .size(20.dp)
                .clickable {
                    favoriteClicked(isFavorite, match)
                    isFavorite = isFavorite.not()
                }
        )
    }


}

@Composable
fun MatchScoreComponent(
    match: MatchUIModel,
    modifier: Modifier
) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = match.homeTeam, style = MaterialTheme.typography.labelSmall)
            Text(text = match.awayTeam, style = MaterialTheme.typography.labelSmall)
        }
        Row(
            modifier = Modifier
                .align(Alignment.Center)
                .clip(RoundedCornerShape(5.dp))
                .background(MaterialTheme.colorScheme.onSurfaceVariant)
        ) {
            Text(
                text = match.score,
                modifier = Modifier.padding(horizontal = 4.dp),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}