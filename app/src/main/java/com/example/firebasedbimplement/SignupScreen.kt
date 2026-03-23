package com.example.firebasedbimplement

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore


@Composable
fun SignupScreen(navController: NavHostController) {
    var email by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current
    val db = Firebase.firestore
val uauth = Firebase.auth
    Box(modifier = Modifier.fillMaxSize()
        , contentAlignment = Alignment.Center
        ){
    Column(modifier = Modifier.fillMaxWidth()
        ) {
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

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
                uauth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        var userlogin = UserData(uauth.currentUser?.uid.toString(), name,email)
                        db.collection("Users")
                            .document(uauth.currentUser?.uid.toString())
                            .set(userlogin).addOnCompleteListener {
                                if(it.isSuccessful){
                                    Toast.makeText(context,"Sign Up Successfully", Toast.LENGTH_SHORT).show()
                                }else{
                                    println("Check Login Exception: ${it.exception?.message}")
                                }
                            }.addOnFailureListener {
                                println("CHeck Data Not Saved Exception: ${it.message}")
                            }
                    } else {
                      println("CHeck Failed Exception: ${task.exception?.message}")
                    }
                }.addOnFailureListener {
                    println("Check All Excepiton: ${it.message}")
               }


            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("SignUp")
        }

    }
}}

