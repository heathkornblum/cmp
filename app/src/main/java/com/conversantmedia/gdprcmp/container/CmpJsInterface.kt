package com.conversantmedia.gdprcmp.container

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.preference.PreferenceManager
import androidx.core.content.ContextCompat.startActivity
import android.webkit.JavascriptInterface
import com.conversantmedia.gdprcmp.CmpActivity
import com.conversantmedia.gdprcmp.utils.ConsentStorage

/**
 * Javascript interface to be used with CMP web views
 *
 * This provides all of the Javascript functions used with cmp callbacks to store IAB in-app GDPR
 * consent values.
 *
 * @author Mat Cooper
 * @author Heath Kornblum
 * @version 1.0
 * @updated 04/14/2020
 *
 * @param context
 * @param webView webView that the JavascriptInterface will be used with
 */

@Suppress("UNUSED")
internal class CmpJsInterface(private val context: Context, private val webView: CmpWebView) {

    private val TAG = javaClass.simpleName

    private val store = ConsentStorage(context.applicationContext)

    private val prefs = PreferenceManager.getDefaultSharedPreferences(context)
    /**
     * JS Interface method for setting the CMP SDK ID
     *
     * @param cmpSdkId The unsigned integer ID of CMP SDK
     */


    @JavascriptInterface
    fun storeCmpSdkId(cmpSdkId: Int) {
        store.setCmpSdkId(cmpSdkId)
    }

    /**
     * JS Interface method for setting the CMP SDK Version Number
     *
     * @param sdkVersion The unsigned integer representing the version of the TCF that these consents adhere to.
     */

    @JavascriptInterface
    fun storeCmpSdkVersion(sdkVersion: String) {
        store.setCmpSdkVersion(sdkVersion)
    }

    /**
     * JS Interface method for setting the Policy Version
     *
     * @param policyVersion: The unsigned integer representing the version of the TCF that these
     * consents adhere to.
     */

    @JavascriptInterface
    fun storePolicyVersion(policyVersion: Int) {
        store.setPolicyVersion(policyVersion)
    }

    /**
     * JS Interface method for setting whether GDPR Applies
     *
     * @param applies: Does GDPR apply?  If left unset, deemed to be undetermined (default prior to
     * initialization)
     */

    @JavascriptInterface
    fun storeGdprApplies(applies: Boolean) {
        store.setGdprApplies(applies.toInt())
    }

    /**
     * JS Interface for setting the publisher's country code
     *
     * @param countryCode: 2 letter country code.  AA is the default and means unknown
     */

    @JavascriptInterface
    fun storeCountryCode(countryCode: String) {
        store.setIabPublisherCc(countryCode)
    }

    /**
     * JS Interface for setting Purpose One Treatment
     *
     * @param purposeOne: Integer value of 0 or 1
     */

    @JavascriptInterface
    fun storePurposeOneTreatment(purposeOne: Boolean) {
        store.setPurposeOneTreatment(purposeOne.toInt())
    }

    /**
     * JS Interface for determining whether to use non-standard stacks
     *
     * @param used: Integer value of 0 or 1
     */

    @JavascriptInterface
    fun storeUseNonStandardStacks(used: Boolean) {
        store.setUseNonStandardStacks(used.toInt())
    }

    /**
     * JS Interface for storing the full encoded TC string
     *
     * @param tcString: full encoded TC string
     */

    @JavascriptInterface
    fun storeTCString(tcString: String) {
        store.setTcString(tcString)
    }

    /**
     * JS Interface for storing vendor consents
     *
     * @param consents: Binary string
     */

    @JavascriptInterface
    fun storeVendorConsents(consents: String) {
        store.setVendorConsents(consents)
    }

    /**
     * JS Interface for storing Vendor Legitimate Interests
     *
     * @param vendorLegitimateInterests: Binary String
     */

    @JavascriptInterface
    fun storeVendorLegitimateInterests(vendorLegitimateInterests: String) {
        store.setVendorLegitimateInterests(vendorLegitimateInterests)
    }

