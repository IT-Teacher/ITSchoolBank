package uz.itteacher.itschoolbank

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import uz.itteacher.itschoolbank.screen.BlockedScreen
import uz.itteacher.itschoolbank.screen.CreatePinScreen
import uz.itteacher.itschoolbank.screen.EnterPinScreen
import uz.itteacher.itschoolbank.viewmodel.PinViewModel

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<PinViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface {
                    PinFlow(viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun PinFlow(viewModel: PinViewModel) {
    var currentScreen by remember { mutableStateOf("create") }

    when (currentScreen) {
        "create" -> CreatePinScreen(
            viewModel = viewModel,
            onPinCreated = { currentScreen = "enter" }
        )

        "enter" -> EnterPinScreen(
            viewModel = viewModel,
            onAccessGranted = {
                // You can navigate to a "Home" screen or show success
                currentScreen = "success"
            },
            onBlocked = { currentScreen = "blocked" }
        )

        "blocked" -> BlockedScreen(
            viewModel = viewModel,
            onUnblocked = {
                currentScreen = "enter"
            }
        )

        "success" -> SuccessScreen()
    }
}

@Composable
fun SuccessScreen() {
    androidx.compose.foundation.layout.Box(
        modifier = androidx.compose.ui.Modifier.fillMaxSize(),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        androidx.compose.material3.Text(
            text = "Welcome!",
            style = MaterialTheme.typography.headlineSmall
        )
    }
}
