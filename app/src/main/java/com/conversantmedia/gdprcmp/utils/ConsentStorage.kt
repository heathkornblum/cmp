package com.conversantmedia.gdprcmp.utils

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

/**
 * Class used used to store IAB defined Consent values in Shared Preferences
 * IAB specification can be found here:
 * https://github.com/InteractiveAdvertisingBureau/GDPR-Transparency-and-Consent-Framework
 *
 * @author Mat Cooper
 * @author Heath Kornblum
 * @version 1.0
 * @updated 04/14/2020
 *
 * @param context the Application context.
 */
class ConsentStorage(context: Context) {

    private val prefs : SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    private val tag = javaClass.simpleName
    /**
     * Method for setting IAB Consent Shared Preference
     *
     * @param preferenceKey String key that the preference will be stored in.
     * @param consentValue  String or Int value to be stored in the shared preference
     */

    private fun setConsentValue(preferenceKey: String, consentValue: Any?){
        when (consentValue) {
            is String ->
                prefs.edit().putString(preferenceKey, consentValue).apply()
            is Int -> {
                prefs.edit().putInt(preferenceKey, consentValue).apply()
            }
        }
    }



    /**
     * Set the IABTCF_CmpSdkID
     *
     * @param sdkId: The unsigned integer ID of CMP SDK
     *
    */

    fun setCmpSdkId(sdkId: Int) {
        setConsentValue("IABTCF_CmpSdkID", sdkId)
    }

    /**
     * Set the IABTCF_CmpSdkVersion
     *
     * @param sdkVersion: The unsigned integer version number of CMP SDK
    */

    fun setCmpSdkVersion(sdkVersion: String) {
        setConsentValue("IABTCF_CmpSdkVersion", sdkVersion)
    }

    /**
     * Set the IABTCF_PolicyVersion
     *
     * @param policyVersion: The unsigned integer representing the version of the TCF that these
     * consents adhere to.
     */

    fun setPolicyVersion(policyVersion: Int) {
        setConsentValue("IABTCF_PolicyVersion", policyVersion)
    }

    /**
     * Set IABTCF_gdprApplies
     *
     *  @param applies:
     *  1 - GDPR applies in current contex
     *  0 - GDPR does not apply in current context
     *  Unset - undetermined (default before initialization)
     *
     *  gdprApplies is a boolean value that may be undefined. A CMP shall determine whether or not
     *  GDPR applies in its current context and set the gdprApplies value. A publisher may
     *  determine that GDPR applies to all traffic on their site and signal their CMP to always
     *  return true for gdprApplies, a CMP may invoke a geo-tagging service call to make a
     *  determination on a specific user or may have some other proprietary solution for
     *  determining whether or not GDPR applies in accordance with TCF Policy. In any case, vendors
     *  shall respect the value of gdprApplies put forth by the CMP. If gdprApplies value is
     *  undefined but exists in the schema outlined in the response object in this document,
     *  then calling scripts shall assume that the CMP is still pending a determination on whether
     *  or not GDPR applies in this context.
     */

    fun setGdprApplies(applies: Int? = null) {
        setConsentValue("IABTCF_gdprApplies", applies)
    }

    /**
     * Set IABTCF_PublisherCC
     * Publisher Country Code
     *
     * @param countryCode: Two-letter ISO 3166-1 alpha-2 code – Default: AA (unknown)
     */

    fun setIabPublisherCc(countryCode: String = "AA") {
        setConsentValue("IABTCF_PublisherCC", countryCode)
    }

    /**
     * Set IABTCF_PurposeOneTreatment
     * @param purposeOne:
     * 0 - no special treatment of purpose one
     * 1 - purpose one not disclosed
     * Unset default - 0
     *
     * Vendors can use this value to determine whether consent for purpose one is required.
     */

    fun setPurposeOneTreatment(purposeOne: Int = 0) {
        setConsentValue("IABTCF_PurposeOneTreatment", purposeOne)
    }

    /**
     * set IABTCF_UseNonStandardStacks
     *
     * @param used:
     * 1 - CMP used a non-standard stack
     * 0 - CMP did not use a non-standard stack
     */

    fun setUseNonStandardStacks(used: Int) {
        setConsentValue("IABTCF_UseNonStandardStacks", used)
    }

    /**
     * set IABTCF_TCString
     *
     * @param tcString: Full encoded TC string
     */

    fun setTcString(tcString: String) {
        setConsentValue("IABTCF_TCString", tcString)
    }

    /**
     * set IABTCF_VendorConsents
     *
     * @param consents
     * Binary String: The '0' or '1' at position n – where n's indexing begins at 0 – indicates the
     * consent status for Vendor ID n+1; false and true respectively. eg. '1' at index 0 is consent
     * true for vendor ID 1
     */

    fun setVendorConsents(vendorConsents: String) {
        setConsentValue("IABTCF_VendorConsents", vendorConsents)
    }

    /**
     * set IABTCF_VendorLegitimateInterests
     *
     * @param vendorLegitimateInterests
     * IABTCF_VendorLegitimateInterests	Binary String: The '0' or '1' at position n – where n's
     * indexing begins at 0 – indicates the legitimate interest status for Vendor ID n+1; false and
     * true respectively. eg. '1' at index 0 is legitimate interest established true for vendor ID 1
     */

