package com.example.firebasedbimplement

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.firebase.firestore.FirebaseFirestore


@Composable
fun FirebaseUI(db: FirebaseFirestore) {
    var name by remember { mutableStateOf("") }
    var users by remember { mutableStateOf(listOf<String>()) }

Column(modifier = Modifier.fillMaxSize()
    .padding(16.dp)) {
    TextField(value = name, onValueChange = { name = it }, label = { Text("Name") })
    Button(onClick = {

        val user = hashMapOf(
            "name" to name
        )

        db.collection("Users")
            .add(user)

    }) {
        Text("Add User")
    }

    Spacer(modifier = Modifier.height(16.dp))

    Button (onClick = {

        db.collection("Users")
            .get()
            .addOnSuccessListener { result ->

                val list = mutableListOf<String>()

                for (document in result) {
                    val n = document.getString("name")
                    n?.let { list.add(it) }
                }

                users = list
            }

    }) {
        Text("Load Users")
    }

    Spacer(modifier = Modifier.height(20.dp))


    LazyColumn {

        items(users) { user ->
            Text(user)
        }

    }



}}