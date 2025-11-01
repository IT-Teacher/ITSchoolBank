package uz.itteacher.itschoolbank.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import uz.itteacher.itschoolbank.screen.*
import uz.itteacher.itschoolbank.components.BottomNavBar
import uz.itteacher.itschoolbank.viewmodel.MainTransactionViewModel
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel

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
    val vm: MainTransactionViewModel = viewModel()
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
        NavHost(navController = navController, startDestination = Routes.HOME, modifier = Modifier.padding(innerPadding)) {
            composable(Routes.HOME) {
                HomeScreen(viewModel = vm, onSeeAll = { navController.navigate(Routes.SEARCH) }, onSendMoney = { navController.navigate(Routes.SEND_MONEY) })
            }
            composable(Routes.TRANSACTIONS) { TransactionHistoryScreen(viewModel = vm) }
            composable(Routes.SEARCH) { SearchScreen(viewModel = vm) }
            composable(Routes.REQUEST) { RequestMoneyScreen(viewModel = vm) }
            composable(Routes.PROFILE) { ProfileScreen(viewModel = vm) }
            composable(Routes.SEND_MONEY) { SendMoneyScreen(viewModel = vm) }
        }
    }
}
