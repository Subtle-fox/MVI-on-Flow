package ru.subtlefox.mvi.cookbook.screens.sample5

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import ru.subtlefox.mvi.cookbook.screens.sample5.mvi.entity.SaveFilterAction
import ru.subtlefox.mvi.cookbook.screens.sample5.mvi.entity.SaveFilterState
import ru.subtlefox.mvi.cookbook.databinding.FragmentSample5Binding as Binding

@AndroidEntryPoint
class SaveFilterFragment : Fragment() {
    private val viewModel: SaveFilterViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = Binding.inflate(inflater, container, false)
        val listAdapter = ListAdapter()
        with(binding) {
            list.apply {
                adapter = listAdapter
                layoutManager = LinearLayoutManager(context)
            }
            searchEdit.addTextChangedListener {
                viewModel.accept(SaveFilterAction.FilterChange(it?.toString().orEmpty()))
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.collectState().collect { state -> render(state, binding, listAdapter) }
            }
        }

        return binding.root
    }

    private fun render(state: SaveFilterState, binding: Binding, listAdapter: ListAdapter) {
        listAdapter.submitList(state.items)

        with(binding) {
            progress.isVisible = state.inProgress
            if (searchEdit.text.toString() != state.filter) {
                searchEdit.setText(state.filter)
                searchEdit.setSelection(state.filter.length)
            }
        }
    }
}
