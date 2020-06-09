package com.conversantmedia.gdprcmp

import android.content.Context
import androidx.core.content.ContextCompat
import com.conversantmedia.gdprcmp.config.CmpConfigObject
import com.conversantmedia.gdprcmp.container.CmpWebView
import com.conversantmedia.gdprcmp.utils.ConsentStorage
import com.conversantmedia.gdprcmp.utils.JsonConfigReader
import com.conversantmedia.gdprcmp.utils.exceptions.CnvrCmpException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * Entry Class for the CMP module.
 *
 * @author Mat Cooper
 * @author Heath Kornblum
 * @version 1.0
 * @updated 04/14/2020
 */

open class CnvrCmp private constructor(){

    private lateinit var cmpContext: Context
    private var consentStorage: ConsentStorage? = null
    private val tag = javaClass.simpleName
    private val uiScope = CoroutineScope(Dispatchers.Default)

    /**
     * Constructor to be used when initializing the CMP from the Manifest.
     *
     * @param context
     * @throws CnvrCmpException when Privacy Policy in the manifest is invalid or missing.
     *
     */

    @Throws(CnvrCmpException::class)
    constructor(context: Context) : this() {
        cmpContext = context

        CmpConfigObject.buildConfigFromManifest(context.applicationContext)
        // Find Required Fields
        validate("manifest")
    }

    /**
     * Constructor to be used when initializing with cmp config JSON.
     *
     * @param context
     * @param cmpConfig string for cmpConfig JSON.
     *
     * @throws CnvrCmpException if config is invalid.
     */

    @Throws(CnvrCmpException::class)
    @Suppress("UNUSED")
    constructor(context: Context, cmpConfig: String) : this() {
        cmpContext = context.applicationContext

        //Build Config using supplied JSON.
        val jsonReader = JsonConfigReader()
        jsonReader.buildConfigFromJSON(cmpConfig)
    }

    /**
     * Method for allowing users to reset consents, will load the CMP with no bitString.
     */

    @Suppress("UNUSED")
    fun editConsents() {
        CmpConfigObject.CmpConfig.InApp.tcString = null
        CmpConfigObject.CmpConfig.gdprAppliesGlobally = true
        val intent =  CmpActivity.newIntent(cmpContext)
        ContextCompat.startActivity(cmpContext, intent, null)
    }

    /**
     * Entry method for loading the CMP.  The CmpWebView is never actually 'used' although it does
     * the work of sending a config object to the cmp bootstrap which triggers events that may
     * lead to an instance of the CmpWebView being attached to an Activity.
     */

    fun startCmp() {
        GlobalScope.launch(Dispatchers.Main) {
            val cmpWebView = CmpWebView(cmpContext)
        }
    }

    /**
     * Some fields are required by the publisher if they are integrating this CMP
     *
     * @param source is a string representing the cmp publisher configuration source,
     * either "manifest" or "JSON"
     */

    @Throws(CnvrCmpException::class)
    private fun validate(source: String) {
        if (CmpConfigObject.CmpConfig.countryCode == null) throw CnvrCmpException("Invalid or null Country Code in the $source")
        if (CmpConfigObject.CmpConfig.version == null) throw CnvrCmpException("String representing Config Version is required in the $source")
    }

}