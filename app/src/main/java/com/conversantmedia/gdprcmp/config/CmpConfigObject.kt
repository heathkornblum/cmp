package com.conversantmedia.gdprcmp.config

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.conversantmedia.gdprcmp.utils.AdvertisingId
import com.conversantmedia.gdprcmp.utils.ManifestReader
import org.json.JSONArray
import org.json.JSONObject
import kotlin.math.absoluteValue

/**
 * Configuration object singleton.  This holds the Config structure and provides a means of writing
 * itself out as JSON.
 *
 * @author Mat Cooper
 * @author Heath Kornblum
 * @version 1.0
 * @updated 04/14/2020
 */

internal object CmpConfigObject {

    private val TAG = javaClass.simpleName

    /**
     * Method for populating config data from values set int the app manifest.
     *
     * @param context application context
     */
    fun buildConfigFromManifest(context: Context) {
        ManifestReader(context).getConfigFromManifest()
    }

    /**
     * Method(s) for populating value(s) which require(s) coroutine suspension
     */
    private suspend fun applyDeviceId(context: Context){
        // Populate Device ID Now -- Here lies the suspending function call
        val advertisingId = AdvertisingId(context)
        CmpConfig.InApp.deviceId = advertisingId.getAdvertisingID()

    }

    /**
     * Method for populating the config based on Shared Preferences values
     *
     * @param context
     *
     */
    private fun applySharedPreferencesToConfig(context: Context) {
        val prefs : SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        CmpConfig.InApp.tcString = prefs.getString("IABTCF_TCString", "")
        CmpConfig.InApp.storedVersion = prefs.getString("CNVR_PublisherConfigVersion","")
    }



    /**
     * Function that creates JSON from Config object. Suspended function allows for Advertiser ID
     * retrieval.
     *
     * @return JSON of the config to be injected into the WebView.
     */

