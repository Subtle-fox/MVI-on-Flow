package ru.subtlefox.mvi.cookbook.domain

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.io.IOException
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

/*
 *   Stub api to "fetch" countries list
 *
 *   Simulates IoException if page == `errorPage`
 */

@Singleton
class CountriesApi @Inject constructor() {

    private var errorPage = 1

    suspend fun getCountryList(page: Int, count: Int): List<Locale> {
        return withContext(Dispatchers.IO) {
            delay(2000)

            if (page == errorPage) {
                errorPage++
                throw IOException("Simulated Error")
            }

            val locales = Locale.getAvailableLocales()
                .filter { it.country.length == 2 }
                .distinctBy { it.country }
                .sortedBy { it.country }

            val fromIndex = page * count
            val toIndex = ((page + 1) * count).coerceAtMost(locales.size)

            locales.subList(fromIndex, toIndex)
        }
    }

    suspend fun getCountryList(filter: String): List<Locale> {
        return getCountryList(0, 10000).filter { locale ->
            with(locale) {
                displayCountry.startsWith(filter, true) || displayLanguage.startsWith(filter, true)
            }
        }
    }
}
