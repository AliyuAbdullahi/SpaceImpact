package com.lek.spaceimpact.home.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import com.lek.spaceimpact.game.ui.GameActivity
import com.lek.spaceimpact.home.ui.model.ShowAbout
import com.lek.spaceimpact.home.ui.model.ShowHome
import com.lek.spaceimpact.home.ui.model.StartGameClicked
import com.lek.spaceimpact.home.ui.model.SystemCreated
import com.lek.spaceimpact.home.ui.model.SystemDestroyed
import com.lek.spaceimpact.home.ui.model.SystemPaused
import com.lek.spaceimpact.home.ui.model.SystemResumed
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeActivity : ComponentActivity() {

    private val viewModel by viewModels<HomeViewModel>()

    override fun onPause() {
        super.onPause()
        viewModel.handleEvent(SystemPaused)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.handleEvent(SystemDestroyed)
    }

    override fun onResume() {
        super.onResume()
        viewModel.handleEvent(SystemResumed)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.initialState()
        viewModel.startSound()
        setContent {
            Surface {
                val scope = rememberCoroutineScope()
                val state = viewModel.stateFlow.collectAsState(initial = viewModel.initialState())
                GameHomeComponent(
                    destination = state.value.destination,
                    onNewGameClicked = {
                        viewModel.handleEvent(StartGameClicked)
                        scope.launch {
                            delay(500)
                        }
                        viewModel.handleEvent(SystemCreated)
                        startActivity(Intent(this@HomeActivity, GameActivity::class.java))
                    },
                    onNavigateToAboutClicked = {
                        viewModel.handleEvent(ShowAbout)
                    },
                    onNavigateToHomeClicked = {
                        viewModel.handleEvent(ShowHome)
                    }
                )
            }
        }
    }
}