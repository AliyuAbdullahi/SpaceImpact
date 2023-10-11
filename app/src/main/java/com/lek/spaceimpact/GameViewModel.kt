package com.lek.spaceimpact

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lek.spaceimpact.ui.EnemiesGenerator
import com.lek.spaceimpact.ui.entities.Enemy
import com.lek.spaceimpact.ui.entities.Player
import com.lek.spaceimpact.ui.state.GameState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// 60 FPS
private const val FRAME_PER_SECOND_IN_MILLISECONDS = 17L

@HiltViewModel
class GameViewModel @Inject constructor() : ViewModel() {

    private var mutableState: MutableStateFlow<GameState> = MutableStateFlow(GameState.EMPTY)
    val state: StateFlow<GameState> = mutableState

    private val enemiesPool = mutableListOf<Enemy>()

    private var gameSpeed = 3

    private var currentTime = 0L

    private var gameRunner: MutableStateFlow<Int> = MutableStateFlow(0)

    private fun takeEnemies(count: Int): List<Enemy> {
        return if (enemiesPool.isNotEmpty()) {
            val enemies = enemiesPool.take(count)
            enemies.forEach { enemiesPool.remove(it) }
            enemies
        } else {
            emptyList()
        }
    }

    fun startGame(
        gameScreenWidth: Float,
        gameScreenHeight: Float
    ) {
        val enemies = EnemiesGenerator.generateEnemies(gameScreenWidth.toInt(), 10, 40)
        enemiesPool.addAll(enemies)
        enemiesPool.forEach {
            Log.d("ENEMY: ", it.toString())
        }
        val currentEnemies = takeEnemies(5)

        // based on the screenWidth and height, we generate height and x position of enemies.
        updateState {
            copy(
                isRunning = true,
                enemies = currentEnemies,
                player = Player(
                    healthCount = 3,
                    bulletCount = 40,
                    xPos = gameScreenWidth / 2 - Player.PLAYER_SIZE / 2,
                    yPos = gameScreenHeight - (Player.PLAYER_SIZE)
                ),
                screenWidth = gameScreenWidth,
                screenHeight = gameScreenHeight
            )
        }
        currentTime = System.currentTimeMillis()
        startUpdate()
    }

    private fun startUpdate() {
        viewModelScope.launch {
            gameRunner.collect {
                // check positions
                delay(FRAME_PER_SECOND_IN_MILLISECONDS)
                val isTimePassed = System.currentTimeMillis() - currentTime >= 4000L
                val update = if (isTimePassed) {
                    takeEnemies(5)
                } else {
                    emptyList()
                }
                val updatedEnemies = state.value.enemies.toMutableList().apply {
                    if (update.isNotEmpty()) {
                        addAll(update)
                    }
                }.map { it.copy(yPos = if (it.yPos >= state.value.screenHeight) -Enemy.ENEMY_SIZE else it.yPos + gameSpeed) }
                updateState { copy(enemies = updatedEnemies) }
                gameRunner.emit((gameRunner.value + 1) % 10)
            }
        }
    }

    private fun updateState(reducer: GameState.() -> GameState) {
        mutableState.value = mutableState.value.reducer()
        if (mutableState.value.isRunning) {
            mutableState.tryEmit(mutableState.value)
        }
    }
}