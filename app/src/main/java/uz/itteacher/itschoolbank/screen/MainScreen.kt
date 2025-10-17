package uz.itteacher.itschoolbank.screen



import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MainScreen(onLogout: () -> Unit) {
    Box(Modifier.fillMaxSize(), Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("✅ Access Granted!")
            Spacer(Modifier.height(8.dp))
            Text("Your Credit Cards (dummy)")
            Spacer(Modifier.height(12.dp))
            Button(onClick = onLogout) { Text("Logout") }
        }
    }
}
