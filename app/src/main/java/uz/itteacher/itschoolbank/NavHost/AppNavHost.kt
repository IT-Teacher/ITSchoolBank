package uz.itteacher.itschoolbank.NavHost

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import uz.itteacher.itschoolbank.profile.ChangePasswordScreen
import uz.itteacher.itschoolbank.profile.EditProfileScreen
import uz.itteacher.itschoolbank.profile.ProfileScreen
import uz.itteacher.itschoolbank.profile.SettingsScreen
import uz.itteacher.itschoolbank.profile.TermsAndConditionScreen

@Composable
fun AppNavHost(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "setting") {
        composable("profile") {
            ProfileScreen(navController)
        }
        composable("edit") {
            EditProfileScreen(navController)
        }
        composable("setting") {
            SettingsScreen(navController)
        }
        composable("term") {
            TermsAndConditionScreen(navController)
        }
        composable("change") {
            ChangePasswordScreen(navController)
        }

    }
}