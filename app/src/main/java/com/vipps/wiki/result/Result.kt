package com.vipps.wiki.result

import com.vipps.wiki.result.Result.Success
import java.lang.Exception


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