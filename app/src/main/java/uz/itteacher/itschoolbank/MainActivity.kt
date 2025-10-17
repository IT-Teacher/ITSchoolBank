package uz.itteacher.itschoolbank

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import uz.itteacher.itschoolbank.screens.CardDashboard
import uz.itteacher.itschoolbank.ui.theme.ITSchoolBankTheme
import uz.itteacher.itschoolbank.viewmodel.CardViewModel

class MainActivity : ComponentActivity() {
    @SuppressLint("ViewModelConstructorInComposable")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ITSchoolBankTheme {
                val viewModel = CardViewModel()
                CardDashboard(viewModel)
            }
        }
    }
}
