package com.example.catalist.navigation

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.More
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Leaderboard
import androidx.compose.material.icons.filled.LightbulbCircle
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.catalist.leaderboard.LeaderBoardScreen
import com.example.catalist.profile.ProfileDetailsScreen
import com.example.catalist.quiz.QuizScreen
import kotlin.collections.forEach



@Composable
fun AppNavigation() {
    var selectedTab by remember { mutableStateOf(Tabs.Home) }

    val homeNavController = rememberNavController()

    BackHandler(
        enabled = selectedTab != Tabs.Home,
    ) {
        selectedTab = Tabs.Home
    }

    Scaffold(
        content = {
            when (selectedTab) {
                Tabs.Home -> {
                    CatsNavigation(
                        navController = homeNavController,
                        startDestination = "breeds",
                    )
                }

                Tabs.Quiz -> {
                    QuizScreen()
                }
                Tabs.LeaderBoard -> {
                    LeaderBoardScreen()
                }
                Tabs.Profile -> {
                    //val vm:ProfileDetailsViewModel= hiltViewModel<ProfileDetailsViewModel>()
                    ProfileDetailsScreen()
                    Log.d("sd", "AppNavigation: $it")
                }
            }
        },
        bottomBar = {
            NavigationBar {
                Tabs.entries.forEach {
                    NavigationBarItem(
                        selected = selectedTab == it,
                        icon = {
                            Icon(
                                imageVector = it.icon,
                                contentDescription = null,
                            )
                        },
                        onClick = {
                            selectedTab = it
                        },
                    )
                }
            }
        }
    )
}

private enum class Tabs(val icon: ImageVector) {
    Home(icon = Icons.Default.Home),
    Quiz(icon = Icons.Default.LightbulbCircle),
    LeaderBoard(icon = Icons.Default.Leaderboard),
    Profile(icon = Icons.Default.Person),
}