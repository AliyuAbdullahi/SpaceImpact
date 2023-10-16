package com.lek.spaceimpact.ui.component

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
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
    onDownPressed: () -> Unit,
    onKeyReleased: () -> Unit
) {
    Box(modifier = modifier.size(controllerSize)) {
        // UP
        ControllerButton(
            modifier = Modifier
                .size(40.dp)
                .offset(
                    x = 48.dp
                ),
            direction = ControllerDirection.UP,
            onPressed = {
                onUpPressed()
            },
            onRelease = onKeyReleased
        )

        // LEFT
        ControllerButton(
            modifier = Modifier
                .size(40.dp)
                .offset(
                    y = 48.dp
                ),
            direction = ControllerDirection.LEFT,
            onPressed = {
                onLeftPressed()
                Log.d("BUTTON_PRESSED: ", it.name)
            },
            onRelease = onKeyReleased
        )

        // RIGHT
        ControllerButton(
            modifier = Modifier
                .size(40.dp)
                .offset(
                    x = controllerSize,
                    y = 48.dp
                ),
            direction = ControllerDirection.RIGHT,
            onPressed = {
                onRightPressed()
                Log.d("BUTTON_PRESSED: ", it.name)
            },
            onRelease = onKeyReleased
        )

        // DOWN
        ControllerButton(
            modifier = Modifier
                .size(40.dp)
                .offset(
                    x = 48.dp,
                    y = controllerSize
                ),
            direction = ControllerDirection.DOWN,
            onPressed = {
                onDownPressed()
                Log.d("BUTTON_PRESSED: ", it.name)
            },
            onRelease = onKeyReleased
        )
    }
}

@Composable
fun Dp.dpToPx() = with(LocalDensity.current) { this@dpToPx.toPx() }

@Composable
fun Int.pxToDp() = with(LocalDensity.current) { this@pxToDp.toDp() }

enum class ControllerDirection {
    UP, DOWN, LEFT, RIGHT, NONE
}

@Composable
private fun ControllerButton(
    direction: ControllerDirection,
    modifier: Modifier = Modifier,
    onPressed: (ControllerDirection) -> Unit,
    onRelease: () -> Unit = {}
) {
    val buttonImage = when (direction) {
        ControllerDirection.UP -> painterResource(id = R.drawable.up_button)
        ControllerDirection.DOWN -> painterResource(id = R.drawable.down_button)
        ControllerDirection.LEFT -> painterResource(id = R.drawable.left_button)
        ControllerDirection.RIGHT -> painterResource(id = R.drawable.right_button)
        else -> painterResource(id = 0)
    }

    var isPressing by remember {
        mutableStateOf(false)
    }

    Image(
        painter = buttonImage,
        contentDescription = "Controller Direction",
        modifier = modifier.pointerInput(Unit) {                //<-- Suspends block
            awaitPointerEventScope {
                while (true) {
                    val event = awaitPointerEvent()
                    if (event.type == PointerEventType.Press) {
                        Log.d("PRESSING", "PRESSING")
                        onPressed(direction)
                    }
                    if (event.type == PointerEventType.Release) {
                        Log.d("PRESSING", "RELEASED")
                        onRelease()
                    }
                    // Do something with the event.
                }
            }
        }
    )

    LaunchedEffect(key1 = isPressing) {
        onPressed(direction)
    }
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
                onDownPressed = {},
                onKeyReleased = {}
            )
        }
    }
}
