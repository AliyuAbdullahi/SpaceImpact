package com.lek.spaceimpact.ui.entities

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import com.lek.spaceimpact.R
import java.util.UUID

class Bullet(
    private val xPos: Float,
    private val yPos: Float
) : GameEntity(
    UUID.randomUUID().toString(), xPos, yPos, BULLET_SIZE, BULLET_SIZE, EntityType.BULLET
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

    override val resource: Int = R.drawable.bullet_icon

    companion object {
        val BULLET_SIZE = 40F
    }
}