package com.example.twitterclone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.twitterclone.ui.ProfileScreen
import com.example.twitterclone.ui.TimelineScreen
import com.example.twitterclone.ui.theme.TwitterCloneTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var isDarkMode by rememberSaveable { mutableStateOf(false) }
            var showProfile by rememberSaveable { mutableStateOf(false) }
            
            TwitterCloneTheme(darkTheme = isDarkMode) {
                if (showProfile) {
                    ProfileScreen(
                        onBackClick = { showProfile = false },
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    TimelineScreen(
                        isDarkMode = isDarkMode,
                        onThemeToggle = { isDarkMode = !isDarkMode },
                        onProfileClick = { showProfile = true },
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}