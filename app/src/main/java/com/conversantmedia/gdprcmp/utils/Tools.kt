package com.conversantmedia.gdprcmp.utils

import org.json.JSONArray
import java.lang.NumberFormatException

/**
 * String parsing utilities
 * @author Mat Cooper
 * @author Heath Kornblum
 * @version 1.0
 * @updated 04/14/2020
 */
internal object Tools {
    /**
     *  Method for converting String to Int JSONArray
     */
    fun stringToIntJSONArray(intArrayString: String?) : JSONArray? {
        if (!(intArrayString.isNullOrEmpty())) {
            val noBrackets = intArrayString.removeSurrounding("[", "]")
            val numsCollection = noBrackets.split(",")
            val reallyNumsCollection = numsCollection.map {
                try {
                    it.toInt()
                } catch (e: NumberFormatException) {
                    return null
                }
            }
            return JSONArray(reallyNumsCollection)
        }
        return null
    }


    /*
     * Method for converting literal string array to JSONArray
     */
    fun stringArrayToJSONArray(stringArray: String?) : JSONArray? {
        if (!(stringArray.isNullOrEmpty())) {
            val noBrackets = stringArray.removeSurrounding("[", "]")
            val stringCollection = listOf(noBrackets)
            return JSONArray(stringCollection)
        }
        return null
    }
}