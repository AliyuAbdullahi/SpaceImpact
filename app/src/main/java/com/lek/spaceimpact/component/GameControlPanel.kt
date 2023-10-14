package com.lek.spaceimpact.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lek.spaceimpact.R
import com.lek.spaceimpact.ui.theme.PurpleGrey40
import com.lek.spaceimpact.ui.theme.SpaceImpactTheme

@Composable
fun GameControlPanel(
    modifier: Modifier = Modifier,
    onLeftPressed: () -> Unit = {},
    onRightPressed: () -> Unit = {},
    onUpPressed: () -> Unit = {},
    onDownPressed: () -> Unit = {},
    onFireBulletPressed: () -> Unit = {},
    onKeyReleased: () -> Unit = {},
    onPause: () -> Unit = {}
) {
    Box(
        modifier =
        Modifier
            .fillMaxWidth()
            .height(160.dp)
            .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
            .background(PurpleGrey40)
            .padding(top = 20.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 60.dp, start = 40.dp, end = 40.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                GameControllerDirection(
                    onLeftPressed = onLeftPressed,
                    onRightPressed = onRightPressed,
                    onUpPressed = onUpPressed,
                    onDownPressed = onDownPressed,
                    onKeyReleased = onKeyReleased
                )

                Image(
                    modifier = Modifier
                        .size(80.dp)
                        .clickable { onFireBulletPressed() },
                    painter = painterResource(id = R.drawable.bullet_trigger),
                    contentDescription = ""
                )
            }
            Box(modifier = Modifier.fillMaxHeight().fillMaxWidth(), contentAlignment = Alignment.BottomCenter) {
                PauseButton(modifier = Modifier.padding(start = 60.dp, bottom = 8.dp)) {
                    onPause()
                }
            }
        }
    }
}

@Composable
@Preview
private fun GameControlPanel_Preview() {
    SpaceImpactTheme {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.weight(1F))
            GameControlPanel(
                modifier = Modifier.padding(bottom = 40.dp)
            )
        }
    }
}