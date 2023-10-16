package com.lek.spaceimpact

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize
import com.lek.spaceimpact.ui.component.GameView
import com.lek.spaceimpact.ui.state.DownDirectionClicked
import com.lek.spaceimpact.ui.state.ExplosionRendered
import com.lek.spaceimpact.ui.state.GamePaused
import com.lek.spaceimpact.ui.state.GameResumed
import com.lek.spaceimpact.ui.state.GunFired
import com.lek.spaceimpact.ui.state.KeyReleased
import com.lek.spaceimpact.ui.state.LeftDirectionClicked
import com.lek.spaceimpact.ui.state.RightDirectionClicked
import com.lek.spaceimpact.ui.state.UpDirectionClicked
import com.lek.spaceimpact.ui.theme.SpaceImpactTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: GameViewModel by viewModels()

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
                            explosions = state.explosions,
                            isPaused = state.isRunning.not() && state.isGameOver.not(),
                            onGameRendered = {
                                if (it != IntSize(0, 0) && gameStarted.not()) {
                                    viewModel.startGame(
                                        gameScreenWidth = it.width.toFloat(),
                                        gameScreenHeight = it.height.toFloat(),
                                    )
                                    gameStarted = true
                                }
                            },
                            onLeftPressed = {
                                Log.d("PRESSED", "LEFT")
                                viewModel.onEvent(LeftDirectionClicked)
                            },
                            onRightPressed = {
                                viewModel.onEvent(RightDirectionClicked)
                            },
                            onUpPressed = {
                                viewModel.onEvent(UpDirectionClicked)
                            },
                            onDownPressed = {
                                viewModel.onEvent(DownDirectionClicked)
                            },
                            onFireBulletPressed = {
                                viewModel.onEvent(GunFired)
                            },
                            onKeyReleased = { viewModel.onEvent(KeyReleased) },
                            onPauseGameClicked = { viewModel.onEvent(GamePaused) },
                            onResumeGameClicked = { viewModel.onEvent(GameResumed) },
                            onQuitGameClicked = {
                                // show quit game dialog
                            },
                            onExplosionRendered = { viewModel.onEvent(ExplosionRendered(it)) }
                        )
                    }
                }
            }
        }
    }
}