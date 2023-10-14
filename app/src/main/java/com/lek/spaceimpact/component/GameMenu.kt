package com.lek.spaceimpact.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lek.spaceimpact.R
import com.lek.spaceimpact.ui.theme.DS_MYSTICORA
import com.lek.spaceimpact.ui.theme.FullScreenBox
import com.lek.spaceimpact.ui.theme.GameMenuBackground
import com.lek.spaceimpact.ui.theme.SpaceImpactTheme

@Composable
fun GameMenu(
    onResumeClicked: () -> Unit = {},
    onQuitClicked: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = GameMenuBackground),
        contentAlignment = Alignment.Center
    ) {
        Column {
            MenuItem(menu = stringResource(id = R.string.resume), onResumeClicked)
            MenuItem(menu = stringResource(id = R.string.quit_game), onQuitClicked)
        }
    }
}

@Composable
private fun MenuItem(menu: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.game_menu_background),
            contentDescription = "game menu"
        )
        Text(text = menu, color = Color.White, fontFamily = DS_MYSTICORA)
    }
}

@Composable
@Preview
private fun GameMenu_Preview() {
    SpaceImpactTheme {
        FullScreenBox {
            GameMenu()
        }
    }
}