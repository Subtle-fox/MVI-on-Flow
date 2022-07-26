package ru.subtlefox.mvi.cookbook.screens.sample1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import ru.subtlefox.mvi.cookbook.screens.sample1.entities.StaticCurrencyState
import ru.subtlefox.mvi.cookbook.screens.sample1.mvi.StaticCurrencyFeature
import javax.inject.Inject
import ru.subtlefox.mvi.cookbook.databinding.FragmentSample1Binding as Binding

@AndroidEntryPoint
class StaticCurrencyFragment : Fragment() {

    @Inject
    lateinit var feature: StaticCurrencyFeature

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = Binding.inflate(inflater, container, false)

        lifecycleScope.launchWhenStarted {
            feature.collect {
                render(it, binding)
            }
        }

        return binding.root
    }

    private fun render(state: StaticCurrencyState, binding: Binding) {
        when (state) {
            is StaticCurrencyState.Data -> with(binding.content) {
                binding.progress.isVisible = false

                srcCurrency.text = state.srcCurrency
                srcAmount.text = state.srcAmount
                dstCurrency.text = state.dstCurrency
                dstAmount.text = state.dstAmount
            }
            is StaticCurrencyState.Loading -> {
                binding.progress.isVisible = true
            }
        }
    }
}