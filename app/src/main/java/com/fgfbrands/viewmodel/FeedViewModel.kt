package com.fgfbrands.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fgfbrands.dummyDataRepository.FeedRepository
import com.fgfbrands.model.FeedPost

class FeedViewModel(private val repository: FeedRepository) : ViewModel() {

    private val _feedPosts = MutableLiveData<List<FeedPost>>()
    val feedPosts: LiveData<List<FeedPost>> get() = _feedPosts

    private var currentPage = 0
    private val pageSize = 5
    private var isLoading = false // Flag to prevent multiple API calls

    init {
        loadFeed()
    }

    // Fetch feed data and update LiveData
    fun loadFeed() {
        if (isLoading) return // Don't fetch if already loading
        isLoading = true
        _feedPosts.value = repository.getFeed(currentPage, pageSize)
        isLoading = false
    }

    // Handle pagination when loading more posts
    fun loadMorePosts() {
        if (isLoading) return // Don't fetch if already loading
        isLoading = true
        currentPage++ // Increment the page number for pagination
        val morePosts = repository.getFeed(currentPage, pageSize)
        val updatedPosts = _feedPosts.value.orEmpty() + morePosts // Add new posts to the existing list
        _feedPosts.value = updatedPosts // Update the LiveData
        isLoading = false
    }

    // Toggle like status
    fun toggleLike(postId: Int) {
        repository.toggleLike(postId)
    }

    // Refresh the feed (pull-to-refresh)
    fun refreshFeed() {
        currentPage = 0
        loadFeed()
    }
}
