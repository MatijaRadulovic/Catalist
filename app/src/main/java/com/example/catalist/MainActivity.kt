package com.example.catalist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.catalist.navigation.StartNavigation
import com.example.catalist.profile.ProfileViewModel
import com.example.catalist.ui.theme.CatalistTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class MainActivity : ComponentActivity() {
    private val profileViewModel: ProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                !profileViewModel.bootCompleted.value
            }
        }
        setContent {
            CatalistTheme {
                val hasProfile = profileViewModel.hasProfile.collectAsState()
                var startDestination: String
                if (hasProfile.value) {
                    startDestination = "breeds"
                } else {
                    startDestination = "profile"
                }
                StartNavigation(startDestination)
            }
        }
    }
}