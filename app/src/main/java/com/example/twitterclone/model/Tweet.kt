package com.example.twitterclone.model

data class Tweet(
    val id: Int,
    val displayName: String,
    val username: String,
    val content: String,
    val timestamp: String,
    val replies: Int,
    val reposts: Int,
    val likes: Int
)