    /**
     * JS Interface for storing purpose consents
     *
     * @param purposeConsents: Binary String
     */

    @JavascriptInterface
    fun storePurposeConsents(purposeConsents: String) {
        store.setPurposeConsents(purposeConsents)
    }

    /**
     * JS Interface for setting purpose legitimate interests
     *
     * @param purposeLegitimateInterests: Binary String
     */

    @JavascriptInterface
    fun storePurposeLegitimateInterests(purposeLegitimateInterests: String) {
        store.setPurposeLegitimateInterests(purposeLegitimateInterests)
    }

    /**
     * JS Interface for setting special features opt-ins
     *
     * @param specialFeaturesOptIns: Binary String
     */

    @JavascriptInterface
    fun storeSpecialFeaturesOptIns(specialFeaturesOptIns: String) {
        store.setSpecialFeaturesOptIns(specialFeaturesOptIns)
    }

    /**
     * JS Interface for setting publisher restrictions
     *
     * @param id: the purpose ID string
     * @param restriction: string value
     */

    @JavascriptInterface
    fun storePublisherRestrictions(purpose:String, restrictions: String) {
        store.setPublisherRestrictions(purpose, restrictions)
    }

    /**
     * JS Interface for setting publisher consent
     *
     * @param publisherConsent: Binary String
     */

    @JavascriptInterface
    fun storePublisherConsent(publisherConsent: String) {
        store.setPublisherConsent(publisherConsent)
    }

    /**
     * JS Interface for setting publisher legitimate interests
     *
     * @param publisherLegitimateInterests: Binary String
     */

    @JavascriptInterface
    fun storePublisherLegitimateInterests(publisherLegitimateInterests: String) {
        store.setPublisherLegitimateInterests(publisherLegitimateInterests)
    }

    /**
     * JS Interface for setting publisher custom purposes consents
     *
     * @param publisherCustomPurposesConsents: Binary String
     */

    @JavascriptInterface
    fun storePublisherCustomPurposesConsents(publisherCustomPurposesConsents: String) {
        store.setPublisherCustomPurposesConsents(publisherCustomPurposesConsents)
    }

    /**
     * JS Interface for setting publisher custom purposes legitimate interests
     *
     * @param publisherCustomPurposesLegitimateInterests: Binary String
     */

    @JavascriptInterface
    fun storePublisherCustomPurposesLegitimateInterests(publisherCustomPurposesLegitimateInterests: String) {
        store.setPublisherCustomPurposesLegitimateInterests(publisherCustomPurposesLegitimateInterests)
    }

    /**
     * Set a Shared Preference for the Config Version.  This will later be used as the config
     * inApp.storedVersion for comparision by the CMP while determining whether or not to show the
     * UI.
     */
    @JavascriptInterface
    fun storeConfigVersion(configVersion:String) {
        store.setConfigVersion(configVersion)
    }

    /**
     * Javascript interface Method called when cmp is complete to
     * end the cmpActivity
     */
    @JavascriptInterface
    fun cmpComplete() {
        // Prevent leaky CMP
        webView.endCmp()
    }

    /**
     * Javascript interface Method used to load the cmpActivity
     * if consent is required.
     */

    @JavascriptInterface
    fun loadCmp(consentNeeded: Boolean) {
        //Clean up the consent check WebView.
        destroyWebView()
        if (consentNeeded) {
            store.setGdprApplies(1)
            //Start The CMP Dialog.
            val intent = CmpActivity.newIntent(context)
            startActivity(context, intent, null)
        }

    }

    /**
     *  Method to destroy webView after use.
     */
    private fun destroyWebView() {
        val handler = Handler(Looper.getMainLooper())
        handler.post {
            webView.destroy()
        }
    }

    /**
     * Extension function for Boolean to convert Boolean to Int
     */
    private fun Boolean.toInt() = if (this) 1 else 0

}

