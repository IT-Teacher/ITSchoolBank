package uz.itteacher.itschoolbank

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.google.firebase.FirebaseApp
import uz.itteacher.itschoolbank.screens.HomeScreen
import uz.itteacher.itschoolbank.ui.theme.ITSchoolBankTheme
import uz.itteacher.itschoolbank.viewmodel.CardViewModel

class MainActivity : ComponentActivity() {
    @SuppressLint("ViewModelConstructorInComposable")
    // MainActivity.kt
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (FirebaseApp.getApps(this).isEmpty()) {
            FirebaseApp.initializeApp(this)
            Log.w("Firebase", "Forced init in MainActivity (separate process)")
        }

        setContent {
            val viewModel = CardViewModel()
            val user = User(1, "Tanya Myroniuk", 0)
            HomeScreen(viewModel = viewModel, user = user)
        }
    }
}