    suspend fun getConfigString(context: Context): JSONObject {

        applySharedPreferencesToConfig(context)

        applyDeviceId(context)

        val configJson = JSONObject()
        val gvlJson = JSONObject()
        val customUIJson = JSONObject()
        val inAppJson = JSONObject()
        val legalBasesJson = JSONObject()
        val legalBasesPurpose = JSONObject()
        val textJson = JSONObject()
        val landingText = JSONObject()
        val landingTextBody = JSONObject()
        val seeMore = JSONObject()
        val reviewText = JSONObject()
        val reviewTabs = JSONObject()
        val reviewTabsCompanies = JSONObject()
        val reviewTabsCompaniesHeader = JSONObject()
        val reviewTabsPurposes = JSONObject()
        val reviewTabsPurposesHeader = JSONObject()
        val reviewTabsPurposesLabel = JSONObject()
        val reviewTabsPurposesLegalDescription = JSONObject()
        val reviewTabsPurposesCompanyList = JSONObject()
        val reviewButtons = JSONObject()
        val reviewFooter = JSONObject()

        // Custom Global Vendor List *FOR INTERNAL USE ONLY*
        CmpConfig.GVL.latest?.let { gvlJson.put("latest", it) }
        CmpConfig.GVL.versioned?.let { gvlJson.put("versioned", it) }
        CmpConfig.GVL.baseUrl?.let { gvlJson.put("baseUrl", it) }
        if (gvlJson.length() > 0) configJson.put("GVL", gvlJson)

        // Custom UI
        CmpConfig.CustomUI.backgroundColor?.let { customUIJson.put("backgroundColor", it) }
        CmpConfig.CustomUI.borderRadiusButton?.let { customUIJson.put("borderRadiusButton", it) }
        CmpConfig.CustomUI.linkColor?.let { customUIJson.put("linkColor", it) }
        CmpConfig.CustomUI.primaryColor?.let { customUIJson.put("primaryColor", it) }
        CmpConfig.CustomUI.textColor?.let { customUIJson.put("textColor", it) }
        if (customUIJson.length() > 0) configJson.put("customUI", customUIJson)

        // In App Config
        CmpConfig.InApp.deviceId?.let { inAppJson.put("deviceId", it) }
        CmpConfig.InApp.storedVersion?.let { inAppJson.put("storedVersion", it) }
        CmpConfig.InApp.tcString?.let { inAppJson.put("tcstring", it) }
        if (inAppJson.length() > 0 ) configJson.put("inAppConfig", inAppJson)

        // Legal Bases
        CmpConfig.LegalBases.feature?.let { legalBasesJson.put("feature", it) }
        CmpConfig.LegalBases.purposeConsent?.let { legalBasesPurpose.put("consent", it) }
        CmpConfig.LegalBases.purposeLegitimateInterest?.let { legalBasesPurpose.put("legitimateInterest", it) }
        CmpConfig.LegalBases.specialFeature?.let { legalBasesJson.put("specialFeature", it) }
        CmpConfig.LegalBases.specialPurpose?.let { legalBasesJson.put("specialPurpose", it) }
        if (legalBasesPurpose.length() > 0) legalBasesJson.put("purpose", legalBasesPurpose)
        if (legalBasesJson.length() > 0) configJson.put("legalBases", legalBasesJson)

        // Text
        CmpConfig.Text.Landing.Body.p1?.let { landingTextBody.put("p1", it) }
        CmpConfig.Text.Landing.Body.p2?.let { landingTextBody.put("p2", it) }
        CmpConfig.Text.Landing.cta?.let { landingText.put("cta", it) }
        CmpConfig.Text.Landing.reviewLink?.let { landingText.put("reviewLink", it) }
        CmpConfig.Text.Landing.SeeMoreLink.seeMore?.let { seeMore.put("seeMore", it) }
        CmpConfig.Text.Landing.SeeMoreLink.seeLess?.let { seeMore.put("seeLess", it) }
        CmpConfig.Text.Landing.title?.let { landingText.put("title", it) }
        CmpConfig.Text.Review.Tabs.Companies.Header.consent?.let { reviewTabsCompaniesHeader.put("consent", it) }
        CmpConfig.Text.Review.Tabs.Companies.Header.feature?.let { reviewTabsCompaniesHeader.put("feature", it) }
        CmpConfig.Text.Review.Tabs.Companies.Header.legInt?.let { reviewTabsCompaniesHeader.put("legInt", it) }
        CmpConfig.Text.Review.Tabs.Companies.Header.specialFeature?.let { reviewTabsCompaniesHeader.put("specialFeature", it) }
        CmpConfig.Text.Review.Tabs.Companies.Header.specialPurpose?.let { reviewTabsCompaniesHeader.put("specialPurpose", it) }
        CmpConfig.Text.Review.Tabs.Companies.privacyPolicyLink?.let { reviewTabsCompanies.put("privacyPolicyLink", it) }
        CmpConfig.Text.Review.Tabs.Companies.tabTitle?.let { reviewTabsCompanies.put("tabTitle", it) }
        CmpConfig.Text.Review.Tabs.Purposes.Header.consent?.let { reviewTabsPurposesHeader.put("consent", it) }
        CmpConfig.Text.Review.Tabs.Purposes.Header.feature?.let { reviewTabsPurposesHeader.put("feature", it) }
        CmpConfig.Text.Review.Tabs.Purposes.Header.legInt?.let { reviewTabsPurposesHeader.put("legInt", it) }
        CmpConfig.Text.Review.Tabs.Purposes.Header.specialFeature?.let { reviewTabsPurposesHeader.put("specialFeature", it) }
        CmpConfig.Text.Review.Tabs.Purposes.Header.specialPurpose?.let { reviewTabsPurposesHeader.put("specialPurpose", it) }
        CmpConfig.Text.Review.Tabs.Purposes.Label.feature?.let { reviewTabsPurposesLabel.put("feature", it) }
        CmpConfig.Text.Review.Tabs.Purposes.Label.purpose?.let { reviewTabsPurposesLabel.put("purpose", it) }
        CmpConfig.Text.Review.Tabs.Purposes.Label.specialFeature?.let { reviewTabsPurposesLabel.put("specialFeature", it) }
        CmpConfig.Text.Review.Tabs.Purposes.Label.specialPurpose?.let { reviewTabsPurposesLabel.put("specialPurpose", it) }
        CmpConfig.Text.Review.Tabs.Purposes.LegalDescription.hide?.let { reviewTabsPurposesLegalDescription.put("hide", it) }
        CmpConfig.Text.Review.Tabs.Purposes.LegalDescription.show?.let { reviewTabsPurposesLegalDescription.put("show", it) }
        CmpConfig.Text.Review.Tabs.Purposes.CompanyList.hide?.let { reviewTabsPurposesCompanyList.put("hide", it) }
        CmpConfig.Text.Review.Tabs.Purposes.CompanyList.show?.let { reviewTabsPurposesCompanyList.put("show", it) }
        CmpConfig.Text.Review.Tabs.Purposes.CompanyList.no?.let { reviewTabsPurposesCompanyList.put("no", it) }
        CmpConfig.Text.Review.Tabs.Purposes.tabTitle?.let { reviewTabsPurposes.put("tabTitle", it) }
        CmpConfig.Text.Review.Buttons.allow?.let { reviewButtons.put("allow", it) }
        CmpConfig.Text.Review.Buttons.deny?.let { reviewButtons.put("deny", it) }
        CmpConfig.Text.Review.Buttons.optOut?.let { reviewButtons.put("optOut", it) }
        CmpConfig.Text.Review.Footer.allowAll?.let { reviewFooter.put("allowAll", it) }
        CmpConfig.Text.Review.Footer.denyAll?.let { reviewFooter.put("denyAll", it) }
        CmpConfig.Text.Review.Footer.cta?.let { reviewFooter.put("cta", it) }
        CmpConfig.Text.Review.introBody?.let { reviewText.put("introBody", it) }

        // Assemble Landing Text Object
        if (landingTextBody.length() > 0) landingText.put("body", landingTextBody)
        if (seeMore.length() > 0) landingText.put("seeMoreLink", seeMore)
        if (landingText.length() > 0) textJson.put("landing", landingText)

        // Assemble Review Text Object
        if (reviewTabsCompaniesHeader.length() > 0) reviewTabsCompanies.put("header", reviewTabsCompaniesHeader)
        if (reviewTabsPurposesCompanyList.length() > 0) reviewTabsPurposes.put("companyList", reviewTabsPurposesCompanyList)
        if (reviewTabsPurposesHeader.length() > 0) reviewTabsPurposes.put("header", reviewTabsPurposesHeader)
        if (reviewTabsPurposesLabel.length() > 0) reviewTabsPurposes.put("label", reviewTabsPurposesLabel)
        if (reviewTabsPurposesLegalDescription.length() > 0) reviewTabsPurposes.put("legalDescription", reviewTabsPurposesLegalDescription)
        if (reviewTabsCompanies.length() > 0) reviewTabs.put("companies", reviewTabsCompanies)
        if (reviewTabsPurposes.length() > 0) reviewTabs.put("purposes", reviewTabsPurposes)
        if (reviewTabs.length() > 0) reviewText.put("tabs", reviewTabs)
        if (reviewButtons.length() > 0) reviewText.put("buttons", reviewButtons)
        if (reviewFooter.length() > 0) reviewText.put("footer", reviewFooter)

        if (reviewText.length() > 0) textJson.put("review", reviewText)
        if (textJson.length() > 0) configJson.put("text", textJson)

        // Top Level Values
        CmpConfig.bannerLayout.let { configJson.put("bannerLayout", it) }
        CmpConfig.brandingImg?.let { configJson.put("brandingImg", it) }
        CmpConfig.countryCode?.let { configJson.put("countryCode", it) }
        CmpConfig.cssOverride?.let { configJson.put("cssOverride", it) }
        CmpConfig.gdprAppliesGlobally?.let { configJson.put("gdprAppliesGlobally", it) }
        CmpConfig.id?.let { configJson.put("id", it) }
        CmpConfig.isServiceSpecific?.let { configJson.put("isServiceSpecific", it) }
        CmpConfig.language?.let { configJson.put("lang", it) }
        CmpConfig.legalName?.let { configJson.put("legalName", it) }
        CmpConfig.minShowDays?.let { configJson.put("minShowDays", it) }
        CmpConfig.onGVL?.let { configJson.put("onGVL", it) }
        CmpConfig.policyUrl?.let { configJson.put("policyUrl", it) }
        CmpConfig.vendors?.let { configJson.put("vendors", it) }
        CmpConfig.version?.let { configJson.put("version", it) }

        return configJson
    }

