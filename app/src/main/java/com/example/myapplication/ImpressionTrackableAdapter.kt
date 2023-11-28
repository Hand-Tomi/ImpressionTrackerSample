package com.example.myapplication

import android.graphics.Rect
import android.view.View
import android.view.ViewTreeObserver
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class ImpressionTrackableAdapter<VH : RecyclerView.ViewHolder> :
    RecyclerView.Adapter<VH>() {

    private val globalOnScrollChangedListener =
        ViewTreeObserver.OnScrollChangedListener { checkImpression() }
    private val impressionPositions = mutableSetOf<Int>()
    private var recyclerView: RecyclerView? = null

    abstract fun onImpressionItem(position: Int)
    private val onAttachStateChangeListener = object : View.OnAttachStateChangeListener {
        override fun onViewAttachedToWindow(view: View) {
            view.viewTreeObserver.addOnScrollChangedListener(globalOnScrollChangedListener)
        }

        override fun onViewDetachedFromWindow(view: View) {
            view.viewTreeObserver.removeOnScrollChangedListener(globalOnScrollChangedListener)
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
        recyclerView.viewTreeObserver.addOnScrollChangedListener(globalOnScrollChangedListener)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        this.recyclerView = null
        recyclerView.viewTreeObserver.removeOnScrollChangedListener(globalOnScrollChangedListener)
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
    if (!this.isAttachedToWindow) return false
    val rect = Rect()
    val isVisibleRecyclerView = getGlobalVisibleRect(rect)
    if (!isVisibleRecyclerView) return false
    return (rect.bottom - rect.top) >= height
}