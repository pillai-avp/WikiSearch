package com.vipps.wiki.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vipps.wiki.R
import com.vipps.wiki.getErrorMessage
import com.vipps.wiki.model.HtmlData
import com.vipps.wiki.model.WikiSearch
import com.vipps.wiki.repo.WikiRepository
import com.vipps.wiki.repo.countUsingKMP
import com.vipps.wiki.result.Result
import com.vipps.wiki.result.succeeded
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainViewModel(private val application: Application,
                    private val wikiRepository: WikiRepository): ViewModel() {

    val grepStateLiveData : LiveData<GrepResult<Int>>
    get() = grepState
    private val grepState : MutableLiveData<GrepResult<Int>> = MutableLiveData()


    val networkStateLiveData : LiveData<TopicSearchResult<WikiSearch>>
    get() = networkState

    private val networkState : MutableLiveData<TopicSearchResult<WikiSearch>> = MutableLiveData()

    fun doSearch(query: String) {
        if (validateUserInput(query)){
            viewModelScope.launch {
                wikiRepository.getDataForTopic(query).collect {
                    handleSearchResult(it, query)
                }
            }
        } else {
            onError(application.resources.getString(R.string.no_data))
        }
    }

    private fun validateUserInput(query: String): Boolean = query.isNotEmpty()

    private fun handleSearchResult(
        it: Result<WikiSearch?>,
        query: String
    ) {
        when {
            it is Result.Loading -> networkState.postValue(TopicSearchResult.Loading())
            it.succeeded -> {
                onSuccessResult(it, query)
            }
            else -> {
                onError((it as Result.Error).exception.getErrorMessage(application))
            }
        }
    }

    private fun onError(errorMessage: String) {
        networkState.postValue(
            TopicSearchResult.Error(
                errorMessage
            )
        )
    }

    private fun onSuccessResult(
        it: Result<WikiSearch?>,
        query: String
    ) {
        networkState.postValue(
            TopicSearchResult.DataReady((it as Result.Success).data!!)
        )
        countTheTopicInData(it.data!!.parse.htmlDataObject, query)
    }

    /**
     * Do the counting of index string in the html data
     * and update the [GrepResult]
     */
    private fun countTheTopicInData(wholeData: HtmlData, query: String) {
        viewModelScope.launch {
            val result = try {
                //val first = wholeData.htmlString.countOfSubString(query)
                val second = wholeData.htmlString.countUsingKMP(query)
                GrepResult.Success<Int>(second)
            } catch (e: Exception) {
                GrepResult.Error(e.getErrorMessage(application))
            }
            grepState.postValue(result)
        }

    }

}