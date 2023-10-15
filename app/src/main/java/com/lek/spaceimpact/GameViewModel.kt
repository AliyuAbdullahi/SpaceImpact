package com.lek.spaceimpact

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lek.spaceimpact.component.ControllerDirection
import com.lek.spaceimpact.ui.EnemiesGenerator
import com.lek.spaceimpact.ui.entities.Bullet
import com.lek.spaceimpact.ui.entities.Enemy
import com.lek.spaceimpact.ui.entities.Explosion
import com.lek.spaceimpact.ui.entities.Player
import com.lek.spaceimpact.ui.state.DownDirectionClicked
import com.lek.spaceimpact.ui.state.EnemyKilled
import com.lek.spaceimpact.ui.state.ExplosionRendered
import com.lek.spaceimpact.ui.state.GameEvent
import com.lek.spaceimpact.ui.state.GamePaused
import com.lek.spaceimpact.ui.state.GameResumed
import com.lek.spaceimpact.ui.state.GameState
import com.lek.spaceimpact.ui.state.GunFired
import com.lek.spaceimpact.ui.state.KeyReleased
import com.lek.spaceimpact.ui.state.LeftDirectionClicked
import com.lek.spaceimpact.ui.state.RightDirectionClicked
import com.lek.spaceimpact.ui.state.UpDirectionClicked
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// 60 FPS
private const val FRAME_PER_SECOND_IN_MILLISECONDS = 17L

private const val PLAYER_MOVEMENT = 20

private const val BULLET_SPEED = 15

private const val ENEMY_SPEED = 3

@HiltViewModel
class GameViewModel @Inject constructor() : ViewModel() {

    private var mutableState: MutableStateFlow<GameState> = MutableStateFlow(GameState.EMPTY)
    val state: StateFlow<GameState> = mutableState

    private val enemiesPool = mutableListOf<Enemy>()

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
        val enemies = EnemiesGenerator.generateEnemies(gameScreenWidth.toInt(), 15, 40)
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
        startGameLoop()
    }

    private fun startGameLoop() {
        viewModelScope.launch {
            gameRunner.collect {
                // check positions
                if (state.value.isRunning) {
                    delay(FRAME_PER_SECOND_IN_MILLISECONDS)
                    updateEnemiesAndBullets()
                    updateDirection()
                    checkCollision()
                    refreshGameRunner()
                }
            }
        }
    }

    private fun checkCollision() {
        val enemies = state.value.enemies
        val player = state.value.player
        val enemiesToDelete = mutableListOf<Enemy>()
        val hasCollision = enemies.any { enemy ->
            enemy.intersectsWith(player).also {
                if (it) {
                    enemiesToDelete.add(enemy)
                }
            }
        }

        var health = player.healthCount
        if (hasCollision) {
            health = player.healthCount - 1
        }

        val bullets = state.value.bullets
        val bulletsToDelete = mutableListOf<Bullet>()
        enemies.forEach { enemy ->
            for (bullet in bullets) {
                if (enemy.intersectsWith(bullet)) {
                    enemiesToDelete.add(enemy)
                    bulletsToDelete.add(bullet)
                }
            }
        }

        val explosions = enemiesToDelete.map {
            Explosion(
                it.xPos,
                it.yPos
            )
        }

        val newEnemies = enemies.filter { enemiesToDelete.contains(it).not() }
        val newBullets = bullets.filter { bulletsToDelete.contains(it).not() }
        updateState {
            copy(
                enemies = newEnemies,
                bullets = newBullets,
                player = player.copy(healthCount = health),
                explosions = explosions
            )
        }
    }

    private fun updateEnemiesAndBullets() {
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
        }
            .map {
                it.copy(
                    yPos = if (it.yPos >= state.value.screenHeight) -Enemy.ENEMY_SIZE
                    else it.yPos + ENEMY_SPEED
                )
            }

        val updatedBullets = state.value.bullets.toMutableList().map {
            it.copy(yPos = it.yPos - BULLET_SPEED, isActive = it.yPos >= 0)
        }

        updateState {
            copy(
                enemies = updatedEnemies,
                bullets = updatedBullets.filter { it.isActive })
        }
    }

    private suspend fun refreshGameRunner() {
        gameRunner.emit((gameRunner.value + 1) % 10)
    }

    private fun updateDirection() {
        when (state.value.direction) {
            ControllerDirection.UP -> {
                val currentPlayer = state.value.player
                val playerY = (currentPlayer.yPos - PLAYER_MOVEMENT).coerceAtLeast(0F)
                updateState {
                    copy(player = player.copy(yPos = playerY))
                }
            }

            ControllerDirection.DOWN -> {
                val currentPlayer = state.value.player
                val maxHeight = state.value.screenHeight - currentPlayer.height
                val playerY = (currentPlayer.yPos + PLAYER_MOVEMENT).coerceAtMost(maxHeight)
                updateState {
                    copy(player = player.copy(yPos = playerY))
                }
            }

            ControllerDirection.LEFT -> {
                val currentPlayer = state.value.player
                val playerX = (currentPlayer.xPos - PLAYER_MOVEMENT).coerceAtLeast(0F)
                updateState {
                    copy(player = player.copy(xPos = playerX))
                }
            }

            ControllerDirection.RIGHT -> {
                val currentPlayer = state.value.player
                val maxWidth = state.value.screenWidth - currentPlayer.width
                val playerX = (currentPlayer.xPos + PLAYER_MOVEMENT).coerceAtMost(maxWidth)
                updateState {
                    copy(player = player.copy(xPos = playerX))
                }
            }

            ControllerDirection.NONE -> {

            }
        }
    }

    private fun withGameRunning(next: () -> Unit) {
        if (state.value.isRunning) {
            next()
        }
    }

    fun onEvent(event: GameEvent) {
        when (event) {
            DownDirectionClicked -> withGameRunning {
                Log.d("DIRECTION", "DOWN")
                updateState { copy(direction = ControllerDirection.DOWN) }
            }

            is EnemyKilled -> {

            }

            LeftDirectionClicked -> withGameRunning {
                Log.d("DIRECTION", "LEFT")
                updateState {
                    copy(direction = ControllerDirection.LEFT)
                }
            }

            RightDirectionClicked -> withGameRunning {
                Log.d("DIRECTION", "RIGHT")
                updateState { copy(direction = ControllerDirection.RIGHT) }
            }

            UpDirectionClicked -> withGameRunning {
                Log.d("DIRECTION", "UP")
                updateState { copy(direction = ControllerDirection.UP) }
            }

            GunFired -> withGameRunning {
                val player = state.value.player
                val xPos = player.xPos
                val yPos = player.yPos
                val bullet = Bullet(xPos + player.width / 2 - Bullet.BULLET_SIZE / 4, yPos)
                val currentBullets = state.value.bullets.toMutableList().apply { add(bullet) }
                updateState { copy(bullets = currentBullets) }
            }

            KeyReleased -> withGameRunning {
                updateState { copy(direction = ControllerDirection.NONE) }
            }

            GamePaused -> withGameRunning {
                updateState { copy(isRunning = false) }
            }

            GameResumed -> {
                updateState { copy(isRunning = true) }
                viewModelScope.launch {
                    refreshGameRunner()
                }
            }

            is ExplosionRendered -> {
                val stateValue = state.value
                val currentExplosion =
                    stateValue.explosions.toMutableList().apply { remove(event.explosion) }
                updateState {
                    copy(explosions = currentExplosion)
                }
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