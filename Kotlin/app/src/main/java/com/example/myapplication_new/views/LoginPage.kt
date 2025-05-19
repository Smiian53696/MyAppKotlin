package com.example.myapplication.views

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
fun LogoImage() {
    Image(
        painter = painterResource(id = R.drawable.logo),
        contentDescription = "Witaj w Compose!",
        modifier = Modifier
            .offset(x = 140.dp, y = 62.dp)
            .size(129.dp)
            .width(129.dp)
            .height(129.dp)
    )
}


@Composable
fun LoginPage(
    navController: NavController,
    authViewModel: AuthViewModel
) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
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

    val forgetPasswordOffset = if (lang == "pl") {
        Modifier.offset(x = 250.dp, y = 474.dp)
    } else {
        Modifier.offset(x = 269.dp, y = 474.dp)
    }

    val signInOffset = if (lang == "pl" || lang == "uk") {
        Modifier.offset(x = 27.dp, y = 212.dp)
    } else {
        Modifier.offset(x = 19.dp, y = 212.dp)
    }

    val signUpOffset = if (lang == "pl") {
        Modifier.offset(x = 230.dp, y = 852.dp)
    } else if (lang == "uk") {
        Modifier.offset(x = 220.dp, y = 852.dp)
    } else {
        Modifier.offset(x = 250.dp, y = 852.dp)
    }
    val dontHaveOffset = if (lang == "uk") {
        Modifier.offset(x = 75.dp, y = 852.dp)
    } else {
        Modifier.offset(x = 110.dp, y = 852.dp)
    }



    LogoImage()


    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,

        ) {
        Text(
            text = stringResource(id = R.string.signIn),
            style = TextStyle(
                fontWeight = FontWeight.W700,
                color = Color(0xFF471AA0),
                fontSize = 30.sp,
            ),
            modifier = signInOffset
        )

        Spacer(modifier = Modifier.height(254.dp))

        AuthTextField(
            placeholder = stringResource(id = R.string.emailOrUserName),
            value = email,
            onValueChange = { email = it },
            isPassword = false,
            iconId = R.drawable.user,


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
    }





    Button(
        onClick = {
            authViewModel.login(
                email,
                password,
                emptyMsg = context.getString(R.string.empty),
                wrongMsg = context.getString(R.string.wrong)
            )
        }, enabled = authState.value != AuthState.Loading,
        shape = RoundedCornerShape(15.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFBB84E8)
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp)
            .height(50.dp)
            .offset(y = 532.dp)
    )
    {
        Text(
            text = stringResource(id = R.string.signIn),
            style = TextStyle(
                fontWeight = FontWeight.W700,
                fontSize = 15.sp,
                color = Color.White
            )
        )
    }


    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            text = stringResource(id = R.string.forgetPassword),
            style = TextStyle(
                fontWeight = FontWeight.W700,
                color = Color(0xFF471AA0),
                fontSize = 15.sp,
            ),
            modifier = forgetPasswordOffset
        )
    }
    Text(
        text = stringResource(id = R.string.dontHaveAccount),
        style = TextStyle(
            fontWeight = FontWeight.W400,
            color = Color(0xFF471AA0),
            fontSize = 15.sp,
        ),
        modifier = dontHaveOffset
    )
    Text(
        text = stringResource(id = R.string.signUp),
        style = TextStyle(
            fontWeight = FontWeight.W700,
            color = Color(0xFF471AA0),
            fontSize = 15.sp,
        ),
        modifier = signUpOffset.then(
            Modifier
                .clickable {
                    // Navigate to register page or any other action
                    navController.navigate(Routes.registerPage)
                })
    )
}


