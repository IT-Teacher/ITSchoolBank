package uz.itteacher.itschoolbank

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import uz.itteacher.itschoolbank.ui.theme.ITSchoolBankTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ITSchoolBankTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ITSchoolBankTheme {
        Greeting("Android")
    }
}
//package uz.itteacher.itschoolbank
//
//import android.annotation.SuppressLint
//import android.net.Uri
//import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.activity.enableEdgeToEdge
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.vector.Group
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.lifecycle.viewmodel.compose.viewModel
//import androidx.navigation.NavType
//import androidx.navigation.compose.NavHost
//import androidx.navigation.compose.composable
//import androidx.navigation.compose.rememberNavController
//import androidx.navigation.navArgument
//import uz.itteacher.itschoolbank.ui.theme.ITSchoolBankTheme
//
//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContent {
//            ITSchoolBankTheme {
//               EducationApp()
//            }
//        }
//    }
//}
//
//@SuppressLint("ViewModelConstructorInComposable")
//@Composable
//fun EducationApp() {
//    val navController = rememberNavController()
//    val context = LocalContext.current
//    val (savedName, savedEmail) = SessionManager.getUser(context)
//
//    val startDestination =if (savedName != null && savedEmail != null) {
//        "login"
//    } else {
//        "signup"
//    }
//
//    NavHost(navController = navController, startDestination = startDestination) {
//        composable("login") { LoginScreen(navController) }
//        composable("signup") { SignUpScreen(navController) }
//        composable("end") { all() }
//    }
//}


//package uz.itteacher.itschoolbank
//
//import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Surface
//import androidx.compose.runtime.*
//import androidx.lifecycle.ViewModelProvider
//import uz.itteacher.itschoolbank.data.PinRepository
//import uz.itteacher.itschoolbank.screen.BlockedScreen
//import uz.itteacher.itschoolbank.screen.CreatePinScreen
//import uz.itteacher.itschoolbank.screen.EnterPinScreen
//import uz.itteacher.itschoolbank.viewmodel.PinViewModel
//
//class MainActivity : ComponentActivity() {
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//
//        val repo = PinRepository(applicationContext)
//        val factory = object : ViewModelProvider.Factory {
//            override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
//                return PinViewModel(repo) as T
//            }
//        }
//
//
//        val viewModel: PinViewModel = ViewModelProvider(this, factory)[PinViewModel::class.java]
//
//        setContent {
//            MaterialTheme {
//                Surface(modifier = androidx.compose.ui.Modifier.fillMaxSize()) {
//                    PinFlow(viewModel = viewModel)
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun PinFlow(viewModel: PinViewModel) {
//    val state = viewModel.state
//
//
//    var currentScreen by remember { mutableStateOf("loading") }
//
//    LaunchedEffect(state.savedPin) {
//        currentScreen = when {
//            state.savedPin == null -> "create"
//            state.accessGranted -> "success"
//            else -> "enter"
//        }
//    }
//
//    when (currentScreen) {
//        "loading" -> LoadingScreen()
//
//        "create" -> CreatePinScreen(
//            viewModel = viewModel,
//            onPinCreated = { currentScreen = "enter" }
//        )
//
//        "enter" -> EnterPinScreen(
//            viewModel = viewModel,
//            onAccessGranted = { currentScreen = "success" },
//            onBlocked = { currentScreen = "blocked" }
//        )
//
//        "blocked" -> BlockedScreen(
//            viewModel = viewModel,
//            onUnblocked = { currentScreen = "enter" }
//        )
//
//        "success" -> SuccessScreen()
//    }
//}
//
//@Composable
//fun LoadingScreen() {
//    androidx.compose.foundation.layout.Box(
//        modifier = androidx.compose.ui.Modifier.fillMaxSize(),
//        contentAlignment = androidx.compose.ui.Alignment.Center
//    ) {
//        androidx.compose.material3.Text(
//            text = "Loading...",
//            style = MaterialTheme.typography.headlineSmall
//        )
//    }
//}
//
//@Composable
//fun SuccessScreen() {
//    androidx.compose.foundation.layout.Box(
//        modifier = androidx.compose.ui.Modifier.fillMaxSize(),
//        contentAlignment = androidx.compose.ui.Alignment.Center
//    ) {
//        androidx.compose.material3.Text(
//            text = "Welcome!",
//            style = MaterialTheme.typography.headlineSmall
//        )
//    }
//}