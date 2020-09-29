package net.insi8.wiki

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    val longText = """There is a String in the text fun search1(topic: String) : Int fun search1(topic: String) : Int fun search1(topic: String) : Int fun search1(topic: String) : String"""
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.vipps.wiki", appContext.packageName)

        val text = "aaafbabsdf"
        val pattern = "afb"

        KMP(text, pattern)
    }


    var startIndex = 0
    val endIndex = longText.length
    var count = 0

    // One option but it has a bugger memmory issue creating nm times Strings
    fun search1(topic: String) : Int {
        val topicEndIndex = topic.length
        var itrationCount = 0
        while(startIndex + topic.length < endIndex){
            itrationCount++
            val subString = longText.substring(startIndex, startIndex + topic.length)
            if(subString == topic){
                count++
                print(subString)
            }
            startIndex++
        }

        return count

    }

    // One option Now atleast the objects are gone. still nm itration is happening
    fun search2(topic: String) : Int {
        var topicIndex = 0
        var offset = 0

        while(startIndex < endIndex){
            if(longText[startIndex] == topic[topicIndex]){

                if(topicIndex + 1 == topic.length){
                    count++
                    topicIndex = 0
                } else {
                    topicIndex++
                    startIndex++
                }

            } else {
                offset++
                startIndex = offset
                topicIndex = 0
            }
        }

        return count

    }




    fun KMP(large: String, pattern: String) : Int {
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

            while (i < large.length){
                if(large[i] == pattern[j]){
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
}