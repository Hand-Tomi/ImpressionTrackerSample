package com.example.myapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ParentItemBinding

class ParentItemAdapter(private val itemList: List<ParentItem>) :
    RecyclerView.Adapter<ParentItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        i: Int
    ): ViewHolder {
        val binding = ParentItemBinding.inflate(
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

    class ViewHolder(
        private val binding: ParentItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ParentItem) {
            binding.parentItemTitle.text = item.parentItemTitle
            binding.childRecyclerview.adapter = ChildItemAdapter(
                title = item.parentItemTitle,
                itemList = item.childItemList,
            )
        }
    }
}

class ParentItem(
    var parentItemTitle: String,
    var childItemList: List<ChildItem>
)