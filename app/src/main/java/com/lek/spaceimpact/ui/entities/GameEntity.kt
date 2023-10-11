package com.lek.spaceimpact.ui.entities

import android.graphics.RectF
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.VectorPainter

abstract class GameEntity(
    private val id: String,
    private val xPos: Float,
    private val yPos: Float,
    val width: Float,
    val height: Float,
    private val type: EntityType
) {

    abstract fun render(imageBitmap: ImageBitmap, drawScope: DrawScope, screenWidth: Float, screenHeight: Float)

    abstract val resource: Int?

    fun block() = RectF(xPos, yPos, xPos + width, height + yPos)

    fun intersectsWith(entity: GameEntity): Boolean =
        this.block()
            .intersects(entity.xPos, entity.yPos, entity.xPos + width, entity.yPos + height)
}

enum class EntityType {
    PLAYER, ENEMY, BULLET
}

data class Background(val color: Color, val shape: EntityShape)

enum class EntityShape {
    CIRCLE, SQUARE
}