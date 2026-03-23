package com.example.firebasedbimplement

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
    val users  = remember { mutableStateListOf<Pair<String, String>>() }
    val updates = hashMapOf<String, Any>(
        "name" to "john"
    )

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


    LaunchedEffect(Unit) {
        db.collection("Users")
            .addSnapshotListener { snapshot, _ ->
                if (snapshot != null) {
                    users.clear()
                    for (doc in snapshot.documents) {
                        val name = doc.getString("name") ?: ""
                        users.add(Pair(doc.id,name))
                    }
                }
            }
    }

    LazyColumn {
        items(users){ (id, user) ->
            Text(user,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp))
            Button(onClick = {
                db.collection("Users")
                    .document(id)
                    .update(updates)
            }) {
                Text("Update")
            }
        }
    }


    }



}