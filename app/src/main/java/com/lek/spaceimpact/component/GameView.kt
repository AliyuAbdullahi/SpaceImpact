package com.lek.spaceimpact.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.lek.spaceimpact.ui.entities.Bullet
import com.lek.spaceimpact.ui.entities.Enemy
import com.lek.spaceimpact.ui.entities.Player

@Composable
fun GameView(
    player: Player,
    enemies: List<Enemy>,
    bullets: List<Bullet>,
    onGameRendered: (IntSize) -> Unit = {},
    onLeftPressed: () -> Unit = {},
    onRightPressed: () -> Unit = {},
    onUpPressed: () -> Unit = {},
    onDownPressed: () -> Unit = {},
    onFireBulletPressed: () -> Unit = {},
    onKeyReleased: () -> Unit = {},
    onPause: () -> Unit = {}
) {
    Box(
        modifier = Modifier.background(Color.Black)
    ) {
        Column {
            InGameDisplay(
                modifier = Modifier.weight(1f),
                player = player,
                enemies = enemies,
                bullets = bullets,
                onGameRendered = onGameRendered
            )
            GameControlPanel(
                onLeftPressed = onLeftPressed,
                onRightPressed = onRightPressed,
                onUpPressed = onUpPressed,
                onDownPressed = onDownPressed,
                onFireBulletPressed = onFireBulletPressed,
                onKeyReleased = onKeyReleased,
                onPause = onPause
            )
        }
        HealthAndBulletBar(
            modifier = Modifier
                .height(30.dp)
                .fillMaxWidth()
                .background(Color.Cyan),
            bulletCount = player.bulletCount, healthCount = player.healthCount
        )
    }
}
