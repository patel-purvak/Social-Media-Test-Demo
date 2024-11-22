package com.fgfbrands.dummyDataRepository

import com.fgfbrands.model.FeedPost

class FeedRepository {

    private val feedData: MutableList<FeedPost> = mutableListOf()

    // Initialize some mock data
    init {
        repeat(100) {
            val comments = mutableListOf(
                "Great post!",
                "Nice photo!")

            feedData.add(
                FeedPost(
                    id = it,
                    imageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS8caNwgwgXoGNe7zDB6rSB3hUCxfPds3nM8L8qAycEyOh0T4iie29kENT8MFrFZzT5KFg&usqp=CAU",
                    text = "This is FGF post number ${it + 1}",
                    likes = 10,
                    isLiked = false,
                    comments = comments
                )
            )
        }
    }

    // Simulate network call with pagination
    fun getFeed(page: Int, pageSize: Int): List<FeedPost> {
        val startIndex = page * pageSize
        if (startIndex >= feedData.size) {
            // If the startIndex exceeds the size of the data, return an empty list (no more data)
            return emptyList()
        }
        val endIndex = minOf(
            (page + 1) * pageSize,
            feedData.size
        ) // Ensure endIndex does not exceed the size of the list
        return feedData.subList(startIndex, endIndex)
    }

    // Simulate like/unlike action
    fun toggleLike(postId: Int) {
        feedData.find { it.id == postId }?.let {
            it.isLiked = !it.isLiked
            it.likes += if (it.isLiked) 1 else -1
        }
    }
}
