package ru.subtlefox.mvi.cookbook.screens.sample3

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.subtlefox.mvi.cookbook.databinding.LayoutCountriesBinding
import ru.subtlefox.mvi.cookbook.databinding.LayoutErrorBinding
import ru.subtlefox.mvi.cookbook.databinding.LayoutProgressBinding
import ru.subtlefox.mvi.cookbook.screens.sample3.mvi.entity.CountryListState

class CountryListAdapter(
    private val onItemClick: (iso: String) -> Unit,
    private val onRetryClick: (page: Int) -> Unit,
) : ListAdapter<Any, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        const val VIEW_TYPE_CURRENCY = 0
        const val VIEW_TYPE_PROGRESS = 1
        const val VIEW_TYPE_ERROR = 2

        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Any>() {
            override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
                return oldItem == newItem && oldItem !is CountryListState.LoadingItem
            }
            override fun areContentsTheSame(oldItem: Any, newItem: Any) = areItemsTheSame(oldItem, newItem)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (val item = getItem(position)) {
            is CountryListState.CountryItem -> VIEW_TYPE_CURRENCY
            is CountryListState.LoadingItem -> VIEW_TYPE_PROGRESS
            is CountryListState.ErrorItem -> VIEW_TYPE_ERROR
            else -> throw IllegalArgumentException("Not supported: ${item.javaClass.simpleName}")
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        return when (viewType) {
            VIEW_TYPE_CURRENCY -> CurrencyViewHolder(LayoutCountriesBinding.inflate(inflater, viewGroup, false))
            VIEW_TYPE_PROGRESS -> ProgressViewHolder(LayoutProgressBinding.inflate(inflater, viewGroup, false))
            VIEW_TYPE_ERROR -> ErrorViewHolder(LayoutErrorBinding.inflate(inflater, viewGroup, false))
            else -> throw IllegalArgumentException("Not supported: $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is CountryListState.CountryItem -> with((holder as CurrencyViewHolder).binding) {
                iso.text = item.iso
                name.text = item.name
                language.text = item.language
                root.setOnClickListener {
                    onItemClick(item.iso)
                }
            }
            is CountryListState.ErrorItem -> with((holder as ErrorViewHolder).binding) {
                retryButton.setOnClickListener {
                    onRetryClick(item.failedPage)
                }
            }
        }
    }

    ////////////////
    // ViewHolders

    class CurrencyViewHolder(val binding: LayoutCountriesBinding) : RecyclerView.ViewHolder(binding.root)

    class ProgressViewHolder(binding: LayoutProgressBinding) : RecyclerView.ViewHolder(binding.root)

    class ErrorViewHolder(val binding: LayoutErrorBinding) : RecyclerView.ViewHolder(binding.root)
}