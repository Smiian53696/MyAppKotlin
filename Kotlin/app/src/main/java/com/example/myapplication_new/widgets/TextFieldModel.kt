package com.example.myapplication_new.widgets

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication_new.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthTextField(
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit,
    isPassword: Boolean = false,
    iconId: Int

) {
    var passwordVisible by remember { mutableStateOf(false) }



    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp)
            .border(2.dp, Color(0xFF9747FF), RoundedCornerShape(15.dp))
            .clip(RoundedCornerShape(15.dp))
            .padding(2.dp)
    ) {

        OutlinedTextField(
            shape = RoundedCornerShape(15.dp),
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    (placeholder),
                    color = Color(0x4D000000),
                    fontWeight = FontWeight.W400,
                    fontSize = 15.sp,
                    lineHeight = 15.sp,
                    letterSpacing = 0.sp
                )
            },


            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),


            leadingIcon = {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.size(40.dp)
                ) {

                    Icon(
                        painter = painterResource(id = iconId),
                        contentDescription = null,
                        modifier = Modifier.size(30.dp),
                        tint = Color(0xFF471AA0)
                    )
                }
            },
            trailingIcon = {
                if (isPassword) {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            painter = painterResource(id = if (passwordVisible) R.drawable.eye else R.drawable.eye),
                            contentDescription = if (passwordVisible) "Hide password" else "Show password",
                            modifier = Modifier.size(20.dp),
                            tint = Color.Black
                        )
                    }
                }
            },

            visualTransformation = if (isPassword && !passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = KeyboardOptions(keyboardType = if (isPassword) KeyboardType.Password else KeyboardType.Text),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                cursorColor = Color(0xFF9747FF),
            )

        )
    }
}