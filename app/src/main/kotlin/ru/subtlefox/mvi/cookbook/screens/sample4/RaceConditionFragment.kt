package ru.subtlefox.mvi.cookbook.screens.sample4

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.subtlefox.mvi.cookbook.screens.sample4.mvi.RaceConditionFeature
import ru.subtlefox.mvi.cookbook.screens.sample4.mvi.entity.RaceConditionAction
import ru.subtlefox.mvi.cookbook.screens.sample4.mvi.entity.RaceConditionState
import javax.inject.Inject
import ru.subtlefox.mvi.cookbook.databinding.FragmentSample4Binding as Binding

@AndroidEntryPoint
class RaceConditionFragment : Fragment() {

    @Inject
    lateinit var feature: RaceConditionFeature

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = Binding.inflate(inflater, container, false)
        with(binding) {
            likeButton.setAction(RaceConditionAction.Like)
            unlikeButton.setAction(RaceConditionAction.Unlike)
            shareButton.setAction(RaceConditionAction.Share)

            lifecycleScope.launchWhenStarted {
                feature.collect { binding.render(it) }
            }
        }

        return binding.root
    }

    private fun Binding.render(state: RaceConditionState) {
        likeAmount.text = state.likeAmount
        progress.isInvisible = state.progress == null
        progressAmount.text = state.progress
        shareLikes.text = state.likeShare
    }

    private fun Button.setAction(action: RaceConditionAction) {
        setOnClickListener {
            lifecycleScope.launch { feature.accept(action) }
        }
    }
}