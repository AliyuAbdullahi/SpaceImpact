package com.lek.spaceimpact.ui.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lek.spaceimpact.R
import com.lek.spaceimpact.ui.theme.SpaceImpactTheme

@Composable
fun HealthAndBulletBar(
    modifier: Modifier = Modifier,
    bulletCount: Int,
    healthCount: Int
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        BulletsComponent(count = bulletCount)
        HealthBar(count = healthCount)
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun BulletsComponent(count: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier.size(20.dp),
            painter = painterResource(id = R.drawable.bullet_icon), contentDescription = ""
        )
        Spacer(modifier = Modifier.width(8.dp))
        AnimatedContent(targetState = count, label = "") { bulletCount ->
            Text(text = "$bulletCount", fontSize = 18.sp)
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun HealthBar(count: Int) {
    AnimatedContent(targetState = count, label = "") { theCount ->
        Row {
            repeat((1..theCount).count()) {
                Image(
                    modifier = Modifier.size(30.dp),
                    painter = painterResource(id = R.drawable.health),
                    contentDescription = "health"
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }
}

@Composable
@Preview
private fun HealthAndBulletBar_Preview() {
    SpaceImpactTheme {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopStart) {
            HealthAndBulletBar(
                modifier = Modifier.fillMaxWidth().background(Color.Cyan),
                healthCount = 3,
                bulletCount = 2
            )
        }
    }
}