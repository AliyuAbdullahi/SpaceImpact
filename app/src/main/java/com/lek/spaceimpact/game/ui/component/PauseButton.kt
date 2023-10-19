package com.lek.spaceimpact.game.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.lek.spaceimpact.R
import com.lek.spaceimpact.ui.theme.FullScreenBox
import com.lek.spaceimpact.ui.theme.SpaceImpactTheme

@Composable
fun PauseButton (modifier: Modifier = Modifier, onClick: () -> Unit) {
    Box(modifier = modifier.clickable { onClick() }, contentAlignment = Alignment.Center) {
        Image(painter = painterResource(id = R.drawable.pause_background), contentDescription = "Pause image")
        Text(text = stringResource(id = R.string.pause))
    }
}

@Composable
@Preview(showBackground = true)
private fun PauseButton_Preview() {
    SpaceImpactTheme {
        FullScreenBox {
            PauseButton {

            }
        }
    }
}