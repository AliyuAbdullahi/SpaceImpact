package com.lek.spaceimpact.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.IntSize
import com.lek.spaceimpact.R
import com.lek.spaceimpact.ui.entities.Bullet
import com.lek.spaceimpact.ui.entities.Enemy
import com.lek.spaceimpact.ui.entities.Player

@Composable
fun InGameDisplay(
    modifier: Modifier = Modifier,
    player: Player,
    enemies: List<Enemy>,
    bullets: List<Bullet>,
    onGameRendered: (IntSize) -> Unit = {}
) {
    var gameSize = IntSize(0, 0)
    Box(
        modifier = modifier
            .fillMaxWidth()
            .onSizeChanged {
                gameSize = it
            }
            .background(Color.Black)
    ) {
        val density = LocalDensity.current
        val configuration = LocalConfiguration.current
        val width = with(density) {
            LocalConfiguration.current.screenWidthDp.toDp()
        }
        val height = with(density) {
            configuration.screenHeightDp.toDp()
        }
        val playerImage = ImageBitmap.imageResource(player.resource)
        val enemyImage = ImageBitmap.imageResource(R.drawable.enemy)
        val bulletImage = ImageBitmap.imageResource(R.drawable.bullet_icon)

        Canvas(modifier = Modifier.fillMaxSize()) {
            player.render(playerImage, this, size.width, size.height)
            for (enemy in enemies) {
                enemy.render(enemyImage, this, size.width, size.height)
                bullets.forEach { it.render(bulletImage, this, size.width, size.height) }
            }

            onGameRendered(IntSize(size.width.toInt(), size.height.toInt()))
        }
    }
}

