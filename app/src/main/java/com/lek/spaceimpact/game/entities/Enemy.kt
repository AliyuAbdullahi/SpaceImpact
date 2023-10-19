package com.lek.spaceimpact.game.entities

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import com.lek.spaceimpact.R
import com.lek.spaceimpact.ui.theme.EnemyColor
import java.util.UUID

data class Enemy(val xPos: Float, val yPos: Float, val color: Color = EnemyColor) :
    GameEntity(UUID.randomUUID().toString(), xPos, yPos, ENEMY_SIZE, ENEMY_SIZE) {

    override fun render(
        imageBitmap: ImageBitmap,
        drawScope: DrawScope,
        screenWidth: Float,
        screenHeight: Float
    ) {
        drawScope.drawImage(
            imageBitmap,
            srcOffset = IntOffset.Zero,
            srcSize = IntSize(width.toInt(), height.toInt()),
            dstOffset = IntOffset(xPos.toInt(), yPos.toInt()),
        )
    }

    companion object {
        const val ENEMY_SIZE = 64F
    }
}