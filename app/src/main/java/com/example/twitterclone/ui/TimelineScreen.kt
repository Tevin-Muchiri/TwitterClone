package com.example.twitterclone.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.twitterclone.model.Tweet
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimelineScreen(
    isDarkMode: Boolean,
    onThemeToggle: () -> Unit,
    onProfileClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var tweets by androidx.compose.runtime.remember { androidx.compose.runtime.mutableStateOf(getSampleTweets()) }
    var showCreateDialog by androidx.compose.runtime.remember { androidx.compose.runtime.mutableStateOf(false) }
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Row(
                        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
                        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "𝕏",
                            fontSize = 28.sp,
                            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                        )
                    }
                },
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
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface
            ) {
                NavigationBarItem(
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = "Home"
                        )
                    },
                    label = { Text("Home") },
                    selected = true,
                    onClick = {
                        coroutineScope.launch {
                            listState.animateScrollToItem(0)
                        }
                    }
                )
                NavigationBarItem(
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search"
                        )
                    },
                    label = { Text("Search") },
                    selected = false,
                    onClick = {
                        // Search functionality - placeholder for now
                    }
                )
                NavigationBarItem(
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Profile"
                        )
                    },
                    label = { Text("Profile") },
                    selected = false,
                    onClick = onProfileClick
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showCreateDialog = true },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Create Post"
                )
            }
        },
        modifier = modifier.fillMaxSize()
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            LazyColumn(
                state = listState,
                modifier = Modifier.fillMaxSize()
            ) {
                items(tweets) { tweet ->
                    TweetCard(tweet = tweet)
                    HorizontalDivider(
                        thickness = 1.dp,
                        color = MaterialTheme.colorScheme.outlineVariant
                    )
                }
            }
            
            VerticalScrollbar(
                listState = listState,
                itemCount = tweets.size,
                modifier = Modifier.align(Alignment.CenterEnd)
            )
        }
        
        if (showCreateDialog) {
            CreatePostDialog(
                onDismiss = { showCreateDialog = false },
                onPost = { content, imageUri ->
                    val newTweet = Tweet(
                        id = tweets.maxOfOrNull { it.id }?.plus(1) ?: 1,
                        displayName = "Tevin Muchiri",
                        username = "tevin_muchiri",
                        content = content,
                        timestamp = "now",
                        replies = 0,
                        reposts = 0,
                        likes = 0,
                        imageUrl = imageUri
                    )
                    tweets = listOf(newTweet) + tweets
                }
            )
        }
    }
}

