package com.vipps.wiki.repo

import com.vipps.wiki.api.NoDataException
import com.vipps.wiki.result.Result
import com.vipps.wiki.api.WikiServices
import com.vipps.wiki.model.WikiSearch
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

interface WikiRepository {
    suspend fun getDataForTopic(topic : String): Flow<Result<WikiSearch?>>
}


class WikiRepositoryImpl(
    private val wikiServices: WikiServices
): WikiRepository {

    override suspend fun getDataForTopic(topic : String): Flow<Result<WikiSearch?>> {
        return flow {
            emit(Result.Loading())

            val searchResult = try {
                val wikiSearch = wikiServices.searchTopic(topic = topic)
                validateResponse(wikiSearch)
            } catch (e: Exception){
                Result.Error(e)
            }
            emit(searchResult)
        }
    }

    @Throws(NoDataException::class)
    private fun validateResponse(wikiSearch: WikiSearch): Result<WikiSearch> {
        return if (wikiSearch.parse != null) {
            Result.Success(wikiSearch)
        } else {
            throw NoDataException()
        }
    }

}