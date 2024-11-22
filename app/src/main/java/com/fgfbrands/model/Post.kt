package com.fgfbrands.model

// Post data class
data class FeedPost(
    val id: Int,
    val imageUrl: String,
    val text: String,
    var likes: Int,
    var isLiked: Boolean,
    val comments: MutableList<String>
)
