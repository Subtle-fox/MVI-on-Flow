package ru.subtlefox.mvi.cookbook.screens.sample3

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/*
    https://github.com/codepath/android_guides/wiki/Endless-Scrolling-with-AdapterViews-and-RecyclerView
    Contributed content licensed under cc-wiki with attribution required
 */


abstract class PaginationScrollListener(var layoutManager: LinearLayoutManager) : RecyclerView.OnScrollListener() {
    // The minimum number of items to have below your current scroll position
    // before loading more.
    private var visibleThreshold = 10

    // The current offset index of data you have loaded
    private var currentPage = 0

    // The total number of items in the dataset after the last load
    private var previousTotalItemCount = 0

    // True if we are still waiting for the last set of data to load.
    private var loading = true

    // Sets the starting page index
    private val startingPageIndex = -1

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItem = layoutManager.findFirstVisibleItemPosition()

        if (totalItemCount < previousTotalItemCount) {
            this.currentPage = this.startingPageIndex
            this.previousTotalItemCount = totalItemCount
            if (totalItemCount == 0) {
                this.loading = true
            }
        }

        if (loading && totalItemCount > previousTotalItemCount) {
            loading = false
            previousTotalItemCount = totalItemCount
            currentPage++
        }

        // If it isn't currently loading, we check to see if we have breached
        // the visibleThreshold and need to reload more data.
        // If we do need to reload some more data, we execute onLoadMore to fetch the data.
        if (!loading && firstVisibleItem + visibleItemCount + visibleThreshold >= totalItemCount) {
            loading = onLoadMore(currentPage + 1, totalItemCount)
        }
    }

    // Defines the process for actually loading more data based on page
    // Returns true if more data is being loaded; returns false if there is no more data to load.
    abstract fun onLoadMore(page: Int, totalItemsCount: Int): Boolean
}