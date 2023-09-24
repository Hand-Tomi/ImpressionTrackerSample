package com.example.myapplication

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ChildItemBinding

class ChildItemAdapter(private val itemList: List<ChildItem>) :
    ListAdapter<ChildItem, ChildViewHolder>(ChildDiffCallback), ImpressionTrackable {

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        i: Int
    ): ChildViewHolder {
        val binding = ChildItemBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup,
            false
        )
        return ChildViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ChildViewHolder,
        position: Int,
    ) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onImpressionItem(position: Int) {
        Log.v("###SON###", "onImpression $position")
    }
}

private object ChildDiffCallback : DiffUtil.ItemCallback<ChildItem>() {
    override fun areItemsTheSame(oldItem: ChildItem, newItem: ChildItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ChildItem, newItem: ChildItem): Boolean {
        return oldItem.childItemTitle == newItem.childItemTitle
    }
}

class ChildViewHolder(
    private val binding: ChildItemBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: ChildItem) {
        binding.childItemTitle.text = item.childItemTitle
    }
}