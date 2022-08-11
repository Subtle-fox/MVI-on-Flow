package ru.subtlefox.mvi.flow

import kotlinx.coroutines.flow.Flow

/**
 * MviBootstrap used to provide initial stream of effects.
 *
 * It is useful to preload data at startup or to observe data streams
 *
 * Sample usage:
 * ```
 *   1. Preload:
 *
 *   override fun invoke(): Flow<Effect> {
 *      return repository.loadData().map { Effect.Data(it) }
 *   }
 *
 *
 *   2. Observe:
 *
 *   override fun invoke(): Flow<Effect> {
 *       return combine(
 *           repository.observeConnectionChange(),
 *           repository.observeUserState()
 *       ) { isConnected, isPremium ->
 *           val premiumContent = loadPremium()
 *           Effect.PremiumContent(premiumContent)
 *       }
 *   }
 * ```
 */
fun interface MviBootstrap<Effect : Any> : () -> Flow<Effect> {

    fun interface Factory<Effect : Any> {
        fun create(isRestored: Boolean): MviBootstrap<Effect>
    }

}
