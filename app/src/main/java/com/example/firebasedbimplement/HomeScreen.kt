package com.example.firebasedbimplement

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Preview(showBackground = true)
@Composable

fun HomeScreen() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {

        composable("home") {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "WelcomeScreen",
                    fontSize = 50.sp,
                    color = Color.White
                )

                Button(onClick = {
                    navController.navigate("signup")
                }) {
                    Text(text = "Go To SignUp Screen", fontSize = 14.sp)
                }
                Spacer(modifier = Modifier.height(20.dp))
                Button(onClick = {
                    navController.navigate("login")
                }) {
                    Text(text = "Go To SignIn Screen", fontSize = 14.sp)
                }
            }
        }

        composable("signup") {
            SignupScreen(navController)
        }

        composable("login") {
            SigninScreen(navController)
        }
    }
}

