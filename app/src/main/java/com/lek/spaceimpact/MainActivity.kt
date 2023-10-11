package com.lek.spaceimpact

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.lek.spaceimpact.component.GameView
import com.lek.spaceimpact.component.dpToPx
import com.lek.spaceimpact.component.pxToDp
import com.lek.spaceimpact.ui.theme.SpaceImpactTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val viewModel: GameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SpaceImpactTheme {
                val state = viewModel.state.collectAsState().value
                // A surface container using the 'background' color from the theme


                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var size by remember {
                        mutableStateOf(IntSize(0, 0))
                    }
                    var gameStarted by remember {
                        mutableStateOf(false)
                    }
                    val heightChanged by remember {
                        derivedStateOf { size != IntSize(0, 0) }
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .onSizeChanged {
                                size = it
                            },
                    ) {
                        GameView(
                            player = state.player,
                            enemies = state.enemies,
                            bullets = state.bullets,
                            onGameRendered = {
                                if (it != IntSize(0, 0) && gameStarted.not()) {
                                    viewModel.startGame(
                                        gameScreenWidth = it.width.toFloat(),
                                        gameScreenHeight = it.height.toFloat(),
                                    )
                                    gameStarted = true
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}