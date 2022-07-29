package ru.subtlefox.mvi.cookbook.domain

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import ru.subtlefox.mvi.cookbook.domain.model.Currency
import ru.subtlefox.mvi.cookbook.domain.model.CurrencyRate
import java.io.IOException
import java.math.BigDecimal
import java.math.RoundingMode
import javax.inject.Inject

/*
 *   Stub api to "fetch" currency rates
 *
 *   Simulates IoException if @param:from == @param:to
 */

class CurrencyRateApi @Inject constructor() {

    suspend fun getRate(from: Currency, to: Currency): CurrencyRate {
        println("api call $from -> $to")

        return withContext(Dispatchers.IO) {
            if (from == to) throw IOException("Simulated Error")

            delay(500)
            CurrencyRate(
                from = from,
                to = to,
                rate = (to.base / from.base + BigDecimal(System.currentTimeMillis() % 100.0 / 100)).setScale(
                    5,
                    RoundingMode.HALF_EVEN
                )
            )
        }
    }


}