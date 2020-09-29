package net.insi8.wiki.api

import net.insi8.wiki.model.WikiSearch
import retrofit2.http.GET
import retrofit2.http.Query

interface WikiServices {

    @GET("api.php")// ?action=parse&section=0&prop=text&format=json&%20page=Norway
    suspend fun searchTopic(
            @Query("action") action :String = "parse",
            @Query("section") section :String = "0",
            @Query("prop") prop :String = "text",
            @Query("format") format :String = "json",
            @Query("page") topic :String
    ) : WikiSearch

}