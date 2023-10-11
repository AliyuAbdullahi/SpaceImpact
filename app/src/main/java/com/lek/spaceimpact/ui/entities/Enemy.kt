package com.lek.spaceimpact.ui.entities

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import com.lek.spaceimpact.R
import com.lek.spaceimpact.ui.theme.EnemyColor
import java.util.UUID

data class Enemy(val xPos: Float, val yPos: Float, val color: Color = EnemyColor) :
    GameEntity(UUID.randomUUID().toString(), xPos, yPos, ENEMY_SIZE, ENEMY_SIZE, EntityType.ENEMY) {

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

    override val resource: Int = R.drawable.enemy

    companion object {
        val ENEMY_SIZE = 64F
    }
}