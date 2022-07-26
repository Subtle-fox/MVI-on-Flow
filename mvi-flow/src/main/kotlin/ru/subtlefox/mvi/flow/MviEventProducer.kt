package ru.subtlefox.mvi.flow

/**
 * EventProducer produce one-shot events or null (if no events needed in react to specific Event)
 * Example of such events are: toasts, notifications, navigation intents.
 *
 * EventProducer doesn't alter incoming effect.
 *
 * Sample usage:
 * ```
 *      override fun invoke(effect: Effect): Event {
 *          when(effect) {
 *              is Effect.OpenScreen -> Event.OpenScreen(effect.destination)
 *              is Effect.Error -> Event.ShowToast(effect.error.message)
 *              else -> null
 *          }
 *      }
 * ```
 */
fun interface MviEventProducer<Effect : Any, Event : Any> : (Effect) -> Event? {
    override fun invoke(effect: Effect): Event?
}
