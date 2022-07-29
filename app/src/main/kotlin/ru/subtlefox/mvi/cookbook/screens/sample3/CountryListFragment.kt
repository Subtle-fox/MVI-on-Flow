package ru.subtlefox.mvi.cookbook.screens.sample3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ru.subtlefox.mvi.cookbook.R
import ru.subtlefox.mvi.cookbook.screens.sample3.mvi.entity.CountryListAction
import ru.subtlefox.mvi.cookbook.screens.sample3.mvi.entity.CountryListEvent
import ru.subtlefox.mvi.cookbook.databinding.FragmentSample3Binding as Binding

@AndroidEntryPoint
class CountryListFragment : Fragment() {
    private val viewModel: CountryListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = Binding.inflate(inflater, container, false)

        val listAdapter = CountryListAdapter(
            { itemId -> viewModel.accept(CountryListAction.OpenCurrencyRatesForCountry(itemId)) },
            { failedPage -> viewModel.accept(CountryListAction.LoadPage(failedPage)) },
        )

        with(binding) {
            list.apply {
                val listLayoutManager = LinearLayoutManager(context)
                adapter = listAdapter
                layoutManager = listLayoutManager
                addOnScrollListener(object : PaginationScrollListener(listLayoutManager) {
                    override fun onLoadMore(page: Int, totalItemsCount: Int): Boolean {
                        viewModel.accept(CountryListAction.LoadPage(page))
                        return true
                    }
                })
            }
            refreshAllList.setOnClickListener {
                viewModel.accept(CountryListAction.Refresh)
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.collectState().collect {
                listAdapter.submitList(it.items)
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.collectEvents().collect(::handleEvent)
        }

        return binding.root
    }

    private fun handleEvent(event: CountryListEvent) {
        when (event) {
            is CountryListEvent.OpenCurrencyRatesForCountry -> {
                findNavController().navigate(R.id.to_dynamic_currency_list)
            }
        }
    }
}