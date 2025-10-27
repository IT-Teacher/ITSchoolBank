package uz.itteacher.itschoolbank

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModelProvider
import uz.itteacher.itschoolbank.data.PinRepository
import uz.itteacher.itschoolbank.screen.BlockedScreen
import uz.itteacher.itschoolbank.screen.CreatePinScreen
import uz.itteacher.itschoolbank.screen.EnterPinScreen
import uz.itteacher.itschoolbank.viewmodel.PinViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val repo = PinRepository(applicationContext)
        val factory = object : ViewModelProvider.Factory {
            override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                return PinViewModel(repo) as T
            }
        }


        val viewModel: PinViewModel = ViewModelProvider(this, factory)[PinViewModel::class.java]

        setContent {
            MaterialTheme {
                Surface(modifier = androidx.compose.ui.Modifier.fillMaxSize()) {
                    PinFlow(viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun PinFlow(viewModel: PinViewModel) {
    val state = viewModel.state


    var currentScreen by remember { mutableStateOf("loading") }

    LaunchedEffect(state.savedPin) {
        currentScreen = when {
            state.savedPin == null -> "create"
            state.accessGranted -> "success"
            else -> "enter"
        }
    }

    when (currentScreen) {
        "loading" -> LoadingScreen()

        "create" -> CreatePinScreen(
            viewModel = viewModel,
            onPinCreated = { currentScreen = "enter" }
        )

        "enter" -> EnterPinScreen(
            viewModel = viewModel,
            onAccessGranted = { currentScreen = "comPay" },
            onBlocked = { currentScreen = "blocked" }
        )

        "blocked" -> BlockedScreen(
            viewModel = viewModel,
            onUnblocked = { currentScreen = "enter" }
        )

        "success" -> SuccessScreen()

        "comPay" -> CommunalPaymentApp(

        )
    }
}

@Composable
fun LoadingScreen() {
    androidx.compose.foundation.layout.Box(
        modifier = androidx.compose.ui.Modifier.fillMaxSize(),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        androidx.compose.material3.Text(
            text = "Loading...",
            style = MaterialTheme.typography.headlineSmall
        )
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
