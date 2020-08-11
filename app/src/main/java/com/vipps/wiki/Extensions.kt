package com.vipps.wiki

import android.app.Application
import com.vipps.wiki.api.NoDataException
import retrofit2.HttpException

fun Exception.getErrorMessage(application: Application) : String {
    return when(this){
        is HttpException -> application.getString(R.string.network_error_message)
        is NoDataException -> application.getString(R.string.no_data)
        else -> application.getString(R.string.generic_error_message)
    }
}