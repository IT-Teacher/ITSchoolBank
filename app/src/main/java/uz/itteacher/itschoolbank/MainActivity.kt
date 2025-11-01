package uz.itteacher.itschoolbank

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import uz.itteacher.itschoolbank.navigation.AppNavHost
import uz.itteacher.itschoolbank.ui.theme.ITSchoolBankTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ITSchoolBankTheme {

                Surface {
                    val navController = rememberNavController()
                    Box(Modifier.padding(0.dp)) {
                        AppNavHost(navController = navController)
                    }
                }
            }
        }
    }
}
