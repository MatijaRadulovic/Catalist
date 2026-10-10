package com.example.catalist.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.KeyboardType


@Composable
fun ProfileScreen(
    profileViewModel: ProfileViewModel,
    onLogin: ()->Unit
) {
    LaunchedEffect(profileViewModel, onLogin) {
        profileViewModel.effect.collect {
            when (it) {
                ProfileContract.ProfileEffect.CloseLoginEffect-> onLogin()
            }
        }
    }
    ProfileScreen { name: String, username: String, email: String ->
        profileViewModel.setEvent(
            ProfileContract.ProfileEvent.LoginEvent(name, username, email)
        )
    }
}

@Composable
fun ProfileScreen(
    onLoginClick: (name:String,username:String,email:String)->Unit
){
    var name by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(text = "Login Form", style = MaterialTheme.typography.titleLarge)

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Email
            )
        )

        Button(
            onClick = { onLoginClick(name, username, email);   },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login")
        }
}}