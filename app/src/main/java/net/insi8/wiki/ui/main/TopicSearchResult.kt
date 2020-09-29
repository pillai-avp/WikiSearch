package net.insi8.wiki.ui.main

import net.insi8.wiki.model.WikiSearch
import net.insi8.wiki.ui.main.TopicSearchResult.*

/**
 * This is build as container for data with proper UI state.
 * such as [Loading], [DataReady], and [Error]
 */
sealed class TopicSearchResult<out T> {
    class Loading<T> : TopicSearchResult<T>()
    data class DataReady<T>(val result: WikiSearch) : TopicSearchResult<T>()
    data class Error<out T>(val errorMessage: String) : TopicSearchResult<T>()
}

