package com.example.myapplication_new.views
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import com.example.myapplication_new.MyAppNavigation
import com.example.myapplication_new.viewmodel.AuthViewModel
import com.example.myapplication_new.ui.theme.FirebaseAuthTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val authViewModel: AuthViewModel by viewModels()
        setContent {
            FirebaseAuthTheme{
                Scaffold {  innerPadding ->
                    MyAppNavigation(
                        authViewModel = authViewModel)
                }
            }
        }
    }
}
