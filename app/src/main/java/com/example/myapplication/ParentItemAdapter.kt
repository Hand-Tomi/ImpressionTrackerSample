package com.example.myapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ParentItemBinding

class ParentItemAdapter(private val itemList: List<ParentItem>) :
    ListAdapter<ParentItem, ParentViewHolder>(ParentDiffCallback) {

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        i: Int
    ): ParentViewHolder {
        val binding = ParentItemBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup,
            false
        )
        return ParentViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ParentViewHolder,
        position: Int,
    ) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}

private object ParentDiffCallback : DiffUtil.ItemCallback<ParentItem>() {
    override fun areItemsTheSame(oldItem: ParentItem, newItem: ParentItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ParentItem, newItem: ParentItem): Boolean {
        return oldItem.parentItemTitle == newItem.parentItemTitle
    }
}

class ParentViewHolder(
    private val binding: ParentItemBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: ParentItem) {
        binding.parentItemTitle.text = item.parentItemTitle
        binding.childRecyclerview.adapter = ChildItemAdapter(item.childItemList)
    }
}