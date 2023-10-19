package com.lek.spaceimpact.game.entities

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import com.lek.spaceimpact.R

data class Player(
    val healthCount: Int,
    val bulletCount: Int,
    val xPos: Float,
    val yPos: Float
) : GameEntity(
    "PLAYER", xPos, yPos, PLAYER_SIZE, PLAYER_SIZE
) {
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
        const val PLAYER_SIZE = 157F
    }
}