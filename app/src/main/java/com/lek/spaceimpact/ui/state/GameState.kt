package com.lek.spaceimpact.ui.state

import com.lek.spaceimpact.ui.entities.Bullet
import com.lek.spaceimpact.ui.entities.Enemy
import com.lek.spaceimpact.ui.entities.Player

data class GameState(
    val isRunning: Boolean = false,
    val enemies: List<Enemy> = listOf(),
    val bullets: List<Bullet> = listOf(),
    val player: Player = Player(healthCount = -1, xPos = -1F, yPos = -1F, bulletCount = -1),
    val screenWidth: Float = 0F,
    val screenHeight: Float = 0F
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

data class EnemyKilled(val enemy: Enemy) : GameEvent
