package uz.itteacher.itschoolbank

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import uz.itteacher.itschoolbank.ui.theme.ITSchoolBankTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("ViewModelConstructorInComposable")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ITSchoolBankTheme {
                val navController = rememberNavController()
                val viewModel = CardViewModel()
                NavHost(navController, startDestination = "myCards") {
                    composable("myCards") {
                        MyCardsScreen(
                            viewModel = viewModel,
                            onAddCard = { navController.navigate("addNewCard") },
                            onCardClick = { navController.navigate("allCards") }
                        )
                    }
                    composable("addNewCard") {
                        AddNewCardsScreen(
                            viewModel = viewModel,
                            onBack = { navController.popBackStack() }
                        )
                    }
                    composable("allCards") {
                        AllCardsScreen(
                            viewModel = viewModel,
                            onBack = { navController.popBackStack() },
                            onEditCard = { card ->
                                navController.currentBackStackEntry?.savedStateHandle?.set("cardToEdit", card)
                                navController.navigate("addNewCard") // Reuse AddNewCardsScreen for editing
                            }
                        )
                    }
                }
            }
        }
    }
}
