package net.insi8.wiki.result

import net.insi8.wiki.result.Result.Success


sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    class Loading<T> : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}


/**
 * `true` if [Result] is of type Success & holds non-null [Success.data].
 */
val Result<*>.succeeded
    get() = this is Result.Success && data != null