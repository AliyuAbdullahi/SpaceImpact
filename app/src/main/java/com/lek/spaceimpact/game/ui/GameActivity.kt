package com.lek.spaceimpact.game.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntSize
import com.lek.spaceimpact.R
import com.lek.spaceimpact.game.state.DialogCancelled
import com.lek.spaceimpact.game.state.DownDirectionClicked
import com.lek.spaceimpact.game.state.ExplosionRendered
import com.lek.spaceimpact.game.state.GamePaused
import com.lek.spaceimpact.game.state.GameResumed
import com.lek.spaceimpact.game.state.GameState
import com.lek.spaceimpact.game.state.GunFired
import com.lek.spaceimpact.game.state.KeyReleased
import com.lek.spaceimpact.game.state.LeftDirectionClicked
import com.lek.spaceimpact.game.state.OnQuitGameRequested
import com.lek.spaceimpact.game.state.RestartGame
import com.lek.spaceimpact.game.state.RightDirectionClicked
import com.lek.spaceimpact.game.state.SystemPaused
import com.lek.spaceimpact.game.state.SystemResumed
import com.lek.spaceimpact.game.state.UpDirectionClicked
import com.lek.spaceimpact.game.state.ViewDestroyed
import com.lek.spaceimpact.game.ui.component.DialogAction
import com.lek.spaceimpact.game.ui.component.GameDialogData
import com.lek.spaceimpact.game.ui.component.GameView
import com.lek.spaceimpact.game.ui.component.SuccessDialog
import com.lek.spaceimpact.ui.theme.SpaceImpactTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GameActivity : ComponentActivity() {
    private val viewModel: GameViewModel by viewModels()

    override fun onPause() {
        super.onPause()
        viewModel.onEvent(SystemPaused)
    }

    override fun onResume() {
        super.onResume()
        viewModel.onEvent(SystemResumed)
    }

    override fun onDestroy() {
        viewModel.onEvent(ViewDestroyed)
        super.onDestroy()
    }

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
                            isGameOver = state.isGameOver,
                            isPaused = state.isRunning.not() && state.isGameOver.not(),
                            successDialog = getSuccessDialog(state),
                            gameDialog = getPauseDialog(state),
                            onRestartClicked = {
                                viewModel.onEvent(RestartGame)
                            },
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
                                viewModel.onEvent(OnQuitGameRequested)
                            },
                            onExplosionRendered = { viewModel.onEvent(ExplosionRendered(it)) },
                            onFinishGameClicked = {
                                finish()
                            }
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun getPauseDialog(state: GameState): GameDialogData? {
        return state.gameDialog?.let {
            GameDialogData(
                message = stringResource(R.string.quit_game_question),
                okClicked = {
                    finish()
                },
                cancelClicked = {
                    viewModel.onEvent(DialogCancelled)
                }
            )
        }
    }

    @Composable
    fun getSuccessDialog(state: GameState) =
        if (state.isRunning && state.enemies.isEmpty()) {
            SuccessDialog(
                message = stringResource(R.string.you_have_won), actions = listOf(
                    DialogAction(stringResource(R.string.restart),
                        action = {
                            viewModel.onEvent(RestartGame)
                        }),
                    DialogAction(stringResource(R.string.home), action = {
                        finish()
                    }),
                )
            )
        } else {
            null
        }
}