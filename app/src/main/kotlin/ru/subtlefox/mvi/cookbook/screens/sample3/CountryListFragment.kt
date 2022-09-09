package ru.subtlefox.mvi.cookbook.screens.sample3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
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

        val listLayoutManager = LinearLayoutManager(context)
        with(binding) {
            list.apply {
                adapter = listAdapter
                layoutManager = listLayoutManager
            }
            refreshAllList.setOnClickListener {
                viewModel.accept(CountryListAction.Refresh)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.collectState { state ->
                    binding.list.clearOnScrollListeners()

                    listAdapter.submitList(state.items)

                    binding.list.addOnScrollListener(object : PaginationScrollListener(listLayoutManager) {
                        override fun onLoadMore() {
                            state.nextPage?.let { viewModel.accept(CountryListAction.LoadPage(it)) }
                        }
                    })
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.collectEvents(::handleEvent)
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
