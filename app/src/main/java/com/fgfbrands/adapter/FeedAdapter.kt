package com.fgfbrands.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fgfbrands.R
import com.fgfbrands.databinding.ItemFeedPostBinding
import com.fgfbrands.dialog.CommentsBottomSheetDialog
import com.fgfbrands.dummyDataRepository.FeedPostDiffCallback
import com.fgfbrands.model.FeedPost

class FeedAdapter(
    private val likeClickListener: OnLikeClickListener,
    private val supportFragmentManager: FragmentManager
) : ListAdapter<FeedPost, RecyclerView.ViewHolder>(FeedPostDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            ItemFeedPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val post = getItem(position)
        (holder as PostViewHolder).bind(post)
    }

    override fun getItemCount(): Int = currentList.size // currentList holds the data

    // ViewHolder for Post
    inner class PostViewHolder(private val binding: ItemFeedPostBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("StringFormatMatches")
        fun bind(post: FeedPost) {
            binding.apply {
                Glide.with(binding.root.context)
                    .load(post.imageUrl)
                    .placeholder(R.drawable.ic_add_box)
                    .error(R.drawable.ic_remove_cross)
                    .into(binding.imageView)// Use Glide or Coil for image loading
                binding.textView.text = post.text
                binding.likesCount.text =
                    binding.root.context.getString(R.string.likes_count, post.likes)
                binding.likeTextView.isSelected = post.isLiked
                if (post.isLiked) {
                    binding.likeTextView.text = binding.root.context.getString(R.string.unlike)
                    binding.likeTextView.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.ic_favorite_filled, 0, 0, 0)
                } else {
                    binding.likeTextView.text = binding.root.context.getString(R.string.like)
                    binding.likeTextView.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.ic_favorite_unfilled, 0, 0, 0)
                }

                // Like button click listener
                binding.likeTextView.setOnClickListener {
                    likeClickListener.onLikeClicked(post.id) // Notify the listener
                }

                // Comment section (could expand or open a new activity for comments)
                binding.commentsTextView.setOnClickListener {
                    // Handle comment section
                    val dialog = CommentsBottomSheetDialog(post.comments)
                    dialog.show(supportFragmentManager, "CommentsBottomSheetDialog")
                }
            }
        }
    }
}