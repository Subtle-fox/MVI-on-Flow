package ru.subtlefox.mvi.cookbook.screens.sample2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import ru.subtlefox.mvi.cookbook.screens.sample2.mvi.entitiy.DynamicCurrencyAction
import ru.subtlefox.mvi.cookbook.screens.sample2.mvi.entitiy.DynamicCurrencyEvent
import ru.subtlefox.mvi.cookbook.screens.sample2.mvi.entitiy.DynamicCurrencyState
import ru.subtlefox.mvi.cookbook.databinding.FragmentSample2Binding as Binding

@AndroidEntryPoint
class DynamicCurrencyFragment : Fragment() {
    private val viewModel: DynamicCurrencyViewModel by viewModels()
    private lateinit var binding: Binding


    /*
     * This variant will CANCEL flow when not in target lifecycle state and then RESTART
     *
     * override fun onCreate(savedInstanceState: Bundle?) {
     *     super.onCreate(savedInstanceState)
     *     lifecycleScope.launch {
     *         repeatOnLifecycle(Lifecycle.State.STARTED) {
     *             launch {
     *                 viewModel.collectState().collect {
     *                     // Render new state and delegate actions to ViewModel
     *                     render(it, binding, viewModel::accept)
     *                 }
     *             }
     *             launch {
     *                 viewModel.collectEvents().collect(::handleEvent)
     *             }
     *         }
     *     }
     * }
     */

    /*
    * This variant will SUSPEND flow when not in target lifecycle state and then RESUME
    */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = Binding.inflate(inflater, container, false)

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.collectState {
                // Render new state and delegate actions to ViewModel
                render(it, binding, viewModel::accept)
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.collectEvents {
                handleEvent(it)
            }
        }

        return binding.root
    }

    private fun render(state: DynamicCurrencyState, binding: Binding, onAction: (DynamicCurrencyAction) -> Unit) {
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

    private fun handleEvent(event: DynamicCurrencyEvent) {
        when (event) {
            is DynamicCurrencyEvent.Error -> displayError(event.message)
        }
    }

    private fun displayError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
