package com.lek.spaceimpact.game.ui.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.IntSize
import com.lek.spaceimpact.R
import com.lek.spaceimpact.game.entities.Bullet
import com.lek.spaceimpact.game.entities.Enemy
import com.lek.spaceimpact.game.entities.Explosion
import com.lek.spaceimpact.game.entities.Player
import com.lek.spaceimpact.game.getBitmapDataList

data class GameDialogData(
    val message: String,
    val okClicked: () -> Unit,
    val cancelClicked: () -> Unit
)

data class SuccessDialog(
    val message: String,
    val actions: List<DialogAction>
)

@Composable
fun InGameDisplay(
    modifier: Modifier = Modifier,
    player: Player,
    enemies: List<Enemy>,
    bullets: List<Bullet>,
    explosions: List<Explosion>,
    onExplosionsRendered: (Explosion) -> Unit = {},
    onGameRendered: (IntSize) -> Unit = {}
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Black)
    ) {

        val playerImage = ImageBitmap.imageResource(R.drawable.fighter_jet)
        val enemyImage = ImageBitmap.imageResource(R.drawable.enemy)
        val bulletImage = ImageBitmap.imageResource(R.drawable.bullet_icon)

        val bitmap = getBitmapDataList()[0]
        Canvas(modifier = Modifier.fillMaxSize()) {
            player.render(playerImage, this, size.width, size.height)
            for (enemy in enemies) {
                enemy.render(enemyImage, this, size.width, size.height)
                bullets.forEach { it.render(bulletImage, this, size.width, size.height) }
            }

            for (explosion in explosions) {
                explosion.render(
                    bitmap,
                    this
                )
                onExplosionsRendered(explosion)
            }

            onGameRendered(IntSize(size.width.toInt(), size.height.toInt()))
        }
    }
}

