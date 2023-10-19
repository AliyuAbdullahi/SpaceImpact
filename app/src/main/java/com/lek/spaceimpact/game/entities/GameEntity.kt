package com.lek.spaceimpact.game.entities

import android.graphics.RectF
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope

abstract class GameEntity(
    private val id: String,
    private val xPos: Float,
    private val yPos: Float,
    val width: Float,
    val height: Float
) {

    abstract fun render(imageBitmap: ImageBitmap, drawScope: DrawScope, screenWidth: Float, screenHeight: Float)

    private fun block() = RectF(xPos, yPos, xPos + width, height + yPos)

    fun intersectsWith(entity: GameEntity): Boolean =
        this.block()
            .intersects(entity.xPos, entity.yPos, entity.xPos + width, entity.yPos + height)
}