fun getSampleTweets(): List<Tweet> {
    return listOf(
        Tweet(
            id = 1,
            displayName = "Dennis Murage",
            username = "dennismurage",
            content = "Just deployed a microservices architecture using Kubernetes. The scalability is incredible! 🚀 #SoftwareEngineering",
            timestamp = "2h",
            replies = 45,
            reposts = 89,
            likes = 234,
            imageUrl = "dennis"
        ),
        Tweet(
            id = 2,
            displayName = "Vivian Wairimu",
            username = "vivianwairimu",
            content = "Being a young mum is challenging but so rewarding. Watching my little one grow every day makes it all worth it 💕",
            timestamp = "3h",
            replies = 67,
            reposts = 23,
            likes = 456
        ),
        Tweet(
            id = 3,
            displayName = "George Muchiri",
            username = "georgemuchiri",
            content = "Finally got my dream car! The new BMW M3 is an absolute beast on the road. Can't stop smiling 🚗💨",
            timestamp = "5h",
            replies = 156,
            reposts = 234,
            likes = 892,
            imageUrl = "bmw"
        ),
        Tweet(
            id = 4,
            displayName = "Trevor Onyango",
            username = "trevoronyango",
            content = "What a match! Arsenal's performance today was outstanding. That last-minute goal had me on my feet! ⚽🔥",
            timestamp = "6h",
            replies = 234,
            reposts = 567,
            likes = 1234
        ),
        Tweet(
            id = 5,
            displayName = "Gladys Njeri",
            username = "gladysnjeri",
            content = "Skincare tip: Always use sunscreen, even on cloudy days! Your skin will thank you in 10 years ☀️✨",
            timestamp = "8h",
            replies = 89,
            reposts = 145,
            likes = 678,
            imageUrl = "gladys"
        ),
        Tweet(
            id = 6,
            displayName = "Favour Mumo",
            username = "favourmumo",
            content = "Healthy relationships require communication, trust, and effort from both sides. Don't settle for less than you deserve 💯",
            timestamp = "10h",
            replies = 123,
            reposts = 234,
            likes = 891
        ),
        Tweet(
            id = 7,
            displayName = "Kelvin Mwaniki",
            username = "kelvinmwaniki",
            content = "Golden hour photography at Karura Forest today. Nature never disappoints! 📸🌅",
            timestamp = "12h",
            replies = 45,
            reposts = 78,
            likes = 567,
            imageUrl = "karura"
        ),
        Tweet(
            id = 8,
            displayName = "Liz Ndungu",
            username = "lizndungu",
            content = "Motherhood has taught me patience I never knew I had. Every day is a new adventure with my little ones 👶❤️",
            timestamp = "14h",
            replies = 56,
            reposts = 34,
            likes = 345
        ),
        Tweet(
            id = 9,
            displayName = "William Ngiru",
            username = "williamngiru",
            content = "My German Shepherd just learned a new trick! Dogs are truly man's best friend 🐕💙",
            timestamp = "16h",
            replies = 34,
            reposts = 45,
            likes = 289,
            imageUrl = "german_shepherd"
        ),
        Tweet(
            id = 10,
            displayName = "Eunice Nyaboke",
            username = "eunicenyaboke",
            content = "The new tax policies need serious reconsideration. We need leaders who actually listen to the people! #KenyanPolitics",
            timestamp = "18h",
            replies = 345,
            reposts = 567,
            likes = 1234
        ),
        Tweet(
            id = 11,
            displayName = "Daniel Kamau",
            username = "danielkamau",
            content = "Just closed my biggest deal yet! Hard work and persistence always pay off. Keep grinding entrepreneurs! 💼📈",
            timestamp = "20h",
            replies = 78,
            reposts = 123,
            likes = 567,
            imageUrl = "daniel"
        ),
        Tweet(
            id = 12,
            displayName = "Sheilla Sigeyh",
            username = "sheillasigeyh",
            content = "Night shift at the hospital. Being a nurse is tough but saving lives makes every moment worthwhile 👩‍⚕️💉",
            timestamp = "22h",
            replies = 89,
            reposts = 156,
            likes = 678
        ),
        Tweet(
            id = 13,
            displayName = "Margaret Waithera",
            username = "margaretwaithera",
            content = "First year of marriage complete! It's been a beautiful journey of growth, love, and learning together 💑✨",
            timestamp = "1d",
            replies = 123,
            reposts = 89,
            likes = 892,
            imageUrl = "marriage"
        ),
        Tweet(
            id = 14,
            displayName = "James Mwangi",
            username = "jamesmwangi",
            content = "Military training builds character and discipline like nothing else. Proud to serve! 🎖️💪",
            timestamp = "1d",
            replies = 67,
            reposts = 234,
            likes = 567
        ),
        Tweet(
            id = 15,
            displayName = "Nancy Ouma",
            username = "nancyouma",
            content = "Investment tip: Diversify your portfolio. Don't put all your eggs in one basket! 📊💰 #FinancialFreedom",
            timestamp = "1d",
            replies = 156,
            reposts = 345,
            likes = 1023
        ),
        Tweet(
            id = 16,
            displayName = "Yvonne Wanjiku",
            username = "yvonnewanjiku",
            content = "Just finished watching 'Crash Landing on You' and I'm emotionally wrecked! K-dramas hit different 😭💕",
            timestamp = "2d",
            replies = 234,
            reposts = 123,
            likes = 789,
            imageUrl = "yvonne"
        ),
        Tweet(
            id = 17,
            displayName = "Terrie Ndanu",
            username = "terriendanu",
            content = "Harvest season is here! Organic farming is hard work but seeing the fruits of your labor is priceless 🌾🚜",
            timestamp = "2d",
            replies = 45,
            reposts = 67,
            likes = 456
        ),
        Tweet(
            id = 18,
            displayName = "Gerald Ouko",
            username = "geraldouko",
            content = "WWE Royal Rumble predictions: Who's your pick for the winner? My money's on Seth Rollins! 🤼‍♂️",
            timestamp = "2d",
            replies = 189,
            reposts = 234,
            likes = 891,
            imageUrl = "gerald"
        ),
        Tweet(
            id = 19,
            displayName = "Grace Wambui",
            username = "gracewambui",
            content = "LPG gas business is booming! Providing clean energy to our community feels amazing. Entrepreneurship at its best! 🔥💼",
            timestamp = "3d",
            replies = 56,
            reposts = 89,
            likes = 567
        ),
        Tweet(
            id = 20,
            displayName = "Jaden Muchiri",
            username = "jadenmuchiri",
            content = "Just hit Diamond rank in Valorant! The grind was real but totally worth it 🎮🏆",
            timestamp = "3d",
            replies = 123,
            reposts = 78,
            likes = 678,
            imageUrl = "jaden"
        ),
        Tweet(
            id = 21,
            displayName = "Nelly Wangui",
            username = "nellywangui",
            content = "Engineering is not just for men! Ladies, we can build bridges, design systems, and change the world 👷‍♀️💪",
            timestamp = "3d",
            replies = 234,
            reposts = 456,
            likes = 1234
        ),
        Tweet(
            id = 22,
            displayName = "Paul Otieno",
            username = "paulotieno",
            content = "Mathematics is the language of the universe. Once you understand it, everything else makes sense 📐🧮",
            timestamp = "4d",
            replies = 89,
            reposts = 123,
            likes = 567
        ),
        Tweet(
            id = 23,
            displayName = "Sharon Muhindi",
            username = "sharonmuhindi",
            content = "Fresh juice delivery now available in Nairobi! 100% natural, no preservatives. Support local businesses! 🍊🥤",
            timestamp = "4d",
            replies = 67,
            reposts = 145,
            likes = 456,
            imageUrl = "sharon"
        ),
        Tweet(
            id = 24,
            displayName = "Dennis Meshulam",
            username = "dennismeshulam",
            content = "Justice delayed is justice denied. Our legal system needs reform to serve the people better ⚖️",
            timestamp = "4d",
            replies = 178,
            reposts = 267,
            likes = 891
        ),
        Tweet(
            id = 25,
            displayName = "John Kimathi",
            username = "johnkimathi",
            content = "Finally got my visa approved! Canada here I come! New chapter, new opportunities 🇨🇦✈️",
            timestamp = "5d",
            replies = 234,
            reposts = 345,
            likes = 1456,
            imageUrl = "john"
        )
    )
}


