package com.lek.spaceimpact.game.state

import com.lek.spaceimpact.game.ui.component.ControllerDirection
import com.lek.spaceimpact.game.entities.Bullet
import com.lek.spaceimpact.game.entities.Enemy
import com.lek.spaceimpact.game.entities.Explosion
import com.lek.spaceimpact.game.entities.Player

data class GameState(
    val isRunning: Boolean = false,
    val isGameOver: Boolean = false,
    val enemies: List<Enemy> = listOf(),
    val bullets: List<Bullet> = listOf(),
    val explosions: List<Explosion> = mutableListOf(),
    val player: Player = Player(healthCount = 0, xPos = 0F, yPos = 0F, bulletCount = 0),
    val screenWidth: Float = 0F,
    val screenHeight: Float = 0F,
    val direction: ControllerDirection = ControllerDirection.NONE,
    val gameDialog: Unit? = null,
    val successDialog: Unit? = null,
) {
    companion object {
        val EMPTY = GameState()
    }
}

sealed interface GameEvent

object LeftDirectionClicked : GameEvent

object RightDirectionClicked : GameEvent

object UpDirectionClicked : GameEvent

object DownDirectionClicked : GameEvent

object KeyReleased : GameEvent

object GunFired : GameEvent

object GamePaused : GameEvent

object GameResumed : GameEvent

data class EnemyKilled(val enemy: Enemy) : GameEvent

data class ExplosionRendered(val explosion: Explosion): GameEvent

object DialogCancelled: GameEvent

object RestartGame: GameEvent

object OnQuitGameRequested: GameEvent

object SystemPaused: GameEvent

object SystemResumed: GameEvent

object ViewDestroyed: GameEvent