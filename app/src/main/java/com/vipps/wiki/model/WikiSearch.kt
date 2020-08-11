package com.vipps.wiki.model
import com.google.gson.annotations.SerializedName

data class HtmlData(
        @SerializedName("*")
        val htmlString: String
)

data class Parse(
        val pageid: Int,
        @SerializedName("text")
        val htmlDataObject: HtmlData,
        val title: String
)

/**
 * Data received from wikipedia
 */
data class WikiSearch(
        val parse: Parse
)