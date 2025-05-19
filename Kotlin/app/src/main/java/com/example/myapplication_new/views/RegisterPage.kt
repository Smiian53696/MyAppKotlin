package com.example.myapplication_new.views


import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication_new.R
import com.example.myapplication_new.utils.Routes
import com.example.myapplication_new.viewmodel.AuthState
import com.example.myapplication_new.viewmodel.AuthViewModel
import com.example.myapplication_new.widgets.AuthTextField

@Composable
fun BackImage() {
    Image(
        painter = painterResource(id = R.drawable.back),
        contentDescription = "Witaj w Compose!",
        modifier = Modifier
            .offset(x = 12.dp, y = 58.dp)
            .size(20.dp)
            .width(20.dp)
            .height(20.dp)
    )
}

@Composable
fun ElipceImage() {
    Image(
        painter = painterResource(id = R.drawable.ellipse),
        contentDescription = "Witaj w Compose!",
        modifier = Modifier
            .offset(x = 284.dp, y = 5.dp)
            .size(129.dp)
            .width(129.dp)
            .height(129.dp)
    )
}


@Composable
fun RegisterPage(
    navController: NavController,
    authViewModel: AuthViewModel
) {
    var name by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    val context = LocalContext.current
    val lang = context.resources.configuration.locales[0].language
    val authState = authViewModel.authState.observeAsState()

    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthState.Authenticated -> navController.navigate(Routes.toDoListPage)
            is AuthState.Error -> Toast.makeText(
                context,
                (authState.value as AuthState.Error).message,
                Toast.LENGTH_LONG
            ).show()

            else -> Unit
        }
    }
    val signUpOffset = if (lang == "pl" || lang == "uk") {
        Modifier.offset(x = 27.dp, y = 180.dp)
    } else {
        Modifier.offset(x = 20.dp, y = 180.dp)
    }

    val signInOffset = if (lang == "pl") {
        Modifier.offset(x = 225.dp, y = 852.dp)
    } else if (lang == "uk")
    {
        Modifier.offset(x = 255.dp, y = 852.dp)
    } else {
        Modifier.offset(x = 273.dp, y = 852.dp)
    }

    val alreadyHaveOffset = if (lang == "pl")
    {
        Modifier.offset(x = 108.dp, y = 852.dp)
    } else if (lang == "uk")
    {
        Modifier.offset(x = 107.dp, y = 852.dp)
    } else {
        Modifier.offset(x = 96.dp, y = 852.dp)
    }

    BackImage()
    ElipceImage()
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            text = stringResource(id = R.string.signUp),
            style = TextStyle(
                fontWeight = FontWeight.W700,
                color = Color(0xFF471AA0),
                fontSize = 30.sp,
            ),
            modifier = signUpOffset
        )

        Spacer(modifier = Modifier.height(222.dp))

        AuthTextField(
            placeholder = stringResource(id = R.string.fullName),
            value = name,
            onValueChange = { name = it },
            isPassword = false,
            iconId = R.drawable.user,


            )


        Spacer(modifier = Modifier.height(40.dp))

        AuthTextField(
            placeholder = stringResource(id = R.string.email),
            value = email,
            onValueChange = { email = it },
            isPassword = false,
            iconId = R.drawable.email,


            )


        Spacer(modifier = Modifier.height(40.dp))
        AuthTextField(
            placeholder = stringResource(id = R.string.password),
            value = password,
            onValueChange = { password = it },
            isPassword = true,
            iconId = R.drawable.lock,

            )
        Spacer(modifier = Modifier.height(40.dp))
        AuthTextField(
            placeholder = stringResource(id = R.string.confirmPassword),
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            isPassword = true,
            iconId = R.drawable.lock,

            )
        Spacer(modifier = Modifier.height(40.dp))
    }
    Button(
        onClick = {
            authViewModel.signUp(
                email,
                password,
                name,
                confirmPassword,
                fieldsMsg = context.getString(R.string.fields),
                dontMatchMsg = context.getString(R.string.dontMatch),
                wrongMsg = context.getString(R.string.wrong)
            )
        },
        shape = RoundedCornerShape(15.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFBB84E8)
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp)
            .height(50.dp)
            .offset(y = 662.dp)
    ) {
        Text(
            text = stringResource(id = R.string.signUp),
            style = TextStyle(
                fontWeight = FontWeight.W700,
                fontSize = 15.sp,
                color = Color.White
            )
        )
    }
    Text(
        text = stringResource(id = R.string.alreadyHaveAccount),
        style = TextStyle(
            fontWeight = FontWeight.W400,
            color = Color(0xFF471AA0),
            fontSize = 15.sp,
        ),
        modifier = alreadyHaveOffset
    )
    Text(
        text = stringResource(id = R.string.signIn),
        style = TextStyle(
            fontWeight = FontWeight.W700,
            color = Color(0xFF471AA0),
            fontSize = 15.sp,
        ),
        modifier = signInOffset.then(
            Modifier
                .clickable {
                    // Navigate to login page
                    navController.navigate(Routes.loginPage)
                })
    )
    Text(
        text = stringResource(id = R.string.back),
        style = TextStyle(
            fontWeight = FontWeight.W400,
            color = Color(0xFF471AA0),
            fontSize = 12.sp,
        ),
        modifier = Modifier
            .offset(x = 32.dp, y = 61.dp)
            .clickable {
                // Navigate to register page
                navController.navigate(Routes.loginPage)
            }
    )
}