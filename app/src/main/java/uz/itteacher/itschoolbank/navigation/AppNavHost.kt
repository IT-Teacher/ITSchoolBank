package uz.itteacher.itschoolbank.navigation


import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import uz.itteacher.itschoolbank.screen.*
import androidx.navigation.compose.rememberNavController
import uz.itteacher.itschoolbank.components.BottomNavBar

object Routes {
    const val HOME = "home"
    const val TRANSACTIONS = "transactions"
    const val SEARCH = "search"
    const val REQUEST = "request"
    const val PROFILE = "profile"

    const val SEND_MONEY = "send_money"
}

@Composable
fun AppNavHost(navController: NavHostController = rememberNavController()) {
    var currentRoute by remember { mutableStateOf(Routes.HOME) }
    Scaffold(
        bottomBar = {
            BottomNavBar(current = currentRoute, onNavigate = { route ->
                currentRoute = route
                when (route) {
                    Routes.HOME -> navController.navigate(Routes.HOME) { launchSingleTop = true; popUpTo(Routes.HOME) {} }
                    Routes.TRANSACTIONS -> navController.navigate(Routes.TRANSACTIONS) { launchSingleTop = true }
                    Routes.SEARCH -> navController.navigate(Routes.SEARCH) { launchSingleTop = true }
                    Routes.REQUEST -> navController.navigate(Routes.REQUEST) { launchSingleTop = true }
                    Routes.PROFILE -> navController.navigate(Routes.PROFILE) { launchSingleTop = true }
                }
            })
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Routes.HOME,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Routes.HOME) {
                HomeScreen(
                    onSeeAll = { navController.navigate(Routes.SEARCH) },
                    onShowStats = { /* handled inside HomeScreen via dialog state */ },
                    onSendMoney = { navController.navigate(Routes.SEND_MONEY) }
                )
            }
            composable(Routes.TRANSACTIONS) {
                TransactionHistoryScreen(onSeeAll = { navController.navigate(Routes.SEARCH) })
            }
            composable(Routes.SEARCH) {
                SearchScreen()
            }
            composable(Routes.REQUEST) {
                RequestMoneyScreen()
            }
            composable(Routes.PROFILE) {
                ProfileScreen()
            }
            composable(Routes.SEND_MONEY) {
                SendMoneyScreen()
            }
        }
    }
}
