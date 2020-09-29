package net.insi8.wiki

import android.app.Application
import net.insi8.wiki.api.NoDataException
import retrofit2.HttpException

/**
 * Error handling with exceptions,
 * this is to show message or do action for a specific type of exception.
 */
fun Exception.getErrorMessage(application: Application) : String {
    return when(this){
        is HttpException -> application.getString(R.string.network_error_message)
        is NoDataException -> application.getString(R.string.no_data)
        else -> application.getString(R.string.generic_error_message)
    }
}

/**
 * KMP (Knuth Morris Pratt) Pattern Searching algorithm allows the pattern to be
 * checked without going back in every wrong comparison. SO the complexity is O[N]
 *
 */
fun String.countThePattern(pattern: String) : Int {
    val patternLength = pattern.length

    /**
     * Build a Largest prefix that is a suffix array for the pattern to be checked.
     */
    val lpsArray = IntArray(patternLength).also { lps ->
        var lpsLength = 0
        var index = 1
        lps[0] = 0
        while (index < patternLength) {
            if (pattern[index] == pattern[lpsLength]) {
                lps[index] = lpsLength + 1
                lpsLength++
                index++
            } else {
                if (lpsLength != 0) {
                    lpsLength = lps[lpsLength - 1]
                }
                lps[index] = 0
                index++
            }
        }
    }

    /**
     * Kmp search for pattern matching.
     */
    fun patternSearch(): Int {
        var index = 0
        var patternIndex = 0

        var patternMachCount = 0

        while (index < length) {
            if (this[index] == pattern[patternIndex]) {
                index++
                patternIndex++
            } else {
                if (patternIndex == 0) {
                    index++
                } else {
                    patternIndex = lpsArray[patternIndex - 1]
                }
            }
            if (patternIndex == patternLength) {
                patternMachCount++
                patternIndex = lpsArray[patternIndex - 1]
            }
        }

        return patternMachCount
    }
    return patternSearch()
}