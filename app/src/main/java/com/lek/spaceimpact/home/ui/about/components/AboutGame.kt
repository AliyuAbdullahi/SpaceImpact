package com.lek.spaceimpact.home.ui.about.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lek.spaceimpact.R
import com.lek.spaceimpact.ui.theme.APE_MOUNT
import com.lek.spaceimpact.ui.theme.GreyPurple
import com.lek.spaceimpact.ui.theme.MenuBlueLight
import com.lek.spaceimpact.ui.theme.Purple80
import com.lek.spaceimpact.ui.theme.SpaceImpactTheme

@Composable
fun AboutGame(
    onBackClicked: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GreyPurple)
            .padding(20.dp)
    ) {
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            tint = Color.White,
            contentDescription = "back",
            modifier = Modifier.clickable { onBackClicked() }
        )

        Text(
            text = stringResource(R.string.space_impact),
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontFamily = APE_MOUNT,
            fontSize = 30.sp,
            color = MenuBlueLight
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Space Impact is a simple space shooter game with basic direction controls and a shooting button. \nThe player has three lives on which it can survive on during the game. \nThe live will reduce whenever there is an impact with the enemy.",
            color = Color.White,
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "CREDIT", color = MenuBlueLight, fontSize = 22.sp)
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "This game gives a lot of thanks to these sources from where it obtained some of the assets used in the development of this game. ",
            color = Color.White,
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.height(20.dp))
        for (credit in credits) {
            Credit(url = credit.url, label = credit.label)
            Spacer(modifier = Modifier.height(16.dp))
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "© 2023",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            color = MenuBlueLight
        )
    }
}

data class CreditInfo(
    val url: String, val label: String
)

val credits = listOf(
    CreditInfo(
        url = "https://www.chosic.com/free-music/games/",
        label = "Free Music Games"
    ),
    CreditInfo(
        url = "https://pixabay.com",
        label = "Pixabay Free Image"
    ),
    CreditInfo(
        url = "https://mixkit.co/free-sound-effects/impact/",
        label = "Free Impact Sound"
    )
)

@Composable
private fun Credit(url: String, label: String) {
    val uriHandler = LocalUriHandler.current
    Text(
        text = label,
        modifier = Modifier.clickable { uriHandler.openUri(url) },
        color = Purple80
    )
}

@Composable
@Preview
private fun AboutGame_Preview() {
    SpaceImpactTheme {
        Surface {
            AboutGame()
        }
    }
}