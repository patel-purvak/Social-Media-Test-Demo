package com.fgfbrands.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.fgfbrands.R
import com.fgfbrands.adapter.FeedAdapter
import com.fgfbrands.adapter.OnLikeClickListener
import com.fgfbrands.dummyDataRepository.FeedRepository
import com.fgfbrands.viewmodel.FeedViewModel
import com.fgfbrands.viewmodel.FeedViewModelFactory

class FeedActivity : AppCompatActivity(), OnLikeClickListener {

    private lateinit var feedViewModel: FeedViewModel
    private lateinit var feedAdapter: FeedAdapter
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)

        // Initialize the ViewModel
        val feedRepository = FeedRepository()
        val viewModelFactory = FeedViewModelFactory(feedRepository)
        feedViewModel = ViewModelProvider(this, viewModelFactory)[FeedViewModel::class.java]

        // Initialize ProgressBar
        progressBar = findViewById(R.id.progressBar)

        // Setup RecyclerView
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Create and set the Adapter
        feedAdapter = FeedAdapter(this, supportFragmentManager = supportFragmentManager)
        recyclerView.adapter = feedAdapter

        // Pull-to-refresh
        val swipeRefreshLayout: SwipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)
        swipeRefreshLayout.setOnRefreshListener {
            feedViewModel.refreshFeed()
        }

        // Observe the feed data
        feedViewModel.feedPosts.observe(this) { posts ->
            feedAdapter.submitList(posts)
            swipeRefreshLayout.isRefreshing = false
            progressBar.visibility = View.GONE
        }

        // Pagination: Detect scroll to bottom to load more
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val totalItemCount = layoutManager.itemCount
                val visibleItemCount = layoutManager.childCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                // Load more if we are at the bottom of the list
                if (totalItemCount <= (firstVisibleItemPosition + visibleItemCount)) {
                    progressBar.visibility = View.VISIBLE
                    feedViewModel.loadMorePosts()
                }
            }
        })

        // Load initial feed
        feedViewModel.loadFeed()
        progressBar.visibility = View.VISIBLE
    }

    // Handle the like button click event
    @SuppressLint("NotifyDataSetChanged")
    override fun onLikeClicked(postId: Int) {
        // Call the ViewModel to toggle like for the selected post
        feedViewModel.toggleLike(postId)
        feedAdapter.notifyDataSetChanged()
    }
}
