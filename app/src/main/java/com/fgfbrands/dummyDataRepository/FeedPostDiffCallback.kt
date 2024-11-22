package com.fgfbrands.dummyDataRepository

import androidx.recyclerview.widget.DiffUtil
import com.fgfbrands.model.FeedPost

class FeedPostDiffCallback : DiffUtil.ItemCallback<FeedPost>() {
    override fun areItemsTheSame(oldItem: FeedPost, newItem: FeedPost): Boolean {
        return oldItem.id == newItem.id // Compare by unique id
    }

    override fun areContentsTheSame(oldItem: FeedPost, newItem: FeedPost): Boolean {
        return oldItem == newItem // Compare the entire object
    }
}

