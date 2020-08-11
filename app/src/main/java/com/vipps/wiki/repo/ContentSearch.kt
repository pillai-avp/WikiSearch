package com.vipps.wiki.repo

import android.util.Log

class ContentSearch() {


    fun f(s: String){
        val pie = IntArray(s.length)
        var j = 0

        //a b a b b a cbab
        //0 0 1 2 2
        for (i in 1 until s.length){
            if(s[j] == s[i]){
                pie[i] = j + 1
            }

            j++


        }
    }




    fun KMP(large: String, small: String) : Int {
        var count = 0

        val smallArray = small.toCharArray()
        // aaabbacbab

        // next[i] stores the index of next best partial match
        val next = IntArray(small.length + 1)

        for (i in 1 until small.length) {
            var j = next[i + 1]
            while (j > 0 && smallArray[j] != smallArray[i]){
                j = next[j]
            }
            if (j > 0 || smallArray[j] == smallArray[i]) {
                next[i + 1] = j + 1
            }
        }

        print(next.toString())


        var i = 0
        var j = 0
        while (i < large.length) {
            if (j < small.length && large[i] == small[j]) {
                if (++j == small.length) {
                    count++
                    Log.d("",
                        "Pattern occurs with shift " +
                                (i - j + 1)
                    )
                }
            } else if (j > 0) {
                j = next[j]
                i-- // since i will be incremented in next iteration
            }
            i++
        }
        return count
    }
}

fun String.countUsingKMP(pattern: String) : Int {
    val patternLength = pattern.length

    fun getLargestPrefixSuffixArray() : IntArray {
        return IntArray(patternLength).also {
            var lpsLength = 0
            var index = 1
            it[0] = 0
            while (index < patternLength){
                if(pattern[index] == pattern[lpsLength]){
                    it[index] = lpsLength + 1
                    lpsLength++
                    index++
                } else {
                    if(lpsLength != 0){
                        lpsLength = it[lpsLength -1]
                    }
                    it[index] = 0
                    index++
                }
            }
        }
    }

    fun patternSearch(lps: IntArray): Int{
        var i = 0
        var j = 0

        var count = 0

        while (i < length){
            if(this[i] == pattern[j]){
                i++
                j++
            } else {
                if(j == 0){
                    i++
                } else {
                    j = lps[j-1]
                }
            }
            if(j == patternLength){
                count++
                j = lps[j-1]
            }
        }

        return count
    }

    var lps = getLargestPrefixSuffixArray()
    return patternSearch(lps)


}

fun String.countOfSubString(string: String) : Int {
    var index = 0
    var offset = 0
    var count = 0

    var topicIndex = 0


    while(index < length){
        if(this[index] == string[topicIndex]){

            if(topicIndex + 1 == string.length){
                count++
                topicIndex = 0
            } else {
                topicIndex++
                index++
            }

        } else {
            offset++
            index = offset
            topicIndex = 0
        }
    }

    return count

}