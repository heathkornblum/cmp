package com.conversantmedia.gdprcmp.utils

import android.content.Context
import android.util.Log
import com.conversantmedia.gdprcmp.utils.exceptions.CnvrCmpException


/**
 * Class for retrieving the Google Advertiser ID from the device.  Returns a number with zeroes in
 * case of exceptions.
 *
 * @author Mat Cooper
 * @author Heath Kornblum
 * @version 1.0
 * @updated 04/14/2020
 *
 */

import com.google.android.gms.ads.identifier.AdvertisingIdClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class AdvertisingId(val context: Context) {

    private val tag = javaClass.simpleName
    private val adIdInfo = AdvertisingIdClient(context.applicationContext)

    suspend fun getAdvertisingID(): String =

        withContext(Dispatchers.IO) {
            try {
                adIdInfo.start()
                val adID = adIdInfo.info
                adIdInfo.finish()
                adID.id
            } catch (exception: IOException) {
                Log.e(tag, exception.message)
                "00000000-0000-0000-0000-000000000000"
            } catch (exception: CnvrCmpException) {
                Log.e(tag, exception.message)
                "00000000-0000-0000-0000-000000000000"
            }
        }
}

