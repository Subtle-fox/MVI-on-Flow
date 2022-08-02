package ru.subtlefox.mvi.cookbook.screens.sample4.mvi


import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.subtlefox.mvi.cookbook.screens.sample4.data.LikesRemoteRepository
import ru.subtlefox.mvi.cookbook.screens.sample4.mvi.entity.RaceConditionEffect
import ru.subtlefox.mvi.flow.MviBootstrap
import javax.inject.Inject

class RaceConditionBootstrap @Inject constructor(
    private val likesRepository: LikesRemoteRepository
) : MviBootstrap<RaceConditionEffect> {

    override fun invoke(): Flow<RaceConditionEffect> {
        return likesRepository.likes.map(RaceConditionEffect::Data)
    }
}