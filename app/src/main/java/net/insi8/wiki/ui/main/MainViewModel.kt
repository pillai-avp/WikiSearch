package net.insi8.wiki.ui.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import net.insi8.wiki.R
import net.insi8.wiki.countThePattern
import net.insi8.wiki.getErrorMessage
import net.insi8.wiki.model.HtmlData
import net.insi8.wiki.model.WikiSearch
import net.insi8.wiki.repo.WikiRepository
import net.insi8.wiki.result.Result
import net.insi8.wiki.result.succeeded

class MainViewModel(
    private val application: Application,
    private val wikiRepository: WikiRepository
) : ViewModel() {

    val networkStateLiveData: LiveData<TopicSearchResult<WikiSearch>>
        get() = networkState

    private val networkState: MutableLiveData<TopicSearchResult<WikiSearch>> = MutableLiveData()

    val grepStateLiveData: LiveData<GrepResult<Int>>
        get() = grepState
    private val grepState: MutableLiveData<GrepResult<Int>> = MutableLiveData()


    fun doSearch(query: String) {
        if (validateUserInput(query)){
            viewModelScope.launch {
                wikiRepository.getDataForTopic(query).collect { result ->
                    handleSearchResult(result, query)
                }
            }
        } else {
            onError(application.resources.getString(R.string.no_data))
        }
    }

    private fun validateUserInput(query: String): Boolean = query.isNotEmpty()

    private fun handleSearchResult(
        result: Result<WikiSearch?>,
        query: String
    ) {
        when {
            result is Result.Loading -> networkState.postValue(TopicSearchResult.Loading())
            result.succeeded -> {
                onSuccessResult(result as Result.Success, query)
            }
            else -> {
                onError((result as Result.Error).exception.getErrorMessage(application))
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
        result: Result.Success<WikiSearch?>,
        query: String
    ) {
        result.data?.let {
            networkState.postValue(
                TopicSearchResult.DataReady(it)
            )
            countTheTopicInData(it.parse.htmlDataObject, query)
        }
    }


    /**
     * Do the counting of index string in the html data
     * and update the [GrepResult]
     */
    private fun countTheTopicInData(wholeData: HtmlData, query: String) {
        viewModelScope.launch {
            val result = try {
                val second = wholeData.htmlString.countThePattern(query)
                GrepResult.Success<Int>(second)
            } catch (e: Exception) {
                GrepResult.Error(e.getErrorMessage(application))
            }
            grepState.postValue(result)
        }

    }

}