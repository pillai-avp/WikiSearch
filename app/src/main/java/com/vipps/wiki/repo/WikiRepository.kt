package com.vipps.wiki.repo

import com.vipps.wiki.api.NoDataException
import com.vipps.wiki.api.WikiServices
import com.vipps.wiki.model.WikiSearch
import com.vipps.wiki.result.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface WikiRepository {
    suspend fun getDataForTopic(topic : String): Flow<Result<WikiSearch?>>
}


class WikiRepositoryImpl(
    private val wikiServices: WikiServices
): WikiRepository {

    /**
     * Get data from wiki network resource.
     * the flow first emit a Loading event, and once the api came back with result,
     * the flow or stream is updated with new data.
     * return [Flow<Result<WikiSearch>>]
     */
    override suspend fun getDataForTopic(topic: String): Flow<Result<WikiSearch?>> {
        return flow {
            emit(Result.Loading())

            val searchResult = try {
                val wikiSearch = wikiServices.searchTopic(topic = topic)
                validateResponse(wikiSearch)
            } catch (e: Exception) {
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
            throw NoDataException() // Specific error handling is done be throwing proper exceptions.
        }
    }

}