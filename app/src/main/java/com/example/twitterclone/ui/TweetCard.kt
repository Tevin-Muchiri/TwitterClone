package com.example.twitterclone.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.outlined.Repeat
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.twitterclone.model.Tweet

@Composable
fun TweetCard(tweet: Tweet, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Header: Display name, username, and timestamp
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = tweet.displayName,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "@${tweet.username}",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "· ${tweet.timestamp}",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Content
            Text(
                text = tweet.content,
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.onSurface,
                lineHeight = 20.sp
            )
            
            // Image thumbnail (if available)
            tweet.imageUrl?.let { imageUrl ->
                Spacer(modifier = Modifier.height(12.dp))
                if (imageUrl.startsWith("content://") || imageUrl.startsWith("file://")) {
                    // Real image from device (user-uploaded)
                    androidx.compose.foundation.Image(
                        painter = coil.compose.rememberAsyncImagePainter(imageUrl),
                        contentDescription = "Tweet image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        contentScale = androidx.compose.ui.layout.ContentScale.Crop
                    )
                } else if (imageUrl.startsWith("http://") || imageUrl.startsWith("https://")) {
                    // Image from URL
                    androidx.compose.foundation.Image(
                        painter = coil.compose.rememberAsyncImagePainter(imageUrl),
                        contentDescription = "Tweet image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        contentScale = androidx.compose.ui.layout.ContentScale.Crop
                    )
                } else if (imageUrl != "placeholder") {
                    // Image from drawable resources
                    val context = androidx.compose.ui.platform.LocalContext.current
                    val resourceId = context.resources.getIdentifier(
                        imageUrl,
                        "drawable",
                        context.packageName
                    )
                    if (resourceId != 0) {
                        androidx.compose.foundation.Image(
                            painter = androidx.compose.ui.res.painterResource(resourceId),
                            contentDescription = "Tweet image",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .clip(RoundedCornerShape(12.dp)),
                            contentScale = androidx.compose.ui.layout.ContentScale.Crop
                        )
                    } else {
                        // Fallback to placeholder gradient
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(
                                    Brush.linearGradient(
                                        colors = listOf(
                                            Color(0xFF667eea),
                                            Color(0xFF764ba2)
                                        )
                                    )
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Image,
                                contentDescription = "Tweet image",
                                modifier = Modifier.size(64.dp),
                                tint = Color.White.copy(alpha = 0.7f)
                            )
                        }
                    }
                } else {
                    // Placeholder gradient
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(
                                Brush.linearGradient(
                                    colors = listOf(
                                        Color(0xFF667eea),
                                        Color(0xFF764ba2)
                                    )
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Image,
                            contentDescription = "Tweet image",
                            modifier = Modifier.size(64.dp),
                            tint = Color.White.copy(alpha = 0.7f)
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Action Bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ActionButton(
                    icon = Icons.Outlined.ChatBubbleOutline,
                    count = tweet.replies,
                    contentDescription = "Reply"
                )
                ActionButton(
                    icon = Icons.Outlined.Repeat,
                    count = tweet.reposts,
                    contentDescription = "Repost"
                )
                ActionButton(
                    icon = Icons.Outlined.FavoriteBorder,
                    count = tweet.likes,
                    contentDescription = "Like"
                )
                ActionButton(
                    icon = Icons.Outlined.Share,
                    count = null,
                    contentDescription = "Share"
                )
            }
        }
    }
}

@Composable
fun ActionButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    count: Int?,
    contentDescription: String,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        IconButton(
            onClick = { /* Handle action */ },
            modifier = Modifier.size(36.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(18.dp)
            )
        }
        if (count != null && count > 0) {
            Text(
                text = formatCount(count),
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

fun formatCount(count: Int): String {
    return when {
        count >= 1000000 -> String.format("%.1fM", count / 1000000.0)
        count >= 1000 -> String.format("%.1fK", count / 1000.0)
        else -> count.toString()
    }
}


@Preview(showBackground = true, name = "Light Mode")
@Composable
fun TweetCardPreview() {
    MaterialTheme {
        TweetCard(
            tweet = Tweet(
                id = 1,
                displayName = "John Doe",
                username = "johndoe",
                content = "Just shipped a new feature using Jetpack Compose! The declarative UI approach makes everything so much easier. 🚀",
                timestamp = "2h",
                replies = 12,
                reposts = 45,
                likes = 234
            )
        )
    }
}

@Preview(showBackground = true, name = "Dark Mode", uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun TweetCardDarkPreview() {
    MaterialTheme {
        TweetCard(
            tweet = Tweet(
                id = 1,
                displayName = "Jane Smith",
                username = "janesmith",
                content = "Hot take: Kotlin is the best language for Android development. Change my mind.",
                timestamp = "4h",
                replies = 89,
                reposts = 23,
                likes = 567
            )
        )
    }
}
