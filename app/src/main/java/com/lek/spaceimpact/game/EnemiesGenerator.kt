package com.lek.spaceimpact.game

import androidx.compose.ui.graphics.Color
import com.lek.spaceimpact.game.entities.Enemy

object EnemiesGenerator {
    fun generateEnemies(screenWidth: Int, count: Int = 10, enemySize: Int): List<Enemy> {
        val startOffset = (-enemySize * count until -enemySize).step(enemySize * 3).toList()

        val spaced = (Enemy.ENEMY_SIZE.toInt()..(screenWidth - (Enemy.ENEMY_SIZE.toInt()))).step(enemySize * 3).toList()

        return (0 until count).map {
            Enemy(
                xPos = spaced.random().toFloat(),
                yPos = startOffset.random().toFloat(),
                color = Color.Red
            )
        }
    }
}