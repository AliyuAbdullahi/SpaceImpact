package com.lek.spaceimpact.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import com.lek.spaceimpact.R

data class BitmapData(
    val bitmap: ImageBitmap,
    val width: Int,
    val height: Int
)

private val explosionImages = mapOf(
    Pair(64, R.drawable.explosion_1),
    Pair(74, R.drawable.explosion_2),
    Pair(80, R.drawable.explosion_3),
    Pair(90, R.drawable.explosion_4),
    Pair(120, R.drawable.explosion_5),
    Pair(160, R.drawable.explosion_6),
    Pair(150, R.drawable.explosion_7)
)

@Composable
fun getBitmapDataList(): List<BitmapData> {
    return explosionImages.map {
        val bitmap = ImageBitmap.imageResource(it.value)
        BitmapData(
            bitmap,
            it.key,
            it.key
        )
    }
}