@Composable
fun VerticalScrollbar(
    listState: androidx.compose.foundation.lazy.LazyListState,
    itemCount: Int,
    modifier: Modifier = Modifier
) {
    val firstVisibleItemIndex = listState.firstVisibleItemIndex
    val firstVisibleItemScrollOffset = listState.firstVisibleItemScrollOffset
    
    // Calculate scrollbar position and size
    val scrollbarHeight = 0.3f // 30% of screen height
    val totalItems = itemCount.toFloat()
    val visibleItems = listState.layoutInfo.visibleItemsInfo.size.toFloat()
    
    if (totalItems > visibleItems) {
        val scrollProgress = if (totalItems > 0) {
            (firstVisibleItemIndex + (firstVisibleItemScrollOffset / 1000f)) / totalItems
        } else {
            0f
        }
        
        androidx.compose.foundation.Canvas(
            modifier = modifier
                .fillMaxSize()
                .padding(end = 4.dp, top = 8.dp, bottom = 8.dp)
        ) {
            val scrollbarWidth = 4.dp.toPx()
            val scrollbarHeightPx = size.height * scrollbarHeight
            val scrollbarTop = (size.height - scrollbarHeightPx) * scrollProgress
            
            drawRoundRect(
                color = androidx.compose.ui.graphics.Color.Gray.copy(alpha = 0.5f),
                topLeft = androidx.compose.ui.geometry.Offset(
                    x = size.width - scrollbarWidth,
                    y = scrollbarTop
                ),
                size = androidx.compose.ui.geometry.Size(
                    width = scrollbarWidth,
                    height = scrollbarHeightPx
                ),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(scrollbarWidth / 2)
            )
        }
    }
}
