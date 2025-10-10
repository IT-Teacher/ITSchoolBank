package uz.itteacher.itschoolbank.screen


import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uz.itteacher.itschoolbank.viewmodel.PinViewModel
import kotlinx.coroutines.delay

@Composable
fun BlockedScreen(
    viewModel: PinViewModel,
    onUnblocked: () -> Unit
) {
    var remaining by remember { mutableStateOf(viewModel.remainingBlockSeconds()) }

    LaunchedEffect(Unit) {
        while (remaining > 0) {
            delay(1000)
            remaining = viewModel.remainingBlockSeconds()
        }
        onUnblocked()
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "You used all your chances.",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Please wait: ${remaining / 60}:${(remaining % 60).toString().padStart(2, '0')}",
            textAlign = TextAlign.Center
        )
    }
}
