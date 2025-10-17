package uz.itteacher.itschoolbank.components


import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import uz.itteacher.itschoolbank.R

@Composable
fun BottomNavBar(current: String, onNavigate: (String) -> Unit) {
    NavigationBar {
        NavigationBarItem(
            selected = current == "home",
            onClick = { onNavigate("home") },
            icon = { Icon(painterResource(id = R.drawable.home), contentDescription = "Home") },
            label = { Text("Home") }
        )
        NavigationBarItem(
            selected = current == "transactions",
            onClick = { onNavigate("transactions") },
            icon = { Icon(painterResource(id = R.drawable.cards), contentDescription = "Transactions") },
            label = { Text("Transactions") }
        )
        NavigationBarItem(
            selected = current == "request",
            onClick = { onNavigate("request") },
            icon = { Icon(painterResource(id = R.drawable.statistics), contentDescription = "Request") },
            label = { Text("Request") }
        )
        NavigationBarItem(
            selected = current == "profile",
            onClick = { onNavigate("profile") },
            icon = { Icon(painterResource(id = R.drawable.settings), contentDescription = "Profile") },
            label = { Text("Profile") }
        )
    }
}
