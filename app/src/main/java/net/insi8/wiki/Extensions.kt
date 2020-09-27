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