package com.fgfbrands.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.fgfbrands.adapter.CommentAdapter
import com.fgfbrands.databinding.DialogCommentsBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CommentsBottomSheetDialog(private val comments: MutableList<String>) :
    BottomSheetDialogFragment() {

    private var _binding: DialogCommentsBinding? = null
    private val binding get() = _binding!!
    private lateinit var commentAdapter: CommentAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogCommentsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize the adapter
        commentAdapter = CommentAdapter(comments)

        // Setup RecyclerView
        binding.recyclerViewComments.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = commentAdapter
        }

        // Add new comment example (e.g., triggered by a button)
        binding.btnAddComment.setOnClickListener {
            val newComment = binding.etNewComment.text.toString()
            if (newComment.isNotBlank()) {
                commentAdapter.addComment(newComment)
                binding.etNewComment.text.clear()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
