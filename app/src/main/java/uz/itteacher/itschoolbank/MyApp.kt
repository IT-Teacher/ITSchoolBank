package uz.itteacher.itschoolbank

import android.app.Application
import android.util.Log
import com.google.firebase.FirebaseApp

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        try {
            FirebaseApp.initializeApp(this)
            Log.d("MyApp", "Firebase initialized in Application")
        } catch (e: Exception) {
            Log.e("MyApp", "Failed: ${e.message}")
        }
    }
}