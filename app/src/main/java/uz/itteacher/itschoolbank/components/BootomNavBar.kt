package uz.itteacher.itschoolbank.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import uz.itteacher.itschoolbank.navigation.Routes

@Composable
fun BottomNavBar(
    current: String,
    onNavigate: (String) -> Unit
) {
    Column {
        Divider(color = Color(0xFFE0E0E0), thickness = 1.dp)
        NavigationBar(
            containerColor = Color.White,
            tonalElevation = 0.dp,
            modifier = Modifier.navigationBarsPadding()
        ) {
            val items = listOf(
                Triple(Routes.HOME, uz.itteacher.itschoolbank.R.drawable.home, "Home"),
                Triple(Routes.TRANSACTIONS, uz.itteacher.itschoolbank.R.drawable.cards, "Transactions"),
                Triple(Routes.REQUEST, uz.itteacher.itschoolbank.R.drawable.statistics, "Request"),
                Triple(Routes.PROFILE, uz.itteacher.itschoolbank.R.drawable.settings, "Profile")
            )
            items.forEach { (route, iconRes, label) ->
                val selected = route == current
                NavigationBarItem(
                    selected = selected,
                    onClick = { onNavigate(route) },
                    icon = {
                        Icon(
                            painter = painterResource(id = iconRes),
                            contentDescription = label,
                            tint = if (selected) Color(0xFF1E88E5) else Color.Gray
                        )
                    },
                    label = { Text(text = label, color = if (selected) Color(0xFF1E88E5) else Color.Gray) },
                    alwaysShowLabel = true
                )
            }
        }
    }
}