    /**
     * Object for managing cmp config data.
     * required fields will have some/empty value
     */
     object CmpConfig {

        var bannerLayout = true // This setting removes box shadow
        var brandingImg: String? = null
        var countryCode: String? = null
        var cssOverride: String? = null
        var gdprAppliesGlobally: Boolean? = null
        var id: Int? = System.currentTimeMillis().toInt().absoluteValue
        var isServiceSpecific: Boolean? = true // always true for mobile
        var language: String? = null
        var legalName: String? = null
        var minShowDays: Int? = null
        var onGVL: Boolean? = null
        var policyUrl: String? = null
        var vendors: JSONArray? = null
        var version: String? = null

        object CustomUI {
            var backgroundColor: String? = null
            var borderRadiusButton: String? = null
            var linkColor: String? = null
            var primaryColor: String? = null
            var textColor: String? = null
        }

        object InApp {
            var deviceId: String? = null
            var storedVersion: String? = ""
            var tcString: String? = ""
        }

        object LegalBases {
            var feature: JSONArray? = null
            var purposeConsent: JSONArray? = null
            var purposeLegitimateInterest: JSONArray? = null
            var specialFeature: JSONArray? = null
            var specialPurpose: JSONArray? = null
        }

        object Text {
            object Landing {
                object Body {
                    var p1: String? = null
                    var p2: String? = null
                }
                var cta: String? = null
                var reviewLink: String? = null
                object SeeMoreLink {
                    var seeMore: String? = null
                    var seeLess: String? = null
                }
                var title: String? = null
            }

