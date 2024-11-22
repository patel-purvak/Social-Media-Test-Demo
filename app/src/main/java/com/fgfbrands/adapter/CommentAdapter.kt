package com.fgfbrands.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fgfbrands.databinding.ItemFeedCommentBinding

class CommentAdapter(private val comments: MutableList<String>) :
    RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {

    // ViewHolder class with data binding
    inner class CommentViewHolder(private val binding: ItemFeedCommentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(comment: String) {
            binding.commentText.text = comment
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val binding = ItemFeedCommentBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CommentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val comment = comments[position] // Get the comment at the given position
        holder.bind(comment)
    }

    override fun getItemCount(): Int {
        return comments.size // Return the size of the comments list
    }

    // Helper method to add a comment dynamically
    fun addComment(newComment: String) {
        comments.add(newComment)
        notifyItemInserted(comments.size - 1)
    }

    // Helper method to remove a comment dynamically
    fun removeComment(position: Int) {
        if (position in comments.indices) {
            comments.removeAt(position)
            notifyItemRemoved(position)
        }
    }
}
