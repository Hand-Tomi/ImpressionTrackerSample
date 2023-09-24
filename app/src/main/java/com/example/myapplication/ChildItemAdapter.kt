package com.example.myapplication

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ChildItemBinding

class ChildItemAdapter(
    private val title: String,
    private val itemList: List<ChildItem>,
) : ImpressionTrackableAdapter<ChildItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        i: Int
    ): ViewHolder {
        val binding = ChildItemBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
    ) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onImpressionItem(position: Int) {
        Log.v("###SON###", "$title ${itemList[position].childItemTitle}")
    }

    class ViewHolder(
        private val binding: ChildItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ChildItem) {
            binding.childItemTitle.text = item.childItemTitle
        }
    }
}

class ChildItem(
    var childItemTitle: String
)