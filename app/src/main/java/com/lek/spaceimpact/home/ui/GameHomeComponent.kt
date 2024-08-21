package com.lek.spaceimpact.home.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lek.spaceimpact.R
import com.lek.spaceimpact.home.ui.about.components.AboutGame
import com.lek.spaceimpact.home.ui.model.Destination
import com.lek.spaceimpact.ui.theme.APE_MOUNT
import com.lek.spaceimpact.ui.theme.MenuBlue
import com.lek.spaceimpact.ui.theme.MenuBlueLight
import com.lek.spaceimpact.ui.theme.SpaceImpactTheme

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun GameHomeComponent(
    destination: Destination,
    onNewGameClicked: () -> Unit = {},
    onNavigateToAboutClicked: () -> Unit = {},
    onNavigateToHomeClicked: () -> Unit = {}
) {
    AnimatedContent(targetState = destination, label = "") { theDestination ->
        when (theDestination) {
            Destination.HOME -> {
                HomeScreen(
                    onNewGameClicked = onNewGameClicked,
                    onNavigateToAboutClicked = onNavigateToAboutClicked
                )
            }

            Destination.ABOUT -> {
                AboutGame(onBackClicked = { onNavigateToHomeClicked() })
            }
        }
    }
}

@Composable
private fun HomeScreen(
    onNewGameClicked: () -> Unit,
    onNavigateToAboutClicked: () -> Unit
) {
    var isVisible by remember {
        mutableStateOf(false)
    }

    val scale = animateFloatAsState(
        targetValue = if (isVisible) 1F else 0.3F,
        animationSpec = tween(durationMillis = 1000),
        label = ""
    )

    val heightInDp = animateDpAsState(
        targetValue = if (isVisible) 150.dp else 10.dp,
        animationSpec = tween(durationMillis = 1000), label = ""
    )

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.home_background),
            contentDescription = "",
            contentScale = ContentScale.Crop
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.wrapContentSize()
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                modifier = Modifier.scale(scale.value),
                text = "Space Impact",
                fontFamily = APE_MOUNT,
                fontWeight = FontWeight.Bold,
                fontSize = 40.sp,
                color = MenuBlue
            )
            Spacer(modifier = Modifier.height(heightInDp.value))
            HomeButton(
                "New Game",
                onClick = onNewGameClicked
            )
            Spacer(modifier = Modifier.height(15.dp))
            HomeButton(
                "About Game",
                onClick = onNavigateToAboutClicked
            )
        }

        LaunchedEffect(key1 = Unit) {
            isVisible = true
        }
    }
}

@Composable
private fun HomeButton(text: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds,
            painter = painterResource(id = R.drawable.home_button),
            contentDescription = ""
        )
        Text(text = text, fontFamily = APE_MOUNT, fontSize = 22.sp, color = MenuBlueLight)
    }
}

@Composable
@Preview
private fun GameHomeComponent_Preview() {
    SpaceImpactTheme {
        Surface {
            GameHomeComponent(destination = Destination.HOME)
        }
    }
}