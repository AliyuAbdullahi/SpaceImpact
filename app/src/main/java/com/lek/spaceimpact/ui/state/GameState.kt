package com.lek.spaceimpact.ui.state

import com.lek.spaceimpact.component.ControllerDirection
import com.lek.spaceimpact.ui.entities.Bullet
import com.lek.spaceimpact.ui.entities.Enemy
import com.lek.spaceimpact.ui.entities.Player

data class GameState(
    val isRunning: Boolean = false,
    val isGameOver: Boolean = false,
    val enemies: List<Enemy> = listOf(),
    val bullets: List<Bullet> = listOf(),
    val player: Player = Player(healthCount = 0, xPos = 0F, yPos = 0F, bulletCount = 0),
    val screenWidth: Float = 0F,
    val screenHeight: Float = 0F,
    val direction: ControllerDirection = ControllerDirection.NONE
) {
    companion object {
        val EMPTY = GameState()

        val dummyState = GameState(
            enemies = listOf(
                Enemy(
                    xPos = 30F,
                    yPos = 50F
                ),
                Enemy(
                    xPos = 200F,
                    yPos = 300F
                )
            )
        )
    }
}

sealed interface GameEvent

object LeftDirectionClicked : GameEvent

object RightDirectionClicked : GameEvent

object UpDirectionClicked: GameEvent

object DownDirectionClicked: GameEvent

object KeyReleased: GameEvent

object GunFired: GameEvent

object GamePaused: GameEvent

object GameResumed: GameEvent

data class EnemyKilled(val enemy: Enemy) : GameEvent
