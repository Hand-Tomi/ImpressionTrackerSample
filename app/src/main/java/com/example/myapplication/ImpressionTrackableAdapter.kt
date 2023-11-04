package com.example.myapplication

import android.graphics.Rect
import android.view.View
import android.view.ViewTreeObserver
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class ImpressionTrackableAdapter<VH : RecyclerView.ViewHolder> : RecyclerView.Adapter<VH>() {

    private val globalOnScrollChangedListener =
        ViewTreeObserver.OnScrollChangedListener { checkImpression() }
    private val impressionPositions = mutableSetOf<Int>()
    private var recyclerView: RecyclerView? = null

    abstract fun onImpressionItem(position: Int)

    private val onAttachStateChangeListener = object: View.OnAttachStateChangeListener {
        override fun onViewAttachedToWindow(v: View) {
            impressionPositions.clear()
            v.viewTreeObserver.addOnScrollChangedListener(globalOnScrollChangedListener)
        }

        override fun onViewDetachedFromWindow(v: View) {
            v.viewTreeObserver.removeOnScrollChangedListener(globalOnScrollChangedListener)
        }
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        checkImpression()
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
        recyclerView.addOnAttachStateChangeListener(onAttachStateChangeListener)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        this.recyclerView = null
        recyclerView.removeOnAttachStateChangeListener(onAttachStateChangeListener)
    }

    private fun checkImpression() {
        val newImpressionPositions = recyclerView?.getImpressionPositions() ?: return
        for (position in newImpressionPositions.minus(impressionPositions)) {
            onImpressionItem(position)
        }
        impressionPositions.clear()
        impressionPositions.addAll(newImpressionPositions)
    }
}

fun RecyclerView.getImpressionPositions(): Set<Int>? {

    if (!this.isRecyclerViewFullyVisible()) {
        return setOf()
    }

    val layoutManager = layoutManager as? LinearLayoutManager ?: return null
    val firstVisibleItemPosition = layoutManager.findFirstCompletelyVisibleItemPosition()
    val lastVisibleItemPosition = layoutManager.findLastCompletelyVisibleItemPosition()

    val set = mutableSetOf<Int>()
    for (position in firstVisibleItemPosition..lastVisibleItemPosition) {
        val view = layoutManager.findViewByPosition(position)
        if (view != null) {
            set.add(position)
        }
    }
    return set
}

/**
 * RecyclerViewが画面上で全て表示されているか
 */
private fun RecyclerView.isRecyclerViewFullyVisible(): Boolean {
    val rect = Rect()
    val isVisibleRecyclerView = getGlobalVisibleRect(rect)
    if (!isVisibleRecyclerView) return false
    return (rect.bottom - rect.top) >= height
}