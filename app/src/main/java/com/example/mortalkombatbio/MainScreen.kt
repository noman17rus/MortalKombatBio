package com.example.mortalkombatbio

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.mortalkombatbio.data.DataSource
import com.example.mortalkombatbio.data.Hero
import com.example.mortalkombatbio.ui.theme.MortalKombatBioTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(herolist: List<Hero>, modifier: Modifier = Modifier) {
    Scaffold(
        topBar = { MKTopAppBar() },
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_small))
    ) {
        val visibleState = remember {
            MutableTransitionState(false).apply {
                // Start the animation immediately.
                targetState = true
            }
        }

        AnimatedVisibility(
            visibleState = visibleState,
            enter = fadeIn(
                animationSpec = spring(dampingRatio = Spring.DampingRatioLowBouncy)
            ),
            exit = fadeOut(),
            modifier = modifier
        ) {
            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                itemsIndexed(herolist) { index, item ->
                    HeroItem(hero = item,
                        modifier = modifier.animateEnterExit(
                            enter = slideInVertically(
                                animationSpec = spring(
                                    stiffness = Spring.StiffnessLow,
                                    dampingRatio = Spring.DampingRatioLowBouncy
                                ),
                                initialOffsetY = { it * (index + 1) } // staggered entrance
                            )
                        ))
                    Spacer(modifier = modifier.size(dimensionResource(id = R.dimen.padding_small)))
                }
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MKTopAppBar(modifier: Modifier = Modifier) {
    MortalKombatBioTheme {
        CenterAlignedTopAppBar(modifier = modifier,
            title = {
                Row {
                    Text(
                        text = stringResource(R.string.mk_1_biography),
                        style = MaterialTheme.typography.displayMedium
                    )
                }
            }
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun MainPreview() {
    MortalKombatBioTheme {
        MainScreen(herolist = DataSource.dataHeroes)
    }
}

