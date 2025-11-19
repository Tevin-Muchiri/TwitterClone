package com.example.twitterclone.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.twitterclone.model.Tweet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimelineScreen(
    isDarkMode: Boolean,
    onThemeToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    val sampleTweets = getSampleTweets()
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Timeline") },
                actions = {
                    IconButton(onClick = onThemeToggle) {
                        Icon(
                            imageVector = if (isDarkMode) Icons.Outlined.LightMode else Icons.Outlined.DarkMode,
                            contentDescription = if (isDarkMode) "Switch to Light Mode" else "Switch to Dark Mode"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        },
        modifier = modifier.fillMaxSize()
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            items(sampleTweets) { tweet ->
                TweetCard(tweet = tweet)
                HorizontalDivider(
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.outlineVariant
                )
            }
        }
    }
}

fun getSampleTweets(): List<Tweet> {
    return listOf(
        Tweet(
            id = 1,
            displayName = "John Doe",
            username = "johndoe",
            content = "Just shipped a new feature using Jetpack Compose! The declarative UI approach makes everything so much easier. 🚀",
            timestamp = "2h",
            replies = 12,
            reposts = 45,
            likes = 234
        ),
        Tweet(
            id = 2,
            displayName = "Jane Smith",
            username = "janesmith",
            content = "Hot take: Kotlin is the best language for Android development. Change my mind.",
            timestamp = "4h",
            replies = 89,
            reposts = 23,
            likes = 567
        ),
        Tweet(
            id = 3,
            displayName = "Tech News",
            username = "technews",
            content = "Breaking: New Android version announced with improved performance and battery life!",
            timestamp = "6h",
            replies = 156,
            reposts = 892,
            likes = 2341
        ),
        Tweet(
            id = 4,
            displayName = "Dev Community",
            username = "devcommunity",
            content = "What's your favorite Jetpack library? Mine is Compose for sure!",
            timestamp = "8h",
            replies = 67,
            reposts = 34,
            likes = 189
        ),
        Tweet(
            id = 5,
            displayName = "Sarah Johnson",
            username = "sarahj",
            content = "Finally fixed that bug that's been haunting me for days. Time to celebrate! 🎉",
            timestamp = "10h",
            replies = 8,
            reposts = 5,
            likes = 92
        ),
        Tweet(
            id = 6,
            displayName = "Code Master",
            username = "codemaster",
            content = "Pro tip: Always write clean, readable code. Your future self will thank you.",
            timestamp = "12h",
            replies = 45,
            reposts = 123,
            likes = 678
        ),
        Tweet(
            id = 7,
            displayName = "Android Weekly",
            username = "androidweekly",
            content = "This week's top articles on Android development are now live! Check them out on our website.",
            timestamp = "14h",
            replies = 23,
            reposts = 67,
            likes = 345
        ),
        Tweet(
            id = 8,
            displayName = "Mobile Dev",
            username = "mobiledev",
            content = "Anyone else excited about the new Material Design 3 components? The theming system is incredible!",
            timestamp = "16h",
            replies = 34,
            reposts = 19,
            likes = 156
        )
    )
}
