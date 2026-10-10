package com.example.catalist.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProfileDetailsScreen(
    //profileDetailsViewModel: ProfileDetailsViewModel
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(text = "User Profile", style = MaterialTheme.typography.titleLarge)

        Text(text = "Name: Pera", fontSize = 16.sp)
        Text(text = "Username: Peric", fontSize = 16.sp)
        Text(text = "Email: pera@gmail.com", fontSize = 16.sp)
    }
    }
}
