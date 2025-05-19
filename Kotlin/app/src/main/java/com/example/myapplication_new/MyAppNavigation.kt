package com.example.myapplication_new

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.views.LoginPage
import com.example.myapplication_new.views.RegisterPage
import com.example.myapplication_new.utils.Routes
import com.example.myapplication_new.viewmodel.AuthViewModel
import com.example.myapplication_new.viewmodel.TodoViewModel
import com.example.myapplication_new.views.TodoListPage

@Composable
fun MyAppNavigation(authViewModel: AuthViewModel) {
    val navController = rememberNavController()
    val todoViewModel: TodoViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = Routes.loginPage,
        builder = {
            composable(Routes.loginPage) {
                LoginPage(navController, authViewModel)
            }
            composable(Routes.registerPage) {
                RegisterPage(navController, authViewModel)
            }
            composable(Routes.toDoListPage) {
                TodoListPage(
                    viewModel = todoViewModel,
                    navController = navController,
                    authViewModel = authViewModel
                )
            }
        }
    )
}