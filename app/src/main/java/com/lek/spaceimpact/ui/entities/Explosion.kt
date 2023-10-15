package com.lek.spaceimpact.ui.entities

import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import com.lek.spaceimpact.ui.BitmapData
import java.util.UUID

data class Explosion(
    val xPos: Float,
    val yPos: Float,
    val id: String = UUID.randomUUID().toString()
) {

    fun render(
        bitmap: BitmapData,
        drawScope: DrawScope
    ) {
        drawScope.drawImage(
            bitmap.bitmap,
            srcOffset = IntOffset.Zero,
            srcSize = IntSize(bitmap.width, bitmap.height),
            dstOffset = IntOffset(xPos.toInt(), yPos.toInt()),
        )
    }
}

