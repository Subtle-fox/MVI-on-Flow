package ru.subtlefox.mvi.cookbook.screens.sample6

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.subtlefox.mvi.cookbook.databinding.LayoutCountriesBinding
import ru.subtlefox.mvi.cookbook.screens.sample6.mvi.entity.SaveStateState.CountryItem

class ListAdapter : ListAdapter<CountryItem, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<CountryItem>() {
            override fun areItemsTheSame(oldItem: CountryItem, newItem: CountryItem) = oldItem === newItem
            override fun areContentsTheSame(oldItem: CountryItem, newItem: CountryItem) = oldItem == newItem
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        return ItemViewHolder(LayoutCountriesBinding.inflate(inflater, viewGroup, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        with((holder as ItemViewHolder).binding) {
            iso.text = item.iso
            name.text = item.name
            language.text = item.language
        }
    }

    class ItemViewHolder(val binding: LayoutCountriesBinding) : RecyclerView.ViewHolder(binding.root)
}