            object Review {
                object Tabs {
                    object Companies {
                        object Header {
                            var consent: String? = null
                            var feature: String? = null
                            var legInt: String? = null
                            var specialPurpose: String? = null
                            var specialFeature: String? = null
                        }
                        var privacyPolicyLink: String? = null
                        var tabTitle: String? = null
                    }
                    object Purposes {
                        object Header {
                            var consent: String? = null
                            var feature: String? = null
                            var legInt: String? = null
                            var specialFeature: String? = null
                            var specialPurpose: String? = null
                        }
                        object Label {
                            var feature: String? = null
                            var purpose: String? = null
                            var specialFeature: String? = null
                            var specialPurpose: String? = null
                        }
                        object LegalDescription {
                            var hide: String? = null
                            var show: String? = null
                        }
                        object CompanyList {
                            var hide: String? = null
                            var no: String? = null
                            var show: String? = null
                        }
                        var tabTitle: String? = null
                    }
                }
                object Buttons {
                    var allow: String? = null
                    var deny: String? = null
                    var optOut: String? = null
                }
                object Footer {
                    var allowAll: String? = null
                    var cta: String? = null
                    var denyAll: String? = null
                }
                var introBody: String? = null
            }
        }


        // Internal Use Only, GVL Override
        object GVL {
            var baseUrl: String? = null
            var latest: String? = null
            var versioned: String? = null
        }

    }
}