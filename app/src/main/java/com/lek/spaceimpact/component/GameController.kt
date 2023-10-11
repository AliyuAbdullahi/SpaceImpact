package com.lek.spaceimpact.component

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.lek.spaceimpact.R
import com.lek.spaceimpact.ui.theme.SpaceImpactTheme

@Composable
fun GameControllerDirection(
    modifier: Modifier = Modifier,
    controllerSize: Dp = 88.dp,
    onLeftPressed: () -> Unit,
    onRightPressed: () -> Unit,
    onUpPressed: () -> Unit,
    onDownPressed: () -> Unit
) {
    Box(modifier = modifier.size(controllerSize)) {
        ControllerButton(
            modifier = Modifier
                .size(40.dp)
                .offset(
                    x = 48.dp
                ),
            direction = ControllerDirection.UP,
            onPressed = {
                onUpPressed()
                Log.d("BUTTON_PRESSED: ", it.name)
            }
        )

        ControllerButton(
            modifier = Modifier
                .size(40.dp)
                .offset(
                    y = 48.dp
                ),
            direction = ControllerDirection.LEFT,
            onPressed = {
                onUpPressed()
                Log.d("BUTTON_PRESSED: ", it.name)
            }
        )

        ControllerButton(
            modifier = Modifier
                .size(40.dp)
                .offset(
                    x = controllerSize,
                    y = 48.dp
                ),
            direction = ControllerDirection.RIGHT,
            onPressed = {
                onUpPressed()
                Log.d("BUTTON_PRESSED: ", it.name)
            }
        )

        ControllerButton(
            modifier = Modifier
                .size(40.dp)
                .offset(
                    x = 48.dp,
                    y = controllerSize
                ),
            direction = ControllerDirection.DOWN,
            onPressed = {
                onUpPressed()
                Log.d("BUTTON_PRESSED: ", it.name)
            }
        )
    }
}

@Composable
fun Dp.dpToPx() = with(LocalDensity.current) { this@dpToPx.toPx() }

@Composable
fun Int.pxToDp() = with(LocalDensity.current) { this@pxToDp.toDp() }

enum class ControllerDirection {
    UP, DOWN, LEFT, RIGHT
}

@Composable
private fun ControllerButton(
    direction: ControllerDirection,
    modifier: Modifier = Modifier,
    onPressed: (ControllerDirection) -> Unit
) {
    val buttonImage = when (direction) {
        ControllerDirection.UP -> painterResource(id = R.drawable.up_button)
        ControllerDirection.DOWN -> painterResource(id = R.drawable.down_button)
        ControllerDirection.LEFT -> painterResource(id = R.drawable.left_button)
        ControllerDirection.RIGHT -> painterResource(id = R.drawable.right_button)
    }
    Image(
        painter = buttonImage,
        contentDescription = "Controller Direction",
        modifier = modifier.clickable { onPressed(direction) }
    )
}

@Composable
@Preview
private fun GameControllerDirection_Preview() {
    SpaceImpactTheme {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.CenterStart) {
            GameControllerDirection(
                onLeftPressed = {},
                onRightPressed = {},
                onUpPressed = {},
                onDownPressed = {}
            )
        }
    }
}