    fun setVendorLegitimateInterests(vendorLegitimateInterests: String) {
        setConsentValue("IABTCF_VendorLegitimateInterests", vendorLegitimateInterests)
    }

    /**
     * set IABTCF_PurposeConsents
     *
     * @param purposeConsents
     * Binary String: The '0' or '1' at position n – where n's indexing begins at 0 – indicates the
     * consent status for purpose ID n+1; false and true respectively. eg. '1' at index 0 is
     * consent true for purpose ID 1
     */

    fun setPurposeConsents(purposeConsents: String) {
        setConsentValue("IABTCF_PurposeConsents", purposeConsents)
    }

    /**
     * set IABTCF_PurposeLegitimateInterests
     *
     * @param purposeLegitimateInterests
     * Binary String: The '0' or '1' at position n – where n's indexing begins at 0 – indicates the
     * legitimate interest status for purpose ID n+1; false and true respectively. eg. '1' at index
     * 0 is legitimate interest established true for purpose ID 1
     */

    fun setPurposeLegitimateInterests(purposeLegitimateInterests: String) {
        setConsentValue("IABTCF_PurposeLegitimateInterests", purposeLegitimateInterests)
    }

    /**
     * set IABTCF_SpecialFeaturesOptIns
     *
     * @param specialFeaturesOptIns
     * Binary String: The '0' or '1' at position n – where n's indexing begins at 0 – indicates the
     * opt-in status for special feature ID n+1; false and true respectively. eg. '1' at index 0 is
     * opt-in true for special feature ID 1
     */

    fun setSpecialFeaturesOptIns(specialFeaturesOptIns: String) {
        setConsentValue("IABTCF_SpecialFeaturesOptIns", specialFeaturesOptIns)
    }

    /**
     * set IABTCF_PublisherRestrictions
     *
     * @param id = The Purpose ID
     * @param restriction
     * String ['0','1', or '2']: The value at position n – where n's indexing begins at 0 –
     * the publisher restriction type (0-2) for vendor n+1; (see Publisher Restrictions Types). eg.
     * '2' at index 0 is restrictionType 2 for vendor ID 1. {ID} refers to the purpose ID.
     */

    fun setPublisherRestrictions(purpose: String, restrictions: String) {
        setConsentValue("IABTCF_PublisherRestrictions$purpose", restrictions)
    }

    /**
     * IABTCF_PublisherConsent
     *
     * @param publisherConsent
     * Binary String: The '0' or '1' at position n – where n's indexing begins at 0 – indicates the
     * purpose consent status for purpose ID n+1 for the publisher as they correspond to the Global
     * Vendor List Purposes; false and true respectively. eg. '1' at index 0 is consent true for
     * purpose ID 1
     */

    fun setPublisherConsent(publisherConsent: String) {
        setConsentValue("IABTCF_PublisherConsent", publisherConsent)
    }

    /**
     * set IABTCF_PublisherLegitimateInterests
     *
     * @param publisherLegitimateInterests
     * Binary String: The '0' or '1' at position n – where n's indexing begins at 0 – indicates the
     * purpose legitimate interest status for purpose ID n+1 for the publisher as they correspond
     * to the Global Vendor List Purposes; false and true respectively. eg. '1' at index 0 is
     * legitimate interest established true for purpose ID 1
     */

    fun setPublisherLegitimateInterests(publisherLegitimateInterests: String) {
        setConsentValue("IABTCF_PublisherLegitimateInterests", publisherLegitimateInterests)
    }

    /**
     * set IABTCF_PublisherCustomPurposesConsents
     *
     * @param publisherCustomPurposesConsents
     * Binary String: The '0' or '1' at position n – where n's indexing begins at 0 – indicates the
     * purpose consent status for the publisher's custom purpose ID n+1 for the publisher; false
     * and true respectively. eg. '1' at index 0 is consent true for custom purpose ID 1
     */

    fun setPublisherCustomPurposesConsents(publisherCustomPurposesConsents: String) {
        setConsentValue("IABTCF_PublisherCustomPurposesConsents", publisherCustomPurposesConsents)
    }

    /**
     * set IABTCF_PublisherCustomPurposesLegitimateInterests
     *
     * @param publisherCustomPurposesLegitimateInterests
     * Binary String: The '0' or '1' at position n – where n's indexing begins at 0 – indicates the
     * purpose legitimate interest status for the publisher's custom purpose ID n+1 for the
     * publisher; false and true respectively. eg. '1' at index 0 is legitimate interest
     * established true for custom purpose ID 1
     */

    fun setPublisherCustomPurposesLegitimateInterests(publisherCustomPurposesLegitimateInterests: String) {
        setConsentValue("IABTCF_PublisherCustomPurposesLegitimateInterests", publisherCustomPurposesLegitimateInterests)
    }

    /**
     * Conversant Only Below
     */

    /**
     * The config version is assigned to the InApp.storedVersion member of the publisher config.
     * It is compared by the CMP to the top level config version when making the determination to
     * show the CMP UI.  The CNVR_ prefix denotes that this value is used by Conversant only.
     */
    fun setConfigVersion(configVersion: String) {
        setConsentValue("CNVR_PublisherConfigVersion", configVersion)
    }


}