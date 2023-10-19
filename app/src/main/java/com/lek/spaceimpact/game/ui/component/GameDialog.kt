package com.lek.spaceimpact.game.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lek.spaceimpact.R
import com.lek.spaceimpact.ui.theme.DS_MYSTICORA
import com.lek.spaceimpact.ui.theme.GameMenuBackground
import com.lek.spaceimpact.ui.theme.PurpleGrey80
import com.lek.spaceimpact.ui.theme.SpaceImpactTheme

data class DialogAction(
    val message: String,
    val action: () -> Unit = {}
)

@Composable
fun GameDialog(
    message: String,
    isOkayCancel: Boolean = true,
    onOkayClicked: () -> Unit = {},
    onCancelClicked: () -> Unit = {},
    dialogActions: List<DialogAction> = listOf()
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable { }
            .background(GameMenuBackground),
        contentAlignment = Alignment.Center
    ) {
        Card(
            colors = CardDefaults.cardColors(containerColor = PurpleGrey80),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Column(
                modifier = Modifier.padding(30.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = message, fontFamily = DS_MYSTICORA)
                Spacer(modifier = Modifier.height(16.dp))
                Row (
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(8.dp)
                ){
                    if (isOkayCancel) {
                        DialogButton(
                            message = stringResource(R.string.cancel),
                            background = R.drawable.negative_button,
                            onClick = onCancelClicked
                        )
                        Spacer(modifier = Modifier.size(8.dp))
                        DialogButton(
                            message = stringResource(R.string.ok),
                            background = R.drawable.positive_button,
                            onClick = onOkayClicked
                        )

                    } else {
                        for (dialogAction in dialogActions) {
                            DialogButton(
                                message = dialogAction.message,
                                background = R.drawable.positive_button,
                                onClick = { dialogAction.action() }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun DialogButton(
    message: String,
    background: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    fontFamily: FontFamily = DS_MYSTICORA
) {
    Box(modifier = modifier.clickable { onClick() }, contentAlignment = Alignment.Center) {
        Image(painter = painterResource(id = background), contentDescription = "")
        Text(text = message, fontFamily = fontFamily)
    }
}

@Composable
@Preview
private fun GameOkayCancelDialog_Preview() {
    SpaceImpactTheme {
        Surface {
            Box(modifier = Modifier.fillMaxSize()) {
                GameDialog(
                    message = "You won",
                    isOkayCancel = true
                )
            }
        }
    }
}

@Composable
@Preview
private fun GameDialog_Preview() {
    SpaceImpactTheme {
        Surface {
            Box(modifier = Modifier.fillMaxSize()) {
                GameDialog(
                    message = "Everything works fine",
                    isOkayCancel = false,
                    dialogActions = listOf(DialogAction(message = "Ok"))
                )
            }
        }
    }
}