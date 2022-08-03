package ru.subtlefox.mvi.cookbook.screens.sample7

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.subtlefox.mvi.cookbook.databinding.FragmentSample2Binding
import ru.subtlefox.mvi.cookbook.databinding.FragmentSample5Binding
import ru.subtlefox.mvi.cookbook.screens.sample2.mvi.entitiy.DynamicCurrencyAction
import ru.subtlefox.mvi.cookbook.screens.sample2.mvi.entitiy.DynamicCurrencyEvent
import ru.subtlefox.mvi.cookbook.screens.sample2.mvi.entitiy.DynamicCurrencyState
import ru.subtlefox.mvi.cookbook.screens.sample5.ListAdapter
import ru.subtlefox.mvi.cookbook.screens.sample5.mvi.entity.SaveFilterAction
import ru.subtlefox.mvi.cookbook.screens.sample5.mvi.entity.SaveFilterState
import ru.subtlefox.mvi.cookbook.databinding.FragmentSample7Binding as Binding

/*
    ==== DISCLAIMER =====
    Just for demonstration 2 independent MviFeatures can be combined inside of single ViewModel

    In real world this example is much easier to implement using simple fragment stacking

 */
@AndroidEntryPoint
class CombinationFragment : Fragment() {
    private val viewModel: CombinationViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = Binding.inflate(inflater, container, false)
        val listAdapter = ListAdapter()
        with(binding) {
            footer.list.apply {
                adapter = listAdapter
                layoutManager = LinearLayoutManager(context)
            }
            footer.searchEdit.addTextChangedListener {
                viewModel.accept(SaveFilterAction.FilterChange(it?.toString().orEmpty()))
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.collectState().collect { state ->
                    when (state) {
                        is DynamicCurrencyState -> renderHeader(state, binding.header, viewModel::accept)
                        is SaveFilterState -> renderFooter(state, binding.footer, listAdapter)
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.collectEvents().collect(::handleEvent)
        }

        return binding.root
    }

    private fun renderHeader(
        state: DynamicCurrencyState,
        binding: FragmentSample2Binding,
        onAction: (DynamicCurrencyAction) -> Unit
    ) {
        when (state) {
            is DynamicCurrencyState.Data -> with(binding.content) {
                binding.progress.isVisible = false
                binding.changeCurrencyButton.setOnClickListener {
                    onAction(DynamicCurrencyAction.ChangeCurrency)
                }

                srcCurrency.text = state.srcCurrency
                srcAmount.text = state.srcAmount
                dstCurrency.text = state.dstCurrency
                dstAmount.text = state.dstAmount

            }
            is DynamicCurrencyState.Loading -> {
                binding.progress.isVisible = true
                binding.changeCurrencyButton.setOnClickListener(null)
            }
        }
    }

    private fun renderFooter(state: SaveFilterState, binding: FragmentSample5Binding, listAdapter: ListAdapter) {
        listAdapter.submitList(state.items)

        with(binding) {
            progress.isVisible = state.inProgress
            if (searchEdit.text.toString() != state.filter) {
                searchEdit.setText(state.filter)
                searchEdit.setSelection(state.filter.length)
            }
        }
    }

    private fun handleEvent(event: Any) {
        when (event) {
            is DynamicCurrencyEvent.Error -> displayError(event.message)
        }
    }

    private fun displayError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
