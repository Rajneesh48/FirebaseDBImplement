package com.example.firebasedbimplement

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.Firebase
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth


@Composable
fun SigninScreen(navController: NavHostController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current
    val googleSignInOptions = remember {
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("786458816428-lev30n5ktlhil4nju981t289bjo0ftvb.apps.googleusercontent.com")
            .requestEmail()
            .build()
    }
    val googleSignInClient = remember {
        GoogleSignIn.getClient(context, googleSignInOptions)
    }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)

        try {
            val account = task.result
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)

            Firebase.auth.signInWithCredential(credential)
                .addOnCompleteListener {
                    if (task.isSuccessful) {
                        navController.navigate("home")
                        {
                        popUpTo("Sign in") {inclusive = true}}

                    } else {
Toast.makeText(context, "Sign In Failed", Toast.LENGTH_SHORT).show()
                    }
                }

        } catch (e: Exception) {
            Toast.makeText(context, "Sign In Failed :${e.message}", Toast.LENGTH_SHORT).show()
        }}
        Box(
            modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {

                TextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(10.dp))

                TextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        Firebase.auth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    navController.navigate("home")
                                    Toast.makeText(
                                        context,
                                        "Authentication successful.",
                                        Toast.LENGTH_SHORT,
                                    ).show()

                                } else {
                                    Toast.makeText(
                                        context,
                                        "Authentication failed.",
                                        Toast.LENGTH_SHORT,
                                    ).show()
                                }
                            }


                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("SignIn")
                }
                Spacer(modifier = Modifier.height(20.dp))
                Button(onClick = {
                    var signinintent = googleSignInClient.signInIntent
                    launcher.launch(signinintent)
                }) {Text(text = "sign in with google",
                    modifier = Modifier.fillMaxWidth()) }
            }

        }
    }





