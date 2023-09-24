package com.example.myapplication

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import android.view.ViewTreeObserver
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class ImpressionTrackingRecyclerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : RecyclerView(context, attrs, defStyleAttr) {

    private val globalOnScrollChangedListener =
        ViewTreeObserver.OnScrollChangedListener { checkImpression() }
    private val fullyVisibleItems = mutableSetOf<Int>()
    private var impressionTrackableAdapter: ImpressionTrackable? = null

    override fun setAdapter(adapter: Adapter<*>?) {
        super.setAdapter(adapter)
        impressionTrackableAdapter = (adapter as? ImpressionTrackable)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        fullyVisibleItems.clear()
        this.viewTreeObserver.addOnScrollChangedListener(globalOnScrollChangedListener)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        this.viewTreeObserver.removeOnScrollChangedListener(globalOnScrollChangedListener)
    }

    private fun checkImpression() {
        val layoutManager = layoutManager as? LinearLayoutManager ?: return

        val firstVisibleItemPosition = layoutManager.findFirstCompletelyVisibleItemPosition()
        val lastVisibleItemPosition = layoutManager.findLastCompletelyVisibleItemPosition()

        for (position in firstVisibleItemPosition..lastVisibleItemPosition) {
            val view = layoutManager.findViewByPosition(position)
            if (view != null && isViewFullyVisible(view)) {
                if (!fullyVisibleItems.contains(position)) {
                    fullyVisibleItems.add(position)
                    impressionTrackableAdapter?.onImpressionItem(position)
                }
            } else {
                fullyVisibleItems.remove(position)
            }
        }
    }

    private fun isViewFullyVisible(target: View): Boolean {
        val rect = Rect()
        val isVisibleRecyclerView = getGlobalVisibleRect(rect)
        if (!isVisibleRecyclerView) return false
        if ((rect.bottom - rect.top) < height) {
            return false
        }
        return target.getGlobalVisibleRect(rect)
    }
}