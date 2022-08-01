package ru.subtlefox.mvi.cookbook.screens.sample1.mvi.entitiy

import ru.subtlefox.mvi.cookbook.domain.model.CurrencyRate


/*
 * For simplest cases there we can use domain class directly as Effect:
 */
typealias StaticCurrencyEffect = CurrencyRate

/*
 * Using sealed class is still OK and up to you:
 *
 * sealed class CurrencyEffect {
 *     data class Data(val currencyRate: CurrencyRate) : CurrencyEffect()
 * }
 *
*/
