package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val parentRecyclerViewItem = findViewById<RecyclerView>(
            R.id.parent_recyclerview
        )

        val parentItemAdapter = ParentItemAdapter(parentItemList())

        parentRecyclerViewItem.adapter = parentItemAdapter
        parentRecyclerViewItem.layoutManager = LinearLayoutManager(this)
    }

    private fun parentItemList(): List<ParentItem> {
        val itemList: MutableList<ParentItem> = ArrayList()
        for (i in 1..10) {
            itemList.add(
                ParentItem(
                    parentItemTitle = "Title $i",
                    childItemList = childItemList(),
                )
            )
        }
        return itemList
    }

    private fun childItemList(): List<ChildItem> {
        val childItemList: MutableList<ChildItem> = ArrayList()
        for (i in 1..10) {
            childItemList.add(
                ChildItem(
                    childItemTitle = "Card $i",
                )
            )
        }
        return childItemList
    }
}