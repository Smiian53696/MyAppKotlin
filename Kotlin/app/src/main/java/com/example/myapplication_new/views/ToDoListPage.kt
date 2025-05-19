package com.example.myapplication_new.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication_new.R
import com.example.myapplication_new.model.Todo
import com.example.myapplication_new.ui.theme.PurpleGrey80
import com.example.myapplication_new.utils.Routes
import com.example.myapplication_new.viewmodel.AuthViewModel
import com.example.myapplication_new.viewmodel.TodoViewModel
import com.example.myapplication_new.gymcalendar.addToCalendar
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoListPage(viewModel: TodoViewModel, navController: NavController, authViewModel: AuthViewModel) {
    var showDialog by remember { mutableStateOf(false) }
    var newTitle by remember { mutableStateOf("") }
    var newDescription by remember { mutableStateOf("") }
    val todoList by viewModel.todoList.observeAsState(emptyList())
    val context = LocalContext.current

    // Визначаємо потрібні кольори
    val purpleTextColor = Color(0xFF471AA0)
    val pinkTextColor = Color(0xFFBB84E8)
    val pinkButtonColor = Color(0xFFBB84E8)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        stringResource(R.string.myToDoList),
                        color = purpleTextColor
                    )
                },
                actions = {
                    IconButton(onClick = {
                        authViewModel.signOut()
                        navController.navigate(Routes.loginPage) {
                            popUpTo(Routes.toDoListPage) { inclusive = true }
                        }
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                            contentDescription = "Log out",
                            tint = purpleTextColor
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showDialog = true },
                containerColor = pinkButtonColor,
                contentColor = Color.White,
                elevation = FloatingActionButtonDefaults.elevation(8.dp)
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Add Task",
                    tint = Color.White
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(padding)
                .background(PurpleGrey80)
        ) {
            if (todoList.isEmpty()) {
                Text(
                    text = stringResource(R.string.noItems),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    color = purpleTextColor
                )
            } else {
                LazyColumn {
                    items(todoList) { todo ->
                        TodoItem(todo, viewModel) { viewModel.deleteTodo(todo) }
                    }
                }
            }
        }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = {
                    Text(
                        stringResource(R.string.addNewTask),
                        color = purpleTextColor
                    )
                },
                text = {
                    Column {
                        TextField(
                            value = newTitle,
                            onValueChange = { newTitle = it },
                            label = {
                                Text(
                                    stringResource(R.string.title),
                                    color = purpleTextColor
                                )
                            }
                        )
                        TextField(
                            value = newDescription,
                            onValueChange = { newDescription = it },
                            label = {
                                Text(
                                    stringResource(R.string.description),
                                    color = purpleTextColor
                                )
                            }
                        )
                    }
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            viewModel.addTodo(newTitle, newDescription)
                            addToCalendar(context, newTitle, newDescription)
                            newTitle = ""
                            newDescription = ""
                            showDialog = false
                        }
                    ) {
                        Text(
                            stringResource(R.string.add),
                            color = purpleTextColor
                        )
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDialog = false }) {
                        Text(
                            stringResource(R.string.cancel),
                            color = purpleTextColor
                        )
                    }
                }
            )
        }
    }
}

@Composable
fun TodoItem(todo: Todo, viewModel: TodoViewModel, onDelete: () -> Unit) {
    // Визначаємо потрібні кольори
    val purpleTextColor = Color(0xFF471AA0)
    val pinkTextColor = Color(0xFFBB84E8)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = todo.isCompleted,
            onCheckedChange = { viewModel.toggleCompletion(todo) },
            colors = CheckboxDefaults.colors(
                checkedColor = purpleTextColor,
                uncheckedColor = Color.White,
                checkmarkColor = Color.White
            )
        )
        Text(
            text = todo.title,
            color = if (todo.isCompleted) purpleTextColor else pinkTextColor,
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp)
                .clickable { viewModel.toggleCompletion(todo) }
        )
        IconButton(onClick = onDelete) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete Task",
                tint = purpleTextColor
            )
        }
    }
}