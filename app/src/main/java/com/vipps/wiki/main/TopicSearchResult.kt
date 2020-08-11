package com.vipps.wiki.main

import com.vipps.wiki.model.WikiSearch

sealed class TopicSearchResult<out T> {
    class Loading<T> : TopicSearchResult<T>()
    data class DataReady<T>(val result: WikiSearch) : TopicSearchResult<T>()
    data class Error<out T>(val errorMessage: String) : TopicSearchResult<T>()
}

sealed class GrepResult<out T> {
    data class Success<T>(val count: Int) : GrepResult<T>()
    data class Error(val errorMessage: String) : GrepResult<Nothing>()
}

